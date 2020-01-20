package com.example.das.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.das.R;
import com.example.das.data.infoContract.infoEntry;

public class alertCursorAdapter extends CursorAdapter {

    public alertCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.alert_list_item, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView idTextView = (TextView) view.findViewById(R.id.alert_v_id);
        TextView vnoTextView = (TextView) view.findViewById(R.id.alert_v_vno);
        TextView timeTextView = (TextView) view.findViewById(R.id.alert_v_time);
        TextView speedTextView = (TextView) view.findViewById(R.id.alert_v_speed);
        TextView dstateTextView = (TextView) view.findViewById(R.id.alert_v_state);
        TextView latTextView = (TextView) view.findViewById(R.id.alert_v_lat);
        TextView lonTextView = (TextView) view.findViewById(R.id.alert_v_log);

        // Find the columns of pet attributes that we're interested in
        int idColumnIndex = cursor.getColumnIndex(infoEntry._ID);
        int vnoColumnIndex = cursor.getColumnIndex(infoEntry.COLUMN_VEHICLE_NO);
        int timeColumnIndex = cursor.getColumnIndex(infoEntry.COLUMN_TIME);
        int speedColumnIndex = cursor.getColumnIndex(infoEntry.COLUMN_SPEED);
        int dstateColumnIndex = cursor.getColumnIndex(infoEntry.COLUMN_DRIVER_STATE);
        int latColumnIndex = cursor.getColumnIndex(infoEntry.COLUMN_LATITUDE);
        int lonColumnIndex = cursor.getColumnIndex(infoEntry.COLUMN_LONGITUDE);

        String Vno = cursor.getString(vnoColumnIndex);
        String id = cursor.getString(idColumnIndex);
        String time = cursor.getString(timeColumnIndex);
        String speed = cursor.getString(speedColumnIndex);
        String Dstate = cursor.getString(dstateColumnIndex);
        String lat = cursor.getString(latColumnIndex);
        String lon = cursor.getString(lonColumnIndex);

        vnoTextView.setText(Vno);
        timeTextView.setText(time);
        speedTextView.setText(speed);
        dstateTextView.setText(Dstate);
        latTextView.setText(lat);
        lonTextView.setText(lon);
        idTextView.setText(id);
    }
}