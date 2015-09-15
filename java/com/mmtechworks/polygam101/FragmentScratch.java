package com.mmtechworks.polygam101;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import scratch.WScratchView;

public class FragmentScratch extends Fragment {
    private WScratchView scratchView;
    private TextView percentageView;
    private float mPercentage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment then return
        // //////////////return inflater.inflate(R.layout.fragment_scratch, container, false);
        View view = inflater.inflate(R.layout.fragment_scratch, container, false);
        Drawable bg = getResources().getDrawable(R.drawable.mainroom02);
        RelativeLayout relativeLayout = (RelativeLayout)view.findViewById(R.id.fragment_scratch);
        relativeLayout.setBackground(bg);

        Log.v("LOG_GA_Scratch22", "1");
        percentageView = (TextView)view.findViewById(R.id.percentage);
        scratchView = (WScratchView)view.findViewById(R.id.scratch_view);
        Log.v("LOG_GA_Scratch22", "2");

        // set drawable to scratchview
        scratchView.setScratchDrawable(getResources().getDrawable(R.drawable.mainroom01));
        Log.v("LOG_GA_Scratch22", "3getdrawable mainroom01");

        // add callback for update scratch percentage
        scratchView.setOnScratchCallback(new WScratchView.OnScratchCallback() {
            @Override
            public void onScratch(float percentage) {
                Log.v("LOG_GA_Scratch22", "35-on scratch of 01");
                updatePercentage(percentage);
            }

            @Override
            public void onDetach(boolean fingerDetach) {
                if (mPercentage > 50) {
                    Log.v("LOG_GA_Scratch22", "42 ondetach of 01");
                    scratchView.setScratchAll(true);
                    updatePercentage(100);
                }
            }
        });
        updatePercentage(0f);
        //return inflater.inflate(R.layout.fragment_scratch, container, false);
        return view;
    }

    protected void updatePercentage(float percentage) {
        mPercentage = percentage;
        Log.v("LOG_GA_Scratch22", "Updateing percentatge");
        String percentage2decimal = String.format("%.2f", percentage) + " %";
        percentageView.setText(percentage2decimal);
    }
}
