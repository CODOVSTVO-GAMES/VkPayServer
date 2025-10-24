package ru.codovstvo.srvadmin.dto;

public class GamerDTO {
    public long userId;
    public int amountPurchases;
    public int scoreEvent;
    public String mark;
    public String frameAwatar;
    public String userName;
    public String avatar;
    public String platform;
    public Long installDate;
    public Long lastSessionDate;

    @Override
    public String toString() {
        return "GamerDTO{" +
                "userId=" + userId +
                ", amountPurchases=" + amountPurchases +
                ", scoreEvent=" + scoreEvent +
                ", mark='" + mark + '\'' +
                ", frameAwatar='" + frameAwatar + '\'' +
                ", userName='" + userName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", platform='" + platform + '\'' +
                ", installDate=" + installDate +
                ", lastSessionDate=" + lastSessionDate +
                '}';
    }
}
