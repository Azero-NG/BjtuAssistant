package com.example.azero.recyclerview;

/**
 * Created by azero on 18-1-19.
 */


import java.io.Serializable;
import java.util.ArrayList;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TreeNode implements Serializable{
    private ArrayList<TreeNode> ChildDir = new ArrayList<TreeNode>();
    private ArrayList<TreeNode> ChildFile = new ArrayList<TreeNode>();
    private String Name;
    private String url;
    private boolean IsDir;
    private static session_alone s = null;
    private int height = 0;

    public ArrayList<TreeNode> getChildFile() {
        return ChildFile;
    }

    public ArrayList<TreeNode> getChildDir() {
        return ChildDir;
    }
    public boolean getIsDir(){
        return IsDir;
    }

    public TreeNode(String url, String Name, session_alone s, int height,boolean IsDir) throws Exception {
        this.url = url;
        this.Name = Name;
        this.height = height;
        if (this.s == null) //static s protect multi change
            this.s = s;
        this.IsDir = IsDir;
        if (IsDir == true)
            FindChild();
    }

    public String getName() {
        return Name;
    }

    public int FindChild() throws Exception {
        s.IsGET(url);
        Document doc = Jsoup.parse(s.IsGET(url).getIs(), "gbk", url);
        Elements EChildDir = doc.select(".valuelist>tbody>tr>td>a[title=\"\"]:not([target])");
        for (Element e : EChildDir) {
            ChildDir.add(new TreeNode("http://cc.bjtu.edu.cn:81/meol/common/script/" + e.attributes().get("href"), e.text(), s, height + 1,true));
        }
        Elements EChildFile = doc.select(".valuelist>tbody>tr>td>a[target=\"_blank\"]");
        for (Element e : EChildFile) {
            ChildFile.add(new TreeNode("http://cc.bjtu.edu.cn:81/meol/common/script/" + e.attributes().get("href"), e.text(), s,height + 1 ,false));
        }
        return 1;
    }

    public void show() {
        //for (int i = 0; i < height; i++)
            //System.out.print("  ");
//        System.out.println(Name);
        if(IsDir) {
            for (TreeNode e : ChildDir) e.show();
            for (TreeNode e : ChildFile) e.show();
        }
        Log.d("lesson",Name);
    }

}

