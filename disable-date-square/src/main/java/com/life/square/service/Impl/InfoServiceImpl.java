package com.life.square.service.Impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.life.square.common.RestClient;
import com.life.square.pojo.BasicInfo;
import com.life.square.pojo.BasicInfoDoc;
import com.life.square.pojo.DetailInfo;
import com.life.square.service.InfoService;
import com.life.square.dao.InfoMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class InfoServiceImpl extends ServiceImpl<InfoMapper, BasicInfo> implements InfoService {

    @Autowired
    private RestClient restClient;

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private InfoMapper infoMapper;


    //通过id来查询个人 基本信息
    public BasicInfo getByPersonId(int id) {
        return infoMapper.getByPersionId(id);
    }

    //通过距离范围获取
    public List<JSONObject> getBasicInfoByDistance(String index, String filed, String location, String distance) {
        try {
            SearchRequest request = new SearchRequest(index);

            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.must(QueryBuilders.matchAllQuery());
            request.source().query();
            System.out.println("-----------request.source().query();--------");
            restClient.sortDistance(request,filed,location,distance);
            System.out.println("+++++++++++++++++ sortDistance ++++++++++++++++++++");
            //3.发送请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            System.out.println("------------SearchResponse response = client.search(request, RequestOptions.DEFAULT);--------------");

            SearchHit[] hits = restClient.handleResponce(response);
            System.out.println("----------------hits------");
            //反序列
            Integer n = 0;
            List<JSONObject> list = new ArrayList<JSONObject>();
            System.out.println("1111111111111111111111111111111111");
            for(SearchHit hit : hits){
                System.out.println("2222222222222222222222222222");
                JSONObject jsonObject = new JSONObject();
                n++;
                //获取文档source
                System.out.println("33333333333333333333333333");
                String json = hit.getSourceAsString();
                System.out.println("444444444444444444444444444444");
                BigDecimal geoDis = new BigDecimal((Double) hit.getSortValues()[0]);  //获取距离具体值
                System.out.println("555555555555555555555555");
                String Distance = geoDis+"";
                //反序列化
                BasicInfoDoc basicInfoDoc = JSON.parseObject(json,BasicInfoDoc.class);
                System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
                System.out.println("获取到第 "+n+" 个结果 --> "+basicInfoDoc);
                System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
                jsonObject.put("distacne",Distance);
                jsonObject.put("BasicInfoDoc",basicInfoDoc);

                list.add(jsonObject);
            }
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //从索引库中获取一个用户的基本信息
    public BasicInfoDoc getOneUserBasicInfoById(String userId) throws IOException {
//        String index = "disable-date-basic-info";
        String index = "disable-date-basic-info";
        String name = "personId";
        SearchHit[] hits = restClient.boolQuery_termQuery_getAllByOneText(index,name,userId);
        System.out.println("---------------------hits++++++++++++++++++");
        System.out.println("-->"+hits.length);
        //反序列
        Integer n = 0;
        List<BasicInfoDoc> list = new ArrayList<BasicInfoDoc>();
        for(SearchHit hit : hits){
            JSONObject jsonObject = new JSONObject();
            n++;
            //获取文档source
            String json = hit.getSourceAsString();
            //反序列化
            BasicInfoDoc basicInfoDoc = new BasicInfoDoc();
            System.out.println("-----BasicInfoDoc basicInfoDoc = new BasicInfoDoc(new BasicInfo());-------");
            basicInfoDoc = JSON.parseObject(json,BasicInfoDoc.class);
            System.out.println("===========  basicInfoDoc = JSON.parseObject(json,BasicInfoDoc.class); ==========");
            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
            System.out.println("获取到第 "+n+" 个结果 --> "+basicInfoDoc);
            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
            jsonObject.put("BasicInfoDoc",basicInfoDoc);
            list.add(basicInfoDoc);
        }
        BasicInfoDoc basicInfoDoc = new BasicInfoDoc();
        if(list!=null&&list.size()!=0) {
            basicInfoDoc = list.get(0);
        }else if(list == null){
            //从数据库中查询个人基本信息
            BasicInfo basicInfo = getByPersonId(Integer.parseInt(userId));
            if(basicInfo != null)
                basicInfoDoc = new BasicInfoDoc(basicInfo);
        }
        return basicInfoDoc;
    }

    //从数据库中查询所的用户的详细的信息
    public List<DetailInfo> getAllDetail() {
        return infoMapper.getAllDetail();
    }

    //从索引库中查询出所有爱好匹对的用户的详细信息
    public List<DetailInfo> getAllUserDetailFromIndexMatchEsname(String index,String esName,Set<String> hobbiesSet) {
        try {
            BoolQueryBuilder boolQuery = new BoolQueryBuilder();
            SearchRequest request = new SearchRequest(index);
            restClient.filters_moreLike_should(boolQuery,hobbiesSet,esName);
            request.source().query(boolQuery);
            //3.发送请求
            SearchResponse response = client.search(request,RequestOptions.DEFAULT);

            SearchHit[] hits = restClient.handleResponce(response);

            //反序列
            Integer n = 0;
            List<DetailInfo> list = new ArrayList<DetailInfo>();
            for(SearchHit hit : hits){
                n++;
                //获取文档source
                String json = hit.getSourceAsString();
                //反序列化
                DetailInfo detailInfo = JSON.parseObject(json,DetailInfo.class);
                System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
                System.out.println(" 获取到第 "+n+" 个结果 --> id："+detailInfo.getPersonId()+" 爱好： "+detailInfo.getHobby()+"  标签："+detailInfo.getPersonTag());
                System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
                list.add(detailInfo);
            }



            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    //从索引库中查询出所有爱好匹对的用户的详细信息
    public List<DetailInfo> getAllUserDetailFromIndexMatchHobby(Set<String> hobbiesSet) {
        try {
            BoolQueryBuilder boolQuery = new BoolQueryBuilder();
//            SearchRequest request = new SearchRequest("tb_disable_date_person_detail_info");
            SearchRequest request = new SearchRequest("disable-date-detail-info");
            restClient.filters_moreLike_should(boolQuery,hobbiesSet,"hobby");
            request.source().query(boolQuery);
            //3.发送请求
            SearchResponse response = client.search(request,RequestOptions.DEFAULT);

            SearchHit[] hits = restClient.handleResponce(response);

            //反序列
            Integer n = 0;
            List<DetailInfo> list = new ArrayList<DetailInfo>();
            for(SearchHit hit : hits){
                n++;
                //获取文档source
                String json = hit.getSourceAsString();
                //反序列化
                DetailInfo detailInfo = JSON.parseObject(json,DetailInfo.class);
                System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
                System.out.println(" 获取到第 "+n+" 个结果 --> "+detailInfo.getPersonId()+" : "+detailInfo.getHobby());
                System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
                list.add(detailInfo);
            }



            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //1.根据 年龄范围、距离范围、性别 筛选出所有的用户基本信息
    public List<BasicInfoDoc> getBasicInfosByAgeDistanceSex(Integer age1,Integer age2,Integer distance,Integer sex) {
        String index = "disable-date-basic-info";

        SearchHit[] hits = restClient.getAllByAgeDistanceSex(index,age1,age2,distance,sex);

        //反序列
        Integer n = 0;
        List<BasicInfoDoc> list = new ArrayList<BasicInfoDoc>();
        for(SearchHit hit : hits){
            n++;
            //获取文档source
            String json = hit.getSourceAsString();
//            BigDecimal geoDis = new BigDecimal((Double) hit.getSortValues()[0]);  //获取距离具体值
            //反序列化
            BasicInfoDoc basicInfoDoc = JSON.parseObject(json,BasicInfoDoc.class);
            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
            System.out.println("获取到第 "+n+" 个结果 --> id："+basicInfoDoc.getPersonId()+" 性别："+basicInfoDoc.getSex()
                    +" age："+basicInfoDoc.getAge());
            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
//            jsonObject.put("distacne",Distance);
//            jsonObject.put("BasicInfoDoc",basicInfoDoc);
            list.add(basicInfoDoc);
        }
        return list;
    }


    public Integer getAmount() {
        return infoMapper.getAmount();
    }
}
