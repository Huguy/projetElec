package com.example.user.projetelec;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SlideBarController extends AppCompatActivity implements SlideBarView.SlideBarListener {

    SlideBarView slide1;
    SlideBarView slide2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_bar_controller_activity);

        slide1 = findViewById(R.id.slide1);
        slide2 = findViewById(R.id.slide2);


    }

    @Override
    public void onSlideMoved(float position, int id){
        switch(id){
            case R.id.slide1 :
                Log.d("Vertical :", "position : " + position);
            case R.id.slide2 :
                Log.d("Horizontal", "position : " + position);
        }
    }


}