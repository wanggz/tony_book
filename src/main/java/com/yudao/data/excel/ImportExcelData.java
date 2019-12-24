package com.yudao.data.excel;

import java.util.List;

public class ImportExcelData {

    //第一个sheet
    private List<List<String>> firstSheetList;

    //第二个sheet
    private List<List<String>> secondSheetList;

    //第三个sheet
    private List<List<String>> thirdSheetList;

    public List<List<String>> getFirstSheetList() {
        return firstSheetList;
    }

    public void setFirstSheetList(List<List<String>> firstSheetList) {
        this.firstSheetList = firstSheetList;
    }

    public List<List<String>> getSecondSheetList() {
        return secondSheetList;
    }

    public void setSecondSheetList(List<List<String>> secondSheetList) {
        this.secondSheetList = secondSheetList;
    }

    public List<List<String>> getThirdSheetList() {
        return thirdSheetList;
    }

    public void setThirdSheetList(List<List<String>> thirdSheetList) {
        this.thirdSheetList = thirdSheetList;
    }
}