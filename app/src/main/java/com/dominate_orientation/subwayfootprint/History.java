package com.dominate_orientation.subwayfootprint;

public class History {
    private String crid;
    private String uid;
    private  int operation;
    private String way;
    private String num;
    private  String balance;
    private String time;

     public  History(){

     }
    public History(String crid, String uid, int operation, String way, String num, String balance, String time) {
        this.crid = crid;
        this.uid = uid;
        this.operation = operation;
        this.way = way;
        this.num = num;
        this.balance = balance;
        this.time = time;
    }

    public String getCrid() {
        return crid;
    }

    public void setCrid(String crid) {
        this.crid = crid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int isOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
