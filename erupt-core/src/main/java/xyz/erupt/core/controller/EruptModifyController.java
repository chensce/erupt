package xyz.erupt.core.controller;import com.google.gson.Gson;import com.google.gson.JsonObject;import lombok.RequiredArgsConstructor;import lombok.SneakyThrows;import lombok.extern.slf4j.Slf4j;import org.apache.commons.lang3.StringUtils;import org.springframework.context.annotation.Lazy;import org.springframework.web.bind.annotation.*;import xyz.erupt.annotation.SceneEnum;import xyz.erupt.annotation.fun.PowerObject;import xyz.erupt.annotation.sub_erupt.LinkTree;import xyz.erupt.core.annotation.EruptRecordOperate;import xyz.erupt.core.annotation.EruptRouter;import xyz.erupt.core.config.GsonFactory;import xyz.erupt.core.constant.EruptRestPath;import xyz.erupt.core.exception.EruptApiErrorTip;import xyz.erupt.core.exception.EruptWebApiRuntimeException;import xyz.erupt.core.invoke.DataProcessorManager;import xyz.erupt.core.invoke.DataProxyInvoke;import xyz.erupt.core.naming.EruptRecordNaming;import xyz.erupt.core.service.EruptCoreService;import xyz.erupt.core.service.EruptService;import xyz.erupt.core.service.IEruptDataService;import xyz.erupt.core.util.EruptUtil;import xyz.erupt.core.util.Erupts;import xyz.erupt.core.util.ReflectUtil;import xyz.erupt.core.view.EruptApiModel;import xyz.erupt.core.view.EruptModel;import javax.annotation.Resource;import javax.servlet.http.HttpServletRequest;import javax.transaction.Transactional;import java.util.ArrayList;import java.util.HashMap;import java.util.List;import java.util.Map;/** * Erupt 对数据的增删改查 * * @author YuePeng * date 9/28/18. */@Slf4j@RestController@RequestMapping(EruptRestPath.ERUPT_DATA_MODIFY)@RequiredArgsConstructorpublic class EruptModifyController {    private final EruptService eruptService;    private final HttpServletRequest request;    private static final Gson gson = GsonFactory.getGson();    @Resource    @Lazy    private EruptModifyController eruptModifyController;    @PostMapping({"/{erupt}"})    @EruptRecordOperate(value = "新增", dynamicConfig = EruptRecordNaming.class)    @EruptRouter(skipAuthIndex = 3, authIndex = 1, verifyType = EruptRouter.VerifyType.ERUPT)    public EruptApiModel addEruptDataCtrl(@PathVariable("erupt") String erupt, @RequestBody JsonObject data) {        return eruptModifyController.addEruptData(erupt, data, null);    }    @SneakyThrows    @Transactional    public EruptApiModel addEruptData(String erupt, JsonObject data, Map<String, Object> extraData) {        EruptModel eruptModel = EruptCoreService.getErupt(erupt);        Erupts.powerLegal(eruptModel, PowerObject::isAdd);        this.setLinkValue(eruptModel, extraData);        EruptApiModel eruptApiModel = EruptUtil.validateEruptValue(eruptModel, data);        if (eruptApiModel.getStatus() == EruptApiModel.Status.ERROR) return eruptApiModel;        Object obj = EruptUtil.jsonToEruptEntity(eruptModel, data, extraData);        DataProxyInvoke.invoke(eruptModel, (dataProxy -> dataProxy.beforeAdd(obj)));        DataProcessorManager.getEruptDataProcessor(eruptModel.getClazz()).addData(eruptModel, obj);        this.modifyLog(eruptModel, "ADD", data.toString());        DataProxyInvoke.invoke(eruptModel, (dataProxy -> dataProxy.afterAdd(obj)));        return EruptApiModel.successApi();    }    @SneakyThrows    public void batchAddEruptData(EruptModel eruptModel, List<JsonObject> data) {        List<Object> objectList = new ArrayList<>();        for (JsonObject item : data) {            EruptApiModel eruptApiModel = EruptUtil.validateEruptValue(eruptModel, item);            if (eruptApiModel.getStatus() == EruptApiModel.Status.ERROR) throw new EruptApiErrorTip(eruptApiModel);            Object obj = EruptUtil.jsonToEruptEntity(eruptModel, item, null);            DataProxyInvoke.invoke(eruptModel, (dataProxy -> dataProxy.beforeAdd(obj)));            objectList.add(obj);        }        DataProcessorManager.getEruptDataProcessor(eruptModel.getClazz()).batchAddData(eruptModel, objectList);        for (Object obj : objectList) DataProxyInvoke.invoke(eruptModel, (dataProxy -> dataProxy.afterAdd(obj)));    }    private void setLinkValue(EruptModel eruptModel, Map<String, Object> extraData) {        LinkTree dependTree = eruptModel.getErupt().linkTree();        if (StringUtils.isNotBlank(dependTree.field()) && dependTree.dependNode()) {            String linkVal = request.getHeader("link");            //必须是强依赖才会自动注入值            if (dependTree.dependNode()) {                if (StringUtils.isBlank(linkVal)) {                    throw new EruptWebApiRuntimeException("Place select tree node");                } else {                    if (null == extraData) extraData = new HashMap<>();                    String rm = ReflectUtil.findClassField(eruptModel.getClazz(), dependTree.field()).getType().getSimpleName();                    JsonObject sub = new JsonObject();                    sub.addProperty(EruptCoreService.getErupt(rm).getErupt().primaryKeyCol(), linkVal);                    extraData.put(dependTree.field(), sub);                }            }        }    }    @PostMapping("/{erupt}/update")    @EruptRecordOperate(value = "Update", dynamicConfig = EruptRecordNaming.class)    @EruptRouter(skipAuthIndex = 3, authIndex = 1, verifyType = EruptRouter.VerifyType.ERUPT)    @Transactional    public EruptApiModel updateEruptData(@PathVariable("erupt") String erupt, @RequestBody JsonObject data) throws IllegalAccessException {        return this.putUpdateEruptData(erupt, data);    }    @PostMapping("/{erupt}/delete")    @EruptRouter(skipAuthIndex = 3, authIndex = 1, verifyType = EruptRouter.VerifyType.ERUPT)    @EruptRecordOperate(value = "Delete", dynamicConfig = EruptRecordNaming.class)    public EruptApiModel deleteEruptData(@PathVariable("erupt") String erupt, @RequestBody String[] ids) {        return this.deleteEruptDataList(erupt, ids);    }    @Deprecated    @PutMapping("/{erupt}")    @EruptRecordOperate(value = "Update", dynamicConfig = EruptRecordNaming.class)    @EruptRouter(skipAuthIndex = 3, authIndex = 1, verifyType = EruptRouter.VerifyType.ERUPT)    @Transactional    public EruptApiModel putUpdateEruptData(@PathVariable("erupt") String erupt, @RequestBody JsonObject data) throws IllegalAccessException {        EruptModel eruptModel = EruptCoreService.getErupt(erupt);        Erupts.powerLegal(eruptModel, PowerObject::isEdit);        EruptApiModel eruptApiModel = EruptUtil.validateEruptValue(eruptModel, data);        if (eruptApiModel.getStatus() == EruptApiModel.Status.ERROR) return eruptApiModel;        eruptService.verifyIdPermissions(eruptModel, data.get(eruptModel.getErupt().primaryKeyCol()).getAsString());        Object o = gson.fromJson(data.toString(), eruptModel.getClazz());        EruptUtil.clearObjectDefaultValueByJson(o, data);        Object obj = EruptUtil.dataTarget(eruptModel, o, DataProcessorManager.getEruptDataProcessor(eruptModel.getClazz())                .findDataById(eruptModel, ReflectUtil.findClassField(eruptModel.getClazz(), eruptModel.getErupt().primaryKeyCol()).get(o)), SceneEnum.EDIT);        DataProxyInvoke.invoke(eruptModel, (dataProxy -> dataProxy.beforeUpdate(obj)));        DataProcessorManager.getEruptDataProcessor(eruptModel.getClazz()).editData(eruptModel, obj);        this.modifyLog(eruptModel, "EDIT", data.toString());        DataProxyInvoke.invoke(eruptModel, (dataProxy -> dataProxy.afterUpdate(obj)));        return EruptApiModel.successApi();    }    @Deprecated    @DeleteMapping("/{erupt}/{id}")    @EruptRecordOperate(value = "Delete", dynamicConfig = EruptRecordNaming.class)    @EruptRouter(skipAuthIndex = 3, authIndex = 1, verifyType = EruptRouter.VerifyType.ERUPT)    @Transactional    public EruptApiModel deleteEruptData(@PathVariable("erupt") String erupt, @PathVariable("id") String id) {        EruptModel eruptModel = EruptCoreService.getErupt(erupt);        Erupts.powerLegal(eruptModel, PowerObject::isDelete);        eruptService.verifyIdPermissions(eruptModel, id);        IEruptDataService dataService = DataProcessorManager.getEruptDataProcessor(eruptModel.getClazz());        //获取对象数据信息用于DataProxy函数中        Object obj = dataService.findDataById(eruptModel, EruptUtil.toEruptId(eruptModel, id));        DataProxyInvoke.invoke(eruptModel, (dataProxy -> dataProxy.beforeDelete(obj)));        dataService.deleteData(eruptModel, obj);        this.modifyLog(eruptModel, "DELETE", id);        DataProxyInvoke.invoke(eruptModel, (dataProxy -> dataProxy.afterDelete(obj)));        return EruptApiModel.successApi();    }    @Deprecated    @DeleteMapping("/{erupt}")    @EruptRouter(skipAuthIndex = 3, authIndex = 1, verifyType = EruptRouter.VerifyType.ERUPT)    @EruptRecordOperate(value = "批量删除", dynamicConfig = EruptRecordNaming.class)    public EruptApiModel deleteEruptDataList(@PathVariable("erupt") String erupt, @RequestParam("ids") String[] ids) {        EruptApiModel eruptApiModel = EruptApiModel.successApi();        for (String id : ids) {            eruptApiModel = this.deleteEruptData(erupt, id);            if (eruptApiModel.getStatus() == EruptApiModel.Status.ERROR) break;        }        return eruptApiModel;    }    private void modifyLog(EruptModel eruptModel, String placeholder, String content) {        log.info("[" + eruptModel.getEruptName() + " -> " + placeholder + "]:" + content);    }}