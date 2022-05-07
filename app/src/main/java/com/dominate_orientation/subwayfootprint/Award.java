package com.dominate_orientation.subwayfootprint;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Award implements Parcelable {
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

    public Award() {
    }

    public Award(String aid, String variety, int num, String name, String content, int credit, String fromdate, String todate, String mid, int status) {
        this.aid = aid;
        this.variety = variety;
        this.num = num;
        this.name = name;
        this.content = content;
        this.credit = credit;
        this.fromdate = fromdate;
        this.todate = todate;
        this.mid = mid;
        this.status = status;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

//    private String aid;//奖品所属批次id
//    private String variety;//奖品种类
//    private int num;//该批次奖品剩余数量
//    private String name;//奖品名称
//    private String content;//奖品内容
//    private int credit;//兑换所需碳积分
//    private String fromdate;//奖品上传时间
//    private String todate;//奖品有效期
//    private String mid;//(若为商户上传)商户id
//    private int status;//奖品当前状态（0正常/1下架/2售空等）

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getAid());
        dest.writeString(getVariety());
        dest.writeInt(getNum());
        dest.writeString(getName());
        dest.writeString(getContent());
        dest.writeInt(getCredit());
        dest.writeString(getFromdate());
        dest.writeString(getTodate());
        dest.writeString(getMid());
        dest.writeInt(getStatus());
    }

    public static final Creator<Award> CREATOR=new Creator<Award>() {
        @Override
        public Award createFromParcel(Parcel parcel) {
            return new Award(parcel.readString(),parcel.readString(),parcel.readInt(),
                    parcel.readString(),parcel.readString(),parcel.readInt(),
                    parcel.readString(),parcel.readString(),parcel.readString(),parcel.readInt());
        }

        @Override
        public Award[] newArray(int i) {
            return new Award[i];
        }
    };
}
