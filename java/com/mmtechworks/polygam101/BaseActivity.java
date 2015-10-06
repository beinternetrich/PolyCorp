package com.mmtechworks.polygam101;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import java.util.HashMap;


abstract class BaseActivity extends Activity {
    protected TextView showTtl;
    protected TextView showLvl;
    protected TextView showXps;
    protected TextView showDzh;
    protected TextView showJmz;
    protected TextView showTps;

    public void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR); //if utilizing actionBar.setTitle
        super.onCreate(savedInstanceState);
        Log.v("LOG_BA28", "BA-End Calling super.onCreate SaveI");
//        String s = getJSONFile();
//        String myDataArray[];
/*
        try{
            JSONObject rootJSON=new JSONObject(s);
            JSONArray unitJSON = rootJSON.getJSONArray("");
            myDataArray = new String[unitJSON.length()];
            for(int i=0; i<unitJSON.length(); i++){
                JSONObject scoreObject = unitJSON.getJSONObject(i);
                myDataArray[i] = scoreObject.getString("game_xp_count");
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
*/

        //Lets display the array in  XP TextView
       // mListView=(ListView) findViewById(R.id.myListView);
        //showXP.setText("B" + showXP.getText());
        //ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.row,myDataArray);
 //       if(showXP != null){ showXP.setText("Y" + showXP.getText()); }
 //       if(showXP == null){ showXP.setText("N" + showXP.getText()); }
        ActionBar actionBar = getActionBar();
        if(actionBar!=null) actionBar.setTitle("Surplus...");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.v("LOG_B60", "BA-Calling Super.onSaveI(with SavedI)");
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState. Bundle passed to onCreate on process kill/restart.
        Log.v("LOG_BA64", "BA-Called Super.onSaveI(with SavedI)");
        //savedInstanceState.putBoolean("bolStorGrn", greenBop);
        //savedInstanceState.putString("strStorTtl", (String) showTtl.getText());
        savedInstanceState.putString("strStorLvl", (String) showLvl.getText());
        savedInstanceState.putString("strStorXps", (String) showXps.getText());
        savedInstanceState.putString("strStorDzh", (String) showDzh.getText());
        savedInstanceState.putString("strStorJmz", (String) showJmz.getText());
        savedInstanceState.putString("strStorTps", (String) showTps.getText());
        Log.v("LOG_BA75", "5 x SaveInstances populated - " + savedInstanceState.getString("strStorJmz"));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.v("LOG_Ba79RestorI", "Calling super.onRestore...");
        super.onRestoreInstanceState(savedInstanceState);
        Log.v("LOG_Ba81RestorI", "Called super.onRestore ok");
        // Restore UI from savedInstanceState. Bundle passed to onCreate.
        //showTtl.setText(savedInstanceState.getString("strStorTtl"));
        showLvl.setText(savedInstanceState.getString("strStorLvl"));
        showXps.setText(savedInstanceState.getString("strStorXps"));
        showDzh.setText(savedInstanceState.getString("strStorDzh"));
        showJmz.setText(savedInstanceState.getString("strStorJmz"));
        showTps.setText(savedInstanceState.getString("strStorTps"));
        Log.v("LOG_Ba91Resti", "ggot savedI-strings..");
    }

    protected void setHeading(HashMap<String, String> hssGameValues) {
        if(showDzh == null) {
            //showTtl = (TextView) findViewById(R.id.sTitle);
            showLvl = (TextView) findViewById(R.id.iLevel);
            showXps = (TextView) findViewById(R.id.iXps);
            showDzh = (TextView) findViewById(R.id.iDozh);
            showJmz = (TextView) findViewById(R.id.iJemz);
            showTps = (TextView) findViewById(R.id.iLevel);
            Log.v("LOG_BA96", "JMZ set next from #>"+showJmz.getText());
        }
		//change to else
        if(showDzh != null) {
            //showTtl.setText(hssGameValues.get("game_ttl"));
            showLvl.setText(hssGameValues.get("game_lvl"));
            showXps.setText(hssGameValues.get("game_xps"));
            showDzh.setText(hssGameValues.get("game_dzh"));
            showJmz.setText(hssGameValues.get("game_jmz"));
            showTps.setText(hssGameValues.get("game_tps"));
            Log.v("LOG_BA106", "HeadVals set-1");
        }
    }
}