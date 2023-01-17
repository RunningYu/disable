package com.life.square.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.life.square.common.RestClient;
import com.life.square.dao.DiaryMapper;
import com.life.square.dao.PictureMapper;
import com.life.square.pojo.*;
import com.life.square.service.IDiaryService;
import com.life.square.dao.CommentMapper;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class IDiaryServiceImpl extends ServiceImpl<DiaryMapper, Diary> implements IDiaryService {

    @Autowired
    private DiaryMapper diaryMapper;

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RestClient restClient = new RestClient();

    @Autowired
    private RestHighLevelClient client;

    //动态的评论
    public void insertComment(Integer id,String comment_id, String comment, String diary_id,String commentator_ip ,String commentator_name,Integer is_deleted) {
        diaryMapper.insertComment(id, comment_id,  comment, diary_id, commentator_ip, commentator_name,is_deleted);
    }

    //添加动态信息
    public void insertDiary(Integer id, String diary_id, String diary_user_id,String diary_user_name, String diary_title, String diary_content,Integer diary_kind, Integer diary_status, String enable_comment) {
        diaryMapper.insertDiary( id,  diary_id,  diary_user_id, diary_user_name, diary_title,  diary_content, diary_kind, diary_status,  enable_comment);
    }

    //该动态的评论数+1
    public void commentAddOne(String diary_id) {
        diaryMapper.commentAddOne(diary_id);
    }

    public Diary getDiaryByDiaryId(String diaryId) {
        try {
//            String index  = "tb_disable_date_diary";  //索引库名（==表名）
//            String esName = "diaryId";       //查询的字段名
//            List<Diary> diaries = new ArrayList<Diary>();
//            SearchHit[] hits = restClient.boolQuery_termQuery_getAllByOneText(index,esName,diaryId);
//            //反序列
//            for(SearchHit hit : hits){
//                //获取文档source
//                String json = hit.getSourceAsString();
//                //反序列化
//                Diary diary = JSON.parseObject(json,Diary.class);
//                diaries.add(diary);
//            }
//
//            if(diaries.size()!=0)
//                return diaries.get(0);
//            else return null;
            return diaryMapper.getDiaryById(diaryId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据动态diaryId查询所有的评论
     * @param page
     * @param size
     * @param sortName   排序依据的索引库中的字段名
     * @param sortOrder  排序的规则，升序 or 降序
     * @param diaryId    动态的id
     * @return
     * @throws IOException
     */
    public List<Comment> getAllComentByDiaryId(String page, String size, String sortName, String sortOrder, String diaryId) throws IOException {
        String index  = "tb_disable_date_diary_comment";  //索引库名（==表名）
        String esName = "diaryId";       //查询的字段名
        String esStatus = "commentStatus";
//        SearchHit[] hits = null;
        Map<String,String> es = new HashMap<String, String>();
        es.put("commentStatus","1");
        SearchHit[] hits = restClient.getAllByISSPG(index,sortName,page,size,sortOrder,esName,diaryId,es);
        List<Comment> list = new ArrayList<Comment>();
        int n = 0;
        for(SearchHit hit : hits){
            n++;
            //获取文档source
            String json = hit.getSourceAsString();
            //反序列化
            Comment comment = JSON.parseObject(json,Comment.class);
            list.add(comment);
            System.out.println("------------------------------------------------------------------------");
            System.out.println("获取到第 "+n+" 个结果 --> "+comment);
            System.out.println("------------------------------------------------------------------------");
        }
        return list;
    }

    //查询全部
    public List<Comment> getAll() {
        return diaryMapper.getAll();
    }

    //查询多条动态信息
    public List<Diary> gertNearActiveList(String name,int page,int size,String sortOrder) {
        try {
            String index  = "tb_disable_date_diary";  //索引库名（==表名）
            Map<String,String> es = new HashMap<String, String>();
            es.put("diaryStatus","1");
            es.put("enableLook","1");
            es.put("isDeleted","0");
            es.put("isReport","0");     //0-举报失败或未被举报 1-举报成功
            SearchHit[] hits = restClient.getAllByISSPG(index,name,page+"",size+"", sortOrder,null,null,es);

            List<Diary> list = new ArrayList<Diary>();
            //反序列
            int n = 0;
            for(SearchHit hit : hits){
                n++;
                //获取文档source
                String json = hit.getSourceAsString();
                //反序列化
                Diary diary = JSON.parseObject(json,Diary.class);
                list.add(diary);
    //            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
    //            System.out.println("获取到第 "+n+" 个结果 --> "+diary);
    //            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //根据id将新增的diary插入（到索引库）
    public void insertDiaryToIndexById(String diaryId) {
        try {
            // 根据id查询酒店数据
            Diary diary = getDiaryByDiaryId(diaryId);

            //转换为文档类型
            DiaryDoc diaryDoc = new DiaryDoc(diary);

            //1.准备Request对象
            IndexRequest request = new IndexRequest("tb_disable_date_diary").id(diaryDoc.getId().toString());
            //2.准备Json文档
            request.source(JSON.toJSONString(diaryDoc), XContentType.JSON);
            //3.发送请求
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //根据id将要删除的diary从索引库中删除
    @Override
    public void deleteDiaryFromIndexById(String id) {
        String index = "tb_disable_date_diary";
        // 根据id查询酒店数据
//        Diary diary = getDiaryByDiaryId(diaryId);
//        String id = diary.getId()+"";
        restClient.DeleteDocument(index,id);
    }

    //根据动态dirayId找出所有的配图并新增到索引库
    public void insertPicToIndexById(String diaryId) {
        System.out.println("【根据动态dirayId找出所有的配图并新增到索引库】");
        try {
            //批量查询酒店的数量
            List<Picture> pictures = getAllPictureBydiaryId(diaryId);

            System.out.println("--------------------------------------------------------------------------");
            System.out.println("查询到的是diary ："+pictures);
            System.out.println("--------------------------------------------------------------------------");

            //1.创建Request
            BulkRequest request = new BulkRequest();
            //2.准备参数，添加多个新增的Request
            for(Picture picture : pictures){
                System.out.println("-->"+picture.getPicPath());
                //转换为文档类型的HotelDoc
    //            HotelDoc hotelDoc = new HotelDoc(hotel);
                //创建新增文档的Request对象
//                request.add(new IndexRequest("tb_disable_pictures")
                request.add(new IndexRequest("tb_disable_diary_pictures")
                        .id(picture.getId().toString())
                        .source(JSON.toJSONString(picture),XContentType.JSON));
            }

            //3.发送请求
            client.bulk(request,RequestOptions.DEFAULT);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //根据动态dirayId找出所有的配图

    private List<Picture> getAllPictureBydiaryId(String diaryId) {
        return pictureMapper.getAllPictureBydiaryId(diaryId);
    }

    //添加评论的回复到回复表
    public void insertReply(Integer id, String reply_id,String comment_id, String diary_id,String reply_content, String reply_user_id, String reply_user_name, String comment_user_id, String comment_user_name) {
        diaryMapper.insertReply(id, reply_id, comment_id, diary_id, reply_content,  reply_user_id,  reply_user_name,  comment_user_id,  comment_user_name);
    }

    //给对应的动态的回复量 +1
    public void commentReplyAmountAddOne(String comment_id) {
        commentMapper.commentReplyAmountAddOne(comment_id);
    }

    //从数据库中删除动态
    public void deleteDiary(String diaryId) {
        diaryMapper.deleteDiary(diaryId);
    }

    //修改动态是否可见的状态status
    public void updateDiaryStatus(String diaryId, String status) {
        diaryMapper.updateDiaryStatus( diaryId,  status);
    }

    //获取关注的动态列表
    @Override
    public List<Diary> getFollowsDiaryList(String name, Integer page, Integer size, String sortOrder, List<String> follows) {
        try {
            String index  = "tb_disable_date_diary";  //索引库名（==表名）
//            String esStatus = "diaryStatus";
            Map<String,String> esMust = new HashMap<String, String>();
            esMust.put("diaryStatus","1");    //要审核通过
            esMust.put("enableLook","1");     //要是公开的
            esMust.put("isDeleted","0");     //0：未删除 1：删除了
            esMust.put("isReport","0");     //0-举报失败或未被举报 1-举报成功
            Map<String,String[]> esShould = new HashMap<String, String[]>();
            String[] userId = new String[follows.size()];
            int n = 0;
            for(String id : follows){
//                if(follow.getFollowedUserId()==follow.getUserId()) {
//                    n++;
//                    userId[n] = follow.getFollowedUserId() + "";
//                    System.out.println("diaryUserId -- >" + follow.getFollowedUserId());
//                }
                userId[n] = id;
                System.out.println("————>"+userId[n]);
                n++;

            }
            esShould.put("diaryUserId",userId);
            SearchHit[] hits = restClient.getAllByISSPSEE(index,name, sortOrder,page+"",size+"",esMust,esShould);

            List<Diary> list = new ArrayList<Diary>();
            //反序列
            n = 0;
            for(SearchHit hit : hits){
                n++;
                //获取文档source
                String json = hit.getSourceAsString();
                //反序列化
                Diary diary = JSON.parseObject(json,Diary.class);
                list.add(diary);
                            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
                            System.out.println("获取到第 "+n+" 个结果 --> "+diary);
                            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 用交集的用户id去从索引库搜索出所有的动态信息，并且进行时间的降序排序
     * @param idList   查询的交集id
     * @param page
     * @param size
     * @return
     */
    public List<Diary> getVideoDiariesByUserId(List<String> idList, Integer page, Integer size) {
        String index  = "tb_disable_date_diary";  //索引库名（==表名）
        Map<String,String> map = new HashMap<String, String>();
        map.put("diaryStatus","1");
        map.put("enableLook","1");
        map.put("diaryKind","2");  //表示是视频类型
        map.put("isDeleted","0");  //0-未被删除
        map.put("isReport","0");     //0-举报失败或未被举报 1-举报成功
        String sortName = "createTime"; //根据这个索引库的字段来进行排序
        String sortOrder = "DESC";
        String esName = "diaryUserId";
        SearchHit[] hits = restClient.getAllBy_Index_SortName_SortOrder_page_size_esName_esList(
                                         index,sortName,sortOrder,page,size,map,esName,idList,true);

        //反序列
        List<Diary> list = new ArrayList<Diary>();
        int n = 0;
        for(SearchHit hit : hits){
            n++;
            //获取文档source
            String json = hit.getSourceAsString();
            //反序列化
            Diary diary = JSON.parseObject(json,Diary.class);
            System.out.println("—————————————1———————————————————————————————————————————————————————————————————————————————————————————");
            System.out.println("获取到第 "+n+" 个结果 --> 用户id:"+diary.getDiaryUserId()+" ("+diary.getDiaryStatus()+","
                   +diary.getDiaryKind()+" " +diary.getEnableLook()+") 发布时间 ："+diary.getCreateTime());
            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
            list.add(diary);
        }
        return list;
    }

    //获取非交集id的用户动态
    public List<Diary> getDiariesByNotUserId(List<String> idList, Map<String,String> map,Integer page, Integer size) {
        String index  = "tb_disable_date_diary";  //索引库名（==表名）
        String sortName = "createTime"; //根据这个索引库的字段来进行排序
        String sortOrder = "DESC";
        String esName = "diaryUserId";
        SearchHit[] hits = restClient.getAllBy_Index_SortName_SortOrder_page_size_esName_esList(
                index,sortName,sortOrder,page,size,map,esName,idList,false);

        //反序列
        List<Diary> list = new ArrayList<Diary>();
        int n = 0;
        for(SearchHit hit : hits){
            n++;
            //获取文档source
            String json = hit.getSourceAsString();
            //反序列化
            Diary diary = JSON.parseObject(json,Diary.class);
            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
            System.out.println("获取到第 "+n+" 个结果 --> 用户id:"+diary.getDiaryUserId()+" ("+diary.getDiaryStatus()+","
                    +diary.getEnableLook()+") 发布时间 ："+diary.getCreateTime());
            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
            list.add(diary);
        }
        return list;
    }

    //视频秀的搜索框搜索功能(通过输入关键字搜索)  匹对的字段 ： 动态标题 、 动态内容
    public List<Diary> getVideoDiaryByMatchKeyWords(Set<String> keywords, Integer page, Integer size) {
        String index = "tb_disable_date_diary";
        Map<String,String> map = new HashMap<String, String>();
        map.put("diaryStatus","1");
        map.put("enableLook","1");
//        map.put("diaryKind","2");  //表示是视频类型
        String sortName = "createTime"; //根据这个索引库的字段来进行排序
        String sortOrder = "DESC";
        String esName = "diaryUserId";
        Map<String,String[]> keywordsMap = new HashMap<String, String[]>();
        String[] str = new String[keywords.size()];
        Integer n = 0;
        for(String s : keywords) {
            str[n] = s;
            n++;
            System.out.print(str[n-1]+" ");
        }
        keywordsMap.put("diaryTitle",str);
        keywordsMap.put("diaryContent",str);
        /**
         *  public SearchHit[] getAllByISSPSEE(String index, String sortName,String sortOrder,String Page,String Size,
         *                                       Map<String,String> esMust,Map<String,String[]> esShould)
         */
        SearchHit[] hits = restClient.getAllByISSPSEE(index,sortName,sortOrder,page+"",size+"",map,keywordsMap);

        //反序列
        List<Diary> list = new ArrayList<Diary>();
        n = 0;
        for(SearchHit hit : hits){
            n++;
            //获取文档source
            String json = hit.getSourceAsString();
            //反序列化
            Diary diary = JSON.parseObject(json,Diary.class);
            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
            System.out.println("获取到第 "+n+" 个结果 --> 用户id:"+diary.getDiaryUserId()+" ("+diary.getDiaryStatus()+","
                    +diary.getEnableLook()+") 发布时间 ："+diary.getCreateTime());
            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
            list.add(diary);
        }
        return list;
    }

    //获取自己的所有动态列表
    public List<Diary> getMyDiary(String userId, Integer page, Integer size) {
        String index = "tb_disable_date_diary";
        String esName = "diaryUserId";
        String sortName = "createTime";
        String sort = "DESC";   //倒叙
        SearchHit[] hits = restClient.getMyDiary(index,esName,userId,page,size, sortName, sort);
        //反序列
        List<Diary> list = new ArrayList<Diary>();
        Integer n = 0;
        for(SearchHit hit : hits){
            n++;
            //获取文档source
            String json = hit.getSourceAsString();
            //反序列化
            Diary diary = JSON.parseObject(json,Diary.class);
            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
            System.out.println("获取到第 "+n+" 个结果 --> 用户id:"+diary.getDiaryUserId()+" ("+diary.getDiaryStatus()+","
                    +diary.getEnableLook()+") 发布时间 ："+diary.getCreateTime());
            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
            list.add(diary);
        }
        return list;
    }

    //动态收藏量+1
    public void addDiaryCollectAmount(String likedId) {
        diaryMapper.addDiaryCollectAmount(likedId);
    }

    //动态收藏量-1
    public void subDiaryCollectAmount(String likedId) {
        diaryMapper.subDiaryCollectAmount(likedId);
    }

    //存储用户点赞的记录
    public void addLikeDiaryRecord(Integer id,String userId, String diaryId) {
        diaryMapper.addLikeDiaryRecord(id,userId,diaryId);
    }

    //从数据库中获取一条用户点赞动态的记录信息
    public UserLikeDiary getOneLikeDiary(String userId, String diaryId) {
        return diaryMapper.getOneLikeDiary(userId, diaryId);
    }

    //动态点赞量+1
    public void addDiaryLikeAmount(String diaryId) {
        diaryMapper.addDiaryLikeAmount(diaryId);
    }

    //动态点赞量-1
    public void subDiaryLikeAmount(String diaryId) {
        diaryMapper.subDiaryLikeAmount(diaryId);
    }

    //删除点赞记录
    public void deleteLikeDiaryRecord(String userId, String diaryId) {
        diaryMapper.deleteLikeDiaryRecord(userId,diaryId);
    }

    //根据收藏的对象id从数据库中删除所有有关的记录
    public void deleteUserLike(String likedId) {
        diaryMapper.deleteUserLike(likedId);
    }

    //从数据库中删除对这个的点赞的记录
    public void deleteAllLikeRecord(String diaryId) {
        diaryMapper.deleteAllLikeRecord(diaryId);
    }

    //获取用户点赞过的视频id
    public List<String> getCollectedDiaryIds(String userId) {
        try {
            String index = "tb_disable_date_user_collect";
            String esName = "userId";
            SearchHit[] hits = restClient.boolQuery_termQuery_getAllByOneText(index,esName,userId);
            //反序列
            int n = 0;
            List<String> list = new ArrayList<String>();
            for(SearchHit hit : hits){
                n++;
                //获取文档source
                String json = hit.getSourceAsString();
                //反序列化
                UserCollection userCollection = JSON.parseObject(json,UserCollection.class);
                if(userCollection!=null)
                    list.add(userCollection.getLikedId());
            }
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
