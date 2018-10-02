package com.example.user.projetelec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.user.projetelec.JoystickView;

public class MainActivity extends AppCompatActivity implements JoystickView.JoystickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JoystickView joystick = new JoystickView(this);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id){
        Log.d("Main method", "Xpercent : " + xPercent + " Y percent : " + yPercent);
    }


}
