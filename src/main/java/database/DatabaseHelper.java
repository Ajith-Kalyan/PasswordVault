package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import database.model.Note;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "vault_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Note.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop table if existed
        db.execSQL(" DROP TABLE IF EXISTS " + Note.TABLE_NAME);

        // CREATE TABLE AGAIN
        onCreate(db);
    }


    //Inserting data
    public long insertData(String username, String password, String website) {


        String _username,_password,_website;
        _username = username;
        _password = password;
        _website = website;
        //use writeble database to write values to data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Note.COLUMN_USERNAME, _username);
        values.put(Note.COLUMN_PASSWORD, _password);
        values.put(Note.COLUMN_WEBSITE, _website);


        //insert row
        long id = db.insert(Note.TABLE_NAME, null, values);

        //close db connection
        db.close();

        //return newly inserted row id
        return id;

    }


    //Reading data
    public Note getValues(long id) {

        //get readable database to read values from db

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Note.TABLE_NAME, new String[]{Note.COLUMN_ID, Note.COLUMN_USERNAME, Note.COLUMN_PASSWORD, Note.COLUMN_WEBSITE, Note.TIMESTAMP},
                Note.COLUMN_ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_USERNAME)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_PASSWORD)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_WEBSITE)),
                cursor.getInt(cursor.getColumnIndex(Note.TIMESTAMP)));

        //close db connection
        cursor.close();

        return note;
    }

    public List<Note> getAllValues() {

        List<Note> notes = new ArrayList<>();
        //Select all query
        String selectQuery = "Select * from " + Note.TABLE_NAME + " order by " + Note.COLUMN_ID + " Desc ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);


        //loop through all rows and add to list
        if(cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setUsername(cursor.getString(cursor.getColumnIndex(Note.COLUMN_USERNAME)));
                note.setPassword(cursor.getString(cursor.getColumnIndex(Note.COLUMN_PASSWORD)));
                note.setWebsite(cursor.getString(cursor.getColumnIndex(Note.COLUMN_WEBSITE)));
                note.setTimestamp(cursor.getInt(cursor.getColumnIndex(Note.TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        return notes;
    }


    public int getValuesCount() {

        String countQuery = " SELECT * FROM "+Note.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);


        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateValues(Note username, Note password, Note website) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Note.COLUMN_USERNAME,username.getUsername());
        values.put(Note.COLUMN_PASSWORD, password.getPassword());
        values.put(Note.COLUMN_WEBSITE, website.getWebsite());

        //update row
        return db.update(Note.TABLE_NAME,values,Note.COLUMN_ID+" = ? ", new String[]{String.valueOf(username.getId())});


    }


    public void deleteValues(Note username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note.TABLE_NAME,Note.COLUMN_ID + " = ? ", new String[]{String.valueOf(username.getId())});

        db.close();
    }









}
