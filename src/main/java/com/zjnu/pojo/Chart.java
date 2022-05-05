package com.zjnu.pojo;


public class Chart {
    private String count;
    private String data;

    public Chart(String count, String data) {
        this.count = count;
        this.data = data;
    }

    public Chart() {
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Chart{" +
                "count='" + count + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
