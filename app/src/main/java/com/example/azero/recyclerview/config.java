package com.example.azero.recyclerview;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Created by azero on 18-1-20.
 */
class config implements Serializable {
//    Properties config;
    ArrayList<TreeNode> TN;

    public config(ArrayList<TreeNode> TN) {
        this.TN = TN;
    }

    public ArrayList<TreeNode> getTN() {
        return TN;
    }
}
