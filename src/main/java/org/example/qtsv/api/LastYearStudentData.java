package org.example.qtsv.api;

import org.example.qtsv.entity.LastYearStudentEntity;

import java.util.List;

public class LastYearStudentData {
    private long totalElements;
    private int totalPages;
    private int size;
    private Object content;

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public LastYearStudentData(long totalElements, int totalPages, int size, List<LastYearStudentEntity> content) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.size = size;
        this.content = content;
    }
}
