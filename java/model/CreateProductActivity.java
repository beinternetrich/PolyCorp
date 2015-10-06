package model;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.mmtechworks.polygam101.GameActivity;
import com.mmtechworks.polygam101.R;

import java.util.HashMap;

import data.Constants;
import data.DBaseAdapter;

public class CreateProductActivity extends Activity {
    private static final String TAG = CreateProductActivity.class.getSimpleName();
    DBaseAdapter controller = new DBaseAdapter(this);
    HashMap<String, String> objProduct;
    private EditText vfProductName;
    private EditText vfProductShort;
    private EditText vfProductCat;
    private Button vbtnCreateProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR); //if utilizing actionBar.setTitle
        setContentView(R.layout.activity_create_product);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        vfProductName   =  (EditText) findViewById(R.id.fProductName);
        vfProductShort = (EditText) findViewById(R.id.fProductShort);
        vfProductCat = (EditText) findViewById(R.id.fProductCat);
        vbtnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);
        vbtnCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(CreateProductActivity.this);
                final String getProductName = vfProductName.getText().toString();
                final String getProductShort = vfProductShort.getText().toString();
                final String getProductCat = vfProductCat.getText().toString();

                if (getProductName.equals("") || getProductShort.equals("") || getProductCat.equals("")) {
                    dialog.setTitle("Empty Fields");
                    dialog.setMessage("Please complete ALL the form");
                    dialog.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {
                    boolean contYN=false;
                    try {
                        // @@@@@contYN = controller.addBusiness(getProductShort, getProductCat, getProductNam);
						ContentValues objProduct = new ContentValues();
                        //objProduct = new HashMap<String, String>();
                        objProduct.put(Constants.BIZ_NAME, getProductName);
                        objProduct.put(Constants.BIZ_SHORT,getProductShort);
                        objProduct.put(Constants.BIZ_CATID,  getProductCat);
                        //contYN = controller.addParsedBusiness(objProduct);
                                //controller.addParsedBusiness(objProduct);
                        controller.openn();
                        controller.addBusiness(objProduct);
                        contYN = true;
                    } catch (SQLiteConstraintException e) {
                        Log.v(TAG, "ProductName already exists. Check your Business List");
                    } catch(Exception e) {
                        Log.v(TAG, "CatchAll other exceptions");
                    }

                    if (contYN){
                        ////controller.pullDomUser(getProductName, getProductShort);
                        Intent i = new Intent(getApplicationContext(), GameActivity.class);
                        //i.putExtra("game_new", "true");
                        ////controller.onCompleteI(getProductName,i);
                        startActivity(i);
                    } else {
                        Log.v(TAG, "Business Addition Failure. Try Again.");
                        dialog.setTitle("New Business Creation Failure");
                        dialog.setMessage("An account may already exist for this Business. Check your Portfolio");
                        dialog.setPositiveButton("Close then Go Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                }
            }
        });
        ActionBar actionBar = getActionBar();
        if(actionBar!=null) actionBar.setTitle("PolyCorp: "+"New Business");
    }
}