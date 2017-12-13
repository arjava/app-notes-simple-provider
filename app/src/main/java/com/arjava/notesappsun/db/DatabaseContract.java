package com.arjava.notesappsun.db;

import android.provider.BaseColumns;

/**
 * Created by arjava on 12/10/17.
 */

public class DatabaseContract {

    static String TABLE_NOTE = "note";

    static final class NoteColumns implements BaseColumns {

        //Note title
        static String TITLE = "title";

        //Note description
        static String DESCRIPTION = "description";

        //Note date
        static String DATE = "date";
    }

}
