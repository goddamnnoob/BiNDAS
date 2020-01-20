package com.example.das;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.das.data.infoContract.infoEntry;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class AddVehicleActivity extends AppCompatActivity {
    private EditText vno ;
    private EditText dna ;
    private EditText vna ;
    private EditText dno;
    private EditText vphno;
    private com.example.das.data.infoDbHelper mDBHelper;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_v);
        mDBHelper = new com.example.das.data.infoDbHelper(this);
        vno= (EditText) findViewById(R.id.add_v_vno);
        dna= (EditText) findViewById(R.id.add_v_dname);
        vna= (EditText) findViewById(R.id.add_v_vname);
        dno= (EditText) findViewById(R.id.add_dno);
        vphno = (EditText) findViewById(R.id.add_vphno);
        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertInfo();
                finish();
                Toast.makeText(AddVehicleActivity.this,"Saved sucessfully"+vno.getText().toString(),Toast.LENGTH_LONG).show();

            }

        });
        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddVehicleActivity.this, MainActivity.class);
                startActivity(i);
            }

        });
    }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private void insertInfo(){
            SQLiteDatabase database;
            database = mDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(infoEntry.COLUMN_VEHICLE_NO, vno.getText().toString());
            values.put(infoEntry.COLUMN_VEHICLE_NAME, vna.getText().toString());
            values.put(infoEntry.COLUMN_DRIVER_NAME, dna.getText().toString());
            values.put(infoEntry.COLUMN_DRIVER_PHNO, dno.getText().toString());
            values.put(infoEntry.COLUMN_VEHICLE_PHNO,vphno.getText().toString());

            long newRowId = database.insert(infoEntry.TABLE_NAME, null, values);


    }
}
