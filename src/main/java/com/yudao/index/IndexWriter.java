package com.yudao.index;

import com.yudao.constant.Constant;
import com.yudao.data.excel.ImportExcelData;
import com.yudao.entity.Book;
import com.yudao.entity.News;
import com.yudao.utils.DateUtils;
import com.yudao.utils.LucenceUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.yudao.data.excel.ExcelReaderUtil.readExcel2;

/**
 * 功能1：导入数据，批量新建索引，根据id自动更新索引。
 * 功能2：新增或修改单条索引。
 * 功能3：删除单条或多条索引。
 *
 * Created by guangzhi.wang on 2019/12/4/004.
 */
public class IndexWriter {

    public static void main(String[] args) {

        loadData();
    }

    private static void loadData(){
        String file = System.getProperty("user.dir") + "/files/books_en.xls";
        File file1 = new File(file);
        try {
            org.apache.lucene.index.IndexWriter writer = LucenceUtils.getWriter(FSDirectory.open(Paths.get(Constant.INDEX_PATH)));
            ImportExcelData data = readExcel2(file1);
            for (List<String> item : data.getFirstSheetList()) {
                String no = item.size()>0?item.get(0):"";
                String index = item.size()>1?item.get(1):"";
                String name = item.size()>2?item.get(2):"";
                String author = item.size()>3?item.get(3):"";
                String publisher = item.size()>4?item.get(4):"";
                String count = item.size()>5?item.get(5):"";
                String address = item.size()>6?item.get(6):"";

                Book book = new Book(no, index, name, author, publisher, count, address);

                Document doc = new Document();
                doc.add(new StringField("name",book.getName(), Field.Store.YES));
                doc.add(new StringField("author",book.getAuthor(), Field.Store.YES));
                doc.add(new StringField("publisher",book.getPublisher(), Field.Store.YES));
                doc.add(new StringField("count",book.getCount(), Field.Store.YES));
                doc.add(new StringField("address",book.getAddress(), Field.Store.YES));
                //用于排序
                doc.add(new StringField("index", book.getIndex(), Field.Store.YES));
                doc.add(new StringField("id" , book.getNo(), Field.Store.YES));

                Term id=new Term("id",book.getNo());

                writer.updateDocument(id,doc);
            }

            LucenceUtils.closeWriter(writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
