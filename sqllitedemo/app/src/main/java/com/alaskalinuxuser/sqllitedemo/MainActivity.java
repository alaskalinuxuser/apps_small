/*  Copyright 2017 by AlaskaLinuxUser (https://thealaskalinuxuser.wordpress.com)
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/
package com.alaskalinuxuser.sqllitedemo;

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
        String nameVar = "Joined the Navy";
        int yearVar = 2004;

        try {

            // First, make a database, open or create will open if exist, create if not.
            SQLiteDatabase firstDatabaseEver = this.openOrCreateDatabase("events", MODE_PRIVATE, null);

            // Now, let's ceate a table.
            firstDatabaseEver.execSQL("CREATE TABLE IF NOT EXISTS events (name VARCHAR, year INT(4))");

            // And add some information to that table.
            firstDatabaseEver.execSQL("INSERT INTO events (name, year) VALUES ('Birth', 1985)");

            // And a second one for fun, this time, we will use our variable we created, you can see
            // how this would be useful for an app, so you could have the user's input dumped into a
            // table.
            firstDatabaseEver.execSQL("INSERT INTO events (name, year) VALUES ('" + nameVar + "', " + yearVar + ")");

            // Now we need a cursor to retreive the data with a query.
            Cursor myCursor = firstDatabaseEver.rawQuery("SELECT * FROM events", null);

            // Now that we have the data, we need a way to index them, or grab the parts we need.
            int nameIndex = myCursor.getColumnIndex("name");
            int yearIndex = myCursor.getColumnIndex("year");

            // Let's start from the top of the table, by moving to the first position.
            myCursor.moveToFirst();

            // And if it is not null, let's use it.
            while (myCursor != null){

                // Logging for posterity....
                Log.i("WJH", myCursor.getString(nameIndex));
                Log.i("WJH", Integer.toString(myCursor.getInt(yearIndex)));

                // And move on to the next result in the table.
                myCursor.moveToNext();
            }


        } catch (Exception e) {

            e.printStackTrace();

        }


    }
}
