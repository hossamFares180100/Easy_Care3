package com.example.easycare.mapClasses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.easycare.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NearbyHospitalMapActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, ButtonMapListener
{

    private GoogleMap mMap;
    AlertDialog dialog;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private static final int Request_User_Location_code=99;
    private double latitude,longitude,user_latitude,user_longitude;
    LatLng source,destination;
     ArrayList<HospitalLocations>sortedLocation;
    ArrayList<HospitalLocations>hospitalLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_hospital_map);

        sortedLocation=new ArrayList<>();

        hospitalLocations=new ArrayList<>();
        hospitalLocations.add(new HospitalLocations(new LatLng(30.066789098084197, 31.254276757694328),"مستشفى القبطي بالقاهره"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.063402405080385,31.25249829143286),"مستشفى القاهره التخصصي"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.045056324310504,31.266190633177757),"مستشفى الحسين التخصصي"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.046572742121516,31.27131599932909),"مستشفى القاهره الفاطميه"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.06282205945841,31.27466306090355),"المستشفى الجوي العام"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.084055270265843,31.27976395189762),"مستشفى السنابل"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.078491357372567,31.287656687200073),"مستشفى عين شمس التخصصى"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.070005337959337,31.29655189812183),"مستشفى العباسيه للصحه النفسيه"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.030634829145317,31.231233403086662),"مستشفى قصر العيني التعليمي الجديد"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.054335566994105,31.215432845056057),"مستشفى العجوزه"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.040034874459916, 31.22784094821963),"مستشفى ٦ أكتوبر للتأمين الصحي"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.031581098207674,31.227077655494217),"مستشفيات جامعه القاهره"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.121333357558303,31.24474335461855),"مستشفى النيل"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.00414782268365,31.199492812156677),"مستشفى الجزيره"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.036092006988802,31.197934448719025),"مستشفى المروه"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.037526712170674,31.192441955208775),"مستشفى الحياه"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.2862985,31.207165599999996),"مستشفى قها المركزي"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.473186051862243,31.180636547505856),"مستشفى حميات بنها"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.552614199999997,31.1389988),"مستشفى قويسنا العام"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.044389199999998,31.2247634),"مستشفى المعلمين"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.04461488999776,31.224963068962094),"مستشفى القصر العينى"));
        hospitalLocations.add(new HospitalLocations(new LatLng(31.277262718801566, 30.009370078999222),"مستشفى مبره العصافره"));
        hospitalLocations.add(new HospitalLocations(new LatLng(31.22149532436714, 29.94104885068185),"مستشفى الإسكندرية الدولي"));
        hospitalLocations.add(new HospitalLocations(new LatLng(31.17480151756665, 29.87925075471639),"مستشفى القباري العام"));
        hospitalLocations.add(new HospitalLocations(new LatLng(31.249676650289935, 29.981904258380663),"مستشفى فكتوريا"));
        hospitalLocations.add(new HospitalLocations(new LatLng(24.09365029989727, 32.89697922529771),"مستشفى الرمد باسوان"));
        hospitalLocations.add(new HospitalLocations(new LatLng(24.089889297438944, 32.89174355327842),"مستشفى الصداقه"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.46898125621885,31.18112839758396),"مستشفى دار الشفاء"));
        hospitalLocations.add(new HospitalLocations(new LatLng(27.179341934907207, 31.19255109631243),"مستشفى الإنسانيه"));
        hospitalLocations.add(new HospitalLocations(new LatLng(27.191634148460366, 31.18602796396052),"مستشفى سانت ماريا"));
        hospitalLocations.add(new HospitalLocations(new LatLng(31.046521807113447, 31.395673843984742),"مستشفى تبارك للأطفال"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.463750744279775, 31.185349298993998),"مستشفى الراعي الصالح"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.47025649793999,31.178632266819477),"مستشفى الجامعه ببنها"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.455131486985003, 31.1792553200863),"مستشفى الشروق"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.466303081848704, 31.188267542414593),"المستشفى الكويتي التخصصي"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.465987999202675,31.184816434979435),"مستشفى بنها للتأمين الصحي"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.4731819,31.180644299999997),"مستشفى حميات بنها"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.457757452090835, 31.179899052892456),"مستشفى عصفور الخيري"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.524920086997025, 31.3516021634463),"مستشفى منيا القمح العام"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.521371129294675, 31.35692366615444),"مستشفى العاصمة التخصصي"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.514568598115474, 31.32911452296998),"مستشفى الحياه"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.520631746793562, 31.340444173896984),"مستشفى جلال"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.490755990529248, 31.295125570188976),"مستشفى صلاح التخصصي"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.516638984856137, 31.22131117811371),"الوحده الصحيه بقريه ميت الحوفيين"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.5559679379416, 31.252553548851804),"مستشفى كفر شكر المركزي"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.124465909377967,31.258285492658615),"مستشفى ناصر العام"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.121933742655337, 31.2449997930694),"مستشفى النيل"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.122546699999997,31.249913300000003),"مركز عرابى الطبى"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.13027690392222,31.249586082994938),"مستشفى الأمل التخصصى"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.119813445428445,31.260030269622803),"مستشفى الدكتور محمد عبده"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.123487800000003,31.259289000000003),"مركز عصفور الطبى"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.121316247439342,31.24472390860319),"مستشفى النيل"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.120727831545036,31.258878596127033),"مستشفى ناصر العام (عيادات آفرينو)"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.129616708949776, 31.247231393645233),"مستشفي سانت مارى"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.133971476086302, 31.278418499962825),"مستشفى وادى الطب"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.120269914532223,31.24157432466745),"مستشفى د.عاطف نورالدين"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.13355589495063,31.253367662429813),"مستشفى الندى"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.123046756549858, 31.249677568277196),"مستشفى دكتور محمد شاهين"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.123380832475245, 31.260878473170933),"عيادات دنيا التخصصية"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.130878683649513, 31.230751901387773),"مستشفي أبوالمنجا"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.134083631827284,31.253284513950344),"مستشفى الندى"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.139434202481016,31.250369623303413),"مستشفى زمزم التخصصى"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.102765975192824,31.255129538476464),"مستشفى مجد الاسلام"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.13161310301547,31.27878025174141),"مركز دار الحياه التخصصى"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.146192388138346,31.27782203257084),"مستشفى بهتيم للتامين الصحى"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.109332926203848,31.25285401940346),"مستشفى الناس"));
        hospitalLocations.add(new HospitalLocations(new LatLng(30.133103836778048,31.278421841561798),"مستشفى وادى الطب"));




        /*if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            checkUserLocationPermission();
        }*/

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (ContextCompat.checkSelfPermission(NearbyHospitalMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(NearbyHospitalMapActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }
    }






    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }




    public void onClick(View v)
    {
        //Object transferData[]=new Object[2];
        switch (v.getId())
        {
            case R.id.image_search:
                EditText addressField=findViewById(R.id.location_search);
                String address=addressField.getText().toString();
                List<Address>addressList=null;
                MarkerOptions userMarkerOption=new MarkerOptions();
                if(!TextUtils.isEmpty(address))
                {
                    Geocoder geocoder=new Geocoder(this);
                    try {
                        addressList=geocoder.getFromLocationName(address,6);
                        if(addressList!=null)
                        {
                            for(int i=0;i<addressList.size();i++)
                            {
                                Address userAddress=addressList.get(i);
                                LatLng latLng=new LatLng(userAddress.getLatitude(),userAddress.getLongitude());
                                LatLng userLatLng=new LatLng(user_latitude,user_longitude);
                                source=userLatLng;
                                destination=latLng;
                                float distance = (float) CalculationByDistance(userLatLng,latLng);
                                Toast.makeText(this, "the distance = "+distance+" KM", Toast.LENGTH_LONG).show();
                                userMarkerOption.position(latLng);
                                userMarkerOption.title(address);
                                userMarkerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                mMap.addMarker(userMarkerOption);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                                PolylineOptions polylineOptions=new PolylineOptions().add(userLatLng).add(latLng).width(5).color(Color.RED).geodesic(true);
                                mMap.addPolyline(polylineOptions);
                                Log.e("hos",address+" "+latLng);


                            }
                        }else{
                            Toast.makeText(this, "Location not found...", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    Toast.makeText(this, "please enter any location name...", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.hospitals_nearby:

                final AlertDialog.Builder builder=new AlertDialog.Builder(NearbyHospitalMapActivity.this);
                LayoutInflater inflater=this.getLayoutInflater();
                final View view=inflater.inflate(R.layout.hospital_show_layout_recycle,null);
                final RecyclerView hospital_show=view.findViewById(R.id.rv_hospitals);
                HospitalShowRecycleViewAdapter adapter=new HospitalShowRecycleViewAdapter(hospitalLocations,NearbyHospitalMapActivity.this);
                hospital_show.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(NearbyHospitalMapActivity.this);
                hospital_show.setLayoutManager(layoutManager);
                hospital_show.setAdapter(adapter);
                builder.setView(view).setTitle("Hospitals").setIcon(R.drawable.ic_hospital);
                 dialog=builder.create();
                dialog.show();
                break;
            case R.id.remove_marker:
                mMap.clear();
                MarkerOptions markerOptions=new MarkerOptions();
                markerOptions.position(new LatLng(user_latitude,user_longitude));
                markerOptions.title("user Current Location");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                mMap.addMarker(markerOptions).showInfoWindow();
                mMap.getUiSettings().setZoomControlsEnabled(true); // to show plus and minus button to zoom
                //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(user_latitude,user_longitude),18));


                break;


        }

    }






    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {

            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }

    public boolean checkUserLocationPermission(){

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_User_Location_code);
            }
            else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_User_Location_code);
            }
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        switch (requestCode)
        {
            case Request_User_Location_code:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                    {
                        if(googleApiClient==null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else{
                    Toast.makeText(this, "permission denied...!", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }



    protected synchronized void buildGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        latitude=location.getLatitude();
        longitude=location.getLongitude();
        lastLocation=location;
        user_longitude=location.getLongitude();
        user_latitude=location.getLatitude();
        LatLng userLatlng=new LatLng(user_latitude,user_longitude);

        for(int i=0;i<hospitalLocations.size();i++)
        {
            float dis= (float) CalculationByDistance(userLatlng,hospitalLocations.get(i).getHospital());
            hospitalLocations.get(i).setDistance(dis);
        }

        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("user Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(markerOptions).showInfoWindow();
        mMap.getUiSettings().setZoomControlsEnabled(true); // to show plus and minus button to zoom
        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
        //mMap.animateCamera(CameraUpdateFactory.zoomBy(14));

        Collections.sort(hospitalLocations, new Comparator<HospitalLocations>() {
            @Override
            public int compare(HospitalLocations o1, HospitalLocations o2) {
                return Float.compare(o1.getDistance(),o2.getDistance());
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng).title("new location")
                        /*.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_foreground))*/ // to change icon
                ).showInfoWindow(); // to show title directly above the location
                PolylineOptions polylineOptions=new PolylineOptions().add(new LatLng(user_latitude,user_longitude)).add(latLng).width(5).color(Color.RED).geodesic(true);
                mMap.addPolyline(polylineOptions);
                Log.e("hos",latLng+"");
                float distance = (float) CalculationByDistance(new LatLng(user_latitude,user_longitude),latLng);
                Toast.makeText(NearbyHospitalMapActivity.this, "the distance = "+distance+" KM", Toast.LENGTH_LONG).show();
            }
        });


        if(googleApiClient!=null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);

        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
         locationRequest=new LocationRequest();
         locationRequest.setInterval(1100);
         locationRequest.setFastestInterval(1100);
         locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

         if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
         {
             LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this);
         }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onClick(HospitalLocations hospitalLocations) {
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(hospitalLocations.getHospital());
        markerOptions.title(hospitalLocations.getName());
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(markerOptions).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hospitalLocations.getHospital(),18));
        PolylineOptions polylineOptions=new PolylineOptions().add(new LatLng(user_latitude,user_longitude)).add(hospitalLocations.getHospital()).width(5).color(Color.RED).geodesic(true);
        mMap.addPolyline(polylineOptions);
        dialog.dismiss();

    }
}