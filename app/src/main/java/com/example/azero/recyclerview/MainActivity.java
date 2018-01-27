package com.example.azero.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private MyAdapter myad;
    private String UserName;
    private String UserPassword;
    private String Courseware;
    private SharedPreferences prefer;
    private Mis m;
    MaterialDialog dialog;
    public static Object FromString(String s) {
        try {
            ByteArrayInputStream bin = new ByteArrayInputStream(Base64.decode(s, Base64.DEFAULT));
            ObjectInputStream oos = new ObjectInputStream(bin);
            return oos.readObject();
        } catch (Exception e) {
            Log.e("dfsdf",e.toString());
            return null;
        }
    }

    public static String ToString(Serializable o) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            oos.close();
            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        }
    }

    public static void NetError(Context con){
        new AlertDialog.Builder(con)
                .setTitle(R.string.LoginError_Title)
                .setMessage(R.string.LoginError_content)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
    }

    private void SaveMis(){
        SharedPreferences.Editor PreEd = prefer.edit();
        PreEd.putString(getString(R.string.ConfMis), ToString(m));
        PreEd.commit();
    }
//    private ArrayList<TreeNode> writefile() {
//        try {
//            //Mis m = new Mis("16281036", "122036");
//            m.LoginMis();
//            m.LoginKcpt();
//            ArrayList<TreeNode> TN = m.GetCourseList();
//            return TN;
//        } catch (SocketTimeoutException e) {
//
//        } catch (Exception e) {
//
//        }
////        try {
//////            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//////            StrictMode.setThreadPolicy(policy);
//////            session_alone s = new session_alone();
//////            response res = s.IsGET("https://mis.bjtu.edu.cn/");
//////            Document doc_login = Jsoup.parse(res.getIs(), "utf-8", "");
//////            doc_login.select("input[value]");
//////            s.IsPost(res.getUrl(), doc_login.select("input[value]").get(0).attributes().get("name") + "=" + URLEncoder.encode(doc_login.select("input[value]").get(0).attributes().get("value"))
//////                    + "&" + doc_login.select("input[value]").get(1).attributes().get("name") + "=" + URLEncoder.encode(doc_login.select("input[value]").get(1).attributes().get("value"))
//////                    + "&" + "loginname=16281036"
//////                    + "&" + "password=122036"
//////            );
//////            res = s.IsGET("https://mis.bjtu.edu.cn/module/module/280/");
//////            Document doc = Jsoup.parse(res.getIs(), "utf-8", "hTTps://mis.bjtu.edu.cn/");
//////            doc.select("input[value]");
//////            s.IsPost("http://cc.bjtu.edu.cn:81/meol/homepage/common/sso_login_portal.jsp", doc.select("input[value]").get(0).attributes().get("name") + "=" + doc.select("input[value]").get(0).attributes().get("value")
//////                    + "&" + doc.select("input[value]").get(1).attributes().get("name") + "=" + doc.select("input[value]").get(1).attributes().get("value")
//////                    + "&" + doc.select("input[value]").get(2).attributes().get("name") + "=" + doc.select("input[value]").get(2).attributes().get("value"));
//////            ArrayList<TreeNode> TN = jsoupParse(s.IsGET("http://cc.bjtu.edu.cn:81/meol/lesson/blen.student.lesson.list.jsp").getIs(), "http://cc.bjtu.edu.cn:81/meol/lesson/blen.student.lesson.list.jsp", s);
//////
////            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/people.txt";
////            Log.d("Systtt", path);
////            File file = new File(path);
////            ObjectOutputStream fos;
////            //file.createNewFile();
////            //获取输出流
////            //这里如果文件不存在会创建文件，这是写文件和读文件不同的地方
////            fos = new ObjectOutputStream(new FileOutputStream(file));
////            fos.writeObject(new config(TN));
////            Log.d("Systtt", "序列化成功");
////            for (TreeNode e : TN) e.show();
////            return TN;
////        } catch (Exception e) {
////            Log.e("error", e.toString());
////            return null;
////        }
//
//    }

//    private ArrayList<TreeNode> readfile() {
//
////        try {
////            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/people.txt";
////            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(path)));
////            config TN = (config) ois.readObject();
////            return TN.getTN();
////        } catch (Exception e) {
////            Log.d("error", e.toString());
////            return null;
////        }
//
//    }
    private class progress{
        private float progress;
        private String name;

    public progress(float progress, String name) {
        this.progress = progress;
        this.name = name;
    }

    public float getProgress() {
        return progress;
    }

    public String getName() {
        return name;
    }
}
    private void RfreshList(){
        new AsyncTask<Void,progress,Integer>(){
            @Override
            protected Integer doInBackground(Void... voids) {
                try {
                    if(!m.LoginMis()){

                    };
                    m.LoginKcpt();
                    InputStream is = m.getS().IsGET("http://cc.bjtu.edu.cn:81/meol/lesson/blen.student.lesson.list.jsp").getIs();
                    String url = "http://cc.bjtu.edu.cn:81/meol/lesson/blen.student.lesson.list.jsp";
                    Document doc = Jsoup.parse(is, "gbk", url);
                    Elements le = doc.select("#table2>tbody>tr>td>a:not(.moveup):not(.movedown)");
                    ArrayList<TreeNode> CourseList = new ArrayList<TreeNode>();
                    for (Element a : le) {
                        Elements Te = a.getElementsByTag("td");
                        Pattern r = Pattern.compile("init_course.jsp\\?lid=(\\d*)");
                        Matcher mr = r.matcher(a.attributes().get("href"));
                        mr.find();
                        CourseList.add(new TreeNode("http://cc.bjtu.edu.cn:81/meol/common/script/listview.jsp?acttype=enter&folderid=0&lid=" + mr.group(1), a.text(),m.getS() , 0,true));
                        publishProgress(new progress((float)(le.indexOf(a)+1)/le.size(),a.text()));
                    }
                    //m.getNewCourseList();
                    m.setCourseList(CourseList);
                    SaveMis();

                }
                catch (SocketTimeoutException e){

                }
                catch (Exception e){

                }

                return null;
            }

            @Override
            protected void onProgressUpdate(progress... values) {
                //Toast.makeText(getApplicationContext(),"获取"+values[0].getName()+"列表完毕，已完成"+values[0].getProgress()*100+"%",Toast.LENGTH_SHORT).show();
                dialog.incrementProgress((int)(values[0].getProgress()*100)-dialog.getProgressBar().getProgress());
                dialog.setContent("正在获取"+values[0].getName()+"课件列表");
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPreExecute() {
                Toast.makeText(getApplicationContext(),"获取列表中",Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Integer integer) {
               myad.getData().addAll(m.getCourseList());
               myad.notifyDataSetChanged();
               dialog.dismiss();
                Toast.makeText(getApplicationContext(),"获取列表完毕",Toast.LENGTH_SHORT).show();
               super.onPostExecute(integer);
            }
        }.execute();
    }

    private void CheckCourseList() {
        if (m.getCourseList() == null) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.GetCourseList_title)
                    .setMessage(R.string.GetCourseList_content)
                    .setPositiveButton(R.string.GetCourseList_ButtonOk, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            rv = (RecyclerView) findViewById(R.id.Recy);
                            rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            myad = new MyAdapter(new ArrayList<TreeNode>());
                            rv.setAdapter(myad);
                            dialog = new MaterialDialog.Builder(MainActivity.this)
                                    .title("正在获取课件列表")
                                    .content("正在登录课程平台")
                                    .progress(false, 100, true)
                                    .cancelable(false)
                                    .show();
                            //dialog.set
                            RfreshList();
                        }
                    })
                    .setNegativeButton(R.string.GetCourseList_ButtonCancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).show();
        } else {
            rv = (RecyclerView) findViewById(R.id.Recy);
            rv.setLayoutManager(new LinearLayoutManager(this));
            myad = new MyAdapter(m.getCourseList());
            rv.setAdapter(
                    myad);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            prefer = getSharedPreferences(getString(R.string.config), MODE_PRIVATE);
            String S_m = prefer.getString(getString(R.string.ConfMis), null);
            if (S_m != null) {
                m = (Mis)FromString(S_m);
                CheckCourseList();

            } else {
                startActivityForResult(new Intent(this, LoginActivity.class), 1);
            }

        } catch (Exception e) {
            Log.e("error", e.toString());
            return;
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==1&&resultCode==1) {
            m = (Mis) FromString(data.getStringExtra(getString(R.string.ConfMis)));
            SaveMis();
            CheckCourseList();
        }

    }

    @Override
    public void onBackPressed() {
        myad.Return();
        //super.onBackPressed();

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Alerting Message")
                        .setMessage("search")
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do nothing - it will close on its own
                            }
                        })
                        .show();
                return true;
            case R.id.action_notifications:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Alerting Message")
                        .setMessage("search")
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do nothing - it will close on its own
                            }
                        })
                        .show();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }

    }
}
