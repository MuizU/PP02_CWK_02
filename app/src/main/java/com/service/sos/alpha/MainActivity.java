package com.service.sos.alpha;

import android.content.Context;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.service.sos.alpha.chat.data.FriendDB;
import com.service.sos.alpha.chat.data.GroupDB;
import com.service.sos.alpha.chat.data.StaticConfig;
import com.service.sos.alpha.chat.service.ServiceUtils;
import com.service.sos.alpha.chat.ui.FriendsFragment;
import com.service.sos.alpha.chat.ui.GroupFragment;
import com.service.sos.alpha.chat.ui.UserProfileFragment;
import com.service.sos.alpha.ui.Help_and_support.HelpActivity;
import com.service.sos.alpha.ui.SettingsActivity;
import com.service.sos.alpha.ui.map.MapsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


//public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//    private Toolbar toolbar;
//    private DrawerLayout drawerLayout;
//    private ActionBarDrawerToggle drawerToggle;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        setTitle("Chat");
//        drawerLayout = findViewById(R.id.chat);
//        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
//        drawerLayout.addDrawerListener(drawerToggle);
//        drawerToggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        NavigationView navigationView = findViewById(R.id.nav_bar);
//        navigationView.setNavigationItemSelectedListener(this);
//
//
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if (drawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        NavigationView navigationView = findViewById(R.id.nav_bar);
//        navigationView.setNavigationItemSelectedListener(this);
//        int id = item.getItemId();
//        android.support.v4.app.Fragment fragment = null;
//        if (id == R.id.nav_chat) {
//            Toast.makeText(getApplicationContext(), "This is chat!",
//                    Toast.LENGTH_LONG).show();
//
//        } else if (id == R.id.nav_map) {
//            Intent map = new Intent(MainActivity.this, MapsActivity.class);
//            startActivity(map);
//        } else if (id == R.id.nav_account) {
//            Toast.makeText(getApplicationContext(), "This is My Account",
//                    Toast.LENGTH_LONG).show();
//        } else if (id == R.id.nav_settings) {
//            Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
//            startActivity(settings);
//        } else if (id == R.id.nav_help) {
//            Intent help = new Intent(MainActivity.this, HelpActivity.class);
//            startActivity(help);
//        } else if (id == R.id.nav_logout) {
//            Intent log = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(log);
//        }
//
//        DrawerLayout drawerLayout = findViewById(R.id.chat);
//        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }
//}

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //AppCompatActivity used for action bar features in
    //Android Support Library
    private static String TAG = "MainActivity";
    private ViewPager viewPager;//Allows swiping right and left between tabs
    private TabLayout tabLayout = null;//Horizontal layout for tabs
    public static String STR_FRIEND_FRAGMENT = "FRIEND";//Name of first tab
    public static String STR_GROUP_FRAGMENT = "GROUP";//Name of second tab
    public static String STR_INFO_FRAGMENT = "INFO";//Name of third tab

    private FloatingActionButton floatButton;//Float button in bottom-right
    private ViewPagerAdapter adapter;//Managing page views

    private FirebaseAuth mAuth;//extends object implements internalTokenProvider
    private FirebaseAuth.AuthStateListener mAuthListener;//Listener for change in authentication states
    private FirebaseUser user;//UserAccount of current instance
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initializing the main activity
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


        viewPager = findViewById(R.id.viewpager);
        floatButton = findViewById(R.id.fab);
        initTab();
        initFirebase();
    }

    private void initFirebase() {
        //Initialising Firebase Authentication (Email only)
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    StaticConfig.UID = user.getUid();
                } else {
                    MainActivity.this.finish();
                    // UserAccount is signed in
                    startActivity(new Intent(MainActivity.this, com.service.sos.alpha.chat.ui.LoginActivity.class));
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        ServiceUtils.stopServiceFriendChat(getApplicationContext(), false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onDestroy() {
        ServiceUtils.startServiceFriendChat(getApplicationContext());
        super.onDestroy();
    }

    /**
     * 3 tabs on the screen
     */
    private void initTab() {
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorIndivateTab));
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }


    private void setupTabIcons() {
        int[] tabIcons = {
                R.drawable.ic_tab_person,
                R.drawable.ic_tab_group,
                R.drawable.ic_tab_infor
        };

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(tabIcons[1]);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FriendsFragment(), STR_FRIEND_FRAGMENT);
        adapter.addFrag(new GroupFragment(), STR_GROUP_FRAGMENT);
        adapter.addFrag(new UserProfileFragment(), STR_INFO_FRAGMENT);
        floatButton.setOnClickListener(((FriendsFragment) adapter.getItem(0))
                .onClickFloatButton.getInstance(this));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ServiceUtils.stopServiceFriendChat(MainActivity.this
                        .getApplicationContext(), false);
                if (adapter.getItem(position) instanceof FriendsFragment) {
                    floatButton.setVisibility(View.VISIBLE);
                    floatButton.setOnClickListener(((FriendsFragment) adapter
                            .getItem(position)).onClickFloatButton.getInstance(MainActivity.this));
                    floatButton.setImageResource(R.drawable.plus);
                } else if (adapter.getItem(position) instanceof GroupFragment) {
                    floatButton.setVisibility(View.VISIBLE);
                    floatButton.setOnClickListener(((GroupFragment) adapter.getItem(position))
                            .onClickFloatButton.getInstance(MainActivity.this));
                    floatButton.setImageResource(R.drawable.ic_float_add_group);
                } else {
                    floatButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //This is for the About menu item in the top-right hand corner
        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            Toast.makeText(this, "AlertMe version 1.0", Toast.LENGTH_LONG).show();
            return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        finish();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        startActivity(intent);
    }
    Context context = this;
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_chat) {
            Intent chat = new Intent(MainActivity.this, MainActivity.class);
            overridePendingTransition(0, 0);
            chat.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(chat);
        } else if (id == R.id.nav_map) {
            Intent map = new Intent(MainActivity.this, MapsActivity.class);
            overridePendingTransition(0, 0);
            map.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(map);
        } else if (id == R.id.nav_settings) {
            Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
            settings.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(settings);
        } else if (id == R.id.nav_help) {
            Intent help = new Intent(MainActivity.this, HelpActivity.class);
            help.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(help);
        } else if (id == R.id.nav_logout) {
            try {
                mAuth.signOut();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        DrawerLayout drawerLayout = findViewById(R.id.chat);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * ViewPageAdapter for Fragment
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            // return null to display only the icon
            return null;
        }
    }
}