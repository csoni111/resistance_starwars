package com.droidwars.starwars.resistance;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView human = (ImageView) findViewById(R.id.iv_left);
        ImageView darth_vader = (ImageView) findViewById(R.id.iv_right);
        Picasso.with(this).load(R.drawable.human).fit().into(human);
        Picasso.with(this).load(R.drawable.darth_vader).fit().into(darth_vader);




    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void addWarrior_screen(View view)
    {
        Intent slideactivity = new Intent(MainActivity.this, addWarrior.class);
        Bundle bndlanimation =
                ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim3, R.anim.anim4).toBundle();
        //startActivity(new Intent(MainActivity.this, addWarrior.class));
        startActivityForResult(slideactivity,1, bndlanimation);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void disp_warrior_screen(View view)
    {
        Intent slideactivity = new Intent(MainActivity.this, disp_warrior.class);
        Bundle bndlanimation =
                ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim1, R.anim.anim2).toBundle();
        //startActivity(new Intent(MainActivity.this, addWarrior.class));
        startActivity(slideactivity, bndlanimation);
    }




}
