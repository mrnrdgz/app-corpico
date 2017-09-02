package ar.com.corpico.appcorpico.orders.presentation;

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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;


/**
 * Muestra el mapa
 */
public class OrdersMapsFragment extends SupportMapFragment implements OnMapReadyCallback, OrdersListMvp.View {
    // Keys de argumentos
    public static final String ARG_TIPO_CUADRILLA = "orders.tipo_cuadrilla";
    public static final String ARG_ESTADO = "orders.estado";
    public static final String ARG_ZONAS_SELECCIONADAS = "orders.zonas_seleccionadas";
    public static final String ARG_FECHA_INICIO = "orders.fecha_inicio";
    public static final String ARG_FECHA_FIN = "orders.fecha_fin";
    public static final String ARG_SEARCH = "orders.search";
    private static final String ARG_TIPOS_TRABAJO_SELECCIONADOS = "orders.tipos_trabajo_seleccionados";

    private static final int LOCATION_REQUEST_CODE = 1;


    // Dependencias
    private OrdersListMvp.Presenter mOrdersMapPresenter;

    //GoogleMap
    private GoogleMap mMap;

    // Lógica
    private Marker marker;

    // Argumentos
    private String mTipoCuadrilla;
    private String mEstado;
    private List<String> mTiposTrabajoSeleccionados;
    private List<String> mZonasSeleccionadas;
    private DateTime mFechaInicio;
    private DateTime mFechaFin;
    private String mSearch;


    public OrdersMapsFragment() {
    }

    public static OrdersMapsFragment newInstance(
            String tipoCuadrilla, String estado,
            ArrayList<String> tiposTrabajoSeleccionados, ArrayList<String> zonasSeleccionadas,
            DateTime fechaInicio, DateTime fechaFin, String query) {

        OrdersMapsFragment fragment = new OrdersMapsFragment();
        Bundle args = new Bundle();

        args.putString(ARG_TIPO_CUADRILLA, tipoCuadrilla);
        args.putString(ARG_ESTADO, estado);
        args.putStringArrayList(ARG_ZONAS_SELECCIONADAS, (ArrayList<String>) zonasSeleccionadas);
        args.putStringArrayList(ARG_TIPOS_TRABAJO_SELECCIONADOS, tiposTrabajoSeleccionados);
        args.putSerializable(ARG_FECHA_INICIO, fechaInicio);
        args.putSerializable(ARG_FECHA_FIN, fechaFin);
        args.putString(ARG_SEARCH, query);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();

        if (getArguments() != null) {
            // Toman parámetros
            mTipoCuadrilla = arguments.getString(ARG_TIPO_CUADRILLA);
            mEstado = arguments.getString(ARG_ESTADO);
            mTiposTrabajoSeleccionados = arguments.getStringArrayList(ARG_TIPOS_TRABAJO_SELECCIONADOS);
            mZonasSeleccionadas = arguments.getStringArrayList(ARG_ZONAS_SELECCIONADAS);
            mFechaInicio = (DateTime) arguments.get(ARG_FECHA_INICIO);
            mFechaFin = (DateTime) arguments.get(ARG_FECHA_FIN);
            mSearch = arguments.getString(ARG_SEARCH);
        }
        getMapAsync(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        mOrdersMapPresenter.loadOrders(mTipoCuadrilla, mEstado, mTiposTrabajoSeleccionados,
                mZonasSeleccionadas, mFechaInicio, mFechaFin, mSearch, true);

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

           //LatLng pico = new LatLng(-35.666667, -63.733333);
           LatLng pico = new LatLng(-35.658103, -63.757882);
           CameraPosition cameraPosition = new CameraPosition.Builder()
                   .target(pico)
                   .zoom(14)
                   .build();
           mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    public void LoadOrderMap(List<Order> orders){
        for (Order order : orders) {
            Double mLat,mLng;

            mLat = new Double((order.getLatitud().substring(0,7))).doubleValue()*-1;
            mLng = new Double((order.getLongitud().substring(0,7))).doubleValue()*-1;

            LatLng mLatLng = new LatLng(mLat,mLng);
            marker = mMap.addMarker(new MarkerOptions()
                    .position(mLatLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                    .title(order.getTitular() + " - " + order.getDomicilio()));
        }
    }

    @Override
    public void showOrderList(List<Order> listorder) {
        if (mMap != null) {
            mMap.clear();
        }
        LoadOrderMap(listorder);
    }

    @Override
    public void showOrderError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void setPresenter(OrdersListMvp.Presenter presenter) {
        mOrdersMapPresenter=presenter;
    }

    @Override
    public void showOrdesEmpty() {

    }

    @Override
    public void showProgressIndicator(boolean show) {

    }

    @Override
    public void close() {

    }
}