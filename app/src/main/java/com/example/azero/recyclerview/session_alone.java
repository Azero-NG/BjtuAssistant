package com.example.azero.recyclerview;

/**
 * Created by azero on 18-1-19.
 */


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.cert.X509Certificate;
import java.net.CookieHandler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

//error setInstanceFollowRedirects *********post vs get//
public class session_alone {
    private static final int TryTime=10;
    private URL Uurl;
    private String refer;
    private CookieManager cm;// cm = new CookieManager();
    private String SCookie = "";
    public session_alone()  {
        //default cookie manager
        cm = new CookieManager();
        CookieHandler.setDefault(cm);
        //auto refer
        refer = "";

//        //https不检查证书
//        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
//            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                return null;
//            }
//
//            public void checkClientTrusted(X509Certificate[] certs, String authType) {
//            }
//
//            public void checkServerTrusted(X509Certificate[] certs, String authType) {
//            }
//        }
//        };
//
//        // Install the all-trusting trust manager
//        SSLContext sc = SSLContext.getInstance("SSL");
//        sc.init(null, trustAllCerts, new java.security.SecureRandom());
//        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//
//        // Create all-trusting host name verifier
//        HostnameVerifier allHostsValid = new HostnameVerifier() {
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        };
//
//        // Install the all-trusting host verifier
//        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

    }
    private HttpURLConnection GetConn(String url) {
        try {
            Uurl = new URL(url);
            if (Uurl.getProtocol().equals("https")) {
                return (HttpsURLConnection) Uurl.openConnection();
            } else {
                return (HttpURLConnection) Uurl.openConnection();
            }
        }
        catch (Exception e){
            return null;
        }
    }


    public response IsGET(String url)  throws SocketTimeoutException,Exception{
        for(int i=0;i<TryTime;i++)
        try {
            HttpURLConnection conn = GetConn(url);
            conn.setDoInput(true);
            conn.setRequestProperty("Referer", refer);
            refer = url;
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setInstanceFollowRedirects(false);//手动跳转
            InputStream is = conn.getInputStream();
            if (conn.getResponseCode() == 302) {
                String next_url = conn.getHeaderField("Location").toLowerCase();
                if (next_url.substring(0, 4).equals("http"))
                    return IsGET(next_url);
                else
                    return IsGET(conn.getURL().getProtocol() + "://" + conn.getURL().getAuthority() + next_url);
            } else {
                System.out.println(conn.getURL());
                return new response(conn.getURL().toString(), is);
            }
        }
        catch (java.net.SocketTimeoutException e)
        {
        }
        catch (Exception e){
            Log.e("error",e.toString());
            return null;
        }
        throw new SocketTimeoutException();
    }

    public response IsPost(String url, String data) throws SocketTimeoutException,Exception {
        for(int i=0;i<TryTime;i++)
        try {
            HttpURLConnection conn = GetConn(url);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoOutput(true);
            conn.setRequestProperty("Referer", refer);
            //conn.setInstanceFollowRedirects(false);
            refer = url;
            conn.getOutputStream().write(data.getBytes());
            InputStream is = conn.getInputStream();
            if (conn.getResponseCode() == 302) {
                String next_url = conn.getHeaderField("Location").toLowerCase();
                if (next_url.substring(0, 4).equals("http"))
                    return IsGET(next_url);
                else
                    return IsGET(conn.getURL().getProtocol() + "://" + conn.getURL().getAuthority() + next_url);
            } else {
                return new response(conn.getURL().toString(), is);
            }
        }
        catch (java.net.SocketTimeoutException e)
        {
        }
        catch (Exception e){
            Log.e("error",e.toString());
            return null;
        }
        throw new SocketTimeoutException();
    }

}
