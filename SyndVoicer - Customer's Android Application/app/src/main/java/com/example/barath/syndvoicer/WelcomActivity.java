package com.example.barath.syndvoicer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.SpeechRecognizer;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class WelcomActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 9000;
   public static String user,s1,s2,s3,s4,s5,s6;

    private static final String KEY1 = "accholdername";
    private static final String KEY2 = "accno";
    private static final String KEY3 = "branch";
    private static final String KEY4 = "ifsc";
    private static final String KEY5 = "mailid";
    private static final String KEY6 = "phone";

    FirebaseFirestore db;
    DocumentReference ref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

        user = LoginActivity.username;
        db = FirebaseFirestore.getInstance();
        ref = db.collection("users").document(LoginActivity.username);

        final MediaPlayer welcome = MediaPlayer.create(this,R.raw.last);

        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                s1 = documentSnapshot.getString(KEY1);
                s2 = documentSnapshot.getString(KEY2);
                s3 = documentSnapshot.getString(KEY3);
                s4 = documentSnapshot.getString(KEY4);
                s5 = documentSnapshot.getString(KEY5);
                s6 = documentSnapshot.getString(KEY6);
                Typewriter writer = (Typewriter)findViewById(R.id.typewriter);
                //Add a character every 150ms

                welcome.start();

                writer.setCharacterDelay(90);
                writer.animateText("Hello "+s1+"\nWelcome to Syndicate Bank\nHow may I help you?");

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent homeintent = new Intent(WelcomActivity.this, HomeActivity.class);
                startActivity(homeintent);
                finish();

            }
        }, SPLASH_TIME_OUT);

    }


}
