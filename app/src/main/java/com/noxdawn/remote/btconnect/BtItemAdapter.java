package com.noxdawn.remote.btconnect;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.noxdawn.remote.Controller;
import com.noxdawn.remote.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BtItemAdapter extends BaseAdapter {
    private final BluetoothAdapter bluetoothAdapter;
    private final List<BluetoothDevice> pairedDevices;
    private final Activity parentActivity;
    public static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static final String BLUETOOTH_ADDRESS = "bluetooth_address";
    public static final String BLUETOOTH_NAME = "bluetooth_name";
    
    public BtItemAdapter(BluetoothAdapter bluetoothAdapter, Activity parentActivity) {
        this.bluetoothAdapter = bluetoothAdapter;
        pairedDevices = new ArrayList<>(bluetoothAdapter.getBondedDevices());
        this.parentActivity = parentActivity;
    }
    
    @Override
    public int getCount() {
        return pairedDevices.size();
    }
    
    @Override
    public Object getItem(int position) {
        return null;
    }
    
    @Override
    public long getItemId(int position) {
        return 0;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item, parent, false);
            ((TextView) convertView.findViewById(R.id.textView))
                    .setText(pairedDevices.get(position).getName());
            convertView.setOnClickListener((v) -> {
                Intent intent = new Intent(parentActivity, Controller.class);
                intent.putExtra(BLUETOOTH_NAME, pairedDevices.get(position).getName());
                intent.putExtra(BLUETOOTH_ADDRESS, pairedDevices.get(position).getAddress());
                parentActivity.startActivity(intent);
            });
        }
        return convertView;
    }
}
