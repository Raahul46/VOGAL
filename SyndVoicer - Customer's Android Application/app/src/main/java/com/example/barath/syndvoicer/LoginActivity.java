package com.example.barath.syndvoicer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText user , pass ;
   public static String username , password ;
    DatabaseReference ref ;
    Button login ;

    private static String TAG = "PermissionDemo";
    private static final int RECORD_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);

        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);

        ref = FirebaseDatabase.getInstance().getReference();

        // String DeviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);

        login.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                username = user.getText().toString();
                password = pass.getText().toString();

                if( username != null && password !=null){

                    if(username.equalsIgnoreCase("janani01")&&password.equalsIgnoreCase("syndicate")){
                Intent intent = new Intent(LoginActivity.this,WelcomActivity.class);
                startActivity(intent);}
                    else if (username.equalsIgnoreCase("rahul02")&&password.equalsIgnoreCase("syndicate")){
                        Intent intent = new Intent(LoginActivity.this,WelcomActivity.class);
                        startActivity(intent);}
                    else {
                        Toast.makeText(LoginActivity.this,"Login Unsuccessful",Toast.LENGTH_SHORT);
                    }

            }}
        });


    }

    private void askpermissions() {
        String permissions[] = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE};
        if (ContextCompat.checkSelfPermission(this,
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,
                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,
                permissions[2]) == PackageManager.PERMISSION_GRANTED ){
            Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();


        }
        else{
            ActivityCompat.requestPermissions(this,permissions,RECORD_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RECORD_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user");
                } else {
                    Log.i(TAG, "Permission has been granted by user");
                }
            }
        }
    }







}
