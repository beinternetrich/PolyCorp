package com.mmtechworks.polygam101;

//import android.content.SharedPreferences;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.HashMap;


public class GameActivity extends BaseActivity {
    String strGameValues;
    HashMap<String, String> hGameValues = new HashMap<>();
    boolean nuGamer;

    public void onCreate(Bundle savedInstanceState) {
		Log.v("LOG_GA18", "GA-Calling super.onCreate...");
        super.onCreate(savedInstanceState);
        //final Button btnOpenPopup = (Button)findViewById(R.id.openpopup);
		Log.v("LOG_GA20", "GA-End Calling super.onCreate. GA Calling SetContentView.");
        setContentView(R.layout.activity_game);
        Log.v("LOG_GA18", "GA ContentView loaded...");

        if (savedInstanceState == null) {
            Log.v("LOG_GA25", "SaveI is NULL. Getting Extras");
            Bundle extras = getIntent().getExtras();
            Log.v("LOG_GA27", "Got Extras");
            if(extras == null) {Log.v("LOG_GA28", "Extras are NULL");}
            else {
                //nuGamer = getIntent().getExtras().getBoolean("greenBop");
                nuGamer = extras.getBoolean("greenBop");
                strGameValues = extras.getString("game_values");
                strGameValues = strGameValues.substring(1, strGameValues.length()-1); //remove curly braces
                Log.v("LOG_GA30", "GV1:"+strGameValues);
                String[] keyValuePairs = strGameValues.split(",");          //split string for key-value pairs
                    for(String pair : keyValuePairs)  {                     //iterate over the pairs
                    String[] dbValue = pair.split("=");                     //split pairs for key + value
                    hGameValues.put(dbValue[0].trim(), dbValue[1].trim());  //add to hashmap and trim.
                }
            }
        } else {
            Log.v("LOG_GA40", "XXXXSaveI not NULL. Getting SavedSerialized");
            //strGameValues = (String) savedInstanceState.getSerializable("game_values");
            savedInstanceState.getSerializable("game_values");
// // // // //ABOVE CALLED IN RESTORE SO NOT NECESSARY HERE. IS IT????
            Log.v("LOG_GA43", "SavedInstance NOT NULL. Got Serializable GV:");
        }

        if (nuGamer){
            FragmentManager fm = getFragmentManager();
            StoryDialog storyDialog = new StoryDialog();
            storyDialog.setRetainInstance(true);
            storyDialog.show(fm, "activity_story");
            Log.v("LOG_GA_onCreate-Greenie", String.valueOf(nuGamer));
            nuGamer=false;
            Log.v("LOG_GA_onCreate-Greenie", String.valueOf(nuGamer));
        }

        Log.v("LOG_GA48", "Moved on.  Now after intents...");
        Log.v("LOG_GA48", "Going SettingHeading:hGV:"+hGameValues.toString());


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
    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.v("LOG_GA58", "GA-Calling super.SaveInstance...");
        super.onSaveInstanceState(savedInstanceState);
        Log.v("LOG_GA60", "GA-Called super.SaveInstance...");
        // Save UI state changes to the savedInstanceState. Bundle passed to onCreate if process killed/restarted.
        Log.v("LOG_GA71", "Saved Instance Saved. " + showJmz.getText());
        // etc.
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState. Bundle passed to onCreate.
        Log.v("LOG_GA89", "Called super.onRestoreI ok."+showJmz.getText());
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