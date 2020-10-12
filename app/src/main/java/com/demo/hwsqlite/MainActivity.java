package com.demo.hwsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnRead, btnClear;
    EditText etFirstName, etSecondName;
    ListView listViewSecondNames;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etSecondName = (EditText) findViewById(R.id.etSecondName);

        listViewSecondNames = (ListView) findViewById(R.id.listViewSecondNames);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View view) {
        String name = etFirstName.getText().toString();
        String email = etSecondName.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        switch (view.getId()) {
            case R.id.btnAdd:
                contentValues.put(DBHelper.KEY_FNAME, name);
                contentValues.put(DBHelper.KEY_SNAME, email);

                database.insert(DBHelper.TABLE_PEOPLE, null, contentValues);
                etFirstName.setText("");
                etSecondName.setText("");
                Toast.makeText(getApplicationContext(), "Человек добавлен в БД", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnRead:
                /*String[] columns = new String[]{DBHelper.KEY_SNAME};*/
                Cursor cursor = database.query(DBHelper.TABLE_PEOPLE, null, null, null, null, null, null);

                final ArrayList<Person> people = new ArrayList<>();

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int first_nameIndex = cursor.getColumnIndex(DBHelper.KEY_FNAME);
                    int second_nameIndex = cursor.getColumnIndex(DBHelper.KEY_SNAME);
                    do {
                        int id = cursor.getInt(idIndex);
                        String firstName = cursor.getString(first_nameIndex);
                        String secondName = cursor.getString(second_nameIndex);

                        people.add(new Person(id, firstName, secondName));

                        // for debug
                        /*Log.d("mLog", "ID = " + id +
                                ", first_name = " + firstName +
                                ", last_name = " + secondName);*/
                    } while (cursor.moveToNext());

                    ArrayAdapter<Person> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, people);
                    listViewSecondNames.setAdapter(adapter);
                    listViewSecondNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Person person = people.get(i);
                            Toast.makeText(getApplicationContext(), (i + 1) + ". " + person.getFirstName() + " " + person.getSecondName(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Log.d("mLog", "0 rows");
                }

                cursor.close();
                break;
            case R.id.btnClear:
                database.delete(DBHelper.TABLE_PEOPLE, null, null);
                break;
        }
        dbHelper.close();
    }
}