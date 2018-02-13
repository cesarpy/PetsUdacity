package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cesar on 12/2/2018.
 */

public class DbManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pets.db";
    private static final int VERSION = 1;

    public DbManager(Context context) {
        super(context, DATABASE_NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + PestsContract.PetEntry.TABLE_NAME + " ("
                                + PestsContract.PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                               + PestsContract.PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
                               + PestsContract.PetEntry.COLUMN_PET_BREED + " TEXT, "
                                + PestsContract.PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
                                + PestsContract.PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
