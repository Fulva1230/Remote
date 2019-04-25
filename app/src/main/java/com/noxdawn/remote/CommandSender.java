package com.noxdawn.remote;

import android.content.Context;
import android.widget.Toast;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

public class CommandSender {
    private final String identifier;
    private final OutputStream oStream;
    
    public CommandSender(String identifier, OutputStream oStream) {
        this.identifier = identifier;
        this.oStream = oStream;
    }
    
    public void sendCommand(Context context, @Nullable String... args) {
        StringBuilder arg = null;
        if (args != null) {
            arg = new StringBuilder();
            for (int i = 0; i < args.length - 1; i++) {
                arg.append(args[i]).append(":");
            }
            arg.append(args[args.length - 1]);
        }
        try {
            synchronized (oStream) {
                if (arg != null) {
                    oStream.write(String.format(Locale.getDefault(), "%s:%s;", identifier, arg.toString()).getBytes());
                } else {
                    oStream.write(identifier.getBytes());
                }
                oStream.flush();
            }
        } catch (IOException e) {
            Toast message = Toast.makeText(context, "fail to send message", Toast.LENGTH_SHORT);
            message.show();
            e.printStackTrace();
        }
    }
}
