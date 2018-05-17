package com.service.sos.alpha;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.service.sos.alpha.ui.HelpActivity;
import com.service.sos.alpha.ui.LoginActivity;
import com.service.sos.alpha.ui.MapActivity;
import com.service.sos.alpha.ui.RegistrationActivity;
import com.service.sos.alpha.ui.SettingsActivity;

import java.util.Set;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Chat");
        drawerLayout = findViewById(R.id.chat);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.nav_bar);
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
        NavigationView navigationView = findViewById(R.id.nav_bar);
        navigationView.setNavigationItemSelectedListener(this);
        int id = item.getItemId();
        android.support.v4.app.Fragment fragment = null;
        if (id == R.id.nav_chat) {
            Toast.makeText(getApplicationContext(), "This is chat!",
                    Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_map) {
            Intent map = new Intent(MainActivity.this, MapActivity.class);
            startActivity(map);
        } else if (id == R.id.nav_account) {
            Toast.makeText(getApplicationContext(), "This is My Account",
                    Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_settings) {
            Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settings);
        } else if (id == R.id.nav_help) {
            Intent help = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(help);
        } else if (id == R.id.nav_logout) {
            Intent log = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(log);
        }

        DrawerLayout drawerLayout = findViewById(R.id.chat);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}