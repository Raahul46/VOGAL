package com.example.barath.syndvoicer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    ImageView option1 , option2,option3;

   public static String s1,s2,s3,s4,t;

    private static final String KEY1 = "statusone";
    private static final String KEY2 = "statustwo";
    private static final String KEY3 = "statusthree";
    private static final String KEY4 = "statusfour";

    Firebase ref ;

    FirebaseFirestore db;
    DocumentReference refone;
    Task<Void> reftwo;

    Map<String,Object>  cred = new HashMap<>() ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        option1 = (ImageView)findViewById(R.id.cardone);
        option2 = (ImageView) findViewById(R.id.cardtwo);
        option3 = (ImageView) findViewById(R.id.cardthree);

        cred.put("acc",WelcomActivity.s2);


        ref = new Firebase("https://synduser-aa827.firebaseio.com/filename");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                t = dataSnapshot.getValue().toString();
                System.out.println(t);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        db = FirebaseFirestore.getInstance();
        refone = db.collection("status").document("a716659");

        refone.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                s1 = documentSnapshot.getString(KEY1);
                s2 = documentSnapshot.getString(KEY2);
                s3 = documentSnapshot.getString(KEY3);
                s4 = documentSnapshot.getString(KEY4);

            }
        });

        db.collection("voice").document("Accno").set(cred)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,VoiceChat.class);
                startActivity(intent);
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,StatusActivity.class);
                startActivity(intent);
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,DetailsActivity.class);
                startActivity(intent);
            }
        });







    }
}
