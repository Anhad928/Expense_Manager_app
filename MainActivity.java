package com.example.anhadpre_expbook;

import static com.example.anhadpre_expbook.R.id.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment_of_expenses expensefragment;

    private dashFragment dashboardfrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(my_toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawerLayout = findViewById(drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        expensefragment = new Fragment_of_expenses();
        dashboardfrag = new dashFragment();
        setFragment(dashboardfrag);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    public void displaySelectedListener(int itemID) {
        Fragment fragment = null;
        if (itemID == dashboard) {
            setFragment(dashboardfrag);
            // Handle the "Dashboard" menu item
        }

        // Close the navigation drawer after selection
        DrawerLayout drawerLayout = findViewById(drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        if (fragment != null) {
            setFragment(fragment);
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        String fragmentTag = fragment.getClass().getName();

        if (fragmentManager.findFragmentByTag(fragmentTag) == null) {
            // Fragment is not in backstack, so add it and give it a unique tag
            transaction.replace(fragment_container, fragment, fragmentTag);
            transaction.addToBackStack(fragmentTag);
        } else {
            // Fragment is in backstack, so just pop back to it
            fragmentManager.popBackStackImmediate(fragmentTag, 0);
        }

        transaction.commit();

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedListener(item.getItemId());


        return false;
    }

}
