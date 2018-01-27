package com.example.azero.recyclerview;

/**
 * Created by azero on 18-1-26.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

public class LoginActivity extends AppCompatActivity {
    private TextView User;
    private TextView Pass;
    private Button login;
    private Mis m;

    private void validate(Mis mis){
        new AsyncTask<Mis,Void,Boolean>(){
            @Override
            protected Boolean doInBackground(Mis... mis) {
                m.setUserName(User.getText().toString());
                m.setUserPass(Pass.getText().toString());
                try {
                    return m.LoginMis();
                }
                catch (SocketTimeoutException e){
                    MainActivity.NetError(LoginActivity.this);
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if(aBoolean){
                    Toast.makeText(getApplicationContext(),"登录mis系统成功",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.putExtra(getString(R.string.ConfMis), MainActivity.ToString(m));
                    setResult(1, i);
                    finish();
                }
                else {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle(R.string.LoginError_Title)
                            .setMessage(R.string.LoginError_content)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                }
                super.onPostExecute(aBoolean);
            }

            @Override
            protected void onPreExecute() {
                Toast.makeText(getApplicationContext(),"正在登录mis系统",Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }
        }.execute(mis);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        m = new Mis();
        User = findViewById(R.id.User);
        Pass = findViewById(R.id.password);
        login = findViewById(R.id.sign_in_);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(m);

            }
        });
    }

}
