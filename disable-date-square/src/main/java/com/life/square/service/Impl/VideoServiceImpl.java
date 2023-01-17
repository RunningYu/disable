package com.life.square.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.life.square.common.RestClient;
import com.life.square.dao.VideoMapper;
import com.life.square.pojo.Video;
import com.life.square.service.IVideoService;
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
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

    @Autowired
    private RestClient restClient;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private RestHighLevelClient client;

    //从索引库中获取视频的路径
    public List<String> getPathById(String name, String diaryId) {
        try {
            String index = "tb_disable_diary_videos";
            SearchHit[] hits = restClient.boolQuery_termQuery_getAllByOneText(index,name,diaryId);
            List<String> path = new ArrayList<String>();
            //反序列
            int n = 0;
            for(SearchHit hit : hits){
                n++;
                //获取文档source
                String json = hit.getSourceAsString();
                //反序列化
                Video video = JSON.parseObject(json,Video.class);
                path.add(video.getVideoPath());
//                System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
//                System.out.println(n + " 获取到的视频路径为 --> "+video.getVideoPath());
//                System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
            }
            System.out.println(path);
            return path;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //添加视频
    public void insertVideo(Integer id,String videoId, String diary_id,String diary_user_id, String name) {
        videoMapper.insertVideo(id,videoId,diary_id,diary_user_id,name);
    }

    //同步动态的视频到索引库
    public void insertDiaryVideosToIndexById(String videoId) {
        try {
            String index = "tb_disable_diary_videos";
            System.out.println("---- String index = tb_disable_diary_videos ->>");
            Video video = videoMapper.getDiaryVideoById(videoId);
            System.out.println("---->"+video.getCreateTime());

            //1.准备Request对象
            IndexRequest request = new IndexRequest(index).id(video.getId().toString());
            //2.准备Json文档
            request.source(JSON.toJSONString(video), XContentType.JSON);
            //3.发送请求
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //同步删除动态的视频
    public void deleteDiaryVideosFromIndexById(String videoId) {
        String index = "tb_disable_diary_videos";
        System.out.println("String index = \"tb_disable_diary_videos\";");
        Video video = videoMapper.getDiaryVideoById(videoId);
        String id = video.getId()+"";
        System.out.println("————————————>"+id);
        restClient.DeleteDocument(index,id);
    }

    //索引库中中找到动态的视频
    public Video getDiaryVideos(String diaryId) {
        try {
            String index = "tb_disable_diary_videos";
            String name = "diaryId";
            SearchHit[] hits = restClient.boolQuery_termQuery_getAllByOneText(index, name,diaryId);
            List<Video> list = new ArrayList<Video>();
            //反序列
            int n = 0;
            for(SearchHit hit : hits){
                n++;
                //获取文档source
                String json = hit.getSourceAsString();
                //反序列化
                Video video = JSON.parseObject(json,Video.class);
                list.add(video);
//                System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
//                System.out.println(n + " 获取到的视频路径为 --> "+video.getVideoPath());
//                System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
            }
            if(list.size() > 0)
                return list.get(0);
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //从数据库中删除动态视频
    public void deleteDiaryVideo(String diaryId) {
        videoMapper.deleteDiaryVideo(diaryId);
    }

    //获取一个动态视频
    public Video getDiaryVideoById(String videoId) {
        return videoMapper.getDiaryVideoById(videoId);
    }
}
