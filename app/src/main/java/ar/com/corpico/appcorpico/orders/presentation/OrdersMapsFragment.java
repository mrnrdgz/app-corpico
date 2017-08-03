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
import com.google.android.gms.maps.model.MarkerOptions;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;


/**
 * Muestra el mapa
 */
public class OrdersMapsFragment extends SupportMapFragment implements OnMapReadyCallback, ar.com.corpico.appcorpico.orders.presentation.View {
    private GoogleMap mMap;
    private String mTipoCuadrilla;
    private List<String> mTipoTrabajoSelected = new ArrayList();
    private List<String> mTipoTrabajo = new ArrayList();
    private List<String> mZona = new ArrayList();
    private List<String> mZonaSelected = new ArrayList();
    private String mEstado;
    private String mSector;
    private DateTime mDesde = new DateTime();
    private DateTime mHasta = new DateTime();
    private Presenter mOrdersMapPresenter;
    private static final int LOCATION_REQUEST_CODE = 1;

    public OrdersMapsFragment() {
    }

    public static OrdersMapsFragment newInstance(String tipoCuadrilla, String estado, List<String> zona,DateTime desde, DateTime hasta) {
        OrdersMapsFragment fragment = new OrdersMapsFragment();
        Bundle args = new Bundle();
        // TODO: Pasar los demás parámetros de la Action Bar
        args.putString("tipoCuadrilla", tipoCuadrilla);
        args.putString("estado", estado);
        args.putStringArrayList("zona", (ArrayList<String>) zona);
        args.putSerializable("desde", desde);
        args.putSerializable("hasta", hasta);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            // Toman parámetros
            mTipoCuadrilla = getArguments().getString("tipoCuadrilla");
            mEstado = getArguments().getString("estado");
            mZona = getArguments().getStringArrayList("zona");
            mDesde = (DateTime) getArguments().get("desde");
            mHasta = (DateTime) getArguments().get("hasta");
            //Spinner activitySpinner = (Spinner) getActivity().findViewById(R.id.spinner_toolBar);
        }
        setLoadOrderList(mTipoCuadrilla);
        getMapAsync(this);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        this.setLoadOrderList(mTipoCuadrilla);
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
        if (mMap != null) {
            mMap.clear();
        }
        LoadOrderMap(listorder);
    }

    @Override
    public void showTipoCuadrillaList(List<Tipo_Cuadrilla> listorder) {

    }


    @Override
    public void showCuadrillaxTipoList(List<Tipo_Cuadrilla> listcuadrilla) {

    }

    @Override
    public List<String> getTipoTrabajo() {
        return mTipoTrabajo;
    }

    @Override
    public List<String> getZona() {
        return mZona;
    }

    @Override
    public void cleanData() {
        mDesde=null;
        mHasta=null;
        mZonaSelected=new ArrayList<>();
        mTipoTrabajoSelected=new ArrayList<>();
    }

    @Override
    public void showOrderError(String error) {

    }

    @Override
    public void setPresenter(Presenter presenter) {
        mOrdersMapPresenter=presenter;
    }

    @Override
    public void setTipoTrabajo(List<String> tipoTrabajo) {
        mTipoTrabajo=tipoTrabajo;
        //mTipoTrabajoSelected = new ArrayList<>();
    }

    @Override
    public void setZonas(List<String> zona) {
        mZona=zona;
        //mZonaSelected = new ArrayList<>();
    }

    @Override
    public void showOrdesEmpty() {

    }

    @Override
    public void showProgressIndicator(boolean show) {

    }

    @Override
    public void setOrderFilter(String estado, List<String> tipo, List<String> zona, DateTime desde, DateTime hasta, String search, Boolean estadoActual) {
        if (tipo.size() == 0){
            mTipoTrabajoSelected= mTipoTrabajo;
        }else{
            mTipoTrabajoSelected = tipo;
        }
        if (zona.size() == 0){
            mZonaSelected= mZona;
        }else{
            mZonaSelected = zona;
        }
        mDesde=desde;
        mHasta=hasta;
        mOrdersMapPresenter.loadOrderList(mEstado,mTipoTrabajoSelected,mZona,mDesde,mHasta,search,estadoActual);
    }

    @Override
    public void setLoadOrderList(String tipocuadrilla) {
        mTipoCuadrilla=tipocuadrilla;
        if (mTipoCuadrilla!=null){
            mOrdersMapPresenter.setLoadTipoTrabajos(mTipoCuadrilla);
            mOrdersMapPresenter.setLoadZonas();
            if(mTipoTrabajoSelected.size()==0 && mZonaSelected.size()==0){
                mOrdersMapPresenter.loadOrderList(mEstado,mTipoTrabajo,mZona,mDesde,mHasta,null,true);
            }
            if(mTipoTrabajoSelected.size()!=0 && mZonaSelected.size()==0){
                mOrdersMapPresenter.loadOrderList(mEstado,mTipoTrabajoSelected,mZona,mDesde,mHasta,null,true);
            }
            if(mTipoTrabajoSelected.size()==0 && mZonaSelected.size()!=0){
                mOrdersMapPresenter.loadOrderList(mEstado,mTipoTrabajoSelected,mZonaSelected,mDesde,mHasta,null,true);
            }
            if(mTipoTrabajoSelected.size()!=0 && mZonaSelected.size()!=0){
                mOrdersMapPresenter.loadOrderList(mEstado,mTipoTrabajoSelected,mZonaSelected,mDesde,mHasta,null,true);
            }
        }
    }

    @Override
    public void setAsignarOrder(String cuadrilla, List<String> listorder) {

    }
}