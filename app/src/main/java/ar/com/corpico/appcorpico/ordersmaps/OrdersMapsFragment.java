package ar.com.corpico.appcorpico.ordersmaps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ar.com.corpico.appcorpico.R;


/**
 * Muestra el mapa
 */
public class OrdersMapsFragment extends SupportMapFragment  {
    private GoogleMap mMap;
    private static final int LOCATION_REQUEST_CODE = 1;

    public OrdersMapsFragment() {
    }

    public static OrdersMapsFragment newInstance() {
        return new OrdersMapsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        android.view.View root = inflater.inflate(R.layout.map_container, container, false);
        return root;
    }


}