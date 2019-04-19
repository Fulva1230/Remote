package com.noxdawn.remote.btconnect;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

public class AcceptThread extends Thread {
    private final BluetoothServerSocket mmServerSocket;
    private final BluetoothAdapter bluetoothAdapter;
    private final UUID MY_UUID;
    
    public AcceptThread(BluetoothAdapter bluetoothAdapter, UUID my_uuid, String name) {
        this.bluetoothAdapter = bluetoothAdapter;
        MY_UUID = my_uuid;
        // Use a temporary object that is later assigned to mmServerSocket
        // because mmServerSocket is final.
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code.
            tmp = this.bluetoothAdapter.listenUsingRfcommWithServiceRecord(name, MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mmServerSocket = tmp;
    }
    
    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned.
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            
        }
    }
    
    
    // Closes the connect socket and causes the thread to finish.
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
