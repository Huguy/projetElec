package com.example.user.projetelec;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static com.example.user.projetelec.MainActivity.EXTRA_ADDRESS;

public class Commandes extends AppCompatActivity {

    String address = null;
    Button bras;
    Button pince;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commands);

        Intent newint = getIntent();
        address = newint.getStringExtra(EXTRA_ADDRESS); //receive the address of the bluetooth device

        bras = findViewById(R.id.bras);
        pince = findViewById(R.id.pince);

        bras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Commandes.this, JoystickController.class);
                i.putExtra(EXTRA_ADDRESS, address); //this will be received at ledControl (class) Activity
                startActivity(i);
            }
        });

        pince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Commandes.this, SlideBarController.class);
                i.putExtra(EXTRA_ADDRESS, address); //this will be received at ledControl (class) Activity
                startActivity(i);
            }
        });


    }
}
