package com.life.square.common;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * 依赖于JIEBA的Maven依赖
 * 输入一个String类型的句子，调用结巴进行分词，返回一个List
 */

@Controller
public class JIEBA {

    public List<String> JIEBA(String text){
        JiebaSegmenter jieba = new JiebaSegmenter();
        List<String> list = jieba.sentenceProcess(text);
//        List<SegToken> res = segmenter.process(text, JiebaSegmenter.SegMode.INDEX);
        return list;
    }

}