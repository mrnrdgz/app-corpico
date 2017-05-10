package ar.com.corpico.appcorpico.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;


/**
 * Muestra el mapa
 */
public class PendientesMapsFragment extends SupportMapFragment {

    public PendientesMapsFragment() {
    }

    public static PendientesMapsFragment newInstance() {
        return new PendientesMapsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        return root;
    }

}