package com.mihoyo.hk4e.wechat.dto;


public class FileUploader {
    // image voice video file
    private String type;

    private String filePath;
    private String fileName;


    public FileUploader(String type, String filePath) {
        this.type = type;
        this.filePath = filePath;

        String[] fileNameList = filePath.split("/");
        this.fileName = fileNameList[fileNameList.length - 1];
//        System.out.println(this.fileName);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
