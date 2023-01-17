package com.life.square.mq;

import com.life.square.constants.MqConstants;
import com.life.square.service.IPictureService;
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
public class PictureListener {


    @Autowired
    private IPictureService pictureService;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    //—————————————————————————————————————————————————————监听 picture 照片 ——————————————————————————————————————————————————————
    /**
     * 监听 动态的 新增 or 修改 的业务
     * @param message == diaryId
     */
    //定义消费者
    //根据动态dirayId找出所有的配图并新增到索引库
    @RabbitListener(queues = MqConstants.PICTURE_INSERT_QUEUE)
    @Async
    public void listenPicInsertOrUpdate1(String message) {
        if (StringUtils.isNotBlank( message )){
            pictureService.insertPicToIndexById(message);
            System.out.println("-----------【队列1】PICTURE_INSERT_QUEUE 根据动态dirayId找出所有的配图并新增到索引库--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",MqConstants.PICTURE_INSERT_QUEUE,message);
        }
    }

    @RabbitListener(queues = MqConstants.PICTURE_INSERT_QUEUE)
    @Async
    public void listenPicInsertOrUpdate2(String message) {
        if (StringUtils.isNotBlank( message )){
            pictureService.insertPicToIndexById(message);
            System.out.println("-----------【队列2】PICTURE_INSERT_QUEUE 根据动态dirayId找出所有的配图并新增到索引库--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",MqConstants.PICTURE_INSERT_QUEUE,message);
        }
    }

    /**
     * 监听 动态的 删除 的业务
     */
    @RabbitListener(queues = MqConstants.PICTURE_DELETE_QUEUE)
    @Async
    public void listenDiaryDeleteOrUpdate1(String message){
        if (StringUtils.isNotBlank( message )){
            pictureService.deleteDiaryFromIndexById(message);
            System.out.println("-----------【队列1】 PICTURE_DELETE_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",MqConstants.PICTURE_DELETE_QUEUE,message);
        }
    }

    @RabbitListener(queues = MqConstants.PICTURE_DELETE_QUEUE)
    @Async
    public void listenDiaryDeleteOrUpdate2(String message){
        if (StringUtils.isNotBlank( message )){
            pictureService.deleteDiaryFromIndexById(message);
            System.out.println("-----------【队列2】 PICTURE_DELETE_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",MqConstants.PICTURE_DELETE_QUEUE,message);
        }
    }

//————————————————————————————————————————————————————监听 picture 照片 ——————————————————————————————————————————————————————————


}
