package com.example.barath.syndvoicer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StatusActivity extends AppCompatActivity {
    TextView one,two,three,four ;
    ImageView onei,twoi,threei,fouri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Firebase.setAndroidContext(this);

        one = (TextView)findViewById(R.id.status1);
        onei = (ImageView) findViewById(R.id.img1);
        two = (TextView)findViewById(R.id.status2);
        twoi = (ImageView) findViewById(R.id.img2);
        three = (TextView)findViewById(R.id.status3);
        threei = (ImageView) findViewById(R.id.img3);
        four = (TextView)findViewById(R.id.status4);
        fouri = (ImageView) findViewById(R.id.img4);


        if(HomeActivity.s1.equalsIgnoreCase("yes"))
        {
            YoYo.with(Techniques.FadeIn)
                    .duration(500)
                    .repeat(1)
                    .playOn(one);

            Animation animation = AnimationUtils.loadAnimation(this,R.anim.lefttoright);
            onei.startAnimation(animation);
        }
        if(HomeActivity.s1.equalsIgnoreCase("no"))
        {
            YoYo.with(Techniques.FadeOut)
                    .duration(500)
                    .repeat(1)
                    .playOn(one);

            YoYo.with(Techniques.FadeOut)
                    .duration(500)
                    .repeat(1)
                    .playOn(onei);
        }


        if(HomeActivity.s2.equalsIgnoreCase("yes"))
        {
            YoYo.with(Techniques.FadeIn)
                    .duration(500)
                    .repeat(1)
                    .playOn(two);

            Animation animation = AnimationUtils.loadAnimation(this,R.anim.lefttoright);
            twoi.startAnimation(animation);
        }
        if(HomeActivity.s2.equalsIgnoreCase("no"))
        {
            YoYo.with(Techniques.FadeOut)
                    .duration(500)
                    .repeat(1)
                    .playOn(two);

            YoYo.with(Techniques.FadeOut)
                    .duration(500)
                    .repeat(1)
                    .playOn(twoi);
        }

        if(HomeActivity.s3.equalsIgnoreCase("yes"))
        {
            YoYo.with(Techniques.FadeIn)
                    .duration(500)
                    .repeat(1)
                    .playOn(three);

            Animation animation = AnimationUtils.loadAnimation(this,R.anim.lefttoright);
            threei.startAnimation(animation);
        }
        if(HomeActivity.s3.equalsIgnoreCase("no"))
        {
            YoYo.with(Techniques.FadeOut)
                    .duration(500)
                    .repeat(1)
                    .playOn(three);

            YoYo.with(Techniques.FadeOut)
                    .duration(500)
                    .repeat(1)
                    .playOn(threei);
        }


        if(HomeActivity.s4.equalsIgnoreCase("yes"))
        {
            YoYo.with(Techniques.FadeIn)
                    .duration(500)
                    .repeat(1)
                    .playOn(four);

            Animation animation = AnimationUtils.loadAnimation(this,R.anim.lefttoright);
            fouri.startAnimation(animation);

        }

        if(HomeActivity.s4.equalsIgnoreCase("no"))
        {
            YoYo.with(Techniques.FadeOut)
                    .duration(500)
                    .repeat(1)
                    .playOn(four);

            YoYo.with(Techniques.FadeOut)
                    .duration(500)
                    .repeat(1)
                    .playOn(fouri);
        }



    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}
