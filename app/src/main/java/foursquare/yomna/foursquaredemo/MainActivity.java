package foursquare.yomna.foursquaredemo;

import android.app.Activity;
import android.app.ProgressDialog;
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



public class MainActivity extends Activity{
    LocationManager manager;
    ProgressDialog progress;



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

    }


    private void locateMe() {
        //if isGPSEnabled
        // show progress dialog
        // get lat & long
        // call foursqaure api and get POI
        // then view retrieved data in another screen

        CurrentLocation currentLocation = new CurrentLocation(this);
        if (!currentLocation.canGetLocation()){
            openLocationServices();}
        else{
            progress = ProgressDialog.show(this, "Please wait", "Getting Location ...", true);
            Toast.makeText(getApplicationContext(), "current location : "+ currentLocation.getLongitude() + ", "+ currentLocation.getLatitude(), Toast.LENGTH_LONG).show();
            progress.dismiss();
        }

    };


    private void openLocationServices() {
        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    };

    private void callFourSquare(){
        
    };



    @Override
    public void onRestart() {
        super.onRestart();

        // when user naviagtes back from location settings, we check if he enabled or disabled location services
        // if user enabled location services, then go complete our scenario and get lat & lng
        // if user didn't enable location services, stop! and show error toast

        CurrentLocation currentLocation = new CurrentLocation(this);
        if (!currentLocation.canGetLocation()) {
            Toast.makeText(getApplicationContext(), "Please enable location services", Toast.LENGTH_LONG).show();
        } else {
//            progress = ProgressDialog.show(this, "Please wait", "Getting Location ...", true);
//            Toast.makeText(getApplicationContext(), "Location services enabled now : "+ currentLocation.getLongitude() + ", "+ currentLocation.getLatitude(), Toast.LENGTH_LONG).show();
//            progress.dismiss();

            //go complete your senario
        }

    }
}
