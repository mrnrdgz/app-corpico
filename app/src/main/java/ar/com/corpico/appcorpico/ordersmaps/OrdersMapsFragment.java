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
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.presentation.*;
import ar.com.corpico.appcorpico.orders.presentation.Presenter;


/**
 * Muestra el mapa
 */
public class OrdersMapsFragment extends SupportMapFragment implements OnMapReadyCallback, ar.com.corpico.appcorpico.orders.presentation.View {
    private GoogleMap mMap;
    private ArrayList<Order> ordersMap = new ArrayList<Order>();
    private static final int LOCATION_REQUEST_CODE = 1;
    private List<Order> mListMap;

    public OrdersMapsFragment() {
    }

    public static OrdersMapsFragment newInstance() {
        return new OrdersMapsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ordersMap.add(new Order("839127", "Eléctrico", "2", "Retiro de Medidor", "Por Morosidad", null, "15514", "1", "Luisa Gonzalez", "Pasaje Rivero 957", "General Pico", "", "35.6630S", "63.7608W", "Nada"));
        ordersMap.add(new Order("839128", "Eléctrico", "3", "Cambio de Medidor", "Trabado", null, "22814", "1", "Jorgelina Rodriguez", "Calle 531", "General Pico", "", "35.6562S", "63.7537W", "Algo"));
        ordersMap.add(new Order("839129", "Eléctrico", "4", "Colocacion de Medidor", "Suministro Nuevo", null, "24429", "7", "Gustavo Turienzo", "Calle 29", "General Pico", "", "35.6657S", "63.7494W", "Todo"));
        ordersMap.add(new Order("839130", "Eléctrico", "4", "Retiro de Medidor", "Solicitud del Cliente", null, "55472", "1", "Gonzalo Fernandez", "Calle 18", "General Pico", "", "35.6601S", "63.7690W", "Siempre"));
        ordersMap.add(new Order("839131", "Eléctrico", "1", "Retiro de Medidor", "Por Morosidad", null, "40462", "2", "Antonella Privitera", "Calle 28", "General Pico", "", "35.6538S", "63.7528W", "Nunca"));
        ordersMap.add(new Order("839132", "Eléctrico", "2", "Retiro de Medidor", "Por Morosidad", null, "17495", "1", "Juan Perez", "Pasaje Rivero 957", "General Pico", "", "35.6629S", "63.7476W", "Nada"));
        ordersMap.add(new Order("839133", "Eléctrico", "3", "Cambio de Medidor", "Solic. Energia Prepaga", null, "6377", "1", "Rodrigo Nieto", "Calle 531", "General Pico", "", "35.6788S", "63.7530W", "Algo"));
        ordersMap.add(new Order("839134", "Eléctrico", "4", "Colocacion de Medidor", "Regularizacion de Deuda", null, "44345", "1", "Jose Ferrando", "Calle 29", "General Pico", "", "35.6678S", "63.7555W", "Todo"));
        ordersMap.add(new Order("839135", "Eléctrico", "4", "Retiro de Medidor", "Solicitud del Cliente", null, "42352", "1", "Fabio Gomez", "Calle 18", "General Pico", "", "35.6810S", "63.7491W", "Siempre"));
        getMapAsync(this);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
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
            uiSettings.setScrollGesturesEnabled(false);
            uiSettings.setTiltGesturesEnabled(false);

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
            LoadOrderMap(ordersMap);

    }
    public void setOrderMap(List<Order> orders){
        mListMap = orders;
    }
    public void LoadOrderMap(List<Order> orders){
        for (Order order : orders) {
            Double mLat,mLng;

            mLat = new Double((order.getLatitud().substring(0,7))).doubleValue()*-1;
            mLng = new Double((order.getLongitud().substring(0,7))).doubleValue()*-1;

            LatLng mLatLng = new LatLng(mLat,mLng);

            mMap.addMarker(new MarkerOptions()
                    .position(mLatLng)
                    .title(order.getTitular() + " - " + order.getDomicilio()));

            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(mLatLng)
                    .zoom(2)
                    .build();

            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
    public Double Convert_Decimal(String dsm) {
        Double grados;
        Double minutos;
        Double segundos;


        grados = new Double((dsm.substring(0,2))).doubleValue()*-1;
        minutos = new Double((dsm.substring(3,5))).doubleValue()/-60;
        segundos = new Double((dsm.substring(5,7))).doubleValue()/-3600;
        return grados + minutos + segundos;
    }

    @Override
    public void showOrderList(List<Order> listorder) {

    }

    @Override
    public void showOrderError(String error) {

    }

    @Override
    public void setPresenter(Presenter presenter) {

    }

    @Override
    public void showOrdesEmpty() {

    }

    @Override
    public void showProgressIndicator(boolean show) {

    }

    @Override
    public void setOrderFilter(String estado, String tipo, String sector, DateTime desde, DateTime hasta, String search, Boolean estadoActual) {

    }

    @Override
    public void setLoadOrderList(String tipo) {

    }

    @Override
    public void setAsignarOrder(String cuadrilla, List<String> listorder) {

    }

    @Override
    public List<Order> putOrderList() {
        return null;
    }
}