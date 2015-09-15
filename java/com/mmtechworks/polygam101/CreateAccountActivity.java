package com.mmtechworks.polygam101;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import data.DatabaseHandler;

public class CreateAccountActivity extends Activity {
    DatabaseHandler controller = new DatabaseHandler(this);
    private EditText vfEmailId;
    private EditText vfUsernameId;
    private EditText vfPasswordId;
    private Button vbtnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        vfEmailId   =  (EditText) findViewById(R.id.fEmailId);
        vfUsernameId = (EditText) findViewById(R.id.fUsernameId);
        vfPasswordId = (EditText) findViewById(R.id.fPasswordId);
        vbtnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        vbtnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(CreateAccountActivity.this);
                final String getEmail = vfEmailId.getText().toString();
                final String getUsername = vfUsernameId.getText().toString();
                final String getPassword = vfPasswordId.getText().toString();

                if (getEmail.equals("") || getUsername.equals("") || getPassword.equals("")) {
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
                        contYN = controller.processDomUser("add", getUsername, getPassword, getEmail);
                    } catch (SQLiteConstraintException e) {
                        Log.v("LOG_CREATE", "==================Caught Exception: Email already exists. Please Login instead");
                    } catch(Exception e) {
                        Log.v("LOG_CREATE", "CatchAll Exception: Caught");
                    }

                    if (contYN){
                        controller.pullDomUser(getUsername, getPassword);
                        Intent i = new Intent(getApplicationContext(), GameActivity.class);
                        i.putExtra("game_new", "true");
                        controller.onCompleteI(getUsername,i);
                        startActivity(i);
                    } else {
                        Log.v("LOG_CA-Fail", "73Add failed. Close and Go Back.");
                        dialog.setTitle("New Account Creation Failure");
                        dialog.setMessage("An account may already exist for this email address. Try to login instead");
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
    }
}