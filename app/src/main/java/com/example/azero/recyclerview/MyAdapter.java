package com.example.azero.recyclerview;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by azero on 17-12-30.
 */
class MyAdapter extends RecyclerView.Adapter {
    private ViewGroup parent;
    private ArrayList<TreeNode> data;
    private Stack<ArrayList<TreeNode>> seq;
    public MyAdapter(ArrayList<TreeNode> mydatabase ) {
        data = mydatabase;
        seq = new Stack<ArrayList<TreeNode>>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cell,null));
    }

    public ArrayList<TreeNode> getData() {
        return data;
    }

    public void Return(){
        if(!seq.empty()) {
            data = seq.pop();
            notifyDataSetChanged();
        }
        else return;
    }

    public void setData(ArrayList<TreeNode> data) {
        this.data = data;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        TreeNode cd = data.get(position);
        ViewHolder vh = (ViewHolder)holder;
        vh.getTextHead().setText(cd.getName());

        if(cd.getIsDir()){
            vh.getImV().setImageResource(R.drawable.ic_folder_black_48x);
            vh.getTextHead().setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            seq.push(data);
                            ArrayList<TreeNode> b = (new ArrayList<TreeNode>(data.get(position).getChildDir()));
                            b.addAll(data.get(position).getChildFile());
                            data = b;
                            notifyDataSetChanged();
                        }
                    }
            );
        }
        else {
            vh.getImV().setImageResource(R.drawable.ic_insert_drive_file_black_48px);
            vh.getTextHead().setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new AlertDialog.Builder(parent.getContext())
                                    .setTitle("Alerting Message")
                                    .setMessage("准备下载啦")
                                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //do nothing - it will close on its own
                                        }
                                    })
                                    .show();
                        }
                    }
            );
        }
        vh.getTextOption().setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(parent.getContext())
                                .setTitle("Alerting Message")
                                .setMessage("eek!")
                                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //do nothing - it will close on its own
                                    }
                                })
                                .show();
                    }
                }
        );

    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
