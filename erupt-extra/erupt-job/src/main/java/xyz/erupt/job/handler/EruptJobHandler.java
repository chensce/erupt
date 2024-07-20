package xyz.erupt.job.handler;

import xyz.erupt.annotation.config.Comment;

/**
 * @author YuePeng
 * date 2019-12-26
 */
public interface EruptJobHandler {

    @Comment("任务名称")
    default String name() {
        return this.getClass().getSimpleName();
    }

    @Comment("任务处理方法体")
    String exec(@Comment("任务编码") String code, @Comment("任务参数") String param);

    default String cron() {
        return "0 0 1 * * ?";
    }

    default String param() {
        return null;
    }

    @Comment("任务执行成功时调用")
    default void success(@Comment("执行结果") String result, @Comment("任务参数") String param) {
    }

    @Comment("任务执行失败时调用")
    default void error(@Comment("异常信息") Throwable throwable, @Comment("任务参数") String param) {
    }

}
