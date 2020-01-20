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

public class infoCursorAdapter extends CursorAdapter {

    public infoCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.track_list_item, parent, false);
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
        TextView idTextView = (TextView) view.findViewById(R.id.track_id);
        TextView vnoTextView = (TextView) view.findViewById(R.id.track_v_no);
        TextView vnameTextView = (TextView) view.findViewById(R.id.track_v_vname);
        TextView dnameTextView = (TextView) view.findViewById(R.id.track_v_dname);
        TextView dnoTextView = (TextView) view.findViewById(R.id.track_v_dno);
        TextView latTextView = (TextView) view.findViewById(R.id.track_v_lat);
        TextView lonTextView = (TextView) view.findViewById(R.id.track_v_lon);

        // Find the columns of pet attributes that we're interested in
        int idColumnIndex = cursor.getColumnIndex(infoEntry._ID);
        int vnoColumnIndex = cursor.getColumnIndex(infoEntry.COLUMN_VEHICLE_NO);
        int dnameColumnIndex = cursor.getColumnIndex(infoEntry.COLUMN_DRIVER_NAME);
        int vnameColumnIndex = cursor.getColumnIndex(infoEntry.COLUMN_VEHICLE_NAME);
        int dnoColumnIndex = cursor.getColumnIndex(infoEntry.COLUMN_DRIVER_PHNO);
        int latColumnIndex = cursor.getColumnIndex(infoEntry.COLUMN_LATITUDE);
        int lonColumnIndex = cursor.getColumnIndex(infoEntry.COLUMN_LONGITUDE);

        String Vno = cursor.getString(vnoColumnIndex);
        String id = cursor.getString(idColumnIndex);
        String Dname = cursor.getString(dnameColumnIndex);
        String vname = cursor.getString(vnameColumnIndex);
        String Dno = cursor.getString(dnoColumnIndex);
        String lat = cursor.getString(latColumnIndex);
        String lon = cursor.getString(lonColumnIndex);
        // Update the TextViews with the attributes for the current pet
        vnoTextView.setText(Vno);
        dnameTextView.setText(Dname);
        vnameTextView.setText(vname);
        dnoTextView.setText(Dno);
        latTextView.setText(lat);
        lonTextView.setText(lon);
        idTextView.setText(id);
    }
}