package com.example.azero.recyclerview;

/**
 * Created by azero on 18-1-19.
 */


import java.io.InputStream;

public class response {
    private String url;
    private InputStream is;

    public InputStream getIs() {
        return is;
    }

    public String getUrl() {
        return url;
    }

    public response(String url, InputStream is) {
        this.url = url;
        this.is = is;
    }

}