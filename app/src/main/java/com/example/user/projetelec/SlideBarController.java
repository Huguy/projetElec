package com.example.user.projetelec;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import static com.example.user.projetelec.MainActivity.EXTRA_ADDRESS;

public class SlideBarController extends AppCompatActivity implements JoystickView.JoystickListener {

    JoystickView joy1;
    Button dis;
    Button commands;
    Button bras;
    Button check;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    String address = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog progress;
    private float xJ3, yJ3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_bar_controller_activity);

        Intent newint = getIntent();
        address = newint.getStringExtra(EXTRA_ADDRESS); //receive the address of the bluetooth device

        joy1 = findViewById(R.id.JoyMiddle);

        dis = findViewById(R.id.disconnect);
        commands = findViewById(R.id.commandes);
        bras = findViewById(R.id.bras);
        check = findViewById(R.id.check);

        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Disconnect();
            }
        });

        bras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btSocket != null){
                    try {
                        btSocket.getOutputStream().write("C".toString().getBytes());
                    }
                    catch (IOException e){

                    }
                }
            }
        });

        commands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (SlideBarController.this, Commandes.class);
                i.putExtra(EXTRA_ADDRESS, address); //this will be received at ledControl (class) Activity
                startActivity(i);
            }
        });

        btSocket = Bluetooth.getInstance().getCurrentBluetoothConnection();

    }


    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id){
        switch (id){
            case R.id.JoyMiddle :
                xJ3 = xPercent;
                yJ3 = yPercent;
                controlJoystick2(xJ3, yJ3);
                break;
        }



    }



    private void controlJoystick2(float xPercent, float yPercent){
         if (btSocket!=null){
             if ((xPercent>0.2) && (yPercent<0.5) && (yPercent>-0.5)){
                 try
                 {
                     btSocket.getOutputStream().write("R".toString().getBytes());
                     Log.d("1R", "1R");
                 }
                 catch (IOException e)
                 {
                     // msg("Error");
                 }
             }
             else if ((xPercent<-0.2) && (yPercent<0.5) && (yPercent>-0.5)){
                 Log.d("1r", "1r");
                 try
                 {
                     btSocket.getOutputStream().write("r".toString().getBytes());
                 }
                 catch (IOException e)
                 {
                     //msg("Error");
                 }
             }
             else if ((yPercent<-0.2) && (xPercent<0.5) &&(xPercent>-0.5)){
                 Log.d("1S", "1S");
                 try
                 {
                     btSocket.getOutputStream().write("S".toString().getBytes());
                 }
                 catch (IOException e)
                 {
                     //msg("Error");
                 }
             }
             else if ((yPercent>0.2) && (xPercent<0.5) &&(xPercent>-0.5)){
                 Log.d("1s", "1s");
                 try
                 {
                     btSocket.getOutputStream().write("s".toString().getBytes());
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


    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }


    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(SlideBarController.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }


    }
}