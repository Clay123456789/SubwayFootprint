package com.dominate_orientation.subwayfootprint;

import java.io.Serializable;

public class Award implements Serializable {
    private String aid;//奖品所属批次id
    private String variety;//奖品种类
    private int num;//该批次奖品剩余数量
    private String name;//奖品名称
    private String content;//奖品内容
    private int credit;//兑换所需碳积分
    private String fromdate;//奖品上传时间
    private String todate;//奖品有效期
    private String mid;//(若为商户上传)商户id
    private int status;//奖品当前状态（0正常/1下架/2售空等）

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
