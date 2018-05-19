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
import android.view.MenuItem;
import android.widget.Toast;

import com.service.sos.alpha.MainActivity;
import com.service.sos.alpha.R;

public class MapActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("View Friends");
        drawerLayout = findViewById(R.id.map);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.nav_barMap);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MapActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        finish();
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
        NavigationView navigationView = findViewById(R.id.nav_barMap);
        navigationView.setNavigationItemSelectedListener(this);
        int id = item.getItemId();
        if (id == R.id.nav_chat) {
            Intent home = new Intent(MapActivity.this, MainActivity.class);
            home.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(home);
        } else if (id == R.id.nav_map) {
            Intent map = new Intent(MapActivity.this, MapActivity.class);
            map.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(map);
        } else if (id == R.id.nav_account) {
            Toast.makeText(getApplicationContext(), "This is My Account",
                    Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_settings) {
            Intent settings = new Intent(MapActivity.this, SettingsActivity.class);
            settings.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(settings);
        } else if (id == R.id.nav_help) {
            Intent help = new Intent(MapActivity.this, HelpActivity.class);
            help.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(help);
        } else if (id == R.id.nav_logout) {
            Intent log = new Intent(MapActivity.this, LoginActivity.class);
            log.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(log);
        }

        DrawerLayout drawerLayout = findViewById(R.id.map);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
