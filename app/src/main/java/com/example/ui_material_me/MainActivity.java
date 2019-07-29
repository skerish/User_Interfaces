package com.example.ui_material_me;

import android.content.res.TypedArray;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private ArrayList<Sport> mSportsData;
    private SportsAdapter mAdapter;

    private static final String STATE_SAVER = BuildConfig.APPLICATION_ID + "POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));

        mSportsData = new ArrayList<>();

        mAdapter = new SportsAdapter(this, mSportsData);

        recyclerView.setAdapter(mAdapter);

        // To get the data.
        initializeData();

        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.nav_open, R.string.nav_close);

        if (drawerLayout != null){
            drawerLayout.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation);
        if (navigationView != null){
            navigationView.setNavigationItemSelectedListener(this);
        }

        // The Android SDK includes a class called ItemTouchHelper that is used to define what
        // happens to RecyclerView list items when the user performs various touch actions, such
        // as swipe, or drag and drop.

        // First argument is for "Dragging" and second one is for "Swiping".
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT |
                ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder initial,
                                  @NonNull RecyclerView.ViewHolder target) {
                int from = initial.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(mSportsData, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                mSportsData.remove(viewHolder.getAdapterPosition());           // getAdapterPosition() for smooth animation.
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }

        });

        helper.attachToRecyclerView(recyclerView);

        if (savedInstanceState != null){
            recyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(STATE_SAVER));
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    /**
     * Initialize the sports data from resources.
     */
    private void initializeData() {
        String[] sports_title = getResources().getStringArray(R.array.sports_title);
        String[] sports_info = getResources().getStringArray(R.array.sports_info);
        String[] sports_details = getResources().getStringArray(R.array.sports_details);
        TypedArray sports_image = getResources().obtainTypedArray(R.array.sports_images);

        /**
         *  A TypedArray allows you to store an array of other XML resources. Using a TypedArray,
         *  you can obtain the image resources as well as the sports title and information by using
         *  indexing in the same loop.
         *
         *  int[] won't work.
         */

        // Clears the existing data to avoid duplication.
        mSportsData.clear();

        // Creates the ArrayList of Sports object with title and info about each sport.
        for (int i = 0; i < sports_title.length; i++) {
            mSportsData.add(new Sport(sports_title[i], sports_info[i], sports_image.getResourceId(i, 0), sports_details[i]));
        }

        sports_image.recycle();              // Required to use with TypedArray.
//
//        mAdapter.notifyDataSetChanged();
    }

    public void resetSports(View view) {
        initializeData();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recyclerView != null){
            outState.putParcelable(STATE_SAVER, recyclerView.getLayoutManager().onSaveInstanceState());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        switch (menuItem.getItemId()){
            case R.id.nav_camera:
                // Handle the camera import action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_camera));
                return true;
            case R.id.nav_gallery:
                // Handle the gallery action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_gallery));
                return true;
            case R.id.nav_slideshow:
                // Handle the slideshow action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_slideshow));
                return true;
            case R.id.nav_manage:
                // Handle the tools action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_tools));
                return true;
            case R.id.nav_share:
                // Handle the share action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_share));
                return true;
            case R.id.nav_send:
                // Handle the send action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_send));
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);

        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES){
            menu.findItem(R.id.modes).setTitle(R.string.day_mode);
        }
        else{
            menu.findItem(R.id.modes).setTitle(R.string.night_mode);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.modes){
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if (nightMode ==AppCompatDelegate.MODE_NIGHT_YES){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            recreate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}
