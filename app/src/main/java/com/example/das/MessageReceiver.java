package com.example.das;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class MessageReceiver extends BroadcastReceiver {

    private static com.example.das.MessageListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");
        for(int i=0; i<pdus.length; i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String message =  smsMessage.getMessageBody();
            String phoneNo = smsMessage.getOriginatingAddress();
            mListener.senderPhoneno(phoneNo);
            mListener.alertTime(smsMessage.getTimestampMillis());
            retrieve(message);
        }
    }

    public static void bindListener(com.example.das.MessageListener listener){
        mListener = listener;
    }
    public static void retrieve(String msg){
        String s1 = msg ;
        int i = 0;

        for(String w:s1.split(";",0)){
            if (i==0){
                mListener.vehicleNo(w);
            }
            else if (i==1){
                mListener.latitudeinfo(w);
            }
            else if (i==2){
                mListener.longitudeinfo(w);
            }
            else if (i==3){
                mListener.currentSpeed(w);
            }
            else if (i==4){
                mListener.driverState(w);
            }
            else{
                mListener.latitudeinfo(null);
                mListener.latitudeinfo(null);
                mListener.currentSpeed(null);
                mListener.vehicleNo(null);
                mListener.driverState(null);
            }
            i++;
        }

    }
}