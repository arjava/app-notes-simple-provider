package com.arjava.notesappsun.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arjava.notesappsun.entity.Note;

import java.sql.SQLException;
import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.arjava.notesappsun.db.DatabaseContract.NoteColumns.DATE;
import static com.arjava.notesappsun.db.DatabaseContract.NoteColumns.DESCRIPTION;
import static com.arjava.notesappsun.db.DatabaseContract.NoteColumns.TITLE;
import static com.arjava.notesappsun.db.DatabaseContract.TABLE_NOTE;

/**
 * Created by arjava on 12/10/17.
 */

public class NoteHelper {

    public static String DATABASE_TABLE = TABLE_NOTE;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public NoteHelper(Context context) {
        this.context = context;
    }

    public NoteHelper open() throws SQLException { //exception use java.sql
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<Note> query(){
        ArrayList<Note> arrayList = new ArrayList<Note>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, _ID
        +" DESC", null);
        cursor.moveToFirst();
        Note note;
        if (cursor.getCount()>0) {
            do {
                note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                note.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                note.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));

                arrayList.add(note);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Note note) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(TITLE, note.getTitle());
        initialValues.put(DESCRIPTION, note.getDescription());
        initialValues.put(DATE, note.getDate());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(Note note) {
        ContentValues values = new ContentValues();
        values.put(TITLE, note.getTitle());
        values.put(DESCRIPTION, note.getDescription());
        values.put(DATE, note.getDate());
        return database.update(DATABASE_TABLE, values, _ID + "= '"+ note.getId()+ "'",null);
    }

    public int delete(int id) {
        return database.delete(TABLE_NOTE, _ID + " = '"+id+ "'", null);
    }

}
