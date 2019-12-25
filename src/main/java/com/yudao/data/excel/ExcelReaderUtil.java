package com.yudao.data.excel;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReaderUtil {
 
    //excel2003扩展名
    public static final String EXCEL03_EXTENSION = ".xls";
    //excel2007扩展名
    public static final String EXCEL07_EXTENSION = ".xlsx";
 
 
 
    /**
     * @Author cjw
     * @Description 获取整个Excel的内容
     * @Date 10:18 2019/1/23
     * @Param [file]
     * @return 目前最多只读取三个sheet，可以自行扩展
     **/
    public static ImportExcelData readExcel(MultipartFile file) throws Exception {
        // 获取文件名
        if (file == null) {
            return null;
        }
        String fileName = file.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        if (!prefix.toLowerCase().contains("xls") && !prefix.toLowerCase().contains("xlsx")) {
            throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
        }
        // 防止生成的临时文件重复
        final File excelFile = File.createTempFile(System.currentTimeMillis() + "", prefix);
        file.transferTo(excelFile);
 
        Map<String, List<List<String>>> result = new HashMap<>();
        if (fileName.endsWith(EXCEL03_EXTENSION)) { //处理excel2003文件
            ExcelXlsReader excelXls = new ExcelXlsReader();
            result = excelXls.process(excelFile);
        } else if (fileName.endsWith(EXCEL07_EXTENSION)) {//处理excel2007文件
            ExcelXlsxReader excelXlsxReader = new ExcelXlsxReader();
            result = excelXlsxReader.process(excelFile);
        } else {
            throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
        }
        ImportExcelData data = new ImportExcelData();
        for (Map.Entry<String, List<List<String>>> entry : result.entrySet()) {
            if ("first".equals(entry.getKey())) {
                data.setFirstSheetList(result.get(entry.getKey()));
            }
            if ("second".equals(entry.getKey())) {
                data.setSecondSheetList(result.get(entry.getKey()));
            }
            if ("third".equals(entry.getKey())) {
                data.setThirdSheetList(result.get(entry.getKey()));
            }
        }
        //删除临时转换的文件
        if (excelFile.exists()) {
            excelFile.delete();
        }
        return data;
    }

    public static ImportExcelData readExcel2(File file) throws Exception {
        // 获取文件名
        if (file == null) {
            return null;
        }
        String fileName = file.getName();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        if (!prefix.toLowerCase().contains("xls") && !prefix.toLowerCase().contains("xlsx")) {
            throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
        }

        Map<String, List<List<String>>> result = new HashMap<>();
        if (fileName.endsWith(EXCEL03_EXTENSION)) { //处理excel2003文件
            ExcelXlsReader excelXls = new ExcelXlsReader();
            result = excelXls.process(file);
        } else if (fileName.endsWith(EXCEL07_EXTENSION)) {//处理excel2007文件
            ExcelXlsxReader excelXlsxReader = new ExcelXlsxReader();
            result = excelXlsxReader.process(file);
        } else {
            throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
        }
        ImportExcelData data = new ImportExcelData();
        for (Map.Entry<String, List<List<String>>> entry : result.entrySet()) {
            if ("first".equals(entry.getKey())) {
                data.setFirstSheetList(result.get(entry.getKey()));
            }
            if ("second".equals(entry.getKey())) {
                data.setSecondSheetList(result.get(entry.getKey()));
            }
            if ("third".equals(entry.getKey())) {
                data.setThirdSheetList(result.get(entry.getKey()));
            }
        }
        return data;
    }

    public static void main(String[] args) throws Exception {

        String file = System.getProperty("user.dir") + "/files/books_en.xls";

        File file1 = new File(file);

        ImportExcelData data = readExcel2(file1);
        for(String s : data.getFirstSheetList().get(1)){
            System.out.println(s);
        }


    }
 
 
}