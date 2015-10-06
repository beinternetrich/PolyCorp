package com.mmtechworks.polygam101;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import data.DBaseAdapter;
import util.SampleBC;

public class SyncItemsActivity extends ActionBarActivity {
	// DB Class to perform DB related operations
	DBaseAdapter controller = new DBaseAdapter(this);
	// Progress Dialog Object
	ProgressDialog prgDialog;
	HashMap<String, String> queryValues;
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_items);
		
		// Get User records from SQLite DB
		ArrayList<HashMap<String, String>> userList = controller.getAllUsers();
		// If users exists in SQLite DB
		if (userList.size() != 0) {
			// Set the User Array list in ListView
			ListAdapter adapter = new SimpleAdapter(SyncItemsActivity.this, userList, R.layout.activity_view_item, new String[] {
							"customer_id", "game_name" }, new int[] { R.id.customer_id, R.id.game_name });
			ListView myList = (ListView) findViewById(android.R.id.list);
			myList.setAdapter(adapter);
		}
		// Initialize Progress Dialog properties
		prgDialog = new ProgressDialog(this);
		prgDialog.setMessage("Transferring Data from Remote MySQL DB and Syncing SQLite. Please wait...");
		prgDialog.setCancelable(false);
		// BroadCase Receiver Intent Object
		Intent alarmIntent = new Intent(getApplicationContext(), SampleBC.class);
		// Pending Intent Object
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		// Alarm Manager Object
		AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		// Alarm Manager calls BroadCast for every 20 seconds (20 * 1000), BroadCase further calls service to check if new records are inserted in
		// Remote MySQL DB
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + 5000, 20 * 1000, pendingIntent);
	}

	// Options Menu (ActionBar Menu)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_sync_items, menu);
		return true;
	}
	// When Options Menu is selected
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. 
		int id = item.getItemId();
		// When Sync action button is clicked
		if (id == R.id.refresh) {
			// Transfer data from remote MySQL DB to SQLite on Android and perform Sync
			syncSQLiteMySQLDB();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// Method to Sync MySQL to SQLite DB
	public void syncSQLiteMySQLDB() {
		// Create AsycHttpClient object
		AsyncHttpClient client = new AsyncHttpClient();
		// Http Request Params Object
		RequestParams params = new RequestParams();
		// Show ProgressBar
		prgDialog.show();
		// Make Http call to getusers.php
		client.post("http://polygam101.beinternetrich.net/e/pgsync/getusers.php", params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] response) {
				// Hide ProgressBar
				prgDialog.hide();
				// Update SQLite DB with response sent by getusers.php
				updateSQLite(response.toString());
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] content, Throwable error) {
				// Hide ProgressBar
				prgDialog.hide();
				if (statusCode == 404) {
					Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
				} else if (statusCode == 500) {
					Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}


	public void updateSQLite(String resulttxt){
		ArrayList<HashMap<String, String>> usersynclist;
		usersynclist = new ArrayList<HashMap<String, String>>();
		// Create GSON object
		Gson gson = new GsonBuilder().create();
		try {
			// Extract JSON array from the response
			JSONArray jArray = new JSONArray(resulttxt);
			//System.out.println(jArray.length());
			Toast.makeText(getApplicationContext(), "Number of inserts - " + jArray.length(), Toast.LENGTH_LONG).show();
			// If no of array elements is not zero
			if(jArray.length() != 0){
				// Loop through each array element, get JSON object which has userid and username
				for (int i = 0; i < jArray.length(); i++) {
					// Get JSON object
					JSONObject jsonData = (JSONObject) jArray.get(i);
					//System.out.println(jsonData.get("userId"));
					//System.out.println(jsonData.get("userName"));
					// DB QueryValues Object to insert into SQLite
					queryValues = new HashMap<String, String>();
					// Add userID, userName, etc extracted from Object
					queryValues.put("customer_id", jsonData.get("customer_id").toString());
					queryValues.put("last_name", jsonData.get("last_name").toString());
					queryValues.put("game_name", jsonData.get("game_name").toString());
					queryValues.put("game_xps", jsonData.get("game_xps").toString());
					queryValues.put("game_dzh", jsonData.get("game_dzh").toString());
					queryValues.put("game_jmz", jsonData.get("game_jmz").toString());
					//String customer_id = jsonData.getString("customer_id");
					//String last_name = jsonData.getString("last_name");

					// Insert User into SQLite DB
					controller.insertupdateLiteUser(queryValues);
					HashMap<String, String> map = new HashMap<String, String>();
					// Add status for each User in Hashmap
					map.put("customer_id", jsonData.get("customer_id").toString());
					map.put("syncstat", "1");
					usersynclist.add(map);
				}
				// Inform Remote MySQL DB about the completion of Sync activity by passing Sync status of Users
				updateMySQLSyncSts(gson.toJson(usersynclist));
				// Reload the SyncItemsActivity
				reloadActivity();
			}
		} catch (JSONException e) {
			// T-ODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Method to inform remote MySQL DB about completion of Sync activity
	public void updateMySQLSyncSts(String json) {
		//System.out.println(json);
		Toast.makeText(getApplicationContext(), json, Toast.LENGTH_LONG).show();
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("syncstat", json);
		// Make Http call to updatesyncsts.php with JSON parameter which has Sync statuses of Users
		client.post("http://polygam101.beinternetrich.net/e/pgsync/updatesyncsts.php", params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
				Toast.makeText(getApplicationContext(), "MySQL DB has been SYNCITM informed about Sync activity", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
				Toast.makeText(getApplicationContext(), "Error Occurred", Toast.LENGTH_LONG).show();
			}
		});
	}

	// Reload MainActivity
	public void reloadActivity() {
		Intent objIntent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(objIntent);
	}

}