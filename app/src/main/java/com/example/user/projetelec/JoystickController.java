package com.example.user.projetelec;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;
import java.io.IOException;
import java.util.UUID;
import com.example.user.projetelec.R;

import static com.example.user.projetelec.MainActivity.EXTRA_ADDRESS;


public class JoystickController extends AppCompatActivity implements JoystickView.JoystickListener {

    JoystickView joy1;
    JoystickView joy2;
    Button btnDis;
    Button commands;
    Button pince;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    private float xJ1, xJ2, yJ1, yJ2;
    //SPP UUID. Look for it


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        //address = newint.getStringExtra(MainActivity.EXTRA_ADDRESS); //receive the address of the bluetooth device

        //view of the ledControl
        setContentView(R.layout.activity_joystick_controller);

        //call the widgtes
        btnDis = findViewById(R.id.button);
        pince = findViewById(R.id.pince);
        commands = findViewById(R.id.commandes);
        joy1=findViewById(R.id.Joy1);
        joy2=findViewById(R.id.Joy2);




        //new ConnectBT().execute(); //Call the class to connect


        btnDis.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Disconnect(); //close connection
            }
        });

        pince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(JoystickController.this, SlideBarController.class);
                i.putExtra(EXTRA_ADDRESS, address); //this will be received at ledControl (class) Activity
                startActivity(i);
            }
        });

        commands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (JoystickController.this, Commandes.class);
                i.putExtra(EXTRA_ADDRESS, address); //this will be received at ledControl (class) Activity
                startActivity(i);
            }
        });

        btSocket = Bluetooth.getInstance().getCurrentBluetoothConnection();

    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id){
        /*switch (id){
            case R.id.Joy1:
                Log.d("Left", "Xpercent : " + xPercent + " Y percent : " + yPercent);
                leftJoystick(xPercent, yPercent);
                break;

            case R.id.Joy2:
                Log.d("Right", "Xpercent : " + xPercent + " Y percent : " + yPercent);
                rightJoystick(xPercent, yPercent);
                break;

        }*/
        switch (id){
            case R.id.Joy1:
                xJ1 = xPercent;
                yJ1 = yPercent;
                leftJoystick(xJ1, yJ1);
                break;

            case R.id.Joy2:
                xJ2 = xPercent;
                yJ2 = yPercent;
                rightJoystick(xJ2, yJ2);
                break;
        }



    }

    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            {} //msg("Error");}
        }
        finish(); //return to the first layout

    }



    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_led_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    public void leftJoystick(float xPercent, float yPercent){
        if (btSocket!=null)
        {
            if ((xPercent>0.2) && (yPercent<0.5) && (yPercent>-0.5)){
                try
                {
                    btSocket.getOutputStream().write("D".toString().getBytes());
                    Log.d("1D", "1D");
                }
                catch (IOException e)
                {
                   // msg("Error");
                }
            }
            else if ((xPercent<-0.2) && (yPercent<0.5) && (yPercent>-0.5)){
                Log.d("1G", "1G");
                try
                {
                    btSocket.getOutputStream().write("G".toString().getBytes());
                }
                catch (IOException e)
                {
                    //msg("Error");
                }
            }
            else if ((yPercent<-0.2) && (xPercent<0.5) &&(xPercent>-0.5)){
                Log.d("1H", "1H");
                try
                {
                    btSocket.getOutputStream().write("H".toString().getBytes());
                }
                catch (IOException e)
                {
                    //msg("Error");
                }
            }
            else if ((yPercent>0.2) && (xPercent<0.5) &&(xPercent>-0.5)){
                Log.d("1B", "1B");
                try
                {
                    btSocket.getOutputStream().write("B".toString().getBytes());
                }
                catch (IOException e)
                {
                    //msg("Error");
                }
            }
            else if ((yPercent<0.1) && (yPercent>-0.1) && (xPercent<0.1) && (xPercent>-0.1)){
                Log.d("1N", "1N");
                try
                {
                    btSocket.getOutputStream().write("f".toString().getBytes());
                }
                catch (IOException e){
                    //msg("Error");
                }
            }

        }
    }

    public void rightJoystick(float xPercent, float yPercent){
        if (btSocket!=null)
        {
            if ((xPercent>0.2) && (yPercent<0.5) && (yPercent>-0.5)){
                try
                {
                    btSocket.getOutputStream().write("d".toString().getBytes());
                }
                catch (IOException e)
                {
                    //msg("Error");
                }
            }
            else if ((xPercent<-0.2) && (yPercent<0.5) && (yPercent>-0.5)){
                try
                {
                    btSocket.getOutputStream().write("g".toString().getBytes());
                }
                catch (IOException e)
                {
                    //msg("Error");
                }
            }
            else if ((yPercent>0.2) && (xPercent<0.5) &&(xPercent>-0.5)){
                try
                {
                    btSocket.getOutputStream().write("b".toString().getBytes());
                }
                catch (IOException e)
                {
                    //msg("Error");
                }
            }
            else if ((yPercent<-0.2) && (xPercent<0.5) &&(xPercent>-0.5)){
                try
                {
                    btSocket.getOutputStream().write("h".toString().getBytes());
                }
                catch (IOException e)
                {
                    //msg("Error");
                }
            }
            else if ((yPercent==0) && (xPercent==0)){
                Log.d("2N", "2N");
                try
                {
                    btSocket.getOutputStream().write("f".toString().getBytes());
                }
                catch (IOException e){
                    //msg("Error");
                }
            }

        }
    }


}
