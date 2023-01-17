package com.life.square;

import com.alibaba.fastjson.JSON;
import com.life.square.common.Save;
import com.life.square.pojo.BasicInfo;
import com.life.square.pojo.User;
import com.life.square.service.IDiaryService;
import com.life.square.dao.UserMapper;
import com.life.square.pojo.Diary;
import com.life.square.service.InfoService;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.life.square.constants.IndexLibraryConstants.MAPPING_TEMPLATE;

@SpringBootTest
public class InformationTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private IDiaryService diaryService;

    @Autowired
    private InfoService infoService;

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
//        Diary diary = diaryService.getById(1);
        BasicInfo basicInfo = infoService.getByPersonId(1);
//        Diary diary = diaryService.getDiaryById(2);
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("获取到的："+basicInfo);
        System.out.println("--------------------------------------------------------------------------");

        //转换为文档类型
        //        Diary hotelDoc = new HotelDoc(hotel);

        //1.准备Request对象
        IndexRequest request = new IndexRequest("tb_disable_date_person_basic_info").id(basicInfo.getPersonId().toString());
        //2.准备Json文档
        request.source(JSON.toJSONString(basicInfo),XContentType.JSON);
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
        DeleteRequest request = new DeleteRequest("disable-date","1");

        //2.备请求参数
        client.delete(request,RequestOptions.DEFAULT);
    }


    //批量添加
    @Test
    void testBulkRequest() throws IOException {
        //批量查询酒店的数量
        List<Diary> diaries = diaryService.list();

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("查询到的是diary ："+diaries);
        System.out.println("--------------------------------------------------------------------------");

//        for(Diary diary : diaries){
//            HotelDoc hotelDoc = new HotelDoc(hotel);
//        }

        //1.创建Request
        BulkRequest request = new BulkRequest();
        //2.准备参数，添加多个新增的Request
        for(Diary diary : diaries){
            //转换为文档类型的HotelDoc
//            HotelDoc hotelDoc = new HotelDoc(hotel);
            //创建新增文档的Request对象
            request.add(new IndexRequest("disable-date")
                    .id(diary.getId().toString())
                    .source(JSON.toJSONString(diary),XContentType.JSON));
        }

        //3.发送请求
        client.bulk(request,RequestOptions.DEFAULT);
    }

    //match_all查询
    @Test
    void testMatchAll() throws IOException {
        String index  = "tb_disable_date_diary";
        //1.准备Request
        SearchRequest request = new SearchRequest(index);
        //2.准备DSL                           match_all查询
        request.source().query(QueryBuilders.matchAllQuery());
        //3.发送请求
        SearchResponse response = client.search(request,RequestOptions.DEFAULT);

        //解析响应
        handleResponce(response);

        System.out.println(response);
    }

    //match查询
    @Test
    void testMatch() throws IOException {
        String index  = "tb_disable_date_diary";
        //1.准备Request
        SearchRequest request = new SearchRequest(index);
        //2.准备DSL                           match查询
        request.source().query(QueryBuilders.matchQuery("diaryTitle","行业"));
        //3.发送请求
        SearchResponse response = client.search(request,RequestOptions.DEFAULT);

        //解析响应
        handleResponce(response);

        System.out.println(response);
    }

    //bool查询
    @Test
    void testBool() throws IOException {
        String index  = "tb_disable_date_diary";
        //1.准备Request
        SearchRequest request = new SearchRequest(index);

        //2.准备DSL
        //2.1准备BooleanQuery
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();


        //2.2 添加term                term                 为什么“行业”就查不到，只有“业”可以，因为这是term精确查询，
//        boolQuery.must(QueryBuilders.termQuery("diaryTitle","IT行业"));
        boolQuery.must(QueryBuilders.matchQuery("diaryTitle","IT"));
        //2.3添加 range
//        boolQuery.filter(QueryBuilders.rangeQuery("price").lte(250));

        request.source().query(boolQuery);
        //3.发送请求
        SearchResponse response = client.search(request,RequestOptions.DEFAULT);

        handleResponce(response);

        System.out.println(response);
    }

    //解析响应
    private void handleResponce(SearchResponse response) {
        //4.解析响应
        SearchHits searchHits = response.getHits();
        //4.1获取总条数
        long total = searchHits.getTotalHits().value;
        System.out.println("共搜索到"+total+"条数据");
        //4.2文档数据
        SearchHit[] hits = searchHits.getHits();
        //4.3遍历
        for(SearchHit hit : hits){
            //获取文档source
            String json = hit.getSourceAsString();
            //反序列化
            Diary diary = JSON.parseObject(json,Diary.class);
            System.out.println("------------------------------------------------------------------------");
            System.out.println("diary = "+diary);
            System.out.println("------------------------------------------------------------------------");
        }
    }
}
