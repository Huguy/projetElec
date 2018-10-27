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

public class SlideBarController extends AppCompatActivity implements SlideBarView.SlideBarListener {

    SlideBarView slide1;
    SlideBarView slide2;
    Button dis;
    Button commands;
    Button bras;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    String address = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_bar_controller_activity);

        Intent newint = getIntent();
        address = newint.getStringExtra(EXTRA_ADDRESS); //receive the address of the bluetooth device

        slide1 = findViewById(R.id.slide1);
        slide2 = findViewById(R.id.slide2);

        dis = findViewById(R.id.disconnect);
        commands = findViewById(R.id.commandes);
        bras = findViewById(R.id.bras);

        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Disconnect();
            }
        });

        bras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SlideBarController.this, JoystickController.class);
                i.putExtra(EXTRA_ADDRESS, address); //this will be received at ledControl (class) Activity
                startActivity(i);
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
    public void onSlideMoved(float position, int id){
        switch(id){
            case R.id.slide1 :
                Log.d("Vertical :", "position : " + position);
                VerticalSlide(position);
                break;
            case R.id.slide2 :
                Log.d("Horizontal", "position : " + position);
                HorizontalSlide(position);
                break;
        }
    }


    private void VerticalSlide(float position){
         if (btSocket!=null){
            if (position > 0.25 && position < 0.5){
                try
                {
                    btSocket.getOutputStream().write("i".toString().getBytes());
                    Log.d("Vertical", "V1");
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
            else if (position > 0.5 && position <0.75){
                try
                {
                    btSocket.getOutputStream().write("o".toString().getBytes());
                    Log.d("Vertical", "V2");
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
            else if (position > 0.75) {
                try
                {
                    btSocket.getOutputStream().write("p".toString().getBytes());
                    Log.d("Vertical", "V3");
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
            else if (position < -0.25 && position > -0.5){
                try
                {
                    btSocket.getOutputStream().write("w".toString().getBytes());
                    Log.d("Vertical", "V-1");
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
            else if (position < -0.5 && position > -0.75){
                try
                {
                    btSocket.getOutputStream().write("x".toString().getBytes());
                    Log.d("Vertical", "V-2");
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
            else if (position < -0.75){
                try
                {
                    btSocket.getOutputStream().write("c".toString().getBytes());
                    Log.d("Vertical", "V-3");
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
            else if ((position > -0.25) && (position < 0.25)){
                try
                {
                    btSocket.getOutputStream().write("f".toString().getBytes());
                    Log.d("Vertical", "Vf");
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
         }
    }

    private void HorizontalSlide(float position){
        if (btSocket!=null){
            if (position > 0.25 && position < 0.5){
                try
                {
                    btSocket.getOutputStream().write("1".toString().getBytes());
                    Log.d("Horizontal", "H1");
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
            else if (position > 0.5 && position <0.75){
                try
                {
                    btSocket.getOutputStream().write("2".toString().getBytes());
                    Log.d("Horizontal", "H2");
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
            else if (position > 0.75) {
                try
                {
                    btSocket.getOutputStream().write("3".toString().getBytes());
                    Log.d("Horizontal", "H3");
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
            else if (position < -0.25 && position > -0.5){
                try
                {
                    btSocket.getOutputStream().write("4".toString().getBytes());
                    Log.d("Horizontal", "H-1");
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
            else if (position < -0.5 && position > -0.75){
                try
                {
                    btSocket.getOutputStream().write("5".toString().getBytes());
                    Log.d("Horizontal", "H-2");
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
            else if (position < -0.75){
                try
                {
                    btSocket.getOutputStream().write("6".toString().getBytes());
                    Log.d("Horizontal", "H-3");
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
            else if ((position > -0.25) && (position < 0.25)){
                try
                {
                    btSocket.getOutputStream().write("f".toString().getBytes());
                    Log.d("Vertical", "Vf");
                }
                catch (IOException e)
                {
                    msg("Error");
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