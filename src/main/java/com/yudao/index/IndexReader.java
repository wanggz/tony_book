package com.yudao.index;

import com.yudao.constant.Constant;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
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
        int field = 0;//查找域
        String keyword = "印度";//关键字

        search(pageStart,pageSize,field,keyword);

        //search2();

    }

    private static void search(int pageStart, int pageSize, int field, String keyword){

        String[] fields;
        switch (field){
            case 0:
                fields = new String[]{"name", "author", "publisher"};
            case 1:
                fields = new String[]{"name"};
            case 2:
                fields = new String[]{"author"};
            case 3:
                fields = new String[]{"publisher"};
            default:
                fields = new String[]{"name", "author", "publisher"};
        }

        BooleanClause.Occur [] flags = new BooleanClause.Occur[]{BooleanClause.Occur.MUST,BooleanClause.Occur.MUST,BooleanClause.Occur.MUST};
        Analyzer analyzer = new StandardAnalyzer();

        MultiFieldQueryParser parser=new MultiFieldQueryParser(fields,analyzer);

        try {
            Query query=parser.parse(keyword,fields,flags,analyzer);

            Directory dir = FSDirectory.open(Paths.get(Constant.INDEX_PATH));
            org.apache.lucene.index.IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher search = new IndexSearcher(reader);

            //------------查询
            //searcher.search(query, LuceneManagerImpl.DEFAULT_QUERY_NUM);
            int numHits = search.count(query);
            TopDocs topDocs = search.search(query,pageSize);
            System.out.println(topDocs.totalHits);

            if(numHits > 0) {
                //按照时间倒序
                Sort sort = new Sort();
                //sort.setSort(new SortField("id", SortField.Type.STRING, false));//倒序

                //分页
                TopFieldCollector c = TopFieldCollector.create(sort, pageStart+pageSize, false, false, false);
                search.search(query, c);
                ScoreDoc[] hits = c.topDocs(pageStart, pageSize).scoreDocs;
                System.out.println("匹配[" + keyword + "],总共查询到" +  numHits + "个文档.");
                if(hits.length > 0) {
                    for (int i = 0; i < hits.length; i++) {
                        int docID = hits[i].doc;
                        Document doc = search.doc(docID);
                        System.out.println("文档ID:" + docID + "  书ID:" + doc.get("id")
                                + "   书名:" + doc.get("name") + "  作者:" + doc.get("author")
                                +"  出版社:" + doc.get("publisher") + "  数量:" + doc.get("count")
                                + "  地址:" + doc.get("address"));
                    }
                }
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private static void search2(){
        int pageStart = 0;//开始ID
        int pageSize = 10;//每页大小
        String field = "name";//查找域
        String keywords = "印度";//关键字
        String sortField = "id";//排序关键字

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
                //sort.setSort(new SortField(sortField, SortField.Type.STRING, true));//倒序

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
                                + "   新闻标题:" + doc.get("name") + "  新闻内容:" + doc.get("publisher")
                                +"  时间:" + doc.get("count"));
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
