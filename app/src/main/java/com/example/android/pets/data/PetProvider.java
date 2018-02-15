package com.example.android.pets.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Cesar on 15/2/2018.
 */

public class PetProvider extends ContentProvider {

    public static final String LOG_TAG = PetProvider.class.getSimpleName();

    private DbManager mPetHelper;
    // Creates a UriMatcher object.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final int PETS = 1001;
    public static final int PETS_ID = 1002;
    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        mPetHelper = new DbManager(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }


    static {

        sUriMatcher.addURI(PestsContract.CONTENT_AUTHORITY, PestsContract.PATH_PETS, PETS);


        sUriMatcher.addURI(PestsContract.CONTENT_AUTHORITY, PestsContract.PATH_PETS+"/#", PETS_ID);
    }

}
