package xyz.erupt.flow.bean.entity.node;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.flow.constant.FlowConstant;

import java.util.List;

@Getter
@Setter
public class OaProcessNodeNobody {
    /**
     * 处理模式
     */
    String handler = FlowConstant.NOBODY_TO_PASS;
    /**
     * 如果是指定处理人，需要此字段
     */
    List<OaProcessNodeAssign> assignedUser;
}
