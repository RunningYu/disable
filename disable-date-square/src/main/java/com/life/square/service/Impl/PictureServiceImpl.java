package com.life.square.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.life.square.common.RestClient;
import com.life.square.dao.PictureMapper;
import com.life.square.pojo.Picture;
import com.life.square.service.IPictureService;
import org.elasticsearch.action.bulk.BulkRequest;
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
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements IPictureService {

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private RestClient restClient = new RestClient();
    @Autowired
    private RestHighLevelClient client;

    //添加照片到照片表
    public void insertPicture(Integer id,String diary_id, String name, String diary_user_id, String pic_path, Integer status) {
        pictureMapper.insertPicture(id, diary_id,  name,  diary_user_id,  pic_path,  status);
    }

    //根据用id去找图像路径
    public List<String> getPathById(String name, String id) throws IOException {
//        String index = "tb_disable_pictures";
        String index = "tb_disable_diary_pictures";
        SearchHit[] hits = restClient.boolQuery_termQuery_getAllByOneText(index,name,id);
        List<String> path = new ArrayList<String>();
        //反序列
        int n = 0;
        for(SearchHit hit : hits){
            n++;
            //获取文档source
            String json = hit.getSourceAsString();
            //反序列化
            Picture picture = JSON.parseObject(json,Picture.class);
            path.add(picture.getPicPath());
//            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
//            System.out.println(n + " 获取到的照片路径为 --> "+picture.getPicPath());
//            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
        }
        return path;
    }

    //根据动态dirayId找出所有的配图并新增到索引库
    public void insertPicToIndexById(String diaryId) {
        System.out.println("【根据动态dirayId找出所有的配图并新增到索引库】");
        try {
            //批量查询酒店的数量
            List<Picture> pictures = getPicturesByDiaryId(diaryId);

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
                        .source(JSON.toJSONString(picture), XContentType.JSON));
            }

            //3.发送请求
            client.bulk(request, RequestOptions.DEFAULT);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //从数据库中删除动态的所有配图
    public void deletePictures(String diaryId) {
        pictureMapper.deletePictures(diaryId);
    }

    //根据动态dirayId从数据库中查找出动态的所有配图
    public List<Picture> getPicturesByDiaryId(String diaryId) {
        return pictureMapper.getAllPictureBydiaryId(diaryId);
    }

    //从索引库中删除动态的所有配图
    public void deleteDiaryFromIndexById(String id) {
//        String index = "tb_disable_pictures";
        String index = "tb_disable_diary_pictures";
        restClient.DeleteDocument(index,id);
    }

//    //根据动态dirayId找出所有的配图
//    private List<Picture> getAllPictureBydiaryId(String diaryId) {
//        return pictureMapper.getAllPictureBydiaryId(diaryId);
//    }
}
