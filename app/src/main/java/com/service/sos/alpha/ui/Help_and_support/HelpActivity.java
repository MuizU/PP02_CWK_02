package com.service.sos.alpha.ui.Help_and_support;

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
import com.service.sos.alpha.ui.MapActivity;
import com.service.sos.alpha.ui.SettingsActivity;

public class HelpActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private TextView fAQButton;//1
    private TextView contactUs;
    private TextView terms_privacy;
    private TextView appInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_support);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Help and Support");
        drawerLayout = findViewById(R.id.helpAndSupport);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.nav_barHelpAndSupport);
        navigationView.setNavigationItemSelectedListener(this);
        fAQButton = findViewById(R.id.faqText);//2
        fAQButton.setOnClickListener(new View.OnClickListener() {//3
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpActivity.this,FAQActivity.class);//4
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);//5
            }
        });
        contactUs = findViewById(R.id.contact_us);
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( HelpActivity.this,Contact_Us.class ) ;
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        terms_privacy = findViewById(R.id.terms_privacy);
        terms_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent (HelpActivity.this,Terms_Conditions.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        appInformation = findViewById(R.id.appInfor);
        appInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HelpActivity.this, AppInformation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(HelpActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
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
        NavigationView navigationView = findViewById(R.id.nav_barHelpAndSupport);
        navigationView.setNavigationItemSelectedListener(this);
        int id = item.getItemId();
        if (id == R.id.nav_chat) {
            Intent map = new Intent(HelpActivity.this, MapActivity.class);
            map.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(map);
        } else if (id == R.id.nav_settings) {
            Intent settings = new Intent(HelpActivity.this, SettingsActivity.class);
            settings.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(settings);
        } else if (id == R.id.nav_help) {
            Intent help = new Intent(HelpActivity.this, HelpActivity.class);
            help.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(help);
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            FriendDB.getInstance(this).dropDB();
            GroupDB.getInstance(this).dropDB();
            ServiceUtils.stopServiceFriendChat(this.getApplicationContext(), true);
            finish();
            overridePendingTransition(0, 0);
        }

        DrawerLayout drawerLayout = findViewById(R.id.helpAndSupport);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
