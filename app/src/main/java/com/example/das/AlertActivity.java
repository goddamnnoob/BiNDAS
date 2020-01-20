package com.example.das;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.das.data.alertCursorAdapter;
import com.example.das.data.infoContract.infoEntry;
import com.example.das.data.infoDbHelper;

public class AlertActivity extends AppCompatActivity {
    private infoDbHelper mDBHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_alert);
        mDBHelper = new infoDbHelper(this);
        ListView infoListView = (ListView) findViewById(R.id.alert_v);
        View emptyView = findViewById(R.id.empty_view);
        infoListView.setEmptyView(emptyView);
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.swiperefresh1);
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
                infoEntry.COLUMN_TIME,
                infoEntry.COLUMN_SPEED,
                infoEntry.COLUMN_DRIVER_STATE,
                infoEntry.COLUMN_LONGITUDE,
                infoEntry.COLUMN_LATITUDE,};
        ListView listView = (ListView) findViewById(R.id.alert_v);
        Cursor cursor = database.query(infoEntry.TABLE_NAME,projection,null,null,null,null,null);
        alertCursorAdapter adapter = new alertCursorAdapter(this, cursor);
        listView.setAdapter(adapter);
    }
}
