package com.alaskalinuxuser.sqladvanced;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Let's make some variables to use later.
        String nameVar = "Tux";
        int ageVar = 26;
        String ageLimit = "50";
        String nameChosen = "Tux";

        try {

            // First, make a database, open or create will open if exist, create if not.
            SQLiteDatabase firstDatabaseEver = this.openOrCreateDatabase("company", MODE_PRIVATE, null);

            // Now, let's ceate a table.
            firstDatabaseEver.execSQL("CREATE TABLE IF NOT EXISTS myUsers (name VARCHAR, age INT(3), id INTEGER PRIMARY KEY)");

            firstDatabaseEver.execSQL("DELETE FROM myUsers WHERE age > 0");

            // And add some information to that table.
            firstDatabaseEver.execSQL("INSERT INTO myUsers (name, age) VALUES ('AKLU', 32)");

            // And add some information to that table.
            firstDatabaseEver.execSQL("INSERT INTO myUsers (name, age) VALUES ('SomeGuy', 35)");

            // And add some information to that table.
            firstDatabaseEver.execSQL("INSERT INTO myUsers (name, age) VALUES ('OtherPerson', 25)");

            // And a second one for fun, this time, we will use our variable we created, you can see
            // how this would be useful for an app, so you could have the user's input dumped into a
            // table.
            firstDatabaseEver.execSQL("INSERT INTO myUsers (name, age) VALUES ('" + nameVar + "', " + ageVar + ")");

            // And how to delet an entry.
            firstDatabaseEver.execSQL("DELETE FROM myUsers WHERE name = 'SomeGuy'");

            // And to update an entry....
            firstDatabaseEver.execSQL("UPDATE myUsers SET age = 29 WHERE name = 'Tux'");

            // Now we need a cursor to retreive the data with a query.
            // Search by variables.
            //Cursor myCursor = firstDatabaseEver.rawQuery("SELECT * FROM myUsers WHERE age < "+ ageLimit +
                    //" AND name = '" + nameChosen + "'", null);

            // Now we need a cursor to retreive the data with a query.
            // you could say starts with s: 's%', ends with s: '%s', has an s: '%s%'....
            // Search by name and use a limit of 3, you could impose any limit....
            //Cursor myCursor = firstDatabaseEver.rawQuery("SELECT * FROM myUsers WHERE age < "+ ageLimit +
                    //" AND name LIKE '%u%' LIMIT 3", null);

            Cursor myCursor = firstDatabaseEver.rawQuery("SELECT * FROM myUsers WHERE age < "+ ageLimit, null);

            // Now that we have the data, we need a way to index them, or grab the parts we need.
            int nameIndex = myCursor.getColumnIndex("name");
            int ageIndex = myCursor.getColumnIndex("age");
            int idIndex = myCursor.getColumnIndex("id");

            // Let's start from the top of the table, by moving to the first position.
            myCursor.moveToFirst();

            // And if it is not null, let's use it.
            while (myCursor != null){

                // Logging for posterity....
                Log.i("WJH", myCursor.getString(nameIndex));
                Log.i("WJH", Integer.toString(myCursor.getInt(ageIndex)));
                Log.i("WJH", Integer.toString(myCursor.getInt(idIndex)));
                // And move on to the next result in the table.
                myCursor.moveToNext();
            }


        } catch (Exception e) {

            e.printStackTrace();

        }


    }
}
