package com.dominate_orientation.subwayfootprint;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class route2bury implements Serializable {
    LinkedHashMap<String,String> paraMap;
    String presentCity;
    String presentPosition;

    public route2bury(LinkedHashMap<String, String> paraMap, String presentCity, String presentPosition) {
        this.paraMap = paraMap;
        this.presentCity = presentCity;
        this.presentPosition = presentPosition;
    }

    public LinkedHashMap<String, String> getParaMap() {
        return paraMap;
    }

    public void setParaMap(LinkedHashMap<String, String> paraMap) {
        this.paraMap = paraMap;
    }

    public String getPresentCity() {
        return presentCity;
    }

    public void setPresentCity(String presentCity) {
        this.presentCity = presentCity;
    }

    public String getPresentPosition() {
        return presentPosition;
    }

    public void setPresentPosition(String presentPosition) {
        this.presentPosition = presentPosition;
    }
}
