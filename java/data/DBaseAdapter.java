package data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mmtechworks.polygam101.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.apache.commons.io.IOUtils;
import org.apache.http.util.ByteArrayBuffer;
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

import model.Product;

import static util.DbBitmapUtility.getBytes;
//import com.parse.SignUpCallback;

public class DBaseAdapter {
	
	public static final String TAG = DBaseAdapter.class.getSimpleName();
	public Context appliccontext;
    public DatabaseHelper DbHelper = null;
    public SQLiteDatabase db = null;

		
	private static class DatabaseHelper extends SQLiteOpenHelper {
		static final String TAG = DatabaseHelper.class.getSimpleName();
		//Parse.enableLocalDatastore(this);
		//Parse.initialize(this, Constants.APP_KEY_ID, Constants.APP_CLIENT_ID);

	public DatabaseHelper(Context context) {
        super(context, Constants.DBLOCAL_NAME, null, Constants.DBASE_VERSION);
    }
	
	public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

    //@Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG, "Calling: Create TABLE & VIEW...");
		String CREATE_QRY;
        CREATE_QRY = "CREATE TABLE IF NOT EXISTS "+Constants.TABLE_PEOPLE + " (" +
                Constants.GAMER_ID + " INTEGER PRIMARY KEY, " +
                Constants.GAME_LNAME + " TEXT," +
                Constants.GAME_NAME + " TEXT," +
                Constants.GAME_LVL  + " INTEGER," +
                Constants.GAME_XPS  + " INTEGER," +
                Constants.GAME_DZH  + " INTEGER," +
                Constants.GAME_JMZ  + " INTEGER," +
                Constants.GAME_TPS  + " INTEGER, " +
                Constants.ROW_CREATED + " LONG);";
        db.execSQL(CREATE_QRY); Log.v(TAG, CREATE_QRY);
        CREATE_QRY = "CREATE VIEW IF NOT EXISTS "+"ViewGamers AS SELECT * FROM "+Constants.TABLE_PEOPLE;
        db.execSQL(CREATE_QRY); Log.v(TAG, CREATE_QRY);

        CREATE_QRY = "CREATE TABLE IF NOT EXISTS "+Constants.TABLE_BUSINESSES + " (" +
                Constants.BIZ_ID + " INTEGER PRIMARY KEY, " +
                Constants.BIZ_CODE + " TEXT," +
                Constants.BIZ_NAME + " TEXT," +
                Constants.BIZ_SHORT + " TEXT," +
                "image" + " BLOB," +
                Constants.BIZ_RETAIL  + " INTEGER," +
                Constants.BIZ_SALEPR  + " INTEGER," +
                Constants.BIZ_COSTPR  + " INTEGER," +
                Constants.BIZ_CATID  + " INTEGER," +
                Constants.BIZ_POP  + " INTEGER," +
                Constants.BIZ_TAXTYPE  + " INTEGER, " +
                Constants.ROW_CREATED + " LONG);";
        db.execSQL(CREATE_QRY); Log.v(TAG, CREATE_QRY);
        CREATE_QRY = "CREATE VIEW IF NOT EXISTS "+"ViewBusinesses AS SELECT * FROM "+Constants.TABLE_BUSINESSES;
        db.execSQL(CREATE_QRY); Log.v(TAG, CREATE_QRY);
    }
    //@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String CREATE_QRY;
        CREATE_QRY = "DROP TABLE IF EXISTS "+Constants.TABLE_PEOPLE +
                "; DROP TRIGGER IF EXISTS foreignkey_custaddr_addressid" +
                "; DROP VIEW IF EXISTS " + "ViewCustomers" +
                "; DROP VIEW IF EXISTS " + "ViewGamers" +
                "; DROP TABLE IF EXISTS "+Constants.TABLE_BUSINESSES +
                "; DROP VIEW IF EXISTS " + "ViewBusinesses" +
                ";";
        Log.v(TAG, CREATE_QRY);
        db.execSQL("DROP VIEW IF EXISTS " + "ViewBusinesses");
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.TABLE_BUSINESSES);
        db.execSQL("DROP VIEW IF EXISTS " + "ViewGamers");
        db.execSQL("DROP VIEW IF EXISTS " + "ViewCustomers");
        db.execSQL("DROP TRIGGER IF EXISTS foreignkey_custaddr_addressid");
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.TABLE_PEOPLE);
        //recreate the new one
        onCreate(db);
    }

} 

	
    public DBaseAdapter(Context context)    {
        this.appliccontext = context;
        //this.DbHelper = new DatabaseHelper(this.appliccontext);
		//this.DbHelper = new DatabaseHelper(context, Constants.DBLOCAL_NAME, null, Constants.DBASE_VERSION);
        DbHelper = new DatabaseHelper(context);
    }
	

	// open either writeable else readable database
    // public DBaseAdapter open()  {
    public void open()  {
        Log.v(TAG, "dbopening");
        db = DbHelper.getWritableDatabase();
        Log.v(TAG, "dbopened");
    }
	
	// open either writeable else readable database
	public void openn() throws SQLiteException {
		try {
			db = DbHelper.getWritableDatabase();
			//Log.v(TAG, "WRITEABLE DB CREATED");
		}
		catch ( SQLiteException ex ) {
			//Log.v(TAG, "READABLE DB CREATED");
			db = DbHelper.getReadableDatabase();
		}
	}
	
	public SQLiteDatabase getReadableDatabase() {
		try {
			return DbHelper.getReadableDatabase();
		}
		catch ( SQLiteException ex ) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public void close() {
        Log.v(TAG, "dbclosing");
		db.close();
		this.DbHelper.close();
        Log.v(TAG, "dbclosed");
	}
	
	//====================================================================
    //====================================================================
    //====================================================================
    //====================================================================
    public static ArrayList<Product> businessList; // = new ArrayList<>();
    HashMap<String, String> queryValues;
    boolean nuGamer;
	
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
                Log.v(TAG, "Hello " + parseUser.getUsername());
                //-Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                //getAccountInfo(getUsername, getPassword);
                //startActivity(new Intent(LoginAccountActivity.this, GameActivity.class));
            } else {
                Log.v(TAG, "Try Again");
                //Toast.makeText(getApplicationContext(), "Login Failure", Toast.LENGTH_LONG).show();
            }
        }
        });
    }

    public boolean addDomUser(final String getUsername, final String getPassword, final String getEmail) {
        nuGamer = true;  //Log.v(TAG, String.valueOf(nuGamer));
        String[] params = {null, getEmail, getPassword, getUsername};
        String POST_PARAMS = "email=" + params[1] + "&password=" + params[2] + "&username=" + params[3];
        //Create/Get db row====================================
        URL url = null;
        if (getEmail != null) {
            try {
                url = new URL("http://polygam101.beinternetrich.net" + "/e/pgsync/db_useradd.php" + "?" + POST_PARAMS);
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
                    Log.v(TAG, "RespCode: " + conn.getResponseCode());
                } catch (IOException e) {
                    Log.v(TAG, "Expected Response 200 - Failed");
                    e.printStackTrace();
                }
            }
            InputStream in = null;
            if (true) {
                try {
                    in = new BufferedInputStream(conn.getInputStream());
                    String response = IOUtils.toString(in, "UTF-8");
                    Log.e(TAG, response);
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
        String[] params = {"get-user:", getUsername, getPassword};
        String POST_PARAMS = "username=" + params[1] + "&password=" + params[2];
        Log.v(TAG, "Pulling DOM Info for Params: "+POST_PARAMS );
        //Retrieve db row===========================================
        URL url = null;
        if (getUsername != null) {
            try {
                url = new URL("http://polygam101.beinternetrich.net" + "/e/pgsync/db_userget.php" + "?" + POST_PARAMS);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.v(TAG, "PHP Get script found");
            HttpURLConnection conn = null;
            if (url != null) {
                try {conn = (HttpURLConnection) url.openConnection();}
                catch (IOException e) {e.printStackTrace();}}
            if (true) {
                try {conn.setRequestMethod("POST");}
                catch (java.net.ProtocolException e) {e.printStackTrace();}}
            if (true) {
                try {Log.v(TAG, "RespCode: " + conn.getResponseCode());
                } catch (IOException e) {
                    e.printStackTrace();}}
            InputStream in = null;
            if (true) {
                try {in = new BufferedInputStream(conn.getInputStream());
                    String response = IOUtils.toString(in, "UTF-8");
                    Log.e(TAG, response);
                    if (response.toLowerCase().contains(getUsername) && response.toLowerCase().contains(getPassword)) {
                        parseForLiteUser(response);
                        return true;
                    } else {
                        Log.v(TAG, "DOM Pull Fail or output is empty");
                        //oBtnCreateAccount.setEnabled(false);
                        //vfEmailId.setEnabled(false);
                        //vfUsernameId.setEnabled(false);
                        //vfPasswordId.setEnabled(false);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
            //Row Retrieved=
        } else {return false;}
    }  //end pullDOMUser() + parseForLiteUser()

    public void xparseForLiteBusiness(final String getProductName, final String getProductShort, final String getProductCat){
//        //-Build QueryValues to insert in Lite
//        queryValues = new HashMap<String, String>();
//        queryValues.put("customer_id",jsonData.get("customer_id").toString());
//        queryValues.put("last_name",  jsonData.get("last_name").toString());
//        queryValues.put("game_name",  jsonData.get("game_name").toString());
//
//        SQLiteDatabase db = this.getWritableDatabase(); //Log.v(TAG, "Values: " + String.valueOf(queryValues));
//        ContentValues values = new ContentValues();
//        values.put("customer_id", queryValues.get("customer_id"));
//        values.put("last_name",  queryValues.get("last_name"));
//        values.put("game_name",  queryValues.get("game_name"));
    }

    public void parseForLiteUser(String result){
		Log.v(TAG, "Running parseForLiteUser....");
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

                    Log.v("LOG_DBH210", ">> INSERT or UPDATE LITE USER...............");
                    try {
                        insertupdateLiteUser(queryValues);
                    } catch(Exception e) {}
                }
            } else {/*array.length=0. No users found.*/}
        } catch (JSONException e) {e.printStackTrace();}
    } //parseForLiteUser() + insertLiteUser()

    public boolean insertupdateLiteUser(HashMap<String, String> queryValues) {
        //SQLiteDatabase db = this.getWritableDatabase(); //Log.v(TAG, "Values: " + String.valueOf(queryValues));
        ContentValues values = new ContentValues();
        values.put("customer_id",queryValues.get("customer_id"));
        values.put("last_name",  queryValues.get("last_name"));
        values.put("game_name", queryValues.get("game_name"));
        values.put("game_lvl", queryValues.get("game_lvl"));
        values.put("game_xps", queryValues.get("game_xps"));
        values.put("game_dzh", queryValues.get("game_dzh"));
        values.put("game_jmz", queryValues.get("game_jmz"));
        values.put("game_tps", queryValues.get("game_tps"));
        try {
            if (nuGamer){
                Log.v(TAG, "Calling: db.INSERT for newbies");
                db.insert(Constants.TABLE_PEOPLE, null, values);
            } else {
                String strWhereFilter = "customer_id=" + queryValues.get("customer_id");
                db.update(Constants.TABLE_PEOPLE, values, strWhereFilter, null);
                Log.v(TAG, "Calling: db.UPDATE: " + strWhereFilter);
            }
            Log.v(TAG, "INSERT / UPDATE Completed");
            db.close();
            syncLiteUser(queryValues.get("customer_id"));
            return true;
        } catch(Exception e) {
            db.close();
            Log.v(TAG, "LITE insert/update failure.");
            return false;
        }
    } //insertLiteUser() >> Boolean

    public void onCompleteI(String getUsername, Intent i) {
        HashMap<String, String> queryValues = getLiteUser(getUsername);
        //Add the following to the already existing intent which contains:
        //i.putExtra("game_new", "true");
        i.putExtra("game_name", queryValues.get("game_name"));
        //i.putExtra("game_ttl", queryValues.get("game_ttl"));
        i.putExtra("game_lvl", queryValues.get("game_lvl"));
        i.putExtra("game_xps", queryValues.get("game_xps"));
        i.putExtra("game_dzh", queryValues.get("game_dzh"));
        i.putExtra("game_jmz", queryValues.get("game_jmz"));
        i.putExtra("game_tps", queryValues.get("game_tps"));
    }

    public boolean updateLiteUser(HashMap<String, String> queryValues) {
        Log.v(TAG, "UpdLiteUser: "+String.valueOf(queryValues));
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("customer_id",queryValues.get("customer_id"));
        values.put("last_name",  queryValues.get("last_name"));
        values.put("game_name",  queryValues.get("game_name"));
        values.put("game_lvl", queryValues.get("game_lvl"));
        values.put("game_xps", queryValues.get("game_xps"));
        values.put("game_dzh", queryValues.get("game_dzh"));
        values.put("game_jmz", queryValues.get("game_jmz"));
        values.put("game_tps", queryValues.get("game_tps"));
        Log.v("LOG_DBH", "Calling: db.insert to table....");
        try {
            db.insert(Constants.TABLE_PEOPLE, null, values);
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
//        String selectQuery = "SELECT * from "+Constants.TABLE_PEOPLE+" where game_name='" + getUsername + "'";
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
        //SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Constants.TABLE_PEOPLE,"customer_id=?",new String []{String.valueOf(queryValues.get("customer_id")+"or user.getID()")});
        db.close();
    }

    public int  updateUser(HashMap<String, String> queryValues)    {
        //SQLiteDatabase db=this.getWritableDatabase();
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
        return db.update(Constants.TABLE_PEOPLE, values, "customer_id=?",new String []{String.valueOf(queryValues.get("customer_id")+"or user.getID()")});
    }

    public ArrayList<HashMap<String, String>> getAllUsers() {
		ArrayList<HashMap<String, String>> usersList;
		usersList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT  * FROM "+Constants.TABLE_PEOPLE;
	    //SQLiteDatabase db = this.getWritableDatabase();
        //Cursor kursor=  db.rawQuery("SELECT * from "+Constants.TABLE_PEOPLE, new String[]{});
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

    public byte[] getLogoImage(URL url){
		//URL url = null;
		try {
			try {
				url = new URL("http://192.168.1.3/approot/android/PolyGam101/app/src/main/res/drawable/handcarwash.jpg");
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
			InputStream in = null;
            if (true) {
                try {
                    in = new BufferedInputStream(conn.getInputStream());
                    
					String response = IOUtils.toString(in, "UTF-8");
                    Log.e(TAG, response);
                    //if (response.toLowerCase().contains("success")) {return true;} else {return false;}
                } catch (IOException e) {
                    e.printStackTrace();
                    //return false;
                }
            }

			ByteArrayBuffer baf = new ByteArrayBuffer(500);
			int current = 0;
            while ((current = in.read()) != -1) {
                baf.append((byte) current);
            }
            return baf.toByteArray();
        } catch (Exception e) {
                Log.d("ImageManager", "Error: " + e.toString());
            }
            return null;
    }

    public void addBusiness(ContentValues values){
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Drawable drawable = this.appliccontext.getDrawable(R.drawable.me);
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//        Bitmap bitmap = bitmapDrawable.getBitmap();
//        //Bitmap bitmap2 = ((BitmapDrawable) this.appliccontext.getResource().getDrawable(R.drawable.me)).getBitmap();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] bizImage = baos.toByteArray();

        byte[] bizImage = getBytes(drawable);
        values.put("image", bizImage);
        values.put(Constants.ROW_CREATED, java.lang.System.currentTimeMillis());
        Log.v(TAG, "Ready with: " + values);
        Log.v(TAG, "dbisopenchking");
        if(db.isOpen()) {
            Log.v(TAG, "dbisopenchked");
            Log.v(TAG, "Going in...");
            db.insert(Constants.TABLE_BUSINESSES, null, values);
            Log.v(TAG, "Done yet");
            db.close();
            Log.v(TAG, "dbclosed");
        } else {
            Log.v(TAG, "Db wasnt open");
        }
        //db.close();
    }

    public long addParsedBusiness(HashMap<String, String> objProduct){
        //SQLiteDatabase db = this.getWritableDatabase();
        Log.v(TAG, "Values: " + String.valueOf(objProduct));
        ContentValues values = new ContentValues();
        values.put(Constants.BIZ_ID,5);
        values.put(Constants.BIZ_NAME,"aaa"); //objProduct.get("product_name"));
        //values.put("image", barb.toByteArray());
        values.put(Constants.BIZ_SHORT, "bbb"); //objProduct.get("product_short"));
        values.put(Constants.BIZ_CATID, 3456); //objProduct.get("product_cat"));
        //values.put(Constants.ROW_CREATED, java.lang.System.currentTimeMillis());

        long insertedRowIndex =
                db.insertWithOnConflict(Constants.TABLE_BUSINESSES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Log.d(TAG, "Inserted book record " + insertedRowIndex);
        return insertedRowIndex;
    } //insertLiteBusiness() >> Boolean

    //populate an image view with
    //logoImage.setImageBitmap(BitmapFactory.decodeByteArray(productList.accImage, 0, productList.accImage.length));
    public ArrayList<Product> getBusinessList(){
        ArrayList<Product> pList = new ArrayList<>();
        Log.v(TAG, "1Heerreeee cursy");
        Cursor cursor = db.query(Constants.TABLE_BUSINESSES,
                new String[]{
                    //Constants.BIZ_ID,
                    Constants.BIZ_NAME,
                    Constants.BIZ_SHORT,
                    //Constants.BIZ_COSTPR,
                    //Constants.BIZ_CATID,
                    Constants.BIZ_IMAGE
                },
                null,
                //Constants.BIZ_NAME + " " + "like ?",
                null,
                //new String[]{
                //   "%wert%"
                //},
                null, null, Constants.ROW_CREATED + " ASC");
        Log.v(TAG, cursor.toString());
        //("sku_table", columns, optionalWhere "owner=? and price=?", new String[] { owner, price }
        // ..., @groupBy null, @having null, @orderBy null, @limit optional);
        //loop through cursor
        Log.v(TAG, "2Heerreeee cursy");
        if (cursor.moveToFirst()){
            Log.v(TAG, "3Heerreeee cursy");
        do{
            Log.v(TAG, "4Heerreeee cursy");
            Product business=new Product();
            Log.v(TAG, "5Heerreeee cursy");
            //business.setProductid(cursor.getInt(cursor.getColumnIndex(Constants.BIZ_ID)));
            business.setProductname(cursor.getString(cursor.getColumnIndex(Constants.BIZ_NAME)));
            business.setProductdshort(cursor.getString(cursor.getColumnIndex(Constants.BIZ_SHORT)));
            //business.setProductcostprice(cursor.getInt(cursor.getColumnIndex(Constants.BIZ_COSTPR)));
            //business.setProductcatid(cursor.getInt(cursor.getColumnIndex(Constants.BIZ_CATID)));
            business.setProductimage(cursor.getBlob(cursor.getColumnIndex(Constants.BIZ_IMAGE)));
            //business.setCreated(cursor.getLong(cursor.getColumnIndex(Constants.ROW_CREATED)));
            Log.v(TAG, "6Heerreeee cursy");
            pList.add(business);
            Log.v(TAG, "7Heerreeee cursy");
        }while(cursor.moveToNext());
            Log.v(TAG, "8Heerreeee cursy");
            cursor.close(); /////////////////////////////////
        }
        Log.v(TAG, "999 cursy");
        return pList;
    }

}