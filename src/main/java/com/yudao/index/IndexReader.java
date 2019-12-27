package com.yudao.index;

import com.yudao.constant.Constant;
import com.yudao.entity.Book;
import com.yudao.entity.Result;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guangzhi.wang on 2019/12/4/004.
 */
public class IndexReader {

    public static void main(String[] args) {

        int pageStart = 0;//开始ID
        int pageSize = 10;//每页大小
        String field = "全部";//查找域
        String keyword = "印度";//关键字

        Result result = search(pageStart,pageSize,field,keyword);
        System.out.println(result.getNumHits());
        System.out.println(result.getBooks().get(0).getName());

        //search2();

    }

    public static Result search(int pageStart, int pageSize, String field, String keyword){

        Result result = new Result();

        String[] fields;
        switch (field){
            case "全部":
                fields = new String[]{"name", "author", "publisher"};
            case "题名":
                fields = new String[]{"name"};
            case "责任者":
                fields = new String[]{"author"};
            case "出版者":
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

            int numHits = search.count(query);
            result.setNumHits(numHits);

            List<Book> books = new ArrayList<>();
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

                        books.add(new Book(doc.get("id"),doc.get("index"),doc.get("name"),doc.get("author"),doc.get("publisher"),doc.get("count"),doc.get("address")));
                    }
                }
            }
            reader.close();
            result.setBooks(books);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }

    }


}
