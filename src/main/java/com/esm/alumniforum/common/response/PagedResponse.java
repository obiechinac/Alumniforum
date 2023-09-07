package com.esm.alumniforum.common.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class PagedResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;
    private boolean first;
    private boolean empty;

    public PagedResponse() {}


    public PagedResponse(List<T> content, int page, int size, long totalElements, int totalPages, boolean last, boolean first, boolean empty) {
        setContent(content);
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
        this.first = first;
        this.empty = empty;
    }
    public List<T> getContent() {
        return content == null ? null : List.copyOf(content);
    }

    public final void setContent(List<T> content) {
        this.content = content.isEmpty() ? null : List.copyOf(content);
    }

}
