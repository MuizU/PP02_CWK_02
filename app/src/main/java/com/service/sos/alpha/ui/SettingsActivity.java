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
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.service.sos.alpha.MainActivity;
import com.service.sos.alpha.R;
import com.service.sos.alpha.chat.data.FriendDB;
import com.service.sos.alpha.chat.data.GroupDB;
import com.service.sos.alpha.chat.service.ServiceUtils;
import com.service.sos.alpha.ui.Help_and_support.HelpActivity;

public class SettingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private TextView inviteFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Settings");
        drawerLayout = findViewById(R.id.settings);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.nav_barSettings);
        navigationView.setNavigationItemSelectedListener(this);

        inviteFriends = findViewById(R.id.inviteFriendsText);
        inviteFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Assault Prevention Device");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "http://www.google.com");
                startActivity(Intent.createChooser(shareIntent, "Invite a friend via..."));
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SettingsActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        finish();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavigationView navigationView = findViewById(R.id.nav_barSettings);
        navigationView.setNavigationItemSelectedListener(this);
        int id = item.getItemId();
        if (id == R.id.nav_chat) {
            Intent chat = new Intent(SettingsActivity.this, MainActivity.class);
            overridePendingTransition(0, 0);
            chat.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(chat);

        } else if (id == R.id.nav_map) {
            Intent map = new Intent(SettingsActivity.this, MapsActivity.class);
            map.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(map);
        } else if (id == R.id.nav_settings) {
            Intent settings = new Intent(SettingsActivity.this, SettingsActivity.class);
            overridePendingTransition(0, 0);
            settings.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(settings);
        } else if (id == R.id.nav_help) {
            Intent help = new Intent(SettingsActivity.this, HelpActivity.class);
            overridePendingTransition(0, 0);
            help.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(help);
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            FriendDB.getInstance(this).dropDB();
            GroupDB.getInstance(this).dropDB();
            ServiceUtils.stopServiceFriendChat(this.getApplicationContext(), true);
            overridePendingTransition(0, 0);
            finish();
        }

        DrawerLayout drawerLayout = findViewById(R.id.settings);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
