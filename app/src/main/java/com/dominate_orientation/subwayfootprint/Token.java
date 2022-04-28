package com.dominate_orientation.subwayfootprint;

import android.app.Application;

public class Token extends Application {
    private String token = "";

    public void setToken(String token_) {
        this.token = token_;
    }
    public String getToken() {
        return this.token;
    }
}
