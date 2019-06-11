package android.example.myisiapplication;

import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Bundle extras ;
    private String assembledOutput="";
    protected String access_token="";
    protected String refresh_token="" ;
    private JSONArray JA;
    private CasesAccess casesAccess = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        extras = getIntent().getExtras();
        access_token = extras.getString("access_token");
        refresh_token = extras.getString("refresh_token");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        casesAccess =new CasesAccess(access_token);
        casesAccess.execute("http://process.isiforge.tn/api/1.0/isi/case/start-cases");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_services) {

            Bundle bundle = new Bundle();
            bundle.putString("access_token", access_token );
            bundle.putString("refresh_token", refresh_token);
            bundle.putString("services",assembledOutput);
            ServicesFragment servicesFragment = new ServicesFragment();
            servicesFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, servicesFragment).commit();

        } else if (id == R.id.nav_history) {
            Bundle bundle = new Bundle();
            bundle.putString("access_token", access_token );
            bundle.putString("refresh_token", refresh_token);
            HistoryFragment fragInfo = new HistoryFragment();
            fragInfo.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragInfo).commit();

        } else if (id == R.id.nav_about) {
            AboutFragment aboutFragment = new AboutFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, aboutFragment).commit();

        } else if (id == R.id.nav_web) {
            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.isi.rnu.tn/Fr/accueil_46_4")));
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(HomeActivity.this,
                    LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public class CasesAccess extends AsyncTask<String, Void, String> {
        String token;
        CasesAccess(String token){
            this.token=token;
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO: attempt authentication against a network service.
            try {
                URL urlServices= new URL("http://process.isiforge.tn/api/1.0/isi/case/start-cases");

                HttpURLConnection httpURLConnection=(HttpURLConnection) urlServices.openConnection();
                //httpURLConnection.setRequestMethod("GET");

                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestProperty("Content-Type","application/json");
                httpURLConnection.setRequestProperty("authorization","Bearer "+ access_token);
                httpURLConnection.connect();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);
                BufferedReader reader=new BufferedReader(inputStreamReader);
                String inputStreamData;

                while ((inputStreamData = reader.readLine()) != null) {
                    assembledOutput = assembledOutput + inputStreamData;
                }
                return assembledOutput;

            } catch (Exception e) {
                return "disconnected";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try{
                JA= new JSONArray(result);
                Bundle bundle = new Bundle();
                bundle.putString("access_token", access_token );
                bundle.putString("refresh_token", refresh_token);
                bundle.putString("services",result);
                ServicesFragment servicesFragment = new ServicesFragment();
                servicesFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, servicesFragment).commit();


            }catch (JSONException e){
                e.getMessage();

            }
        }
    }
}
