package studio.smartters.mowordsub_admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import studio.smartters.mowordsub_admin.Fragment.ImageFragment;
import studio.smartters.mowordsub_admin.Fragment.RegisterSurveyFragment;
import studio.smartters.mowordsub_admin.Fragment.VideoFragment;
import studio.smartters.mowordsub_admin.Fragment.ViewBoothFragment;
import studio.smartters.mowordsub_admin.Fragment.ViewWardFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction f = fm.beginTransaction();
        f.replace(R.id.container_main,new RegisterSurveyFragment(),"register");
        f.commit();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_survey);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment ff = null;
        String tag = "other";
        if (id == R.id.nav_survey) {
            ff = new RegisterSurveyFragment();
            tag = "register";
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction f = fm.beginTransaction();
            f.replace(R.id.container_main,ff,tag);
            f.commit();
        } else if (id == R.id.nav_panchayat) {
            ff = new ViewWardFragment();
            tag = "other";
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction f = fm.beginTransaction();
            f.replace(R.id.container_main,ff,tag);
            f.commit();
        } else if (id == R.id.nav_booth) {
            ff = new ViewBoothFragment();
            tag = "other";
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction f = fm.beginTransaction();
            f.replace(R.id.container_main,ff,tag);
            f.commit();
        }else if (id == R.id.nav_image) {
            ff = new ImageFragment();
            tag = "other";
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction f = fm.beginTransaction();
            f.replace(R.id.container_main,ff,tag);
            f.commit();
        } else if (id == R.id.nav_video) {
            ff = new VideoFragment();
            tag = "other";
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction f = fm.beginTransaction();
            f.replace(R.id.container_main,ff,tag);
            f.commit();
        }else if (id == R.id.nav_search_name) {
            startActivity(new Intent(this,SearchNameActivity.class));
        } else if (id == R.id.nav_search_no) {
            startActivity(new Intent(this,SearchNoActivity.class));
        }
        else if (id == R.id.nav_survey_man) {
            //startActivity(new Intent(this,SearchNameActivity.class));
        } else if (id == R.id.nav_no_voter) {
            startActivity(new Intent(this,NoVoterActivity.class));
        }else if (id == R.id.nav_no_adhar) {
            startActivity(new Intent(this,NoAdharActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}