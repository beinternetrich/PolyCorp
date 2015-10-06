package util;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mmtechworks.polygam101.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Marcy on 16/08/2015.
 */
public class SysDatabaseList extends Activity {
    ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR); //if utilizing actionBar.setTitle
        setContentView(R.layout.activity_sys_dblist);

        ArrayList<String> dbFilesArrList = getDBFILES();
        String myDataArray[] = {};

        // Parse the dbFilesArrList and display it in a listview.
        try{
            myDataArray = new String[dbFilesArrList.size()];
            for(int i=0; i<dbFilesArrList.size(); i++){
                myDataArray[i] = dbFilesArrList.get(i);
                Log.v("LOG-myArrayItem", myDataArray[i]);
            }
        }
        catch(Exception e){
            Log.v("LOG-EXCEPTION","Error....");
            e.printStackTrace();
        }

        //Lets display the array in  ListView
        mListView=(ListView) findViewById(R.id.viewIdSysDbs);
        ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_sys_dbrow,myDataArray);

        if(mListView != null){
            mListView.setAdapter(stringAdapter);
        } else {
            Log.v("LOG-mLISTVIEW","Recorded Null");
        }
    }

    private ArrayList<String> getDBFILES() {
        ArrayList<String> arr = new ArrayList<>();
        String db_path, rand_name, str_tmp;

        //ad.1-2. random file name for db
        int rInt = new Random().nextInt(5000+1);
        String rStr = String.valueOf(rInt);
        rand_name = rStr + ".db";
        Log.v("LOG-RANDNAME", rand_name);
        //db_path = openOrCreateDatabase(rand_name, MODE_PRIVATE, null).getPath();
        db_path = openOrCreateDatabase(rand_name, MODE_APPEND, null).getPath();
        Log.v("LOG-DBPath", db_path);

        //ad.3.
        deleteDatabase(rand_name);
        //Log.v("LOG-DBDELETE", "Not deleting....................");

        //ad.4.
        db_path = db_path.replace("/" + rand_name, "");
        Log.v("LOG-DBPATH", db_path);

        //ad.5.
        File[] files = new File(db_path).listFiles();
        if (files == null) {
            Log.v("LOG-FILES","Files Found NULL");
            return null;
        } else {
            //so now we get the filenames one by one
            for (int i = 0; i < files.length; i++) {
                str_tmp = files[i].getName();
                Log.v("LOG-FILENAME", str_tmp);
                if (!str_tmp.endsWith("-journal"))
                {
                    arr.add(str_tmp);
                    //Log.v("LOG-Added2ARR", str_tmp);
                    //SELECT name FROM sqlite_temp_master WHERE type='table';
                }
            }
            return arr;
        }
    }
}

