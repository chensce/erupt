package xyz.erupt.flow.service.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.flow.bean.entity.*;
import xyz.erupt.flow.constant.FlowConstant;
import xyz.erupt.flow.process.listener.AfterCreateInstanceListener;
import xyz.erupt.flow.process.listener.AfterFinishInstanceListener;
import xyz.erupt.flow.process.listener.AfterStopInstanceListener;
import xyz.erupt.flow.process.listener.ExecutableNodeListener;
import xyz.erupt.flow.service.*;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.model.EruptUser;
import xyz.erupt.upms.service.EruptUserService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@Slf4j
public class ProcessInstanceServiceImpl
        implements ProcessInstanceService, DataProxy<OaProcessInstance> {

    private Map<Class<ExecutableNodeListener>, List<ExecutableNodeListener>> listenerMap = new HashMap<>();

    @Autowired
    private EruptUserService eruptUserService;
    @Autowired
    private ProcessInstanceHistoryService processInstanceHistoryService;
    @Lazy
    @Autowired
    private TaskHistoryService taskHistoryService;
    @Autowired
    private TaskOperationService taskOperationService;
    @Autowired
    private TaskUserLinkService taskUserLinkService;
    @Lazy
    @Autowired
    private ProcessDefinitionService processDefinitionService;
    @Lazy
    @Autowired
    private TaskService taskService;

    @Resource
    private EruptDao eruptDao;

    /**
     * 启动新的流程实例（instance）
     *
     * @param processDef
     * @param content    表单内容
     * @return
     */
    @Override
    public OaProcessInstance newProcessInstance(OaProcessDefinition processDef, String content) {
        /**
         * 通过建造者创建实例
         */
        EruptUser currentEruptUser = eruptUserService.getCurrentEruptUser();
        Date now = new Date();
        OaProcessInstance inst = OaProcessInstance.builder()
                .processDefId(String.valueOf(processDef.getId()))
                .formId(processDef.getFormId())
                .formName(processDef.getFormName())
                .businessKey(processDef.getId() + "_business_key")
                .businessTitle(currentEruptUser.getName() + "的《" + processDef.getFormName() + "》工单")
                .status(OaProcessInstance.RUNNING)//直接运行状态
                .creator(currentEruptUser.getAccount())
                .creatorName(currentEruptUser.getName())
                .createDate(now)
                .formItems(content)
                .process(processDef.getProcess())
                .build();
        //保存数据
        eruptDao.persist(inst);
        //保存历史数据
        processInstanceHistoryService.copyAndSave(inst);

        //触发所有创建后监听器
        this.listenerMap.get(AfterCreateInstanceListener.class).forEach(l -> l.execute(inst));

        //手动完成所有开始任务
        List<OaTask> tasks = taskService.listByInstanceId(inst.getId());
        tasks.forEach(t -> taskService.complete(t.getId(), "开始节点自动完成", null));

        return inst;
    }

    @Override
    @Transactional
    public void finish(Long processInstId) {
        OaProcessInstance inst = OaProcessInstance.builder()
                .status(OaProcessInstance.FINISHED)
                .finishDate(new Date())
                .build();
        inst.setId(processInstId);
        processInstanceHistoryService.copyAndSave(inst);//同步更新历史表
        eruptDao.delete(new OaProcessInstance() {{
            this.setId(processInstId);
        }});
        //触发结束后置事件
        this.listenerMap.get(AfterFinishInstanceListener.class).forEach(l -> l.execute(inst));
    }

    /**
     * 停止流程实例
     *
     * @param instId
     * @param remarks
     */
    @Override
    public void stop(Long instId, String remarks) {
        OaProcessInstance inst = OaProcessInstance.builder()
                .status(OaProcessInstance.SHUTDOWN)
                .finishDate(new Date())
                .reason(remarks)
                .build();
        inst.setId(instId);
        processInstanceHistoryService.copyAndSave(inst);//同步更新历史表
        eruptDao.delete(new OaProcessInstance() {{
            this.setId(instId);
        }}); //删除运行时表
        //触发终止后置事件
        this.listenerMap.get(AfterStopInstanceListener.class).forEach(l -> l.execute(inst));
    }

    /**
     * 查询与我相关的实例
     *
     * @param keywords
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<OaProcessInstanceHistory> getMineAbout(String keywords, int pageNum, int pageSize) {
        String account = eruptUserService.getCurrentAccount();
        //查询我处理过的所有任务（发起和审批的）
        List<OaTaskOperation> operations = taskOperationService.listByOperator(account);
        //查询抄送我的所有任务
        List<OaTaskUserLink> links = taskUserLinkService.listByUserIds(Collections.singletonList(account));

        Set<Long> taskIds = operations.stream().map(OaTaskOperation::getTaskId).collect(Collectors.toSet());
        Set<Long> linkTaskIds = links.stream().map(OaTaskUserLink::getTaskId).collect(Collectors.toSet());

        if (CollectionUtils.isEmpty(taskIds) && CollectionUtils.isEmpty(linkTaskIds)) {
            return new ArrayList<>(0);
        }
        Set<Long> allTaskIds = new HashSet<>();
        allTaskIds.addAll(taskIds);
        allTaskIds.addAll(linkTaskIds);
        List<OaTaskHistory> tasks = eruptDao.lambdaQuery(OaTaskHistory.class).in(OaTaskHistory::getId, allTaskIds).list();
        if (CollectionUtils.isEmpty(tasks)) {
            return new ArrayList<>();
        }
        //根据任务id查询流程实例
        eruptDao.lambdaQuery(OaProcessInstanceHistory.class);
        List<OaProcessInstanceHistory> list = eruptDao.lambdaQuery(OaProcessInstanceHistory.class)
                .in(OaProcessInstanceHistory::getId, tasks.stream().map(OaTaskHistory::getProcessInstId).collect(Collectors.toSet()))
                .orderByDesc(OaProcessInstanceHistory::getCreateDate)
                .limit(pageSize).offset(pageNum * pageSize)
                .list();
        //查询所有对应的流程定义
        List<OaProcessDefinition> procDefs = eruptDao.lambdaQuery(OaProcessDefinition.class).in(OaProcessDefinition::getId, list.stream().map(OaProcessInstanceHistory::getProcessDefId).collect(Collectors.toSet())).list();
        Map<String, OaProcessDefinition> procDefMap = procDefs.stream()
                .collect(Collectors.toMap(OaProcessDefinition::getId, e -> e, (key1, key2) -> key1));
        //循环设置logo
        list.forEach(l -> l.setLogo(procDefMap.get(l.getProcessDefId()).getLogo()));

        //先把所有任务分类
        Map<Long, OaTaskHistory> taskMap
                = tasks.stream().collect(Collectors.toMap(OaTaskHistory::getProcessInstId, e -> {
                    //最优先发起
                    if (FlowConstant.NODE_TYPE_ROOT.equals(e.getTaskType())) {
                        e.setTag("发起");
                        return e;
                    } else if (FlowConstant.NODE_TYPE_APPROVAL.equals(e.getTaskType())) {
                        e.setTag("审批");
                        return e;
                    } else if (linkTaskIds.contains(e.getId())) {
                        e.setTag("抄送");
                        return e;
                    }
                    e.setTag("审批");
                    return e;
                }, (v1, v2) -> {
                    //最优先发起
                    if ("发起".equals(v1.getTag())) {
                        return v1;
                    } else if ("发起".equals(v2.getTag())) {
                        return v2;
                    }
                    //其次是审批
                    if ("审批".equals(v1.getTag())) {
                        return v1;
                    } else if ("审批".equals(v2.getTag())) {
                        return v2;
                    }
                    //其次是审批
                    if ("抄送".equals(v1.getTag())) {
                        return v1;
                    } else if ("抄送".equals(v2.getTag())) {
                        return v2;
                    }
                    return v1;
                }
        ));
        //分类打标记
        taskMap.forEach((instId, t) -> {
            for (OaProcessInstanceHistory inst : list) {
                if (inst.getId().equals(instId)) {
                    inst.setTag(t.getTag());
                }
            }
        });
        return list;
    }

    @Override
    public void updateDataById(Long processInstId, String content) {
        OaProcessInstance inst = OaProcessInstance.builder()
                .formItems(content)
                .build();
        inst.setId(processInstId);
        processInstanceHistoryService.copyAndSave(inst);//同步更新历史表
        eruptDao.persist(inst);
    }

    @Override
    public OaProcessInstance getById(Long id) {
        return eruptDao.findById(OaProcessInstance.class, id);
    }

    @Override
    public long countByProcessDefinitionId(String procDefId) {
        return eruptDao.lambdaQuery(OaProcessInstance.class).eq(OaProcessInstance::getProcessDefId, procDefId).count();
    }

    @Override
    public long countByFormId(Long formId) {
        return eruptDao.lambdaQuery(OaProcessInstance.class).eq(OaProcessInstance::getFormId, formId).count();
    }
}
