package com.life.square;

import com.alibaba.fastjson.JSON;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.life.square.pojo.*;
import com.life.square.service.*;
import com.life.square.common.RestClient;
import com.life.square.common.Save;
import com.life.square.dao.CommentMapper;
import com.life.square.dao.UserMapper;
import com.life.square.utils.GetUserFromRedisUtil;
import io.lettuce.core.api.reactive.RedisStringReactiveCommands;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.File;
import java.io.IOException;
import java.util.*;


import static com.life.square.constants.IndexLibraryConstants.MAPPING_TEMPLATE;

/*
       所有的单元测试先运行Before再运行After
 */

/**
 * 要构建查询条件，只需要记住一个类：QueryBuilders
 */
@SpringBootTest
public class test {

    @Autowired
    private UserCollectionService userCollectionService;

    @Autowired
    private LikeHobbyService likeHobbyService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private IDiaryService diaryService;

    @Autowired
    private IPictureService pictureService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IVideoService videoService;

    @Autowired
    private InfoService infoService;

    @Autowired
    private RestClient restClient;

    //保存文件到服务器
//    @Resource
    private Save save = new Save();

    @Test
    public void test1(){
        User user = userMapper.selectById(1);
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println(user);
        System.out.println("-------------------------------------------------------------------------------------");
    }

    @Test
    public void testSave() throws IOException {
        File file = new File("D:\\Users\\Picture\\hhh.jpg");
        String name = UUID.randomUUID()+".jpg";     //使用UUID命名，避免重复问题
        if(file != null){
            System.out.println(file);
            System.out.println("-------------------有的------------------");
            save.savePicture(file,name);
        }
    }


    @Test
    //创建索引库
    void createHotelIndex() throws IOException {
        //1.创建Request对象
        CreateIndexRequest request = new CreateIndexRequest("disable-date");
        //2.准备请求的参数：DSL语句
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        //3.发送请求
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    //删除索引库
    @Test
    void DeleteIndex() throws IOException {
        //1.创建Request对象
        DeleteIndexRequest request = new DeleteIndexRequest("disable-date");

        //2.发送请求
        client.indices().delete(request,RequestOptions.DEFAULT);
    }

    //判断索引库是否存在
    @Test
    void ExitsIndex() throws IOException {
        //1.创建Request对象
        GetIndexRequest request = new GetIndexRequest("disable-date");
        //2.发送请求
        boolean exists = client.indices().exists(request,RequestOptions.DEFAULT);
        System.out.println(exists ? "索引库已经存在！" : "索引库不存在！");
    }

    @Test
    //添加文档（单个）
    public void AddDocument() throws IOException {

        // 根据id查询酒店数据
        Diary diary = diaryService.getById(1);
//        BasicInfo basicInfo = diaryService.getById(1);
//        Diary diary = diaryService.getDiaryById(2);
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("获取到的："+diary);
        System.out.println("--------------------------------------------------------------------------");

        //转换为文档类型
    //        Diary hotelDoc = new HotelDoc(hotel);

        //1.准备Request对象
        IndexRequest request = new IndexRequest("disable-date").id(diary.getId().toString());
        //2.准备Json文档
        request.source(JSON.toJSONString(diary),XContentType.JSON);
        //3.发送请求
        client.index(request,RequestOptions.DEFAULT);

    }

    //根据id查询
    @Test
    public void GetDocumentById() throws IOException {

        //1.准备好request
        GetRequest request = new GetRequest("disable-date","1");
        //2.发送请求 ，得到响应
        GetResponse response = client.get(request,RequestOptions.DEFAULT);
        //3.解析响应结果
        String json = response.getSourceAsString();

        Diary diary= JSON.parseObject(json,Diary.class);
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("查询到的是diary ："+diary);
        System.out.println("--------------------------------------------------------------------------");
    }

    //跟新文档
    @Test
    public void UpdateDocument() throws IOException {
        //1.准备Request
        UpdateRequest request = new UpdateRequest("disable-date","1");

        //2.准备请求参数
        request.doc(
                "diaryTitle","后端相对更卷啊！",
                "diaryUserId","10"
        );

        //3.发送请求
        client.update(request,RequestOptions.DEFAULT);
    }

    //根据id删除文档
    @Test
    void DeleteDocument() throws IOException {
        //1.准备Request
        DeleteRequest request = new DeleteRequest("tb_disable_date_diary","23");

        //2.备请求参数
        client.delete(request,RequestOptions.DEFAULT);
    }


    //批量添加 到 tb_disable_date_diary_comment
    @Test
    void testBulkRequest() throws IOException {
        //批量查询酒店的数量
        List<Comment> comments = diaryService.getAll();

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("查询到的是diary ："+comments);
        System.out.println("--------------------------------------------------------------------------");

//        for(Diary diary : diaries){
//            HotelDoc hotelDoc = new HotelDoc(hotel);
//        }

        //1.创建Request
        BulkRequest request = new BulkRequest();
        //2.准备参数，添加多个新增的Request
        for(Comment comment : comments){
            //转换为文档类型的HotelDoc
//            HotelDoc hotelDoc = new HotelDoc(hotel);
            //创建新增文档的Request对象
            request.add(new IndexRequest("tb_disable_date_diary_comment")
                    .id(comment.getId().toString())
                    .source(JSON.toJSONString(comment),XContentType.JSON));
        }
        //3.发送请求
        client.bulk(request,RequestOptions.DEFAULT);
    }

    //批量添加 到 tb_disable_date_diary_Reply
    @Test
    void testBulkReply() throws IOException {
        //获取所有的回复
        List<Reply> replies = commentService.getAllReplies();

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("查询到的是diary ："+replies);
        System.out.println("--------------------------------------------------------------------------");


        //1.创建Request
        BulkRequest request = new BulkRequest();
        //2.准备参数，添加多个新增的Request
        for(Reply reply : replies){
            System.out.println("-->"+reply.getReplyId()+" "+reply.getCommentId());
            //创建新增文档的Request对象
            request.add(new IndexRequest("tb_disable_date_diary_reply")
                    .id(reply.getId().toString())
                    .source(JSON.toJSONString(reply),XContentType.JSON));
        }
        //3.发送请求
        client.bulk(request,RequestOptions.DEFAULT);
    }


    //批量添加 到 tb_disable_date_diary
    @Test
    void testBulkRequest2() throws IOException {
        //批量查询酒店的数量
        List<Diary> diaries = diaryService.list();

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("查询到的是diary ："+diaries);
        System.out.println("--------------------------------------------------------------------------");


        //1.创建Request
        BulkRequest request = new BulkRequest();
        //2.准备参数，添加多个新增的Request
        for(Diary diary : diaries){

            System.out.println("diaryUserName————>"+diary.getDiaryKind());

            //转换为文档类型的HotelDoc
            DiaryDoc diaryDoc = new DiaryDoc(diary);
            //创建新增文档的Request对象
            request.add(new IndexRequest("tb_disable_date_diary")
                    .id(diaryDoc.getId().toString())
                    .source(JSON.toJSONString(diaryDoc),XContentType.JSON));
        }
        //3.发送请求
        client.bulk(request,RequestOptions.DEFAULT);
    }


    //批量同步基本信息 disable-date-basic-info
    @Test
    void testBulkBasicInfo() throws IOException {
        List<BasicInfo> basicInfos = infoService.list();

        //1.创建Request
        BulkRequest request = new BulkRequest();
        //2.准备参数，添加多个新增的Request
        for(BasicInfo basicInfo : basicInfos){


            //转换为文档类型的HotelDoc
            BasicInfoDoc basicInfoDoc = new BasicInfoDoc(basicInfo);
//            System.out.println("getPersonName————>"+basicInfo.getPersonName());
            System.out.println("getPersonName————>"+basicInfoDoc);
            //创建新增文档的Request对象
//            request.add(new IndexRequest("disable-date-basic-info")
            request.add(new IndexRequest("disable-date-basic-info")
                    .id(basicInfoDoc.getPersonId().toString())
                    .source(JSON.toJSONString(basicInfoDoc),XContentType.JSON));
        }
        //3.发送请求
        client.bulk(request,RequestOptions.DEFAULT);
    }

    //批量同步详细信息
    @Test
    void testBulkDetailInfo() throws IOException {
        List<DetailInfo> detailInfos = infoService.getAllDetail();

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("查询到的是diary ："+detailInfos);
        System.out.println("--------------------------------------------------------------------------");


        //1.创建Request
        BulkRequest request = new BulkRequest();
        //2.准备参数，添加多个新增的Request
        for(DetailInfo detailInfo : detailInfos){

            System.out.println("爱好————>"+detailInfo.getHobby());

            //创建新增文档的Request对象
            request.add(new IndexRequest("tb_disable_date_person_detail_info")
                    .id(detailInfo.getPersonId().toString())
                    .source(JSON.toJSONString(detailInfo),XContentType.JSON));
        }
        //3.发送请求
        client.bulk(request,RequestOptions.DEFAULT);
    }

    //批量同步LikeHobby
    @Test
    void testBulkLikeHobby() throws IOException {
        List<LikeHobby> likeHobbies = likeHobbyService.list();

        //1.创建Request
        BulkRequest request = new BulkRequest();
        //2.准备参数，添加多个新增的Request
        for(LikeHobby likeHobby : likeHobbies){

            System.out.println(likeHobby.getUserId()+" 爱好————>"+likeHobby.getHobby());

            //创建新增文档的Request对象
            request.add(new IndexRequest("tb_disable_date_user_like_hobby")
                    .id(likeHobby.getId().toString())
                    .source(JSON.toJSONString(likeHobby),XContentType.JSON));
        }
        //3.发送请求
        client.bulk(request,RequestOptions.DEFAULT);
    }

    //批量添加 到 tb_disable_date_follow
    @Test
    void testBulkRequestaeg() throws IOException {
        System.out.println("-----------------------------------------------" +
                "__________________________________________________" +
                "___________________________");
        //批量查询酒店的数量
        List<Follow> follows = userMapper.getFollws();


        //1.创建Request
        BulkRequest request = new BulkRequest();
        //2.准备参数，添加多个新增的Request
        for(Follow follow : follows){

            System.out.println("userId————>"+follow.getUserId());

            //创建新增文档的Request对象
            request.add(new IndexRequest("tb_disable_date_follow")
                    .id(follow.getId().toString())
                    .source(JSON.toJSONString(follow),XContentType.JSON));
//            request.add(new IndexRequest("qqwe"))
        }
        //3.发送请求
        client.bulk(request,RequestOptions.DEFAULT);
    }

    //批量添加 到 tb_disable_diary_pictures
    @Test
    void testBulkRequest_Picture3() throws IOException {
        //批量查询酒店的数量
        List<Picture> pictures = pictureService.list();

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("查询到的是diary ："+pictures);
        System.out.println("--------------------------------------------------------------------------");

        //1.创建Request
        BulkRequest request = new BulkRequest();
        //2.准备参数，添加多个新增的Request
        for(Picture picture : pictures){
            //创建新增文档的Request对象
            request.add(new IndexRequest("tb_disable_diary_pictures")
                    .id(picture.getId().toString())
                    .source(JSON.toJSONString(picture),XContentType.JSON));
        }
        //3.发送请求
        client.bulk(request,RequestOptions.DEFAULT);
    }

    //批量添加视频
    @Test
    void testBulkRequest_Videos() throws IOException {
        List<Video> videos = videoService.list();

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("查询到的是diary ："+videos);
        System.out.println("--------------------------------------------------------------------------");

        //1.创建Request
        BulkRequest request = new BulkRequest();
        //2.准备参数，添加多个新增的Request
        for(Video video : videos){
            //创建新增文档的Request对象
            request.add(new IndexRequest("tb_disable_diary_videos")
                    .id(video.getId().toString())
                    .source(JSON.toJSONString(video),XContentType.JSON));
        }
        //3.发送请求
        client.bulk(request,RequestOptions.DEFAULT);
    }

    //批量添加 tb_disable_date_user_collect
    @Test
    void testBulkRequest_userCollection() throws IOException {
        List<UserCollection> userCollections = userCollectionService.list();

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("查询到的是diary ："+userCollections);
        System.out.println("--------------------------------------------------------------------------");

        //1.创建Request
        BulkRequest request = new BulkRequest();
        //2.准备参数，添加多个新增的Request
        for(UserCollection collection : userCollections){
            //创建新增文档的Request对象
            request.add(new IndexRequest("tb_disable_date_user_collect")
                    .id(collection.getId().toString())
                    .source(JSON.toJSONString(collection),XContentType.JSON));
        }
        //3.发送请求
        client.bulk(request,RequestOptions.DEFAULT);
    }


    //测试自动补全查询
    @Test
    public void searchSuggestion() throws IOException {
        String index = "tb_disable_date_diary";
        String prefix = "qd";
        String fieldName = "suggestion";
        List<CompletionSuggestion.Entry.Option> options = restClient.searchSuggestion(index,prefix,fieldName);
        //4.3 遍历
        List<String> suggestions = new ArrayList<String>();
        System.out.println("—————————————————————————————————————————————————————————自动补全查询结果—————————————————————————————————————————————————————");
        for( CompletionSuggestion.Entry.Option option : options){
            String text = option.getText().toString();
            System.out.println(text);
            suggestions.add(text);
        }
        System.out.println("———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————");
    }

    @Test
    public void getReply(){
        Reply reply = commentMapper.getReplyById("3f1b140b-8591-4c8a-b8fc-f6d7d7a21b2a");
        System.out.println(reply.getCreateTime());
        System.out.println(reply);
    }

    @Test
    public void getIk(){
        String s = "大家好，才是真的好啊";
        JiebaSegmenter jieba = new JiebaSegmenter();
        System.out.println(jieba.sentenceProcess(s));
        List<String> list = jieba.sentenceProcess(s);
        System.out.println(list);
        for(int i = 0;i<list.size();i++) {
            System.out.println(list.get(i));
        }

        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> res = segmenter.process(s, JiebaSegmenter.SegMode.INDEX);
        System.out.println(res);
    }

    @Test
    public void getRepeat(){
        List<String> list1 = new ArrayList<String>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
        List<String> list2 = new ArrayList<String>();
        list2.add("2");
        list2.add("4");
        list2.add("3");
        list2.add("5");
//        Set<String> set = new HashSet<String>();
//        for(String s : list1){
//            set.add(s);
//        }
//        for(String s : list2){
//            set.add(s);
//        }
        List list = new ArrayList();
        list.addAll(list1);
        list.retainAll(list2);
        System.out.println(list);

    }

    @Test
    public void getLikeId(){
        List<String> list = userService.getLikeUserIds("1");
        System.out.println(list);
        List<String> list1 = userService.getDisLikeUserIds("1");
        System.out.println(list1);
        List<String> list2 = userService.getConcernUserIds("1");
        System.out.println(list2);
    }


    @Test
    public Integer getNPages(){
        try {
            Integer total = 0;
            for(int page = 0;;page++){
                String name = "createTime";  //根据这个索引库的字段来进行排序
                String sortOrder = "DESC";
                System.out.println("11111111111111111111");
                //获取多条动态
                List<Diary> diaries = diaryService.gertNearActiveList(name,page, 10, sortOrder);
                if(diaries == null || diaries.size() == 0){
                    break;
                }else{
                    total += diaries.size();
                }
            }
            System.out.println("总条数-->"+total);
            return total;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getNotIds(){
        List<String> idList = new ArrayList<String>();
        idList.add("1");
        idList.add("4");
        idList.add("3");
        List<String> notIds = new ArrayList<String>();
        List<User> userList = userService.list();
        for(User user : userList){
            String user_id = user.getUserId()+"";
            if(!idList.contains(user_id))   //如果不包含
                notIds.add(user.getUserId()+"");
        }
        System.out.println(notIds);
    }

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 测试redis
     */
    @Test
    public void redisTestSet(  ) {

        ValueOperations ops = redisTemplate.opsForValue();
        ops.set( "username", 41);
        System.out.println();
        redisTestGet(  );
    }

    @Test
    public void redisTestGet(  ) {
        ValueOperations ops = redisTemplate.opsForValue();
        Object username = ops.get("username");
        System.out.println("username : " + username);
        Object name = ops.get( "age" );
        System.out.println( "age: " + name );
    }

    @Autowired
    private GetUserFromRedisUtil userFromRedisUtil;


    //    // 测试redis
    @Test
    public Object redisTest( String key ) {
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set( "age", key );          // 设置键值
        Object obj = ops.get( key );   // 获取redis值
        System.out.println( obj );
        return obj;
    }

    @Test
    public void TestToken() throws IOException {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpbk5hbWUiOiIxMjM0NTYiLCJwYXNzd29yZE1kNSI6ImUxMGFkYzM5NDliYTU5YWJiZTU2ZTA1N2YyMGY4ODNlIiwiZXhwIjoxNjY5NzEwOTU4LCJ1c2VySWQiOiIxIn0.NT7TF_ZU-lMbyfFS60UaxQujh-0-GUIt38G5v6fur_c";
        ValueOperations ops = redisTemplate.opsForValue();
        Object username = ops.get(token);
        System.out.println( "value of token : " + username );

        //从redis中通过token获取用户信息
        User userFromRedis = userFromRedisUtil.getUserFromRedis(token);

        Integer userId = userFromRedis.getUserId();
        System.out.println( userId );
        System.out.println( userFromRedis.getLoginName() );
        System.out.println( userFromRedis );
    }



}
