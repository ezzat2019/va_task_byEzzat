package com.talabto.vataskbyezzat.ui.main;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.talabto.vataskbyezzat.R;
import com.talabto.vataskbyezzat.data.model.MathModel;
import com.talabto.vataskbyezzat.services.RunAllTimeService;
import com.talabto.vataskbyezzat.ui.pane.MathActivity;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // ui
    private Button btn_calc;
    private EditText ed_num1, ed_num2, ed_time;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLoction();

        init();

        actions();


    }

    @Override
    protected void onStart() {
        super.onStart();
        checkMathServiceIsRunning();
    }

    private void actions() {
        btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(ed_num1.getText())) {
                    Toast.makeText(MainActivity.this, "enter first number", Toast.LENGTH_SHORT).show();
                    return;

                } else if (TextUtils.isEmpty(ed_num2.getText())) {
                    Toast.makeText(MainActivity.this, "enter second number", Toast.LENGTH_SHORT).show();
                    return;

                } else if (TextUtils.isEmpty(ed_time.getText())) {
                    Toast.makeText(MainActivity.this, "enter time", Toast.LENGTH_SHORT).show();
                    return;

                }
                Double n1 = Double.parseDouble(ed_num1.getText().toString());
                Double n2 = Double.parseDouble(ed_num2.getText().toString());
                int time = Integer.parseInt(ed_time.getText().toString());
                String op = spinner.getSelectedItem().toString();
                Log.d("zzzzzzzzz", "onClick: " + op);
                if (op.equals("div") && n2 == 0) {
                    ed_num2.setText("");
                    ed_num2.requestFocus();
                    Toast.makeText(MainActivity.this, "zero not avalible", Toast.LENGTH_SHORT).show();
                    return;
                }

                MathModel mathModel = new MathModel(-1, n1.toString(), n2.toString(), "", ed_time.getText().toString(), op, new Date().toString()
                        , true);

                Intent intent = new Intent(getApplicationContext(), MathActivity.class);
                intent.putExtra("model", mathModel);
                startActivity(intent);

            }
        });
    }

    private void init() {
        btn_calc = findViewById(R.id.btn_calc);

        ed_num1 = findViewById(R.id.ed_num1);
        ed_num2 = findViewById(R.id.ed_num2);
        ed_time = findViewById(R.id.ed_time);

        spinner = findViewById(R.id.spinner);
    }


    private void checkMathServiceIsRunning() {
        if (!isMyServiceRunning(RunAllTimeService.class)) {
            Intent intent = new Intent(getApplicationContext(), RunAllTimeService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }

        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void getLoction(View view) {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!statusOfGPS) {
            Toast.makeText(this, "open gps first", Toast.LENGTH_SHORT).show();
        } else {
            getLoction();
        }

    }

    private void getLoction() {


        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);

            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("gps", "Location permission granted");
                    try {


                    } catch (SecurityException ex) {
                        Log.d("gps", "Location permission did not work!");
                    }
                }
                break;
        }
    }


    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {

            Toast.makeText(
                    getBaseContext(),
                    "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                            + loc.getLongitude(), Toast.LENGTH_SHORT).show();
            String longitude = "Longitude: " + loc.getLongitude();

            String latitude = "Latitude: " + loc.getLatitude();


            /*------- To get city name from coordinates -------- */
            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(),
                        loc.getLongitude(), 1);
                if (addresses.size() > 0) {
                    System.out.println(addresses.get(0).getLocality());
                    cityName = addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                    + cityName;
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }
}