package com.example.das;

import android.Manifest;
import com.example.das.data.infoContract.infoEntry;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.example.das.data.infoDbHelper;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements MessageListener{
    private String phNo = null;
    private String Vno = null;
    private infoDbHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new infoDbHelper(this);
        int REQUEST_PHONE_CALL = 1;

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, REQUEST_PHONE_CALL);
        }
        MessageReceiver.bindListener(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.start);
        new CountDownTimer(1500,500){
            @Override
            public void onTick(long millisUntilFinished){}

            @Override
            public void onFinish(){
                //set the new Content of your activity
                MainActivity.this.setContentView(R.layout.activity_main);
                Button addV = findViewById(R.id.add_v);
                Button trackV = findViewById(R.id.track_v);
                Button alert = findViewById(R.id.alert);
                addV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(MainActivity.this, AddVehicleActivity.class);
                        startActivity(i);
                    }

                });
                trackV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(MainActivity.this, TrackActivity.class);
                        startActivity(i);

                    }

                });
                alert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(MainActivity.this, AlertActivity.class);
                        startActivity(i);
                    }

                });
            }
        }.start();
    }

    @Override
    public void vehicleNo(String vno){
        Vno = vno;
        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        ContentValues infovalues = new ContentValues();
        infovalues.put(infoEntry.COLUMN_VEHICLE_NO,vno);
        String selection = infoEntry.COLUMN_VEHICLE_PHNO + "=?";
        String[] selectionArgs = new String[] {phNo};
        int x = database.update(infoEntry.TABLE_NAME,infovalues,selection,selectionArgs);
    }
    @Override
    public void senderPhoneno(String phno){
        phNo = phno;
        Toast.makeText(MainActivity.this,phNo,Toast.LENGTH_LONG).show();
    }

    @Override
    public void longitudeinfo(String longi){
        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        ContentValues infovalues = new ContentValues();
        infovalues.put(infoEntry.COLUMN_LONGITUDE,longi);
        String selection = infoEntry.COLUMN_VEHICLE_PHNO + "=?";
        String[] selectionArgs = new String[] {phNo};
        int x = database.update(infoEntry.TABLE_NAME,infovalues,selection,selectionArgs);
        if(x!=0){
            showNotification("ALERT!!",Vno+=" In trouble");
        }
    }
    @Override
    public void latitudeinfo(String lati){
        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        ContentValues infovalues = new ContentValues();
        infovalues.put(infoEntry.COLUMN_LATITUDE, lati );
        String selection = infoEntry.COLUMN_VEHICLE_PHNO + "=?";
        String[] selectionArgs = new String[] {phNo};
        database.update(infoEntry.TABLE_NAME,infovalues,selection,selectionArgs);
    }
    @Override
    public void driverState(String state){
        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        ContentValues infovalues = new ContentValues();
        infovalues.put(infoEntry.COLUMN_DRIVER_STATE, state );
        String selection = infoEntry.COLUMN_VEHICLE_PHNO + "=?";
        String[] selectionArgs = new String[] {phNo};
        database.update(infoEntry.TABLE_NAME,infovalues,selection,selectionArgs);
    }
    @Override
    public void currentSpeed(String speed){
        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        ContentValues alertvalues = new ContentValues();
        alertvalues.put(infoEntry.COLUMN_SPEED,speed);
        String selection = infoEntry.COLUMN_VEHICLE_PHNO + "=?";
        String[] selectionArgs = new String[] {phNo};
        database.update(infoEntry.TABLE_NAME,alertvalues,selection,selectionArgs);
    }
    @Override
    public void alertTime(long time){
        Date dateObject = new Date(time);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        String dateToDisplay = dateFormatter.format(dateObject);
        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        ContentValues alertvalues = new ContentValues();
        alertvalues.put(infoEntry.COLUMN_TIME,dateToDisplay);
        String selection = infoEntry.COLUMN_VEHICLE_PHNO + "=?";
        String[] selectionArgs = new String[] {phNo};
        database.update(infoEntry.TABLE_NAME,alertvalues,selection,selectionArgs);
    }
    void showNotification(String title, String message) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("9797",
                    "DASAPP",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("ALERT");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "9797")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(message)// message for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.bus_horn);
        mediaPlayer.start();
    }
}
