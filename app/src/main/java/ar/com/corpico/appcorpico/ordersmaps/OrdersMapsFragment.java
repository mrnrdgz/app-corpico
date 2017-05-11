package ar.com.corpico.appcorpico.ordersmaps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

import ar.com.corpico.appcorpico.R;


/**
 * Muestra el mapa
 */
public class OrdersMapsFragment extends SupportMapFragment {

    public OrdersMapsFragment() {
    }

    public static OrdersMapsFragment newInstance() {
        return new OrdersMapsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        android.view.View root = inflater.inflate(R.layout.activity_pendientes_maps, container, false);
        return root;
    }

}