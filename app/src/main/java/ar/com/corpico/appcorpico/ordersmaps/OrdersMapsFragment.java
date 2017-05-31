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
import android.widget.Spinner;
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

import org.joda.time.DateTime;

import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.presentation.Presenter;


/**
 * Muestra el mapa
 */
public class OrdersMapsFragment extends SupportMapFragment implements OnMapReadyCallback, ar.com.corpico.appcorpico.orders.presentation.View, ar.com.corpico.appcorpico.ordersmaps.View {
    private GoogleMap mMap;
    private String mTipoTrabajo;
    private String mEstado;
    private String mSector;
    private Presenter mOrdersMapPresenter;
    private static final int LOCATION_REQUEST_CODE = 1;

    public OrdersMapsFragment() {
    }

    public static OrdersMapsFragment newInstance(String tipotrabajo,String estado,String sector) {
        OrdersMapsFragment fragment = new OrdersMapsFragment();
        Bundle args = new Bundle();
        // TODO: Pasar los demás parámetros de la Action Bar
        args.putString("tipotrabajo", tipotrabajo);
        args.putString("estado", estado);
        args.putString("sector", sector);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            // Toman parámetros
            mTipoTrabajo = getArguments().getString("tipotrabajo");
            mEstado = getArguments().getString("estado");
            mSector = getArguments().getString("sector");
            Spinner activitySpinner = (Spinner) getActivity().findViewById(R.id.spinner_toolBar);
        }
        setLoadOrderList(mTipoTrabajo);
        getMapAsync(this);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        this.setLoadOrderList(mTipoTrabajo);
        return root;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            // ¿Permisos asignados?
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                Toast.makeText(getActivity(), "Error de permisos", Toast.LENGTH_LONG).show();
            }

        }
    }
   @Override
    public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            UiSettings uiSettings = mMap.getUiSettings();
            uiSettings.setScrollGesturesEnabled(true);
            uiSettings.setTiltGesturesEnabled(true);

            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Mostrar diálogo explicativo
                } else {
                    // Solicitar permiso
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            LOCATION_REQUEST_CODE);
                }
            }
            mMap.getUiSettings().setZoomControlsEnabled(true);

           LatLng pico = new LatLng(-35.666667, -63.733333);
           CameraPosition cameraPosition = new CameraPosition.Builder()
                   .target(pico)
                   .zoom(15)
                   .build();
           mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    public void LoadOrderMap(List<Order> orders){
        for (Order order : orders) {
            Double mLat,mLng;

            mLat = new Double((order.getLatitud().substring(0,7))).doubleValue()*-1;
            mLng = new Double((order.getLongitud().substring(0,7))).doubleValue()*-1;

            LatLng mLatLng = new LatLng(mLat,mLng);

            mMap.addMarker(new MarkerOptions()
                    .position(mLatLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                    .title(order.getTitular() + " - " + order.getDomicilio()));

            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(mLatLng)
                    .zoom(14)
                    .build();

            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    @Override
    public void showOrderList(List<Order> listorder) {
        mMap.clear();
        LoadOrderMap(listorder);
    }

    @Override
    public void showTipoTrabajoList(List<Tipo_Trabajo> listorder) {

    }

    @Override
    public void showOrderError(String error) {

    }

    @Override
    public void setPresenter(Presenter presenter) {
        mOrdersMapPresenter=presenter;
    }

    @Override
    public void showOrdesEmpty() {

    }

    @Override
    public void showProgressIndicator(boolean show) {

    }

    @Override
    public void setOrderFilter(String estado, String tipo, String sector, DateTime desde, DateTime hasta, String search, Boolean estadoActual) {
        mSector=sector;
        mOrdersMapPresenter.loadOrderList(mEstado,mTipoTrabajo,mSector,desde,hasta,search,estadoActual);
    }

    @Override
    public void setLoadOrderList(String tipotrabajo) {
        mTipoTrabajo=tipotrabajo;
        if (mTipoTrabajo != null){
            mOrdersMapPresenter.loadOrderList(mEstado,mTipoTrabajo,mSector,null,null,null,true);
        }
    }

    @Override
    public void setAsignarOrder(String cuadrilla, List<String> listorder) {

    }
}