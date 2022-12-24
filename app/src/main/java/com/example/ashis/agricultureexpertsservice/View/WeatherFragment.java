package com.example.ashis.agricultureexpertsservice.View;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ashis.agricultureexpertsservice.Model.Weather;
import com.example.ashis.agricultureexpertsservice.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class WeatherFragment extends Fragment {

    private LocationManager locationManager;
    private LocationListener locationListener;
    Context context;

    List<Weather> weatherList = new ArrayList<>();

    TextView city;
    TextView street;
    TextView date;
    TextView temperature;
    ImageView clouds;
    TextView cloudType;

    Weather weather;
    String[] PERMISSION_LIST = new String[]{ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION};


    Location currentLocation;

    public WeatherFragment() {


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        context = getContext();

      city = (TextView) view.findViewById(R.id.weather_place);
      street = (TextView) view.findViewById(R.id.weather_street);
      date = (TextView) view.findViewById(R.id.weather_date);
      temperature = (TextView) view.findViewById(R.id.weather_temperature);
      clouds = (ImageView) view.findViewById(R.id.clouds);
      cloudType = (TextView) view.findViewById(R.id.cloud_type);

      if(!checkPermission()){

          requestPermission();

      }else{

          getMyLocation();

      }


      return view;

    }

    private boolean checkPermission(){
        int permission1 = ContextCompat.checkSelfPermission(getActivity(),ACCESS_FINE_LOCATION);
        int permission2 = ContextCompat.checkSelfPermission(getActivity(),ACCESS_COARSE_LOCATION);

        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(){

        ActivityCompat.requestPermissions(getActivity(),PERMISSION_LIST,200);

    }

    private void showMessage(String message, DialogInterface.OnClickListener okListener){

        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantResult) {
        super.onRequestPermissionsResult(requestCode, permission, grantResult);

        if (requestCode == 200){

            if (grantResult.length>0){

                boolean fineLocation = grantResult[0] == PackageManager.PERMISSION_GRANTED;
                boolean coarseLocation = grantResult[1] == PackageManager.PERMISSION_GRANTED;


                if (fineLocation && coarseLocation){

                    getMyLocation();

                }else {

                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)
                                && shouldShowRequestPermissionRationale(ACCESS_COARSE_LOCATION)
                                ) {

                            showMessage("Weather App location permission.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                                requestPermissions(PERMISSION_LIST,200);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                }
            }
        }


    }


    private void getWeatherList(){

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Know Your weather details");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = "https://api.openweathermap.org/data/2.5/weather?lat="+currentLocation.getLatitude()+
                "&lon="+currentLocation.getLongitude()+"&appid=4f8e593e37c08a7bec36aaa3c747ac5a&units=metric";
        Log.i("url",url.toString());



        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    progressDialog.dismiss();
                    Log.i("data", response.toString());
                    weather = new Weather();

                    weather.setWeatherStreetName(response.getString("name"));

                    JSONObject systemObject = response.getJSONObject("sys");
                    weather.setWeatherCityName(systemObject.getString("country"));

                    JSONObject mainObject = response.getJSONObject("main");
                    weather.setWeatherTemperature(String.valueOf(mainObject.getInt("temp")));

                    JSONObject weatherObject = response.getJSONArray("weather").getJSONObject(0);

                    weather.setWeatherCloudType(weatherObject.getString("description"));

                    Date date = new Date();

                    SimpleDateFormat format = new SimpleDateFormat("EEEE, MMM dd");
                    weather.setDate(format.format(date));


                }catch (Exception e){


                }finally {

                   temperature.setText(String.valueOf(weather.getWeatherTemperature())+(char)0x00B0+"C");
                   city.setText(String.valueOf(weather.getWeatherCityName()));
                   street.setText(String.valueOf(weather.getWeatherStreetName()));
                   cloudType.setText(String.valueOf(weather.getWeatherCloudType()));
                   date.setText(weather.getDate());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                progressDialog.dismiss();

            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    public static class MyLocationListener implements LocationListener{


        @Override
        public void onLocationChanged(Location location) {

            Log.i("Current Location",location.toString());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @SuppressLint("MissingPermission")
    private void showMyLocation(){

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0, new MyLocationListener());
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location == null){

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0, new MyLocationListener());
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (location == null){

            return;
        }

        Log.i("Your Current Location",location.toString());

    }

    @SuppressLint("MissingPermission")
    private void getMyLocation(){

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if(myLocation == null){

            myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            currentLocation = myLocation;
            getWeatherList();

        }else{

            currentLocation = myLocation;

            Log.i("My Location lat", String.valueOf(currentLocation.getLatitude())+"lon"+ currentLocation.getLongitude());

            getWeatherList();

        }
    }
}
