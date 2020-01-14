package com.yudao.index;

import com.yudao.entity.Book;
import com.yudao.entity.Result;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
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
        int pageNoNow = 1;
        int pageSize = 10;//每页大小
        String field = "题名";//查找域
        String keyword = "中国藏学";//关键字

        Result result = search(pageStart,pageSize,pageNoNow,field,keyword);
        System.out.println(result.getNumHits());
        System.out.println(result.getBooks().get(0).getName());

    }

    public static Result search(int pageStart, int pageSize, int pageNoNow,String field, String keyword){

        Result result = new Result();

        String indexField = "name";
        switch (field){
            case "全部":
                indexField = "name";
            case "题名":
                indexField = "name";
            case "责任者":
                indexField = "author";
            case "出版者":
                indexField = "publisher";
            default:
                indexField = "name";
        }

        String nkeyword = "*" + keyword + "*";
        Query query = new WildcardQuery(new Term(indexField, nkeyword));

        try {
            //获取path
            String path = Thread.currentThread().getContextClassLoader().getResource("index").getPath();

            if(path.contains(":")){  // windows机器
                path = path.substring(1);
            }

            Directory dir = FSDirectory.open(Paths.get(path));
            org.apache.lucene.index.IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher search = new IndexSearcher(reader);

            int numHits = search.count(query);
            result.setNumHits(numHits);
            result.setPageStart(pageStart);
            result.setPageSize(pageSize);
            result.setPageNoNow(pageNoNow);
            int pageNoMax = numHits / pageSize + 1;
            result.setPageNoList(getPageList(pageNoNow,pageNoMax));


            List<Book> books = new ArrayList<>();
            if(numHits > 0) {
                //按照时间倒序
                Sort sort = new Sort();
                sort.setSort(new SortField("_id", SortField.Type.INT,true));//升序

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

    public static List<Integer> getPageList(int pageNoNow, int pageNoMax) {
        int begin = pageNoNow - 2 >=1 ? pageNoNow - 2 : 1;
        int end = pageNoNow +2 <= pageNoMax ? pageNoNow +2 : pageNoMax;

        List<Integer> pagelist = new ArrayList<>();
        if(begin == 1){
            int s_end = pageNoMax>5 ? 5 : pageNoMax;
            for(int i = 1; i <= s_end; i++) {
                pagelist.add(i);
            }
        } else {
            if(end == pageNoMax) {
                int s_begin = pageNoMax - 5 > 0 ? pageNoMax - 4 : 1;
                for(int i = s_begin; i <= pageNoMax; i++){
                    pagelist.add(i);
                }
            } else {
                for(int i = begin; i <= end; i++) {
                    pagelist.add(i);
                }
            }
        }

        return pagelist;
    }


    private static void test(){
        try {
            Directory dir = FSDirectory.open(Paths.get("D:/workspaces/booksearch_v2.1/tony_book/build/resources/main/index"));
            org.apache.lucene.index.IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher search = new IndexSearcher(reader);




        } catch (Exception e){

        }
    }

}
