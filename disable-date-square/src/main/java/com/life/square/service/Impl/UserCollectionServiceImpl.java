package com.life.square.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.life.square.common.RestClient;
import com.life.square.dao.UserCollectionMapper;
import com.life.square.pojo.UserCollection;
import com.life.square.service.UserCollectionService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionMapper, UserCollection> implements UserCollectionService {

    @Autowired
    private UserCollectionMapper userCollectionMapper;

    @Autowired
    private RestClient restClient;

    @Autowired
    private RestHighLevelClient client;

    //获取自己收藏的 视频 、 图片秀
    public List<UserCollection> getUserCollections(String userId, Integer page, Integer size) {
        String index = "tb_disable_date_user_collect";
        String esName = "userId";
        String sort = "DESC";
        String sortName = "createTime";
        SearchHit[] hits = restClient.getMyDiary( index,  esName,  userId,  page,  size, sortName, sort);
        //反序列
        List<UserCollection> list = new ArrayList<UserCollection>();
        int n = 0;
        for(SearchHit hit : hits){
            n++;
            //获取文档source
            String json = hit.getSourceAsString();
            //反序列化
            UserCollection userCollection = JSON.parseObject(json,UserCollection.class);
            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
            System.out.println("获取到第 "+n+" 个结果 --> 用户id:"+userCollection.getUserId()+" - 发布时间 "+userCollection.getCreateTime());
            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
            list.add(userCollection);
        }
        return list;
    }

    //添加用户的收藏（ 视频 、 图片秀）到索引库中
    public void insertLikeUserCollectToIndexById(String userId, String likedId) {
        try {
            UserCollection userCollection = userCollectionMapper.getUserCollection(userId,likedId);

            //1.准备Request对象
            IndexRequest request = new IndexRequest("tb_disable_date_user_collect").id(userCollection.getId().toString());
            //2.准备Json文档
            request.source(JSON.toJSONString(userCollection), XContentType.JSON);
            //3.发送请求
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //从索引库中删除用户的收藏（ 视频 、 图片秀）
    public void deleteCollectFromIndexById(String id) {
        String index = "tb_disable_date_user_collect";
        restClient.DeleteDocument(index,id);
    }

    //获取单个收藏
    public UserCollection getUserCollection(String userId, String likedId) {
        return userCollectionMapper.getUserCollection(userId,likedId);
    }
}
