package com.example.barath.syndvoicer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailsActivity extends AppCompatActivity {

    TextView name,mobile,accno,branch,ifsc,mail;
    FirebaseFirestore db;
    DocumentReference ref;
    ImageView backit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        name = (TextView) findViewById(R.id.postone);
        mobile = (TextView)findViewById(R.id.posttwo);
        accno = (TextView) findViewById(R.id.postthree);
        branch = (TextView) findViewById(R.id.postfour);
        ifsc = (TextView) findViewById(R.id.postfive);
        mail = (TextView) findViewById(R.id.postsix);
        backit = (ImageView)findViewById(R.id.back);


        backit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

           name.setText(WelcomActivity.s1);
           mobile.setText(WelcomActivity.s6);
           accno.setText(WelcomActivity.s2);
           branch.setText(WelcomActivity.s3);
           ifsc.setText(WelcomActivity.s4);
           mail.setText(WelcomActivity.s5);

    }
}
