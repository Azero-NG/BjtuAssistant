package com.example.azero.recyclerview;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by azero on 18-1-25.
 */

public class Mis implements Serializable{
    private String UserName;
    private String UserPass;
    private static session_alone s;
    private ArrayList<TreeNode> CourseList=null;

    public Mis() {
    }

    public Mis(String userName, String userPass) {
        UserName = userName;
        UserPass = userPass;
    }

    public static session_alone getS() {
        return s;
    }

    public void setCourseList(ArrayList<TreeNode> courseList) {
        CourseList = courseList;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setUserPass(String userPass) {
        UserPass = userPass;
    }

    public Boolean LoginMis() throws SocketTimeoutException {
        try {
            s = new session_alone();
            response res = s.IsGET("https://mis.bjtu.edu.cn/");
            Document doc_login = Jsoup.parse(res.getIs(), "utf-8", "https://mis.bjtu.edu.cn/");
            InputStream result = s.IsPost(res.getUrl(), doc_login.select("input[value]").get(0).attributes().get("name") + "=" + URLEncoder.encode(doc_login.select("input[value]").get(0).attributes().get("value"))
                    + "&" + doc_login.select("input[value]").get(1).attributes().get("name") + "=" + URLEncoder.encode(doc_login.select("input[value]").get(1).attributes().get("value"))
                    + "&" + "loginname=" + UserName
                    + "&" + "password=" + UserPass
            ).getIs();

            Scanner scanner = new Scanner(result, "UTF-8");
            String text = scanner.useDelimiter("\\A").next();
            scanner.close();
            if (text.indexOf("用户密码不正确") != -1)
                return false;
            else if (text.indexOf(UserName) != -1)
                return true;
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (Exception e) {

            Log.e("error",e.toString());
        }
        return false;
    }

    public void LoginKcpt() throws SocketTimeoutException {
        try {

            response res = s.IsGET("https://mis.bjtu.edu.cn/module/module/280/");
            Document doc = Jsoup.parse(res.getIs(), "utf-8", "hTTps://mis.bjtu.edu.cn/");
            doc.select("input[value]");
            s.IsPost("http://cc.bjtu.edu.cn:81/meol/homepage/common/sso_login_portal.jsp", doc.select("input[value]").get(0).attributes().get("name") + "=" + doc.select("input[value]").get(0).attributes().get("value")
                    + "&" + doc.select("input[value]").get(1).attributes().get("name") + "=" + doc.select("input[value]").get(1).attributes().get("value")
                    + "&" + doc.select("input[value]").get(2).attributes().get("name") + "=" + doc.select("input[value]").get(2).attributes().get("value"));

        }
        catch (SocketTimeoutException e){
            throw e;
        }
        catch (Exception e){
        }

    }

    public ArrayList<TreeNode> getCourseList() {
        return CourseList;
    }

    public ArrayList<TreeNode> getNewCourseList() throws Exception {
        InputStream is = s.IsGET("http://cc.bjtu.edu.cn:81/meol/lesson/blen.student.lesson.list.jsp").getIs();
        String url = "http://cc.bjtu.edu.cn:81/meol/lesson/blen.student.lesson.list.jsp";
        Document doc = Jsoup.parse(is, "gbk", url);
        Elements le = doc.select("#table2>tbody>tr>td>a:not(.moveup):not(.movedown)");
        CourseList = new ArrayList<TreeNode>();
        for (Element a : le) {
            Elements Te = a.getElementsByTag("td");
            Pattern r = Pattern.compile("init_course.jsp\\?lid=(\\d*)");
            Matcher m = r.matcher(a.attributes().get("href"));
            m.find();
            CourseList.add(new TreeNode("http://cc.bjtu.edu.cn:81/meol/common/script/listview.jsp?acttype=enter&folderid=0&lid=" + m.group(1), a.text(), s, 0,true));
        }

        return CourseList;
    }
}
