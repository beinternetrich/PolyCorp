package util;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SampleBC extends BroadcastReceiver {
    static int noOfTimes = 0;

    // Called when Broadcast is issued from SyncItemsActivity for every 20 seconds
    @Override
    public void onReceive(final Context context, Intent intent) {
        noOfTimes++;
        Toast.makeText(context, "BC Service Running for " + noOfTimes + " times", Toast.LENGTH_SHORT).show();
        AsyncHttpClient asyncHttp = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        // Checks if new records are inserted in RemoteDB to proceed with Sync operation
        asyncHttp.post("http://polygam101.beinternetrich.net/e/pgsync/getdbrowcount.php",params ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                System.out.println(response.toString());
                try {
                    // Create JSON object out of the response sent by getdbrowcount.php
                    JSONObject obj = new JSONObject(response.toString());
                    System.out.println(obj.get("count"));
                    // If the count value is not zero, call MyService to display notification
                    if(obj.getInt("count") != 0){
                        final Intent intnt = new Intent(context, MyService.class);
                        // Set unsynced count in intent data
                        intnt.putExtra("intntdata", "Unsynced Rows Count "+obj.getInt("count"));
                        // Call MyService
                        context.startService(intnt);
                    }else{
                        Toast.makeText(context, "Sync not needed", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // T-ODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] content, Throwable error) {
                // T-ODO Auto-generated method stub
                if(statusCode == 404){
                    Toast.makeText(context, "404", Toast.LENGTH_SHORT).show();
                }else if(statusCode == 500){
                    Toast.makeText(context, "500", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Error occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
