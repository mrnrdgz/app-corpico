package ar.com.corpico.appcorpico.ordersDetail.presentation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.data.FuenteOrdenesServidor;
import ar.com.corpico.appcorpico.orders.data.OrdersRepository;
import ar.com.corpico.appcorpico.orders.data.OrdersSqliteStore;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddOrdersState;
import ar.com.corpico.appcorpico.orders.presentation.AsignarAConexionesDialog;


public class OrderDetailActivity extends AppCompatActivity implements AsignarAConexionesDetailDialog.OnAsignarAConexionesDetailListener,ar.com.corpico.appcorpico.ordersDetail.presentation.View, OnMapReadyCallback {
    private GoogleMap mMap;
    private static final int LOCATION_REQUEST_CODE = 1;
    private SupportMapFragment mMapFragment;
    private String mLat;
    private String mLng;
    private ArrayList<String> mNumero = new ArrayList<>();
    private String mTipoCuadrilla;
    private AddOrdersState mAddOrdersState;
    private ar.com.corpico.appcorpico.ordersDetail.presentation.OrdersDetailPresenter mOrdersDetailPresenter;


    @Override
    public void setPresenter(Presenter presenterDetail) {
        mOrdersDetailPresenter = (OrdersDetailPresenter) presenterDetail;
    }

    @Override
    public void setAsignarOrder(String cuadrilla, List<String> listorder, String observacion) {
        mOrdersDetailPresenter.asignarOrder(cuadrilla,listorder,observacion);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_detail);

        // Obtención de views
        TextView numero = (TextView)this.findViewById(R.id.numero_text);
        TextView fecha = (TextView)this.findViewById(R.id.fecha_text);
        TextView motivo = (TextView)this.findViewById(R.id.motivo_text);
        TextView tipoTrabajo = (TextView)this.findViewById(R.id.tipotrabajo_text);
        TextView titular = (TextView)this.findViewById(R.id.titular_text);
        TextView asociado = (TextView)this.findViewById(R.id.asociado_text);
        TextView domicilio = (TextView)this.findViewById(R.id.domicilio_text);
        TextView anexo = (TextView)this.findViewById(R.id.anexo_text);
        TextView tipousuario = (TextView)this.findViewById(R.id.tipousuario_text);
        TextView tarifa = (TextView)this.findViewById(R.id.tarifa_text);
        TextView potenciadeclarada = (TextView)this.findViewById(R.id.potenciadeclarada_text);
        TextView medidor = (TextView)this.findViewById(R.id.medidor_text);
        TextView marca = (TextView)this.findViewById(R.id.marca_text);
        TextView modelo = (TextView)this.findViewById(R.id.modelo_text);
        TextView factorM = (TextView)this.findViewById(R.id.factorm_text);
        TextView capacidad = (TextView)this.findViewById(R.id.capacidad_text);
        TextView tension = (TextView)this.findViewById(R.id.tension_text);

        TextView observacion = (TextView)this.findViewById(R.id.observacion_text);
        //Gallery simpleGallery = (Gallery) findViewById(R.id.simpleGallery);

        setToolbar();
        Intent intent=getIntent();
        Bundle extras =intent.getExtras();
        if (extras != null) {
            numero.setText("Orden Nº " + (String)extras.get("NUMERO"));
            mNumero.add((String)extras.get("NUMERO"));
            mTipoCuadrilla= (String)extras.get("TIPO_CUADRILLA");

            String mFecha = (String)extras.get("FECHA");
            String dia = mFecha.substring(8,10);
            String mes = mFecha.substring(5,7);
            String ano = mFecha.substring(0,4);
            fecha.setText(dia + "/" + mes + "/" + ano);

            //TODO: HACER VARIABLE EL ESTADO PARA QUE ME SIRVA EL DETALLE EN OTRAS ACTIVITYS (EL ESTADO ME REFLEJA EL COLOR DE EL ICON DE LA UBICACION)
            //estado.setText((String)extras.get("ESTADO"));
            tipoTrabajo.setText((String)extras.get("TIPO_TRABAJO"));
            motivo.setText((String)extras.get("MOTIVO"));
            titular.setText((String)extras.get("TITULAR"));
            asociado.setText((String)extras.get("ASOCIADO"));
            domicilio.setText((String)extras.get("DOMICILIO"));
            anexo.setText((String)extras.get("ANEXO"));
            tipousuario.setText((String)extras.get("TIPO_USUARIO"));
            tarifa.setText((String)extras.get("TARIFA"));
            potenciadeclarada.setText((String)extras.get("POTENCIA_DECLARADA"));
            medidor.setText((String)extras.get("MEDIDOR"));
            marca.setText((String)extras.get("MARCA"));
            modelo.setText((String)extras.get("MODELO"));
            factorM.setText((String)extras.get("FACTOR_M"));
            capacidad.setText((String)extras.get("CAPACIDAD"));
            tension.setText((String)extras.get("TENSION"));

            mLat=(String)extras.get("LAT");
            mLng=(String)extras.get("LNG");
            observacion.setText((String)extras.get("OBSERVACION"));
        }

        /**
         * <<create>> Almacénes
         */
        FuenteOrdenesServidor restStore = new FuenteOrdenesServidor();
        OrdersSqliteStore sqliteStore = new OrdersSqliteStore();

        /**
         * <<create>> SessionsRepository
         */
        OrdersRepository repository = OrdersRepository.getInstance(restStore);

        /**
         * <<create>> CaseUser
         */
        //TODO: ACA DEBERIA USAR UNA VARIABLE PARA PONER EL CASO DE USO?
        mAddOrdersState = new AddOrdersState(repository);

        mOrdersDetailPresenter = new OrdersDetailPresenter(mAddOrdersState,this);
        this.setPresenter(mOrdersDetailPresenter);

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
            case R.id.action_asignaracuadrilla:
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("AsignarconexionDialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                DialogFragment newFragment = AsignarAConexionesDetailDialog.newInstance(mTipoCuadrilla,mNumero);
                newFragment.show(ft, "AsignarconexionDialog");
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
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
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
        //mMap.getUiSettings().setZoomControlsEnabled(true);*/
        Double mLt,mLn;

        mLt = new Double(mLat.substring(0,7)).doubleValue()*-1;
        mLn = new Double(mLng.substring(0,7)).doubleValue()*-1;

        LatLng mLatLng = new LatLng(mLt,mLn);
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

    @Override
    public void onPossitiveButtonAsignarClick(String cuadrilla, ArrayList<String> numero,String observacion) {
        //this.setAsignarOrder(cuadrilla,numero);
        mOrdersDetailPresenter.asignarOrder(cuadrilla,numero,observacion);
    }

    @Override
    public void onNegativeButtonAsignarClick() {

    }

    @Override
    public void closeDetail(String cuadrilla) {
        this.finish();

    }
}
