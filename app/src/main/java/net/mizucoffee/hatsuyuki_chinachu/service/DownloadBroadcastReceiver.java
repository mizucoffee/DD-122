package net.mizucoffee.hatsuyuki_chinachu.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class DownloadBroadcastReceiver extends BroadcastReceiver {

    private Handler receiverHandler = new Handler();

    public DownloadBroadcastReceiver(Handler handler){
        receiverHandler = handler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Message msg = new Message();
        msg.setData(bundle);
        receiverHandler.sendMessage(msg);
    }
}
