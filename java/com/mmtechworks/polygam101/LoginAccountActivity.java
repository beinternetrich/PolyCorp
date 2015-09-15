package com.mmtechworks.polygam101;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import data.DatabaseHandler;

public class LoginAccountActivity extends Activity {
    DatabaseHandler controller = new DatabaseHandler(this);
    private EditText vfUsernameId;
    private EditText vfPasswordId;
    private Button vbtnLoginAccount;
    //protected boolean greenBop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_account);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        vfUsernameId = (EditText) findViewById(R.id.fUsernameId);
        vfPasswordId = (EditText) findViewById(R.id.fPasswordId);
        vbtnLoginAccount= (Button) findViewById(R.id.btnLoginAccount);
        vbtnLoginAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(LoginAccountActivity.this);
                final String getUsername = vfUsernameId.getText().toString();
                final String getPassword = vfPasswordId.getText().toString();

                if (getUsername.equals("") || getPassword.equals("")) {
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
                    boolean contYN;

                    contYN = controller.pullDomUser(getUsername, getPassword);
                    if (contYN){
                        Intent i = new Intent(getApplicationContext(), GameActivity.class);
                        controller.onCompleteI(getUsername,i);
                        startActivity(i);

                    } else {
                        Log.v("LOG_Log-Fail", "75Find Fail. Close and Go Back.");
                        dialog.setTitle("Existing Account Search Failure");
                        dialog.setMessage("An account of those creds does not exist for this creds. Try to login again");
                        dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
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
    }
}
