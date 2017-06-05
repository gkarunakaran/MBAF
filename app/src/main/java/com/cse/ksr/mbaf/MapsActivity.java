package com.cse.ksr.mbaf;

import android.*;
import android.Manifest;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.renderscript.Double2;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    ProgressDialog pd;
    PolylineOptions line;
    private GoogleMap mMap;
    DrawerLayout drawer;
    NavigationView navigation;
    ActionBarDrawerToggle drawerlistener;
    GoogleApiClient client;
    Locationupdate update;
    double lattitude;
    double longitude;
    String doublelat;
    String doublelng,username,jsonmobile,jsonlat,jsonlng;
    String url, data;
    int notificationvalue=0;
    ArrayList<String> name=new ArrayList<>();
    ArrayList<String> mobile=new ArrayList<>();
    ArrayList<String> jlat=new ArrayList<>();
    ArrayList<String> jlng=new ArrayList<>();

    LatLng ln1 = new LatLng(5.0000000, 77.17666617);
    LatLng ln2 = new LatLng(5.8983333, 77.8450000);
    LatLng ln3 = new LatLng(6.5133333, 78.2033333);
    LatLng ln4 = new LatLng(7.3500000, 78.6466667);
    LatLng ln5 = new LatLng(7.8883333, 78.7616667);
    LatLng ln6 = new LatLng(8.2033333, 78.8950000);
    LatLng ln7 = new LatLng(8.3700000, 78.9233333);
    LatLng ln8 = new LatLng(8.5200000, 79.0783333);
    LatLng ln9 = new LatLng(8.6200000, 79.2166667);
    LatLng ln10 = new LatLng(8.6666667, 79.3033333);
    LatLng ln11 = new LatLng(8.8855556, 79.4841667);
    LatLng ln12 = new LatLng(9.0000000, 79.5216667);
    LatLng ln13 = new LatLng(9.1000000, 79.5333333);
    LatLng ln14 = new LatLng(9.2166667, 79.5333333);
    LatLng ln15 = new LatLng(9.3633333, 79.3783333);
    LatLng ln16 = new LatLng(9.6691667, 79.3766667);
    LatLng ln17 = new LatLng(9.9500000, 79.5833333);
    LatLng ln18 = new LatLng(10.0833333, 80.0500000);
    LatLng ln19 = new LatLng(10.0855556, 80.0833333);
    LatLng ln20 = new LatLng(10.1344444, 80.1513889);
    LatLng ln21 = new LatLng(10.5500000, 80.7666667);
    LatLng ln22 = new LatLng(10.6833333, 81.0347222);
    LatLng ln23 = new LatLng(11.0352778, 81.9333333);
    LatLng ln24 = new LatLng(11.2666667, 82.4011111);
    LatLng ln25 = new LatLng(11.4350000, 83.3666667);
    String mob;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Bundle data = getIntent().getExtras();
        mob = data.getString("mob");
        url = data.getString("url");
        drawer = (DrawerLayout) findViewById(R.id.drawerlayout);
        client = new GoogleApiClient.Builder(this).addApi(LocationServices.API)
                .addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        client.connect();
        update = new Locationupdate();
        drawerlistener = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        drawer.addDrawerListener(drawerlistener);
        drawer.setDrawerListener(drawerlistener);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigation = (NavigationView) findViewById(R.id.drawerlist);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                String title = (String) item.getTitle();
                item.setCheckable(true);
                switch (title) {
                    case "Find Friend":
                        new FindFriend().execute(url+"findfriend.php");
                        item.setChecked(true);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case "Messages":
                        Intent message = new Intent(MapsActivity.this,Message.class);
                        message.putExtra("url",url);
                        item.setChecked(true);
                        drawer.closeDrawer(GravityCompat.START);
                        startActivity(message);
                        break;
                    case "Notice Board":
                        Intent Notice=new Intent(MapsActivity.this,NoticeBoard.class);
                        Notice.putExtra("url","http://www.imdchennai.gov.in/fisheries_TN.htm");
                        item.setChecked(true);
                        drawer.closeDrawer(GravityCompat.START);
                        startActivity(Notice);
                        break;
                    case "Weather Info":
                        Intent weather=new Intent(MapsActivity.this,NoticeBoard.class);
                        weather.putExtra("url","http://www.imdchennai.gov.in/Tamil_regional.htm");
                        item.setChecked(true);
                        drawer.closeDrawer(GravityCompat.START);
                        startActivity(weather);
                        break;
                    case "Reached Home":
                        new ReachedHome().execute(url+"reachedhome.php");
                        item.setChecked(true);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerlistener.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerlistener.syncState();
    }


    public void onMapReady(GoogleMap googleMap) {
        line = new PolylineOptions();
        mMap = googleMap;
        LatLng southindia = new LatLng(9.9168506, 80.2563631);
        line.width(10.0f);
        line.color(Color.RED);
        line.add(ln1);
        line.add(ln2);
        line.add(ln3);
        line.add(ln4);
        line.add(ln5);
        line.add(ln6);
        line.add(ln7);
        line.add(ln8);
        line.add(ln9);
        line.add(ln10);
        line.add(ln11);
        line.add(ln12);
        line.add(ln13);
        line.add(ln14);
        line.add(ln15);
        line.add(ln16);
        line.add(ln17);
        line.add(ln18);
        line.add(ln19);
        line.add(ln20);
        line.add(ln21);
        line.add(ln22);
        line.add(ln23);
        line.add(ln24);
        line.add(ln25);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permission, 1);
            return;
        }
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMyLocationEnabled(true);
        mMap.addPolyline(line);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(southindia,6));
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent=getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0,0);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        }

    }


    public void onConnected(Bundle bundle) {
        LocationRequest request = LocationRequest.create();
        request.setPriority(request.PRIORITY_HIGH_ACCURACY);
        request.setFastestInterval(5000);
        request.setInterval(1000);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);

    }


    public void onConnectionSuspended(int i) {

    }


    public void onLocationChanged(Location location) {
        lattitude = location.getLatitude();
        longitude = location.getLongitude();
        doublelat = Double.toString(lattitude);
        doublelng = Double.toString(longitude);
        if((lattitude>=8.964436529387473&&lattitude<=10.108134086078973)&(longitude>=79.06585693359375&&longitude<=80.08071899414062)){
            notifymessage("You Crossed the border");
        }
        new Locationupdate().execute(url + "updatelocation.php");

    }


    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    public class Locationupdate extends AsyncTask<String, Void, String> {
        String data;


        protected String doInBackground(String params[]) {
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(params[0]);
                ArrayList list = new ArrayList();
                list.add(new BasicNameValuePair("lattitude", doublelat));
                list.add(new BasicNameValuePair("longitude", doublelng));
                list.add(new BasicNameValuePair("mobile", mob));
                post.setEntity(new UrlEncodedFormEntity(list));
                HttpResponse response = client.execute(post);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    data = EntityUtils.toString(entity);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    public class FindFriend extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MapsActivity.this);
            pd.setCancelable(true);
            pd.setTitle("Loading");
            pd.setMessage("Please Wait");
            pd.setIndeterminate(false);
            pd.show();

        }


        protected String doInBackground(String params[]) {
            if(Looper.myLooper()==null)
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(params[0]);
                HttpResponse response = client.execute(post);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    data = EntityUtils.toString(entity);
                }
                try{
                    JSONObject jobject=new JSONObject(data);
                    JSONArray jarray=jobject.getJSONArray("friends");
                    for(int i=0;i<jarray.length();i++){
                        JSONObject jobj=jarray.getJSONObject(i);
                        username =jobj.getString("Username");
                        jsonmobile =jobj.getString("Mobile");
                        jsonlat =jobj.getString("Lattitude");
                        jsonlng =jobj.getString("Longitude");
                        name.add(i,username);
                        mobile.add(i,jsonmobile);
                        jlat.add(i,jsonlat);
                        jlng.add(i,jsonlng);
                    }

                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            for(int i=0;i<name.size();i++)
            {
                String uname=name.get(i);
                String mobnum=mobile.get(i);
                String jslat=jlat.get(i);
                String jslng=jlng.get(i);
                if(mobnum.equals(mob)){
                    continue;
                }
                markonmap(uname,mobnum,jslat,jslng);
            }
            pd.cancel();
        }


    }


    public void markonmap(String uname,String mobile,String latti,String longi){
        double doblat=Double.parseDouble(latti);
        double doblng=Double.parseDouble(longi);
        LatLng mark=new LatLng(doblat,doblng);
        mMap.addMarker(new MarkerOptions().position(mark).title(uname+"("+mobile+")"));
    }


    public class ReachedHome extends AsyncTask<String,Void,String>{


        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MapsActivity.this);
            pd.setCancelable(true);
            pd.setTitle("Updating");
            pd.setMessage("Please Wait");
            pd.setIndeterminate(false);
            pd.show();
        }


        protected String doInBackground(String params[]) {
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(params[0]);
                ArrayList list = new ArrayList();
                list.add(new BasicNameValuePair("mobile", mob));
                post.setEntity(new UrlEncodedFormEntity(list));
                HttpResponse response = client.execute(post);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    data = EntityUtils.toString(entity);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MapsActivity.this,"Updated",Toast.LENGTH_LONG).show();
            pd.cancel();
        }
    }
    public void notifymessage(String message){
        notificationvalue++;
        long[] vib={1000,1000,1000,1000};
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.quantum_ic_stop_grey600_36);
        builder.setContentTitle("Warning");
        builder.setVibrate(vib);
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        builder.setContentText(message);
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        synchronized (notificationManager) {
            notificationManager.notify();
            notificationManager.notify(notificationvalue,builder.build());
            if(notificationvalue>10){
                notificationvalue=0;
            }
        }
    }
}