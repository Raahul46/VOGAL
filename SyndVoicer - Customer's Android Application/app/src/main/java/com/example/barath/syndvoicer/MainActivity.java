package com.example.barath.syndvoicer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity  {

    private Button mrecord,finish;
    private TextView mTextView;

    private MediaRecorder mRecorder;

    private String mFileName = null;

    private static final String LOG_TAG = "Record_log";

    private StorageReference mStorage;
    private ProgressDialog mProgress;

    private DatabaseReference ref;

    String millisInString;
    public static String uniqueID;
    private static final Random random = new Random();
    private static final String CHARS = "1234567890";

    int f =0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ref = FirebaseDatabase.getInstance().getReference();


        mTextView = (TextView)findViewById(R.id.recordLabel);
        mrecord = (Button)findViewById(R.id.recordBtn);
        finish = (Button) findViewById(R.id.finish);

           mProgress =  new ProgressDialog(this);

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audio.mp4";

        mStorage = FirebaseStorage.getInstance().getReference();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         millisInString  = dateFormat.format(new Date());
         
        mrecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    startRecording();
                    mTextView.setText("Recording..");

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){

                   pauseRecording();
                    mTextView.setText("Recording Paused..");
                }

                return false;
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();

                openDialog();

                mTextView.setText("Recording Completed.");

            }
        });


    }


    private void startRecording() {
                if ( f == 0){

                    mRecorder = new MediaRecorder();
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mRecorder.setOutputFile(mFileName);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                    try {
                        mRecorder.prepare();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "prepare() failed");
                    }

                    mRecorder.start();
                }
                else
                {
                    mRecorder.resume();
                }

            }

    private void pauseRecording(){
        mRecorder.pause();
        f = 1;
    }

    private void stopRecording() {

        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        uploadAudio();
    }

    private void uploadAudio(){

        mProgress.setMessage("Uploading Audio");
        mProgress.show();

        getToken(6);

        StorageReference filepath = mStorage.child("Test").child("a"+uniqueID);

        ref.child("tokens").child("a"+uniqueID).child("tokenno").setValue(uniqueID);
        ref.child("tokens").child("a"+uniqueID).child("name").setValue(WelcomActivity.s1);
        ref.child("tokens").child("a"+uniqueID).child("accno").setValue(WelcomActivity.s2);
        ref.child("tokens").child("a"+uniqueID).child("branch").setValue(WelcomActivity.s3);
        ref.child("tokens").child("a"+uniqueID).child("ifsc").setValue(WelcomActivity.s4);
        ref.child("tokens").child("a"+uniqueID).child("mailid").setValue(WelcomActivity.s5);
        ref.child("tokens").child("a"+uniqueID).child("phone").setValue(WelcomActivity.s5);
        ref.child("tokens").child("a"+uniqueID).child("timestamp").setValue(millisInString);
        ref.child("filename").setValue("a"+uniqueID);

        Uri uri = Uri.fromFile(new File(mFileName));

        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mProgress.dismiss();

                mTextView.setText("Complaint Registered");

            }
        });

    }

    public void getToken(int length) {
        StringBuilder token = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            token.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
          uniqueID = token.toString();
    }

    public void openDialog () {
         ExampleDialog exampleDialog = new ExampleDialog();
         exampleDialog.show(getSupportFragmentManager(),"example dialog");


    }


}
