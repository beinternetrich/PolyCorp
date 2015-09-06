package data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import model.MyBusiness;
//import com.parse.SignUpCallback;


public class DatabaseHandler extends SQLiteOpenHelper {
    private final ArrayList<MyBusiness> businessList = new ArrayList<>();
    HashMap<String, String> queryValues;
    //Parse.enableLocalDatastore(this);
    //Parse.initialize(this, Constants.APP_KEY_ID, Constants.APP_CLIENT_ID);

    public DatabaseHandler(Context appliccontext) {
        super(appliccontext, Constants.DBLOCAL_NAME, null, Constants.DBNEW_VERSION);
    }
    public void insertSaasUser(String getUsername, String getPassword, String getEmail){
        ParseUser user = new ParseUser();
        user.setUsername(getUsername);
        user.setPassword(getPassword);
        user.setEmail(getEmail);
        //set some custom properties like default score etc.
        //user.put("custom1", "V150728");

//        user.signUpInBackground(new SignUpCallback() {
//        public void done(ParseException e) {
//            if (e == null) {
//                //yyy oBtnCreateAccount.setEnabled(false);
//                //yyy vfEmailId.setEnabled(false);
//                //yyy vfUsernameId.setEnabled(false);
//                //yyy vfPasswordId.setEnabled(false);
//            } else {
//                //Toast.makeText(getApplicationContext(), "BOOOOO. CDNT LOGIN", Toast.LENGTH_LONG).show();
//            }
//        }});
    } //end void insertSaasUser()
    public void loginSaasUser(final String getUsername,final String getPassword){
        ParseUser.logInInBackground(getUsername, getPassword, new LogInCallback() {
        @Override
        public void done(ParseUser parseUser, ParseException e) {
            if (e == null) {
                Log.v("LOG_LOGINGOOD", "Hello " + parseUser.getUsername());
                //-Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                //getAccountInfo(getUsername, getPassword);
                //startActivity(new Intent(LoginAccountActivity.this, GameActivity.class));
            } else {
                Log.v("LOG_LOGINFAIL", "Try Again");
                //Toast.makeText(getApplicationContext(), "Login Failure", Toast.LENGTH_LONG).show();
            }
        }
        });
    }
    public boolean processDomUser(String action, final String getUsername, final String getPassword, final String getEmail) {
        //Boolean response;
        String[] params = {action + "-user:", getEmail, getPassword, getUsername};
        String POST_PARAMS = "email=" + params[1] + "&password=" + params[2] + "&username=" + params[3];
        //Create/Get db row===========================================
        Log.v("LOG_DBH87", POST_PARAMS);
        URL url = null;
        if (getEmail != null) {
            try {
                url = new URL("http://polygam101.beinternetrich.net" + "/e/pgsync/db_user" + action + ".php" + "?" + POST_PARAMS);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            if (url != null) {
                try {
                    conn = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (true) {
                try {
                    conn.setRequestMethod("POST");
                } catch (java.net.ProtocolException e) {
                    e.printStackTrace();
                }
            }
            if (true) {
                try {
                    Log.v("LOG_DBH118", "RespCode: " + conn.getResponseCode());
                } catch (IOException e) {
                    Log.v("LOG_DBH122", "Expected Response 200 - Failed");
                    e.printStackTrace();
                }
            }
            InputStream in = null;
            if (true) {
                try {
                    in = new BufferedInputStream(conn.getInputStream());
                    String response = IOUtils.toString(in, "UTF-8");
                    Log.e("LOG_DBH122", response);
                    if (response.toLowerCase().contains("success")) {return true;} else {return false;}
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
            //Row Created===========@@@blow ret true filter else false================
        } else {return true;}
    }  //end processDomUser() + return T/F

    public boolean pullDomUser(final String getUsername,final String getPassword){
        Log.v("LOG_PULLDUSER", "Getting Account Info");
        String[] params = {"get-user:", getUsername, getPassword};
        String POST_PARAMS = "username=" + params[1] + "&password=" + params[2];
        Log.v("LOG_PULLDUSER139", "Params: "+POST_PARAMS );
        //Retrieve db row===========================================


        URL url = null;
        if (getUsername != null) {
            try {
                url = new URL("http://polygam101.beinternetrich.net" + "/e/pgsync/db_user" + "get" + ".php" + "?" + POST_PARAMS);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.v("LOG_DBH-GETINFO", "URL OK");
            HttpURLConnection conn = null;
            if (url != null) {
                try {conn = (HttpURLConnection) url.openConnection();}
                catch (IOException e) {e.printStackTrace();}}
            if (true) {
                try {conn.setRequestMethod("POST");}
                catch (java.net.ProtocolException e) {e.printStackTrace();}}
            if (true) {
                try {Log.v("LOG_DBH159", "RespCode: " + conn.getResponseCode());
                } catch (IOException e) {
                    Log.v("LOG_DBH161", "Expected Response 200 - Failed");
                    e.printStackTrace();}}
            InputStream in = null;
            if (true) {
                try {in = new BufferedInputStream(conn.getInputStream());
                    String response = IOUtils.toString(in, "UTF-8");
                    Log.e("LOG_DBH167", response);
                    if (response.toLowerCase().contains(getUsername) && response.toLowerCase().contains(getPassword)) {
                        parseForLiteUser(response);
                        return true;
                    } else {
                        Log.v("LOG_DBH172", "DOM Pull Fail or output is empty");
                        //oBtnCreateAccount.setEnabled(false);
                        //vfEmailId.setEnabled(false);
                        //vfUsernameId.setEnabled(false);
                        //vfPasswordId.setEnabled(false);
                        //Log.v("LOG_DBH176", "DOM Pull Failed or output is empty");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
            //Row Retrieved===========================================================
        } else {return false;}
    }  //end pullDOMUser() + parseForLiteUser()
	public void parseForLiteUser(String result){
		Log.v("LOG_DBH", "Running parseForLiteUser....");
        try {
            // Extract JSON array from the response
            JSONArray jArray = new JSONArray(result);
            // If no of array elements is not zero
            if(jArray.length() != 0){
                // Loop through each array element, get JSON object which has userid and username
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonData = (JSONObject) jArray.get(i);

                    //-Build QueryValues to insert in Lite
                    queryValues = new HashMap<String, String>();
                    queryValues.put("customer_id",jsonData.get("customer_id").toString());
                    queryValues.put("last_name",  jsonData.get("last_name").toString());
                    queryValues.put("game_name",  jsonData.get("game_name").toString());
                    queryValues.put("game_lvl",   jsonData.get("game_lvl").toString());
                    queryValues.put("game_xps",   jsonData.get("game_xps").toString());
                    queryValues.put("game_dzh",   jsonData.get("game_dzh").toString());
                    queryValues.put("game_jmz",   jsonData.get("game_jmz").toString());
                    queryValues.put("game_tps",   jsonData.get("game_tps").toString());

                    Log.v("LOG_DBH", "Starting InsertLiteUser");
                    try {
                        insertLiteUser(queryValues);
                    } catch(Exception e) {}
                }
            } else {/*array.length=0. No users found.*/}
        } catch (JSONException e) {e.printStackTrace();}
    } //parseForLiteUser() + insertLiteUser()
    public boolean insertLiteUser(HashMap<String, String> queryValues) {
        Log.v("LOG_DBH", "Running insertLiteUser...");
        SQLiteDatabase db = this.getWritableDatabase();
        Log.v("LOG_DBH", "Values: "+String.valueOf(queryValues));
        ContentValues values = new ContentValues();
        values.put("customer_id",queryValues.get("customer_id"));
        values.put("last_name",  queryValues.get("last_name"));
        values.put("game_name",  queryValues.get("game_name"));
        values.put("game_lvl",   queryValues.get("game_lvl"));
        values.put("game_xps",   queryValues.get("game_xps"));
        values.put("game_dzh",   queryValues.get("game_dzh"));
        values.put("game_jmz", queryValues.get("game_jmz"));
        values.put("game_tps", queryValues.get("game_tps"));
        Log.v("LOG_DBH", "Calling: db.insert to table....");
        try {
            db.insert("gam_CubeCart_customer", null, values);
            db.close();
            syncLiteUser(queryValues.get("customer_id"));
            return true;
        } catch(Exception e) {
            db.close();
            Log.v("LOG_DBH", "db.insert to LITE - FAILED.");
            return false;
        }
    } //insertLiteUser() >> Boolean

    public void onCompleteI(String getUsername, Intent i) {
        //Intent i = new Intent(getApplicationContext(), GameActivity.class);
        HashMap<String, String> queryValues = getLiteUser(getUsername);
        i.putExtra("game_name", queryValues.get("game_name"));
        i.putExtra("game_lvl", queryValues.get("game_lvl"));
        i.putExtra("game_xps", queryValues.get("game_xps"));
        i.putExtra("game_dzh", queryValues.get("game_dzh"));
        i.putExtra("game_jmz", queryValues.get("game_jmz"));
        i.putExtra("game_tps", queryValues.get("game_tps"));
        i.putExtra("game_values", queryValues.toString());
        //Log.v("LOG", "Launching GameActivity: " + i.getStringExtra("game_values"));
        //startActivity(i);
    }

    public boolean updateLiteUser(HashMap<String, String> queryValues) {
        Log.v("LOG_DBH", "Running insertLiteUser...");
        SQLiteDatabase db = this.getWritableDatabase();
        Log.v("LOG_DBH", "Values: "+String.valueOf(queryValues));
        ContentValues values = new ContentValues();
        values.put("customer_id",queryValues.get("customer_id"));
        values.put("last_name",  queryValues.get("last_name"));
        values.put("game_name",  queryValues.get("game_name"));
        values.put("game_lvl",   queryValues.get("game_lvl"));
        values.put("game_xps",   queryValues.get("game_xps"));
        values.put("game_dzh",   queryValues.get("game_dzh"));
        values.put("game_jmz", queryValues.get("game_jmz"));
        values.put("game_tps", queryValues.get("game_tps"));
        Log.v("LOG_DBH", "Calling: db.insert to table....");
        try {
            db.insert("gam_CubeCart_customer", null, values);
            db.close();
            syncLiteUser(queryValues.get("customer_id"));
            return true;
        } catch(Exception e) {
            db.close();
            Log.v("LOG_DBH", "db.insert to LITE - FAILED.");
            return false;
        }
    } //updateLiteUser() >> Boolean
    public void syncLiteUser(String vCustomer_id){
        Log.v("LOG_DBH", "Running syncLiteUser");
        Gson gson = new GsonBuilder().create();
        ArrayList<HashMap<String, String>> syncDOMUserArray;
        syncDOMUserArray = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> syncMap = new HashMap<String, String>();

        // Add status for each User in Hashmap
        syncMap.put("customer_id", vCustomer_id);
        syncMap.put("syncstat", "1");
        syncDOMUserArray.add(syncMap);

        // Inform RemoteDB of the Sync activity by fwding SYNC status
        updateDOMSyncFlag(gson.toJson(syncDOMUserArray));
        Log.v("LOG_DBH", "syncMap: " + syncMap);
    }
    public void updateDOMSyncFlag(String json) {
        Log.v("LOG_DBH", "Inside updateDOMSyncFlag - json: " + json);
        AsyncHttpClient asyncHttp = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        //params.put("domuser", syncMap); // url params: "domuser[customer_id]=103&domuser[syncstat]=1"
        params.put("domuser", json);

        // Make Http call to updatesyncstatus.php with JSON parameter + Sync status
        asyncHttp.post("http://polygam101.beinternetrich.net/e/pgsync/updatesyncstatus.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
                Log.v("LOG_UPDSYNC", "Synchronizing: " + responseBody);
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
                //Toast.makeText(getApplicationContext(), "CREATEACC: Error Dude", Toast.LENGTH_LONG).show();
            }
        });
        Log.v("LOG_DBH", "SYNCHING DONE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
    public HashMap<String, String> getLiteUser(final String getUsername) {
        return queryValues;
//        ArrayList<HashMap<String, String>> userList;
//        userList = new ArrayList<HashMap<String, String>>();
//        //userList = queryValues;
//        String selectQuery = "SELECT * from gam_CubeCart_customer where game_name='" + getUsername + "'";
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        Log.v("LOG_DBH", "Engaging cursor - BTW(QV):"+String.valueOf(queryValues));
//        Cursor cursor = db.rawQuery(selectQuery, new String[]{});
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                HashMap<String, String> map = new HashMap<String, String>();
//                map.put("customer_id", cursor.getString(0));
//                map.put("game_name",  cursor.getString(1));
//                map.put("game_lvl",  cursor.getString(2));
//                map.put("game_xps",  cursor.getString(3));
//                map.put("game_dzh",  cursor.getString(4));
//                map.put("game_jmz",  cursor.getString(5));
//                map.put("game_tps",  cursor.getString(6));
//                userList.add(map);
//                Log.v("LOG_DBH", "Engaged  Values (same?????????????): " + String.valueOf(map));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        //CHANGE USERNAME TO FIRST_NAME/GAME_NAME/USERNAME AS NEC
//        db.close();
//        return userList;
    }
    public void deleteUser(HashMap<String, String> queryValues)    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("gam_CubeCart_customer","customer_id=?",new String []{String.valueOf(queryValues.get("customer_id")+"or user.getID()")});
        db.close();
    }
    public int  updateUser(HashMap<String, String> queryValues)    {
        SQLiteDatabase db=this.getWritableDatabase();
        Log.v("LOG_TAG1", "Queryvalues 00000000000000000 UPDATING....");
        Log.v("LOG_TAG1", String.valueOf(queryValues));
        ContentValues values=new ContentValues();
        values.put("customer_id", queryValues.get("customer_id"));
        values.put("last_name", queryValues.get("last_name"));
        values.put("game_name", queryValues.get("game_name"));
        values.put("game_lvl", queryValues.get("game_lvl"));
        values.put("game_xps", queryValues.get("game_xps"));
        values.put("game_dzh", queryValues.get("game_dzh"));
        values.put("game_jmz", queryValues.get("game_jmz"));
        values.put("game_tps", queryValues.get("game_tps"));
//      values.put(game_dzh, user.getDzh());
        return db.update("gam_CubeCart_customer", values, "customer_id=?",new String []{String.valueOf(queryValues.get("customer_id")+"or user.getID()")});
    }

    public ArrayList<MyBusiness> getBusinesses(){
//        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{
//                Constants.KEY_ID, Constants.BIZ_NAME, String.valueOf(Constants.BIZ_COST),
//                }, null, null, null, null, Constants.DATE_CREATED + " DESC");
//        //loop through cursor
//        if (cursor.moveToFirst()){
//            do{
//                MyBusiness business=new MyBusiness();
//                business.setBizname(cursor.getString(cursor.getColumnIndex(Constants.BIZ_NAME)));
//                business.setBizcost(cursor.getInt(cursor.getColumnIndex(String.valueOf(Constants.BIZ_COST))));
//                business.setCreated(cursor.getLong(cursor.getColumnIndex(Constants.DATE_CREATED)));
//                businessList.add(business);
//            }while(cursor.moveToNext());
//        }
        return businessList;
    }
	public ArrayList<HashMap<String, String>> getAllUsers() {
		ArrayList<HashMap<String, String>> usersList;
		usersList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT  * FROM gam_CubeCart_customer";
	    SQLiteDatabase db = this.getWritableDatabase();
        //Cursor kursor=  db.rawQuery("SELECT * from gam_CubeCart_customer", new String[]{});
	    Cursor cursor = db.rawQuery(selectQuery, null);
        //either #public Cursor getAllUsers() and return cursor;# or shove cursor into Hashmap for return.
	    if (cursor.moveToFirst()) {
	        do {
	        	HashMap<String, String> map = new HashMap<String, String>();
	        	map.put("customer_id", cursor.getString(cursor.getColumnIndex("customer_id")));
	        	map.put("game_name", cursor.getString(1));
                usersList.add(map);
	        } while (cursor.moveToNext());
	    }
	    db.close();
	    return usersList;
	}
	//@Override
    public void onCreate(SQLiteDatabase db) {
        //create our table(s)
        /* String CREATE_BUSINESSES_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "(" +
                Constants.KEY_ID + " INTEGER PRIMARY KEY," +
                Constants.BIZ_NAME + " TEXT, " +
                Constants.BIZ_COST + " INTEGER, " +
                Constants.DATE_CREATED + " LONG);";
        db.execSQL(CREATE_BUSINESSES_TABLE);
        */
		String qry;
        Log.v("LOG_DBH", "Calling: Create TABLE & VIEW...");
        qry = "CREATE TABLE IF NOT EXISTS gam_CubeCart_customer ( customer_id INTEGER PRIMARY KEY, last_name TEXT,game_name TEXT,game_lvl INTEGER,game_xps INTEGER,game_dzh INTEGER,game_jmz INTEGER,game_tps INTEGER )";
        db.execSQL(qry);
        qry = "CREATE VIEW ViewCustomers AS SELECT * FROM gam_CubeCart_customer";
        db.execSQL(qry);
    }
    //@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //oldVersion = Constants.DBOLD_VERSION;
        //newVersion = Constants.DBNEW_VERSION;
        String query = "DROP TABLE IF EXISTS gam_CubeCart_customer";
		db.execSQL(query);
		//db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        //db.execSQL("DROP TRIGGER IF EXISTS foreignkey_custaddr_addressid");
        db.execSQL("DROP VIEW IF EXISTS "+"ViewCustomers");
        //recreate the new one
        onCreate(db);
    }
    public void addBusiness( MyBusiness biz){
//        SQLiteDatabase db=this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(Constants.BIZ_NAME, biz.getBizname());
//        values.put(String.valueOf(Constants.BIZ_COST), biz.getBizcost());
//        values.put(Constants.DATE_CREATED, java.lang.System.currentTimeMillis());
//        db.insert(Constants.TABLE_NAME, null, values);
//        db.close();
    }
	}