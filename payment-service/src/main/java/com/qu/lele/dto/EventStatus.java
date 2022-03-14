package com.qu.lele.dto;

/**
 * @author: 屈光乐
 * @create: 2022-03-14 17-01
 */
public enum EventStatus {
    NewEvent("NEW","投递中"),
    PublishEvent("PUBLISH","已投递"),
    ConsumerEvent("Consume","已消费"),
    ProcessEvent("Process","已处理"),
    ;
    private String status;
    private String description;

    EventStatus(String status, String description) {
        this.status = status;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
