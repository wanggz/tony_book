package com.yudao.index;

import com.yudao.constant.Constant;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by guangzhi.wang on 2019/12/4/004.
 */
public class IndexReader {

    public static void main(String[] args) {

        int pageStart = 0;//开始ID
        int pageSize = 10;//每页大小
        String field = "title";//查找域
        String keywords = "中国";//关键字
        String sortField = "datetime";//排序关键字

        try {
            Directory dir = FSDirectory.open(Paths.get(Constant.INDEX_PATH));
            org.apache.lucene.index.IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher search = new IndexSearcher(reader);

//            //WildcardQuery 模糊查询  类似于mysql like %中国%
            keywords = "*" + keywords + "*";
            Query query = new WildcardQuery(new Term(field, keywords));
            //Query query = new TermQuery(new Term(field, keywords));
            int numHits = search.count(query);

            if(numHits > 0) {
                //按照时间倒序
                Sort sort = new Sort();
                sort.setSort(new SortField(sortField, SortField.Type.LONG, true));//倒序

                //分页
                TopFieldCollector c = TopFieldCollector.create(sort, pageStart+pageSize, false, false, false);
                search.search(query, c);
                ScoreDoc[] hits = c.topDocs(pageStart, pageSize).scoreDocs;
                System.out.println("匹配[" + keywords + "],总共查询到" +  numHits + "个文档.");
                if(hits.length > 0) {
                    for (int i = 0; i < hits.length; i++) {
                        int docID = hits[i].doc;
                        Document doc = search.doc(docID);
                        System.out.println("文档ID:" + docID + "  新闻ID:" + doc.get("id")
                                + "   新闻标题:" + doc.get("title") + "  新闻内容:" + doc.get("content")
                                +"  时间:" + doc.get("datetime"));
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
