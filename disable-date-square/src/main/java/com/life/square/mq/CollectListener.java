package com.life.square.mq;

import com.life.square.constants.MqConstants;
import com.life.square.service.UserCollectionService;
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
public class CollectListener {

    @Autowired
    private UserCollectionService userCollectionService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 异步处理&MQ监听整合
     * @param message
     */
    //监听 动态的 新增 or 修改 的业务
    //定义消费者
    @RabbitListener(queues = MqConstants.COLLECT_INSERT_QUEUE)
    @Async
    public void listenCollectInsertOrUpdate1(String message) {          //队列1
        String userId = message.split(" ")[0];
        String likedId = message.split(" ")[1];
        if (StringUtils.isNotBlank( message )){
            userCollectionService.insertLikeUserCollectToIndexById(userId,likedId);
            System.out.println("-----------【队列1】COLLECT_INSERT_QUEUE 根据commenId找出评论并新增到索引库--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",MqConstants.COLLECT_INSERT_QUEUE,message);
        }
    }

    @RabbitListener(queues = MqConstants.COLLECT_INSERT_QUEUE)
    @Async
    public void listenCollectInsertOrUpdate2(String message) {          //队列1
        String userId = message.split(" ")[0];
        String likedId = message.split(" ")[1];
        if (StringUtils.isNotBlank( message )){
            userCollectionService.insertLikeUserCollectToIndexById(userId,likedId);
            System.out.println("-----------【队列2】COLLECT_INSERT_QUEUE 根据commenId找出评论并新增到索引库--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",MqConstants.COLLECT_INSERT_QUEUE,message);
        }
    }

    /**
     *
     String index = "tb_disable_date_diary_reply";
     String name = "diaryId";

     List<Comment> commentList = commentService.getCommentByDiayId(diary);
     index = "tb_disable_date_diary_reply";
     name = "diaryId";
     */

    /**
     * 监听 动态的 删除 的业务
     * @param
     */
    @RabbitListener(queues = MqConstants.COLLECT_DELETE_QUEUE)
    @Async
    public void listenCollectDelete1(String message){
        if (StringUtils.isNotBlank( message )){
            userCollectionService.deleteCollectFromIndexById(message);
            System.out.println("-----------【队列1】 COLLECT_DELETE_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",MqConstants.COLLECT_DELETE_QUEUE,message);
        }
    }

    @RabbitListener(queues = MqConstants.COLLECT_DELETE_QUEUE)
    @Async
    public void listenCollectDelete2(String message){
        if (StringUtils.isNotBlank( message )){
            userCollectionService.deleteCollectFromIndexById(message);
            System.out.println("-----------【队列2】 COLLECT_DELETE_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",MqConstants.COLLECT_DELETE_QUEUE,message);
        }
    }



}
