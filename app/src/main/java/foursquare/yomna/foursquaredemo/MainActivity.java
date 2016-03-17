package foursquare.yomna.foursquaredemo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    LocationManager manager;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final FloatingActionButton locateMeButton = (FloatingActionButton) findViewById(R.id.btnLocateMe);
        locateMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locateMe();
            }
        });

        buildGoogleApiClient();
    }


    private void locateMe() {
        //if isGPSEnabled
        if (!isLocationEnabled()) {
            openLocationServices();
        } else {
            getCurrentLocation();
        }
        // show progress dialog
        // get lat & long
        // call foursqaure api and get POI
        // then view retrieved data in anaother screen
    }

    ;


    private boolean isLocationEnabled() {

        if (!(manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
            return false;
        }
        return true;
    }

    ;


    private void openLocationServices() {
        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

    }

    ;

    private void getCurrentLocation() {
        buildGoogleApiClient();
    }

    ;

    @Override
    public void onConnected(Bundle bundle) {
        //fused location api gets latest and accurate location
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //locationRequest.setInterval(10000); // Update location every second
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
    }


    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        buildGoogleApiClient();
    }


    synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isLocationEnabled()) {
            if (googleApiClient != null) {
                googleApiClient.connect();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isLocationEnabled()) {
            if (googleApiClient != null) {
                if (googleApiClient.isConnected()) {
                    googleApiClient.disconnect();
                }
            }
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();

        // when user naviagtes back from location settings, we check if he enabled or disabled location services
        // if user enabled location services, then go complete our scenario and get lat & lng
        // if user didn't enable location services, stop! and show error toast

        if (!isLocationEnabled()) {
            Toast.makeText(getApplicationContext(), "Location services not enabled", Toast.LENGTH_LONG).show();
        } else {
            buildGoogleApiClient();
            Toast.makeText(getApplicationContext(), "Location services enabled : " + currentLocation.getLatitude()+" ,,, "+ currentLocation.getLongitude(), Toast.LENGTH_LONG).show();

        }

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
