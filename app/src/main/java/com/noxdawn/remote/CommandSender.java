package com.noxdawn.remote;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class CommandSender {
    private final String identifier;
    private final AtomicReference<OutputStream> oStreamR;
    
    public CommandSender(String identifier, AtomicReference<OutputStream> oStreamR) {
        this.identifier = identifier;
        this.oStreamR = oStreamR;
    }
    
    public void sendCommand(Context context, @Nullable String... args) {
        StringBuilder arg = null;
        if (args != null && args.length != 0) {
            arg = new StringBuilder();
            for (int i = 0; i < args.length - 1; i++) {
                arg.append(args[i]).append(":");
            }
            arg.append(args[args.length - 1]);
        }
        try {
            OutputStream oStream = oStreamR.get();
            synchronized (oStream) {
                String message = null;
                if (arg != null) {
                    message = String.format(Locale.getDefault(), "%s:%s;", identifier, arg.toString());
                } else {
                    message = String.format("%s;", identifier);
                }
                oStream.write(message.getBytes());
                Log.d("command send:", message);
                oStream.flush();
            }
        } catch (IOException e) {
            Toast message = Toast.makeText(context, "fail to send message", Toast.LENGTH_SHORT);
            message.show();
            e.printStackTrace();
        }
    }
}
