package ru.otus.model;

public enum TaskType {
    PRINT("printTaskHandler"),
    HTTP_GET("httpGetTaskHandler"),
    HTTP_POST("httpPostTaskHandler");

    private String title;

    TaskType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
