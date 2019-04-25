package com.noxdawn.remote.btconnect;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.widget.TextView;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

public class OStreamFetcher implements Runnable, Closeable {
    private final BluetoothDevice device;
    private final AtomicReference<OutputStream> oStreamR;
    private final Activity activity;
    private final TextView blueToothInform;
    private BluetoothSocket socket;
    
    public OStreamFetcher(BluetoothDevice device, AtomicReference<OutputStream> oStreamR, Activity activity, TextView blueToothInform) {
        this.device = device;
        this.oStreamR = oStreamR;
        this.activity = activity;
        this.blueToothInform = blueToothInform;
        socket = null;
        oStreamR.set(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            
            }
        });
    }
    
    
    @Override
    public void run() {
        try {
            socket = device.createRfcommSocketToServiceRecord(BtItemAdapter.SPP_UUID);
            socket.connect();
            oStreamR.set(socket.getOutputStream());
            activity.runOnUiThread(() -> {
                blueToothInform.setText(String.format("connected to %s", device.getName()));
            });
        } catch (IOException e) {
            // Toast toastMessage = Toast.makeText(context, "connect attempt failed", LENGTH_SHORT);
            // toastMessage.show();
            e.printStackTrace();
            activity.runOnUiThread(() -> {
                blueToothInform.setText(String.format("fail to connect to %s", device.getName()));
            });
        }
    }
    
    @Override
    public void close() throws IOException {
        socket.close();
    }
}
