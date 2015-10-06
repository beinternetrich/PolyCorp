package com.mmtechworks.polygam101;

//import android.content.SharedPreferences;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

import startup.StoryDialog;

public class GameActivity extends BaseActivity {
    String strGameValues;
    HashMap<String, String> hGameValues = new HashMap<>();
    boolean nuGamer;

    //private WScratchView scratchView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR); //if utilizing actionBar.setTitle
        setContentView(R.layout.activity_game);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            Log.v("LOG_GA27", "Got Extras");
            if(extras == null) {Log.v("LOG_GA28", "Extras are NULL");}
            else {
                //this follows dbh:completei(). Now split for setHead
                nuGamer = Boolean.valueOf(extras.getString("game_new"));
                strGameValues = extras.toString();
                strGameValues = strGameValues.substring(8, strGameValues.length()-2); //remove curly braces
                 Log.v("LOG_GA35", "EXTRAS: "+strGameValues);
                 String[] keyValuePairs = strGameValues.split(",");         //split string for key-value pairs
                    for(String pair : keyValuePairs)  {                     //iterate over the pairs
                    String[] dbValue = pair.split("=");                     //split pairs for key + value
                    hGameValues.put(dbValue[0].trim(), dbValue[1].trim());  //add to hashmap and trim.
                }
            }
        } else {
            savedInstanceState.getSerializable("game_values");
        }


        Log.v("LOG_GA_ShowStory", String.valueOf(nuGamer));
        if (nuGamer){
            FragmentScratch goScratchFrag = new FragmentScratch();
            goScratchFrag.setRetainInstance(true);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_main, goScratchFrag, "MY_FRAGTAG");
            fragmentTransaction.addToBackStack("MY_FRAGTAG");
            fragmentTransaction.commit();
            Log.v("LOG_GA_Scratch70", "AApostcommit");


            StoryDialog storyDialog = new StoryDialog();
            storyDialog.setRetainInstance(true);
            storyDialog.show(getFragmentManager(), "activity_storydialog");
            Log.v("LOG_GA58-TOGL", String.valueOf(nuGamer));
            nuGamer=false;
        } else {
            FragmentMain goMainFrag = new FragmentMain();
            goMainFrag.setRetainInstance(true);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_main, goMainFrag, "MY_FRAGTAG");
            fragmentTransaction.addToBackStack("MY_FRAGTAG");
            fragmentTransaction.commit();
            Log.v("LOG_GA_Scratch70", "AApostcommit");
        }

        Log.v("LOG_GA48", "HeadVals: " + hGameValues.toString());
        setHeading(hGameValues);

        //String myDataArray[];
        //get data back from SharedPreferences
//        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, 0);
//        if ( prefs.contains("Avatar")){
//            //String GameName = prefs.getString("ktestscore", "NOT FOUND");
//            //showUName.setText(showUName.getText() + " " + userName);
//            Toast.makeText(getApplicationContext(), prefs.getString("ktestscore", "NOT FOUND"), Toast.LENGTH_LONG).show();
//        }else {
//            //showUName.setText(showUName.getText() + " " + "Gamer");
//            Toast.makeText(getApplicationContext(), "FiddlePrefsHere", Toast.LENGTH_LONG).show();
//        }

//===========================
        ActionBar actionBar = getActionBar();
        if(actionBar!=null) actionBar.setTitle("Is whe da magic appens!!! (Prefs)");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.v("LOG_GA58", "GA-Calling super.SaveInstance...");
        super.onSaveInstanceState(savedInstanceState);
        Log.v("LOG_GA60", "GA-Called super.SaveInstance...");
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState. Bundle passed to onCreate.
        Log.v("LOG_GA89", "Called super.onRestoreI ok." + showJmz.getText());
    }


    public void selectFrag(View view) {
        Log.v("LOG_TAG6", "Frag Selection....");
        Fragment frag;
        switch (view.getId()) {
            case R.id.btnGotoMain:
                frag = new FragmentMain();
                break;
            case R.id.btnGotoBusiness:
                frag = new FragmentBusiness();
                break;
            case R.id.btnGotoUtility:
                frag = new FragmentUtility();
                break;
            case R.id.btnGotoScratch:
                frag = new FragmentScratch();
                break;
            case R.id.btnGotoMarket:
                //Intent i = new MyBusinessList();
                frag = new FragmentMain();
                break;
            default:
                frag = new FragmentMain();
                break;
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main, frag, "MY_FRAGTAG");
        fragmentTransaction.addToBackStack("MY_FRAGTAG");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed(){
        //Log.v("LOG_GActivity", "my backstack");
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            //Log.v("LOG_GActivity", "popping backstack");
            fragmentManager.popBackStack();
        } else {
            //Log.v("LOG_GActivity", "nothing backstack, calling super");
            super.onBackPressed();
        }
    }
}