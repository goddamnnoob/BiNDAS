package com.example.das.data;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class infoContract {


    public final static class infoEntry implements BaseColumns{

        public static String TABLE_NAME ="das";
        public static final String _ID =BaseColumns._ID;
        public static final String COLUMN_VEHICLE_NO ="vehicleno";
        public static final String COLUMN_VEHICLE_NAME ="vehiclename";
        public static final String COLUMN_VEHICLE_PHNO = "vehiclephno";
        public static final String COLUMN_DRIVER_NAME ="drivername";
        public static final String COLUMN_DRIVER_PHNO = "driverphoneno";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_SPEED = "speed";
        public static final String COLUMN_DRIVER_STATE = "driverstate";
        public static final String COLUMN_TIME = "time";

    }
}
