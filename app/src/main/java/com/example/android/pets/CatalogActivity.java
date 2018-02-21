/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.pets.data.DbManager;
import com.example.android.pets.data.PestsContract;

import java.net.URI;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String  TAG = CatalogActivity.class.getName();

    // This is the Adapter being used to display the list's data
    PetCursorAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        ListView listView = (ListView) findViewById(R.id.pet_list);

        mAdapter = new PetCursorAdapter(this,null);
        listView.setAdapter(mAdapter);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent editIntent = new Intent(CatalogActivity.this, EditorActivity.class);
            editIntent.setData(ContentUris.withAppendedId(PestsContract.PetEntry.CONTENT_URI,id));

            startActivity(editIntent);
        });

        getLoaderManager().initLoader(0, null, this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                addDummyPet();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteAllPets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void addDummyPet(){

        ContentValues values = new ContentValues();
        values.put(PestsContract.PetEntry.COLUMN_PET_NAME, "toto");
        values.put(PestsContract.PetEntry.COLUMN_PET_BREED, "df");
        values.put(PestsContract.PetEntry.COLUMN_PET_GENDER, "1");
        values.put(PestsContract.PetEntry.COLUMN_PET_WEIGHT, "25");

        Uri newUri = getContentResolver().insert(PestsContract.PetEntry.CONTENT_URI,values);
    }

    private void deleteAllPets() {
        int rowsDeleted = getContentResolver().delete(PestsContract.PetEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] p ={PestsContract.PetEntry._ID, PestsContract.PetEntry.COLUMN_PET_NAME, PestsContract.PetEntry.COLUMN_PET_BREED};
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(this, PestsContract.PetEntry.CONTENT_URI,
                p,
                null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        mAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);

    }
}
