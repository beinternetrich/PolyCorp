package com.mmtechworks.polygam101;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class FragmentMain extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Drawable bg = getResources().getDrawable(R.drawable.mainroom02);
        RelativeLayout relativeLayout = (RelativeLayout)view.findViewById(R.id.fragment_main);
        relativeLayout.setBackground(bg);
        //return inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }
}
