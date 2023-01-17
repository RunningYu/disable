package com.life.square.mq;

import com.life.square.constants.MqConstants;
import com.life.square.service.IVideoService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 用 Work queue（设置两个队），工作队列，可以提高消息处理速度，避免队列消息堆积
 */
@Component
public class DiaryVideoListener {

    @Autowired
    private IVideoService videoService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 异步处理&MQ监听整合
     * @param message
     */
    //监听 动态的 新增 or 修改 的业务
    //定义消费者
    @RabbitListener(queues = MqConstants.DIARYVIDEO_INSERT_QUEUE)
    @Async
    public void listenDiaryVideosInsertOrUpdate1(String message) {          //队列1
        System.out.println("listenDiaryVideosInsertOrUpdate1");
        if (StringUtils.isNotBlank( message )){
            System.out.println("  添加视频啊---》");
            videoService.insertDiaryVideosToIndexById(message);
            System.out.println("-----------【队列1】DIARYVIDEO_INSERT_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",MqConstants.DIARYVIDEO_INSERT_QUEUE,message);
        }
    }

    @RabbitListener(queues = MqConstants.DIARYVIDEO_INSERT_QUEUE)
    @Async
    public void listenDiaryVideosInsertOrUpdate2(String message) {          //队列1
        System.out.println("listenLikeVideosInsertOrUpdate2");
        if (StringUtils.isNotBlank( message )){
            System.out.println("  添加视频啊---》");
            videoService.insertDiaryVideosToIndexById(message);
            System.out.println("-----------【队列2】DIARYVIDEO_INSERT_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",MqConstants.DIARYVIDEO_INSERT_QUEUE,message);
        }
    }


    /**
     * 监听 动态的视频 删除 的业务
     * @param
     */
    @RabbitListener(queues = MqConstants.DIARYVIDEO_DELETE_QUEUE)
    @Async
    public void listenDiaryVideosDeleteOrUpdate1(String message){
        if (StringUtils.isNotBlank( message )){
            System.out.println("----listenDiaryVideosDeleteOrUpdate1---");
            videoService.deleteDiaryVideosFromIndexById(message);
            System.out.println("-----------【队列1】 DIARYVIDEO_DELETE_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",MqConstants.DIARYVIDEO_DELETE_QUEUE,message);
        }
    }

    @RabbitListener(queues = MqConstants.DIARYVIDEO_DELETE_QUEUE)
    @Async
    public void listenDiaryVideosDeleteOrUpdate2(String message){
        if (StringUtils.isNotBlank( message )){
            System.out.println("----listenDiaryVideosDeleteOrUpdate2---");
            videoService.deleteDiaryVideosFromIndexById(message);
            System.out.println("-----------【队列2】 DIARYVIDEO_DELETE_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",MqConstants.DIARYVIDEO_DELETE_QUEUE,message);
        }
    }



}
