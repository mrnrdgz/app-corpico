package ar.com.corpico.appcorpico.orders.presentation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.ordersmaps.OrdersMapsFragment;

public class OrderDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private static final int LOCATION_REQUEST_CODE = 1;
    private SupportMapFragment mMapFragment;
//TODO: CONTROLAR
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_ot);

        // Obtención de views
        TextView numero = (TextView)this.findViewById(R.id.numero_text);
        TextView fecha = (TextView)this.findViewById(R.id.fecha_text);
        TextView estado = (TextView)this.findViewById(R.id.estado_text);
        TextView tipo = (TextView)this.findViewById(R.id.tipo_text);
        TextView sector = (TextView)this.findViewById(R.id.sector_text);
        TextView observacion = (TextView)this.findViewById(R.id.observacion_text);
        Gallery simpleGallery = (Gallery) findViewById(R.id.simpleGallery);

        setToolbar();
        Intent intent=getIntent();
        Bundle extras =intent.getExtras();
        if (extras != null) {
            numero.setText((String)extras.get("Numero"));
            fecha.setText((String)extras.get("Fecha"));
            estado.setText((String)extras.get("Estado"));
            tipo.setText((String)extras.get("Tipo_Trabajo"));
            sector.setText((String)extras.get("Sector"));
            observacion.setText((String)extras.get("Observacion"));
        }

        mMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
    }
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_ot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_editarOrder:

                break;
            case R.id.action_photoOrder:

                break;
            case R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }
        mMap.getUiSettings().setZoomControlsEnabled(true);

        /*LatLng pico = new LatLng(-35.666667, -63.733333);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(pico)
                .zoom(15)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/

        LatLng mLatLng = new LatLng(-35.6657,-63.7494);
        mMap.addMarker(new MarkerOptions()
                .position(mLatLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        //.title(order.getTitular() + " - " + order.getDomicilio()));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(mLatLng)
                .zoom(16)
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            // ¿Permisos asignados?
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
                mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "Error de permisos", Toast.LENGTH_LONG).show();
            }

        }
    }
    public void LoadOrderGeo(Double lat, Double lng){
            Double mLat,mLng;

            mLat = lat;
            mLng = lng;

            LatLng mLatLng = new LatLng(mLat,mLng);

            mMap.addMarker(new MarkerOptions()
                    .position(mLatLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                    //.title(order.getTitular() + " - " + order.getDomicilio()));

            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(mLatLng)
                    .zoom(14)
                    .build();

            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
