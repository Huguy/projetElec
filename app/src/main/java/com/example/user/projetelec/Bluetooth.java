package com.example.user.projetelec;

import android.app.Application;
import android.bluetooth.BluetoothSocket;

public class Bluetooth extends Application {

    private static Bluetooth sInstance;

    public static Bluetooth getInstance(){
        return sInstance;
    }

    BluetoothSocket btSocket = null;

    public void onCreate () {
        super.onCreate();
        sInstance = this;
    }

    public void setupBluetoothConnection(BluetoothSocket bt){
        btSocket = bt;
    }

    public BluetoothSocket getCurrentBluetoothConnection() {
        return btSocket;
    }

}
