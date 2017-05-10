package ar.com.corpico.appcorpico.orders;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ar.com.corpico.appcorpico.R;

/**
 * Created by sistemas on 10/05/2017.
 */


public class PendientesMapsActivity extends AppCompatActivity
            implements OnMapReadyCallback {

        private PendientesMapsFragment mPendientesMapsFragment;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pendientes_maps);

            mPendientesMapsFragment = mPendientesMapsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.map_container, mPendientesMapsFragment)
                    .commit();

            mPendientesMapsFragment.getMapAsync(this);
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            //LatLng cali = new LatLng(3.4383, -76.5161);
            LatLng micasa = new LatLng(-36.120556, -64.298056);

            googleMap.addMarker(new MarkerOptions()
                    .position(micasa)
                    .title("mi casa la Sucursal del cielo"));

            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(micasa)
                    .zoom(10)
                    .build();

            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
}

