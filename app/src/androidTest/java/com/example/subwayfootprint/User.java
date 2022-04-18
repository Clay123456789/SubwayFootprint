package com.example.zhuyihaotest1;

public class User {
    private String uName;
    private String uNumber;//用户碳积分数目
    private String uRank;//用户排名
    private int uIcon;  //头像

    public User(){

    }
    public User( String uName,String uNumber, String uRank,int uIcon){
        this.uIcon=uIcon;
        this.uName=uName;
        this.uNumber=uNumber;
        this.uRank=uRank;
    }
    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuRank() {
        return uRank;
    }

    public void setuRank(String uRank) {
        this.uRank = uRank;
    }

    public String getuNumber() {
        return uNumber;
    }

    public void setuNumber(String uNumber) {
        this.uNumber = uNumber;
    }

    public int getuIcon() {
        return uIcon;
    }

    public void setuIcon(int uIcon) {
        this.uIcon = uIcon;
    }
}

