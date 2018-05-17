package com.service.sos.alpha.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.service.sos.alpha.MainActivity;
import com.service.sos.alpha.R;

public class HelpActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Help and Support");
        drawerLayout = findViewById(R.id.help);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.nav_barHelp);
        navigationView.setNavigationItemSelectedListener(this);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavigationView navigationView = findViewById(R.id.nav_barHelp);
        navigationView.setNavigationItemSelectedListener(this);
        int id = item.getItemId();
        android.support.v4.app.Fragment fragment = null;
        if (id == R.id.nav_chat) {
            Intent map = new Intent(HelpActivity.this, MapActivity.class);
            startActivity(map);

        } else if (id == R.id.nav_map) {
            Intent map = new Intent(HelpActivity.this, MapActivity.class);
            startActivity(map);
        } else if (id == R.id.nav_account) {
            Intent account = new Intent(HelpActivity.this, RegistrationActivity.class);
            startActivity(account);
        } else if (id == R.id.nav_settings) {
            Intent settings = new Intent(HelpActivity.this, SettingsActivity.class);
            startActivity(settings);
        } else if (id == R.id.nav_help) {
            Intent help = new Intent(HelpActivity.this, HelpActivity.class);
            startActivity(help);
        } else if (id == R.id.nav_logout) {
            Toast.makeText(getApplicationContext(), "You have Logged out",
                    Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawerLayout = findViewById(R.id.help);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
