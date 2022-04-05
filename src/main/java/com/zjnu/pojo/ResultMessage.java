package com.zjnu.pojo;

public class ResultMessage {
    private String fromName;
    private String isSystem;
    private String message;

    public ResultMessage(String fromName, String isSystem, String message) {
        this.fromName = fromName;
        this.isSystem = isSystem;
        this.message = message;
    }

    public ResultMessage() {
    }

    @Override
    public String toString() {
        return "ResultMessage{" +
                "fromName='" + fromName + '\'' +
                ", isSystem='" + isSystem + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
