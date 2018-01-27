package com.example.azero.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by azero on 18-1-5.
 */
class ViewHolder extends RecyclerView.ViewHolder {
    private View root;
    private TextView textHead,textOption;
    private ImageView ImV;
    public ViewHolder(View root) {
        super(root);
        textHead = root.findViewById(R.id.textViewHead);
        textOption = root.findViewById(R.id.textViewOptions);
        ImV = root.findViewById(R.id.file_dir);
    }

    public ImageView getImV() {
        return ImV;
    }

    public TextView getTextHead() {
        return textHead;
    }

    public TextView getTextOption() {
        return textOption;
    }

}
