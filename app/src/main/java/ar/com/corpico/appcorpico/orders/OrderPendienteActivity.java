package ar.com.corpico.appcorpico.orders;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;


import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.NavitationDrawerActivity;
import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.data.OrdersRepository;
import ar.com.corpico.appcorpico.orders.data.FuenteOrdenesServidor;
import ar.com.corpico.appcorpico.orders.data.OrdersSqliteStore;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddOrdersState;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetCuadrillaxTipo;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetTipoCuadrilla;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetOrders;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetTipoTrabajo;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetZonas;
import ar.com.corpico.appcorpico.orders.presentation.AsignarAConexionesDialog;
import ar.com.corpico.appcorpico.orders.presentation.OrdersFilterActivity;
import ar.com.corpico.appcorpico.orders.presentation.TipoCuadrillaAdapter;
import ar.com.corpico.appcorpico.orders.presentation.OrdersAdapter;
import ar.com.corpico.appcorpico.orders.presentation.OrdersFilter;
import ar.com.corpico.appcorpico.orders.presentation.OrdersFilterAll;
import ar.com.corpico.appcorpico.orders.presentation.OrdersListFragment;
import ar.com.corpico.appcorpico.orders.presentation.OrdersPresenter;
import ar.com.corpico.appcorpico.orders.presentation.TipoTrabajoDialog;
import ar.com.corpico.appcorpico.orders.presentation.OrdersMapsFragment;
import ar.com.corpico.appcorpico.orders.presentation.TiposCuadrillaToolbarMvp;
import ar.com.corpico.appcorpico.orders.presentation.TiposCuadrillasPresenter;


/**
 * Created by sistemas on 11/04/2017.
 */

public class OrderPendienteActivity extends NavitationDrawerActivity implements
        AsignarAConexionesDialog.OnAsignarAConexionesListener,
        OrdersAdapter.OnAsignarListener,
        OrdersListFragment.OnViewActivityListener,
        TiposCuadrillaToolbarMvp.View {

    //Key Argumentos
    public static final String ARG_ESTADO = "orders.estado";
    public static final String ARG_SERVICIO = "orders.servicio";

    //Argumentos
    private String mEstado;
    private String mServicio;

    //Views
    private TipoCuadrillaAdapter mTipoCuadrillaAdapter;

    //Dependencias
    private TiposCuadrillaToolbarMvp.Presenter mTiposCuadrillasPresenter;
    private OrdersListFragment mOrderView;
    private OrdersMapsFragment mOrderMapView;
    private GetOrders mGetOrders;
    private GetTipoTrabajo mGetTipoTrabajo;
    private GetTipoCuadrilla mGetTipoCuadrilla;
    private OrdersPresenter orderPresenter;

    // Lógica
    private boolean mViewMap = true;

    // key respuesta del Intent para el filtrado
    public final static int OPINION_REQUEST_CODE = 1;

    // Variables
    private String mTipoCuadrilla;
    private List<String> mTipoTrabajo = new ArrayList<>();
    private List<String> mZona = new ArrayList<>();
    private List<String> mTipoTrabajoSelected = new ArrayList<>();
    private List<String> mZonaSelected = new ArrayList<>();
    private DateTime mFechaDesdeSelected;
    private DateTime mFechaHastaSelected;
    private String mQuery;

    private ArrayList<String> mOrdenListNumero;
    private Spinner mSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_list_act);

        // Argumentos
        Bundle args = getIntent().getExtras();
        mEstado = args.getString(ARG_ESTADO);
        mServicio = args.getString(ARG_SERVICIO);

        // UI
        mSpinner = (Spinner) findViewById(R.id.spinner_toolBar);

        // Sets
        setUpSpinner();

        // TODO: Crear repositorios de tipos de cuadrillas
        OrdersRepository repository = OrdersRepository.getInstance(new FuenteOrdenesServidor());
        mGetTipoCuadrilla = new GetTipoCuadrilla(repository);
        mTiposCuadrillasPresenter = new TiposCuadrillasPresenter(this, mGetTipoCuadrilla);

        // Búsqueda
        handleIntent(getIntent());

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Carga el Spinner con los Tipos de Cuadrilla para ese servicio
        mTiposCuadrillasPresenter.loadTiposCuadrilla(mServicio);
    }

    private void iniciarFragmento() {
        mOrderView = (OrdersListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.orders_view_container);


        if (mOrderView == null) {
            mOrderView = OrdersListFragment.newInstance(
                    mTipoCuadrilla, mEstado, new ArrayList<String>(),
                    new ArrayList<String>(), null, null, mQuery);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.orders_view_container, mOrderView, "OrderView")
                    .commit();
             }
            //SETEA LA LLAMADA PARA QUE LA ACTIVIDAD TENGA COMUNICACION CON ORDERADAPTER
            mOrderView.setListener(this);
            mOrderView.setActivityListener(this);

            // Almacénes
            FuenteOrdenesServidor restStore = new FuenteOrdenesServidor();
            OrdersSqliteStore sqliteStore = new OrdersSqliteStore();

            // Repositorios
            OrdersRepository repository = OrdersRepository.getInstance(restStore);

            // Casos de uso
            mGetTipoTrabajo = new GetTipoTrabajo(repository);
            mGetOrders = new GetOrders(repository, mGetTipoTrabajo);

            // Presentador
            orderPresenter = new OrdersPresenter(mGetOrders, mOrderView);
            //mOrderView.setPresenter(orderPresenter);
    }

    private void setUpSpinner() {

        // Adaptador
        mTipoCuadrillaAdapter = new TipoCuadrillaAdapter(this, new ArrayList<String>(0));
        mTipoCuadrillaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mTipoCuadrillaAdapter);


        // Eventos
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                View v = mSpinner.getSelectedView();
                ((TextView) v).setTextColor(Color.WHITE);
                mTipoCuadrilla = (String) mSpinner.getSelectedItem();
                mOrderView.setLoadOrders(mTipoCuadrilla, mEstado, new ArrayList<String>(), new ArrayList<String>(), null, null,mQuery,true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    // Todo: ver
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_otpendientes, menu);

        // Associate searchable configuratio with the SearchView
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(menuItem);

        // Conexión entre SearchView y searchable.xml
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        // Personalización del SearchView
        searchView.setSubmitButtonEnabled(true);

        // TODO: Entender y probar este código
       /* MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(searchItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return false;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        mOrderView.setLoadOrderList(mCuadrilla);
                        return false;
                    }
                }
                    );*/

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    // Search
                } else {
                    // Do something when there's no input
                    //mOrderView.setLoadOrderList(mTipoCuadrilla);
                }
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_filtrar:
                //mTipoTrabajo = mOrderView.getTipoTrabajo();
                //mZona = mOrderView.getZona();
                //TODO: LO MISMO TENGO Q HACER CON LAS ZONAS
                //new OrdersFilter().newInstance((ArrayList<String>) mTipoTrabajo,mEstado,mZona).show(getSupportFragmentManager(), "OrderFilterDialog");
                Intent intent = new Intent(this, OrdersFilterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("ESTADO", mEstado);
                intent.putStringArrayListExtra("TIPO_TRABAJO", (ArrayList<String>) mTipoTrabajo);
                intent.putStringArrayListExtra("TIPO_TRABAJO_SELECTED", (ArrayList<String>) mTipoTrabajoSelected);
                intent.putStringArrayListExtra("ZONA", (ArrayList<String>) mZona);
                intent.putStringArrayListExtra("ZONA_SELECTED", (ArrayList<String>) mZonaSelected);
                intent.putExtra("FECHA_DESDE_SELECTED", mFechaDesdeSelected);
                intent.putExtra("FECHA_HASTA_SELECTED", mFechaHastaSelected);
                //startActivity(intent);
                startActivityForResult(intent, OPINION_REQUEST_CODE);
                break;
            case R.id.action_map:
                mViewMap = false;
                invalidateOptionsMenu();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                //mOrderMapView = (OrdersMapsFragment) fm.findFragmentById(R.id.orders_view_container);
                Fragment fragment = fm.findFragmentById(R.id.orders_view_container);
                if (!(fragment instanceof OrdersMapsFragment)) {
                    // TODO: SI es de estipo, entonces...
                    mOrderMapView = OrdersMapsFragment.newInstance(mTipoCuadrilla, mEstado, mZona, mFechaDesdeSelected, mFechaHastaSelected);
                    ft.replace(R.id.orders_view_container, mOrderMapView, "OrderViewMap")
                            //.addToBackStack("OrderViewMap")
                            .commit();
                    //orderPresenter = new OrdersPresenter(mGetOrders, mAddOrdersState, mGetTipoCuadrilla,mGetCuadrillaxTipo,mGetTipoTrabajo,mGetZona,mOrderMapView);
                    mOrderMapView.setPresenter(orderPresenter);
                }
                break;
            case R.id.action_list:
                mViewMap = true;
                invalidateOptionsMenu();
                fm = getSupportFragmentManager();
                ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment1 = fm.findFragmentById(R.id.orders_view_container);
                if (!(fragment1 instanceof OrdersListFragment)) {
                    //mOrderView = OrdersListFragment.newInstance(mTipoCuadrilla,mEstado, new ArrayList<>(), mZona,mFechaDesdeSelected,mFechaHastaSelected);
                    ft.replace(R.id.orders_view_container, mOrderView, "OrderView")
                            //.addToBackStack("OrderView")
                            .commit();
                    //orderPresenter = new OrdersPresenter(mGetOrders, mAddOrdersState, mGetTipoCuadrilla,mGetCuadrillaxTipo,mGetTipoTrabajo,mGetZona,mOrderView);
                    mOrderView.setPresenter(orderPresenter);
                }
                break;
            case R.id.action_settings:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        //mOrderView = OrdersListFragment.newInstance(mTipoCuadrilla, mEstado, new ArrayList<String>(), new ArrayList<String>(), null, null,mQuery);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            if (mViewMap) {
                mOrderView.setLoadOrders(mTipoCuadrilla, mEstado, new ArrayList<String>(), new ArrayList<String>(), null, null,mQuery,true);
                //mOrderView = OrdersListFragment.newInstance(mTipoCuadrilla, mEstado, new ArrayList<String>(), new ArrayList<String>(), null, null,mQuery);
            } else {
                //Todo: ver
                OrdersMapsFragment mOrderMapFragment = (OrdersMapsFragment) getSupportFragmentManager().findFragmentById(R.id.orders_view_container);
                //mOrderMapFragment.setOrderFilter(mEstado, mTipoTrabajo, mZona, null, null, query,true);
                mOrderMapFragment.setOrderFilter(mEstado, mTipoTrabajo, mZona, mFechaDesdeSelected, mFechaHastaSelected, mQuery, true);
            }
        }
    }

    @Override
    public void onButtonClickListner(ArrayList<String> numero) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("AsignarconexionDialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = AsignarAConexionesDialog.newInstance(mTipoCuadrilla, numero);
        newFragment.show(ft, "AsignarconexionDialog");
    }

    @Override
    public void onPossitiveButtonAsignarClick(String cuadrilla, ArrayList<String> numero) {
        //TODO: HACER LA LLAMADA A LA VISTA PARA LLAMAR AL PRESENTARODOR Y EL CASO DE USO PARA ASIGNARACUADRILLA
        mOrderView.setAsignarOrder(cuadrilla, numero);
    }

    @Override
    public void onNegativeButtonAsignarClick() {
        Toast.makeText(this, "BOTON NEGATIVO", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mViewMap) {
            MenuItem item = menu.findItem(R.id.action_list);
            item.setVisible(false);
            item = menu.findItem(R.id.action_map);
            item.setVisible(true);
        } else {
            MenuItem item = menu.findItem(R.id.action_list);
            item.setVisible(true);
            item = menu.findItem(R.id.action_map);
            item.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onAsignarCuadrillaContextual(String cuadrilla, ArrayList<String> numeros) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("AsignarconexionDialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = AsignarAConexionesDialog.newInstance(mTipoCuadrilla, numeros);
        newFragment.show(ft, "AsignarconexionDialog");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OPINION_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mTipoTrabajoSelected = data.getStringArrayListExtra("TIPO_TRABAJO_SELECTED");
                mZonaSelected = data.getStringArrayListExtra("ZONA_SELECTED");
                mFechaDesdeSelected = (DateTime) data.getSerializableExtra("FECHA_DESDE_SELECTED");
                mFechaHastaSelected = (DateTime) data.getSerializableExtra("FECHA_HASTA_SELECTED");

                //TODO: VER ACA XQ ESTO PUEDE QUE FUNCIONE PERO DEBO ACTUALIZAR EL VALOR DE LAS VARIABLES DE LA VISTA
                /*if (mViewMap){
                    OrdersListFragment mOrderFragmen = (OrdersListFragment) getSupportFragmentManager().findFragmentById(R.id.orders_view_container);
                    mOrderFragmen.setOrderFilter(mEstado, mTipoTrabajoSelected, mZonaSelected, mFechaDesdeSelected, mFechaHastaSelected, null,true);
                }else{
                    OrdersMapsFragment mOrderMapFragment = (OrdersMapsFragment)getSupportFragmentManager().findFragmentById(R.id.orders_view_container);
                    mOrderMapFragment.setOrderFilter(mEstado, mTipoTrabajoSelected, mZonaSelected, mFechaDesdeSelected, mFechaHastaSelected, null,true);
                }*/
            }
        }
    }

    @Override
    public void showTiposCuadrilla(List<String> tiposCuadrilla) {
        mTipoCuadrillaAdapter.addAll(tiposCuadrilla);

        // Valor inicial
        if (mOrderView==null){
            mTipoCuadrilla = (String) mSpinner.getItemAtPosition(0);
            iniciarFragmento();
        }
    }

    @Override
    public void setPresenter(TiposCuadrillaToolbarMvp.Presenter presenter) {
        mTiposCuadrillasPresenter = Preconditions.checkNotNull(presenter);
    }
}
