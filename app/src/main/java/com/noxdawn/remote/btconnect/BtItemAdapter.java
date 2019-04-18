package com.noxdawn.remote.btconnect;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.noxdawn.remote.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BtItemAdapter extends BaseAdapter {
    final BluetoothAdapter bluetoothAdapter;
    private final List<BluetoothDevice> pairedDevices;
    
    public BtItemAdapter(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
        pairedDevices = new ArrayList<>(bluetoothAdapter.getBondedDevices());
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
        }
        return convertView;
    }
}
