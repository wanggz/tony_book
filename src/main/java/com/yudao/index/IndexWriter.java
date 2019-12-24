package com.yudao.index;

import com.yudao.constant.Constant;
import com.yudao.entity.News;
import com.yudao.utils.DateUtils;
import com.yudao.utils.LucenceUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能1：导入数据，批量新建索引，根据id自动更新索引。
 * 功能2：新增或修改单条索引。
 * 功能3：删除单条或多条索引。
 *
 * Created by guangzhi.wang on 2019/12/4/004.
 */
public class IndexWriter {

    public static void main(String[] args) {

        List<News> news = new ArrayList<>();
        News news1 = new News();
        news1.setId(1);
        news1.setTitle("中国对大胜日本队");
        news1.setContent("这是一条足球新闻");
        news1.setDatetime("2017-01-12 12:12:12");

        News news2 = new News();
        news2.setId(2);
        news2.setTitle("中国对大胜美国队");
        news2.setContent("这是一条篮球新闻");
        news2.setDatetime("2017-01-01 12:01:13");

        News news3 = new News();
        news3.setId(3);
        news3.setTitle("食品安全的问题");
        news3.setContent("这食品安全新闻");
        news3.setDatetime("2011-02-22 09:11:12");

        News news4 = new News();
        news4.setId(4);
        news4.setTitle("中国好声音");
        news4.setContent("这是一条娱乐新闻");
        news4.setDatetime("2017-01-12 10:12:12");

        News news5 = new News();
        news5.setId(6);
        news5.setTitle("我爱你中国");
        news5.setContent("这是一首歌");
        news5.setDatetime("2011-01-11 01:10:12");

        news.add(news1);
        news.add(news2);
        news.add(news3);
        news.add(news4);
        news.add(news5);
        try {
            org.apache.lucene.index.IndexWriter writer = LucenceUtils.getWriter(FSDirectory.open(Paths.get(Constant.INDEX_PATH)));
            for(News n : news) {
                Document doc = new Document();
                doc.add(new StringField("content",n.getContent(), Field.Store.YES));
                doc.add(new StringField("title",n.getTitle(), Field.Store.YES));
                doc.add(new StringField("datetime",n.getDatetime(), Field.Store.YES));
                //用于排序
                doc.add(new NumericDocValuesField("datetime", DateUtils.toDate(n.getDatetime(), DateUtils.YYYY_MM_DD_HH_MM_SS).getTime()));
                doc.add(new StringField("id" , n.getId()+"", Field.Store.YES));

                Term id=new Term("id",n.getId()+"");

                writer.updateDocument(id,doc);
            }
            LucenceUtils.closeWriter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void loadData(){

    }



}
