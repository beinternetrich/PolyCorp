package com.mmtechworks.polygam101;
//import android.content.Context;
//import android.content.SharedPreferences;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import util.SysDatabaseList;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button buttonLoginAccount;
    private Button buttonCreateAccount;
    private Button buttonListDatabases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letsbegin);

        buttonCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        buttonCreateAccount.setOnClickListener(this);
        buttonLoginAccount = (Button) findViewById(R.id.btnLoginAccount);
        buttonLoginAccount.setOnClickListener(this);
        buttonListDatabases = (Button) findViewById(R.id.btnListDatabases);
        buttonListDatabases.setOnClickListener(this);

///*
//        TextView showUName  = (TextView) findViewById(R.id.showGameName);
//        inputUName = (EditText) findViewById(R.id.inputGameName);
//        Button savedNuName = (Button) findViewById(R.id.btnSave);
//        Button btnToStory  = (Button) findViewById(R.id.btnGotoStory);
//
//        savedNuName.setOnClickListener(new View.OnClickListener(){
//           @Override
//           public void onClick(View v) {
//           if (inputUName.getText().toString().equals("")){
//               Toast.makeText(getApplicationContext(), "Enter your Gamename.", Toast.LENGTH_LONG).show();
//           }else {
//               writeToPrefs("kgamename", inputUName.getText().toString());
//               writeToPrefs("ktestscore", "seventytwo");
//               String data = inputUName.getText().toString();
//               writeToFile(data);
//
//               Intent i = new Intent (MainActivity.this, StoryActivity.class);
//               i.putExtra("kgamename", data);
//               startActivity(i);
//           }}
//        });
//
//        btnToStory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, StoryActivity.class));}
//        });
//        showUName.setText(readFromPrefs("kgamename"));
//
//        if (readFromFile() != null){
//            inputUName.setText(readFromFile());
//        }else {
//            Toast.makeText(getApplicationContext(), "Read from file was NULL", Toast.LENGTH_LONG).show();
//        }
//*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoginAccount: {
                Log.v("LOG_MAINA-Login", "follows");
                startActivity(new Intent(MainActivity.this, LoginAccountActivity.class));
                break;
            }
            case R.id.btnCreateAccount:{
                Log.v("LOG_MAINA-Create", "follows");
                startActivity(new Intent(MainActivity.this, CreateAccountActivity.class));
                Log.v("LOG_MAINA-Create", "done");
                break;
            }
            case R.id.btnListDatabases:{
                Log.v("LOG_MAINA-ListDbs", "follows");
                startActivity(new Intent(MainActivity.this, SysDatabaseList.class));
                Log.v("LOG_MAINA-ListDbs", "done");
                break;
            }
        }
    }


///*
//    private void writeToFile(String myData){
//        try{
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("PG101SettingsFil.txt", Context.MODE_PRIVATE));
//            outputStreamWriter.write(myData);
//            outputStreamWriter.close(); // always close your streams!
//        }catch(IOException e){
//            Log.v("MyActivity", e.toString());
//        }
//    }
//    private String readFromFile(){
//        String result = "";
//        try{
//            InputStream inputStream = openFileInput("PG101SettingsFil.txt");
//            if ( inputStream != null){
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//                String tempString;
//                StringBuilder stringBuilder = new StringBuilder();
//                while ( (tempString = bufferedReader.readLine()) != null){
//                    stringBuilder.append(tempString);
//                }
//
//                inputStream.close();
//                result = stringBuilder.toString();
//            }
//        }catch (FileNotFoundException e){
//            Log.v("MyActivity", "File not found" + e.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//    public String readFromPrefs(String myKey){
//        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, 0);
//        if ( prefs.contains(myKey)){
//            //String userName = prefs.getString("ktestscore", "NOT FOUND");
//            //showUName.setText(showUName.getText() + " " + userName);
//            Toast.makeText(getApplicationContext(), prefs.getString(myKey, "NOT FOUND"), Toast.LENGTH_LONG).show();
//        }else {
//            //showUName.setText(showUName.getText() + " " + "Gamer");
//            Toast.makeText(getApplicationContext(), "NAAAAA", Toast.LENGTH_LONG).show();
//        }
//        return prefs.getString(myKey, "NOT FOUND");
//    }
//    public void writeToPrefs(String myKey, String myPair){
//        SharedPreferences myPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
//        SharedPreferences.Editor editor = myPrefs.edit();
//        editor.putString(myKey, myPair);
//        editor.commit();
//    }
//*/
}