package com.yudao.utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;

import java.io.IOException;

/**
 * Created by guangzhi.wang on 2019/12/4/004.
 */
public class LucenceUtils {
    /**
     * 获取索引写
     * @param dir
     * @return
     * @throws Exception
     */
    public static org.apache.lucene.index.IndexWriter getWriter(Directory dir) {
        org.apache.lucene.index.IndexWriter writer = null;
        try {
            Analyzer analyzer = new StandardAnalyzer(); // 标准分词器
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            writer = new org.apache.lucene.index.IndexWriter(dir, iwc);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return writer;
    }

    public static void closeWriter(org.apache.lucene.index.IndexWriter writer) {
        if(writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
