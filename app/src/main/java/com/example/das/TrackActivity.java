package com.example.das;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.das.data.infoContract.infoEntry;
import com.example.das.data.infoCursorAdapter;
import com.example.das.data.infoDbHelper;

public class TrackActivity extends AppCompatActivity {
    private infoDbHelper mDBHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_track_v);
        mDBHelper = new infoDbHelper(this);
        ListView infoListView = (ListView) findViewById(R.id.track_list);
        View emptyView = findViewById(R.id.empty_view);
        infoListView.setEmptyView(emptyView);
        Button deleteAll = (Button) findViewById(R.id.delete_all);
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase database = mDBHelper.getReadableDatabase();
                database.delete(infoEntry.TABLE_NAME,null,null);
                deleteDatabase("info.db");
                Toast.makeText(TrackActivity.this,"Deleted",Toast.LENGTH_LONG).show();
            }

        });
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.swiperefresh);
        pullToRefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        displayDatabaseInfo();
                        pullToRefresh.setRefreshing(false);
                    }
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase database = mDBHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                infoEntry._ID,
                infoEntry.COLUMN_VEHICLE_NO,
                infoEntry.COLUMN_VEHICLE_NAME,
                infoEntry.COLUMN_DRIVER_NAME,
                infoEntry.COLUMN_DRIVER_PHNO,
                infoEntry.COLUMN_LONGITUDE,
                infoEntry.COLUMN_LATITUDE,};
        ListView listView = (ListView) findViewById(R.id.track_list);
        Cursor cursor = database.query(infoEntry.TABLE_NAME,projection,null,null,null,null,null);
        infoCursorAdapter adapter = new infoCursorAdapter(this, cursor);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SQLiteDatabase database = mDBHelper.getReadableDatabase();
                String[] projection = {
                        infoEntry.COLUMN_LONGITUDE,
                        infoEntry.COLUMN_LATITUDE,};
                String selection = infoEntry._ID+ "=?";
                String pos = Integer.toString(position+1);
                String[] selectionArgs = new String[] {pos};
                Cursor cursor = database.query(infoEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
                if (cursor != null)
                    cursor.moveToFirst();
                String log = cursor.getString(0);
                String lat = cursor.getString(1);
                Toast.makeText(TrackActivity.this, "LOG AND LAT"+log+lat, Toast.LENGTH_SHORT).show();
                Uri gmmIntentUri = Uri.parse("geo:"+log+","+lat);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                cursor.close();
            }
        });
    }
}
