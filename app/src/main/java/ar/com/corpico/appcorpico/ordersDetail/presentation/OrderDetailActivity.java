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
import android.widget.ListView;
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

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.data.FuenteOrdenesServidor;
import ar.com.corpico.appcorpico.orders.data.OrdersRepository;
import ar.com.corpico.appcorpico.orders.data.OrdersSqliteStore;
import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddOrdersState;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddTurno;
import ar.com.corpico.appcorpico.orders.presentation.OrdersAdapter;


public class OrderDetailActivity extends AppCompatActivity implements
        AsignarAConexionesDetailDialog.OnAsignarAConexionesDetailListener,
        ar.com.corpico.appcorpico.ordersDetail.presentation.View,
        OnMapReadyCallback,
        AsignarTurnoDialog.OnAsignarTurnoDialogListener{
    private GoogleMap mMap;
    private static final int LOCATION_REQUEST_CODE = 1;
    private SupportMapFragment mMapFragment;
    private String mLat;
    private String mLng;
    private DateTime mTurno;
    private String mNumero;
    private String mTipoCuadrilla;
    private AddOrdersState mAddOrdersState;
    private AddTurno mAddTurno;
    private ar.com.corpico.appcorpico.ordersDetail.presentation.OrdersDetailPresenter mOrdersDetailPresenter;
    private TextView mTxtTurno;
    //private ListView mEtapaList;
    //private EtapasAdapter mEtapasAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_detail);

        // Obtención de views
        TextView numero = (TextView)this.findViewById(R.id.numero_text);
        TextView fecha = (TextView)this.findViewById(R.id.fecha_text);
        mTxtTurno = (TextView)this.findViewById(R.id.turno_text);
        TextView motivo = (TextView)this.findViewById(R.id.motivo_text);
        TextView tipoTrabajo = (TextView)this.findViewById(R.id.tipotrabajo_text);
        TextView titular = (TextView)this.findViewById(R.id.titular_text);
        TextView asociado = (TextView)this.findViewById(R.id.asociado_text);
        TextView domicilio = (TextView)this.findViewById(R.id.domicilio_text);
        TextView anexo = (TextView)this.findViewById(R.id.anexo_text);
        TextView grupo = (TextView)this.findViewById(R.id.grupo_text);
        TextView ruta = (TextView)this.findViewById(R.id.ruta_text);
        TextView orden = (TextView)this.findViewById(R.id.orden_text);
        TextView tipousuario = (TextView)this.findViewById(R.id.tipousuario_text);
        TextView tarifa = (TextView)this.findViewById(R.id.tarifa_text);
        TextView potenciadeclarada = (TextView)this.findViewById(R.id.potenciadeclarada_text);
        TextView medidor = (TextView)this.findViewById(R.id.medidor_text);
        TextView marca = (TextView)this.findViewById(R.id.marca_text);
        TextView modelo = (TextView)this.findViewById(R.id.modelo_text);
        TextView factorM = (TextView)this.findViewById(R.id.factorm_text);
        TextView capacidad = (TextView)this.findViewById(R.id.capacidad_text);
        TextView tension = (TextView)this.findViewById(R.id.tension_text);
        ListView mEtapaList = (ListView) this.findViewById(R.id.etapas_list);

        TextView observacion = (TextView)this.findViewById(R.id.observacion_text);
        //Gallery simpleGallery = (Gallery) findViewById(R.id.simpleGallery);

        setToolbar();
        Intent intent=getIntent();
        Bundle extras =intent.getExtras();
        if (extras != null) {
            numero.setText("Orden Nº " + (String)extras.get("NUMERO"));
            mNumero= (String)extras.get("NUMERO");
            mTipoCuadrilla= (String)extras.get("TIPO_CUADRILLA");

            DateTime mFecha = (DateTime)extras.get("FECHA");
            fecha.setText(mFecha.toString("dd-MM-yyyy"));

            mTurno = (DateTime)extras.get("TURNO");
            if (mTurno != null){
                mTxtTurno.setText(mTurno.toString("dd-MM-yyyy HH:mm"));
            }else{
                mTxtTurno.setText("");
            }

            //TODO: HACER VARIABLE EL ESTADO PARA QUE ME SIRVA EL DETALLE EN OTRAS ACTIVITYS (EL ESTADO ME REFLEJA EL COLOR DE EL ICON DE LA UBICACION)
            //estado.setText((String)extras.get("ESTADO"));

            tipoTrabajo.setText((String)extras.get("TIPO_TRABAJO"));
            motivo.setText((String)extras.get("MOTIVO"));
            titular.setText((String)extras.get("TITULAR"));
            asociado.setText((String)extras.get("ASOCIADO"));
            domicilio.setText((String)extras.get("DOMICILIO"));
            anexo.setText((String)extras.get("ANEXO"));
            grupo.setText((String)extras.get("GRUPO"));
            ruta.setText((String)extras.get("RUTA"));
            orden.setText((String)extras.get("ORDEN"));
            tipousuario.setText((String)extras.get("TIPO_USUARIO"));
            tarifa.setText((String)extras.get("TARIFA"));
            potenciadeclarada.setText((String)extras.get("POTENCIA_DECLARADA"));
            medidor.setText((String)extras.get("MEDIDOR"));
            marca.setText((String)extras.get("MARCA"));
            modelo.setText((String)extras.get("MODELO"));
            factorM.setText((String)extras.get("FACTOR_M"));
            capacidad.setText((String)extras.get("CAPACIDAD"));
            tension.setText((String)extras.get("TENSION"));
            //TODO: VER
            List<String> lista = extras.getStringArrayList("ETAPAS");
            mEtapaList = (ListView) Arrays.asList(lista);
            EtapasAdapter mEtapasAdapter = new EtapasAdapter(this, (List<Etapa>) mEtapaList);
            mEtapaList.setAdapter(mEtapasAdapter);

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
        mAddTurno = new AddTurno(repository);

        mOrdersDetailPresenter = new OrdersDetailPresenter(mAddOrdersState,mAddTurno,this);
        this.setPresenter(mOrdersDetailPresenter);

        mMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            case R.id.action_turno:
                FragmentTransaction turnoTransaccion = getSupportFragmentManager().beginTransaction();
                turnoTransaccion.addToBackStack(null);
                //if (mTurno.toString()!="" && mTurno != null ){
                if (mTurno != null ){
                    Calendar c = Calendar.getInstance();
                    c.set(mTurno.getYear(), mTurno.getMonthOfYear(), mTurno.getDayOfMonth(),mTurno.getHourOfDay(),mTurno.getMinuteOfHour());
                    DialogFragment asignarTurnoDialog = AsignarTurnoDialog.newInstance(c.get(Calendar.DAY_OF_MONTH),  c.get(Calendar.MONTH),
                            c.get(Calendar.YEAR),c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE) );
                    asignarTurnoDialog.show(turnoTransaccion, "AsignarTurnoDialog");
                }else{
                    DateTime d = new DateTime();
                    DialogFragment asignarTurnoDialog = AsignarTurnoDialog.newInstance(d.getDayOfMonth(),  d.getMonthOfYear(),
                            d.getYear(),d.getHourOfDay(),d.getMinuteOfHour() );
                    asignarTurnoDialog.show(turnoTransaccion, "AsignarTurnoDialog");
                }


                break;
            case R.id.anular_turno:
                FragmentTransaction anularTurnoTransaccion = getSupportFragmentManager().beginTransaction();
                anularTurnoTransaccion.addToBackStack(null);
                Calendar cal = Calendar.getInstance();
                cal.set(mTurno.getYear(), mTurno.getMonthOfYear(), mTurno.getDayOfMonth(),mTurno.getHourOfDay(),mTurno.getMinuteOfHour());
                DialogFragment anularTurnoDialog = AnularTurnoDialog.newInstance(cal.get(Calendar.DAY_OF_MONTH),  cal.get(Calendar.MONTH),
                        cal.get(Calendar.YEAR),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE) );
                anularTurnoDialog.show(anularTurnoTransaccion, "AnularTurnoDialog");
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

        Double mLt,mLn;

        mLt = new Double(mLat.substring(0,7)).doubleValue()*-1;
        mLn = new Double(mLng.substring(0,7)).doubleValue()*-1;

        LatLng mLatLng = new LatLng(mLt,mLn);
        mMap.addMarker(new MarkerOptions()
                .position(mLatLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

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
    public void onPossitiveButtonAsignarDetailClick(String cuadrilla, String numero,String observacion) {
        ArrayList<String> mNumeroOT = new ArrayList<>();
        mNumeroOT.add(numero);
        mOrdersDetailPresenter.asignarOrder(cuadrilla,mNumeroOT,observacion);
    }

    @Override
    public void closeDetail() {
        this.finish();
    }

    @Override
    public void showOrderError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG)
                .show();
    }
    @Override
    public void setPresenter(Presenter presenterDetail) {
        mOrdersDetailPresenter = (OrdersDetailPresenter) presenterDetail;
    }

    @Override
    public void onPossitiveButtonTurnoClick(DateTime turno) {
        mOrdersDetailPresenter.asignarTurno(mNumero, turno);
    }

    @Override
    public void refreshTurno(String turno) {
        if (turno != ""){
            mTxtTurno.setText(new DateTime(turno).toString("dd-MM-yyyy HH:mm"));
            mTurno = new DateTime(turno);
        }else{
            mTxtTurno.setText("");
            mTurno = null;
        }
    }
}
