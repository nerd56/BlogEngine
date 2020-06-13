package main.dto;

import java.util.List;

public class CountListResponse {
    private int count;
    private List list;

    public CountListResponse(int count, List list) {
        this.count = count;
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public List getList() {
        return list;
    }
}
