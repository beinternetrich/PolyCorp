package com.mmtechworks.polygam101;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Footer extends Activity {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        Log.v("LOG_TAG6", "77F00011");
        return inflater.inflate(R.layout.footer, container, false);
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.v("LOG_TAG6", "77F00022");
//        setContentView(R.layout.footer);
//    }

}
