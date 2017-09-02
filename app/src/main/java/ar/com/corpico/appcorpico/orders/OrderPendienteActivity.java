package ar.com.corpico.appcorpico.orders;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;


import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ar.com.corpico.appcorpico.NavitationDrawerActivity;
import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.data.OrdersRepository;
import ar.com.corpico.appcorpico.orders.data.FuenteOrdenesServidor;
import ar.com.corpico.appcorpico.orders.data.OrdersSqliteStore;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddOrdersState;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetTipoCuadrilla;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetOrders;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetTipoTrabajo;
import ar.com.corpico.appcorpico.orders.presentation.AsignarAConexionesDialog;
import ar.com.corpico.appcorpico.ordersFilter.OrdersFilterActivity;
import ar.com.corpico.appcorpico.orders.presentation.TipoCuadrillaAdapter;
import ar.com.corpico.appcorpico.orders.presentation.OrdersListFragment;
import ar.com.corpico.appcorpico.orders.presentation.OrdersPresenter;
import ar.com.corpico.appcorpico.orders.presentation.OrdersMapsFragment;
import ar.com.corpico.appcorpico.orders.presentation.TiposCuadrillaToolbarMvp;
import ar.com.corpico.appcorpico.orders.presentation.TiposCuadrillasPresenter;


/**
 * Created by sistemas on 11/04/2017.
 */

public class OrderPendienteActivity extends NavitationDrawerActivity implements
        AsignarAConexionesDialog.OnAsignarAConexionesListener,
        TiposCuadrillaToolbarMvp.View {

    //Key Argumentos
    public static final String ARG_ESTADO = "orders.estado";
    public static final String ARG_SERVICIO = "orders.servicio";
    public static final String ARG_TIPO_CUADRILLA = "orders.tipo_cuadrilla";
    private static final String ARG_TIPOS_TRABAJO_SELECCIONADOS = "orders.tipos_trabajo_seleccionados";
    public static final String ARG_ZONAS_SELECCIONADAS = "orders.zonas_seleccionadas";
    public static final String ARG_FECHA_INICIO = "orders.fecha_inicio";
    public static final String ARG_FECHA_FIN = "orders.fecha_fin";

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
    private AddOrdersState mAddOrdersState;
    private GetTipoTrabajo mGetTipoTrabajo;
    private GetTipoCuadrilla mGetTipoCuadrilla;
    private OrdersPresenter orderPresenter;

    // Lógica
    private boolean mViewMap = true;

    // key respuesta del Intent para el filtrado
    public final static int OPINION_REQUEST_CODE = 1;

    // Variables
    private String mTipoCuadrilla;
    //private List<String> mTipoTrabajo = new ArrayList<>();
    //private List<String> mZona = new ArrayList<>();
    private ArrayList<String> mTiposTrabajoSeleccionados = new ArrayList<>();
    private ArrayList<String> mZonasSeleccionadas = new ArrayList<>();
    private DateTime mFechaInicioSeleccionada;
    private DateTime mFechaFinSeleccionada;
    private String mSearch;

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

        //Carga el Spinner con los Tipos de Cuadrilla para ese servicio
        mTiposCuadrillasPresenter.loadTiposCuadrilla(mServicio);

        // Almacénes
        FuenteOrdenesServidor restStore = new FuenteOrdenesServidor();
        OrdersSqliteStore sqliteStore = new OrdersSqliteStore();

        // Casos de uso
        mGetTipoTrabajo = new GetTipoTrabajo(repository);
        mGetOrders = new GetOrders(repository, mGetTipoTrabajo);
        mAddOrdersState = new AddOrdersState(repository);

        // Presentador
        orderPresenter = new OrdersPresenter(mGetOrders, mAddOrdersState, mOrderView);
        // Seteo el presentador a la vista
        mOrderView.setPresenter(orderPresenter);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void iniciarFragmento() {
        mOrderView = (OrdersListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.orders_view_container);


        if (mOrderView == null) {
            mOrderView = OrdersListFragment.newInstance(
                    mTipoCuadrilla, mEstado, new ArrayList<String>(),
                    new ArrayList<String>(), null, null, mSearch);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.orders_view_container, mOrderView, "OrderView")
                    .commit();

        }
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
                mTiposTrabajoSeleccionados.clear();
                mZonasSeleccionadas.clear();
                mSearch ="";
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                mFechaInicioSeleccionada = new DateTime(c);
                mFechaFinSeleccionada = new DateTime(c);

                //TODO: ACA TENDRIA QUE CREAR UNA INSTANCIA XQ SINO ME QUEDA LA VIEW CON VALORES DE VARIABLES VIEJOS
                //TODO: HACER UN RESPOSITORIO X CADA ENTIDAD....UN ENTIDAD PUEDE TENER VARIAS FUENTES DE DATOS
                //TODO: BUSCAR EJEMPLOS DE COMPARATOR JAVA PARA VER COMO FUNCIONA EL ORDENAMIENTO
                //TODO: AGREGAR LA CLASE QUERY Y LA CLASE SELECTOR (ORDER) MODIFICAR Y PROBAR
                //TODO: LEER LOS LINKS QUE ME PASO EN HANGOUST
                /*orderPresenter.loadOrders(mTipoCuadrilla;, mEstado, new ArrayList<String>(),
                        new ArrayList<String>(), null, null, null, true);*/
                FragmentManager fmList = getSupportFragmentManager();
                FragmentTransaction ftList = getSupportFragmentManager().beginTransaction();
                Fragment fragmentList = fmList.findFragmentById(R.id.orders_view_container);
                if ((fragmentList instanceof OrdersListFragment)) {
                    mOrderView = OrdersListFragment.newInstance(mTipoCuadrilla, mEstado, new ArrayList<String>(), new ArrayList<String>(), null, null, null);
                    ftList.replace(R.id.orders_view_container, mOrderView, "OrderView")
                            //.addToBackStack("OrderView")
                            .commit();
                    orderPresenter = new OrdersPresenter(mGetOrders,mAddOrdersState, mOrderView);
                    mOrderView.setPresenter(orderPresenter);
                }
                FragmentManager fmMap = getSupportFragmentManager();
                FragmentTransaction ftMap = getSupportFragmentManager().beginTransaction();
                Fragment fragmentMap = fmMap.findFragmentById(R.id.orders_view_container);
                if ((fragmentMap instanceof OrdersMapsFragment)) {
                    mOrderMapView = OrdersMapsFragment.newInstance(mTipoCuadrilla, mEstado, new ArrayList<String>(),
                            new ArrayList<String>(), null, null, null);
                    ftMap.replace(R.id.orders_view_container, mOrderMapView, "OrderViewMap")
                            .commit();
                    orderPresenter = new OrdersPresenter(mGetOrders, mAddOrdersState, mOrderMapView);
                    mOrderMapView.setPresenter(orderPresenter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    // Search
                } else {
                    // Do something when there's no input
                    mSearch = "";
                    orderPresenter.loadOrders(mTipoCuadrilla, mEstado, new ArrayList<String>(),
                            new ArrayList<String>(), null, null, mSearch, true);
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
                Intent intent = new Intent(this, OrdersFilterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(ARG_ESTADO, mEstado);
                intent.putExtra(ARG_TIPO_CUADRILLA, mTipoCuadrilla);
                intent.putStringArrayListExtra(ARG_TIPOS_TRABAJO_SELECCIONADOS, (ArrayList<String>) mTiposTrabajoSeleccionados);
                intent.putStringArrayListExtra(ARG_ZONAS_SELECCIONADAS, (ArrayList<String>) mZonasSeleccionadas);
                intent.putExtra(ARG_FECHA_INICIO, mFechaInicioSeleccionada);
                intent.putExtra(ARG_FECHA_FIN, mFechaFinSeleccionada);
                startActivityForResult(intent, OPINION_REQUEST_CODE);
                break;
            case R.id.action_map:
                mViewMap = false;
                invalidateOptionsMenu();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment = fm.findFragmentById(R.id.orders_view_container);
                if (!(fragment instanceof OrdersMapsFragment)) {
                    mOrderMapView = OrdersMapsFragment.newInstance(mTipoCuadrilla, mEstado, new ArrayList<String>(),
                            new ArrayList<String>(), null, null,null);
                    ft.replace(R.id.orders_view_container, mOrderMapView, "OrderViewMap")
                            .commit();
                    orderPresenter = new OrdersPresenter(mGetOrders,mAddOrdersState,mOrderMapView);
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
                    mOrderView = OrdersListFragment.newInstance(mTipoCuadrilla, mEstado, new ArrayList<String>(), new ArrayList<String>(), null, null,null);
                    ft.replace(R.id.orders_view_container, mOrderView, "OrderView")
                            .commit();
                    orderPresenter = new OrdersPresenter(mGetOrders, mAddOrdersState,mOrderView);
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
        orderPresenter.loadOrders(mTipoCuadrilla, mEstado,mTiposTrabajoSeleccionados, mZonasSeleccionadas,
                mFechaInicioSeleccionada, mFechaFinSeleccionada, mSearch,true);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mSearch = intent.getStringExtra(SearchManager.QUERY);
            //TODO: VER SI ACA NO TENGO QUE PONER LAS VARIABLES DE SELECCION --TRABAJO Y ZONAS Y FECHAS....
            orderPresenter.loadOrders(mTipoCuadrilla, mEstado, new ArrayList<String>(), new ArrayList<String>(),
                        null, null, mSearch,true);
        }
    }

    @Override
    public void onPossitiveButtonAsignarClick(String tipoCuadrilla, ArrayList<String> listOrders) {
        orderPresenter.asignarOrder(tipoCuadrilla, listOrders,"");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OPINION_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mTiposTrabajoSeleccionados = data.getStringArrayListExtra(ARG_TIPOS_TRABAJO_SELECCIONADOS);
                mZonasSeleccionadas = data.getStringArrayListExtra(ARG_ZONAS_SELECCIONADAS);
                mFechaInicioSeleccionada = (DateTime) data.getSerializableExtra(ARG_FECHA_INICIO);
                mFechaFinSeleccionada = (DateTime) data.getSerializableExtra(ARG_FECHA_FIN);

                //TODO: VER ACA XQ ESTO PUEDE QUE FUNCIONE PERO DEBO ACTUALIZAR EL VALOR DE LAS VARIABLES DE LA VISTA
                /*if (mViewMap){
                    OrdersListFragment mOrderFragmen = (OrdersListFragment) getSupportFragmentManager().findFragmentById(R.id.orders_view_container);
                    mOrderFragmen.setOrderFilter(mEstado, mTipoTrabajoSelected, mZonaSelected, mFechaDesdeSelected, mFechaFinSeleccionada, null,true);
                }else{
                    OrdersMapsFragment mOrderMapFragment = (OrdersMapsFragment)getSupportFragmentManager().findFragmentById(R.id.orders_view_container);
                    mOrderMapFragment.setOrderFilter(mEstado, mTipoTrabajoSelected, mZonaSelected, mFechaDesdeSelected, mFechaFinSeleccionada, null,true);
                }*/
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment = fm.findFragmentById(R.id.orders_view_container);
                if ((fragment instanceof OrdersMapsFragment)) {
                    mOrderMapView = OrdersMapsFragment.newInstance(mTipoCuadrilla, mEstado, mTiposTrabajoSeleccionados,
                            mZonasSeleccionadas, mFechaInicioSeleccionada, mFechaFinSeleccionada, mSearch);
                    ft.replace(R.id.orders_view_container, mOrderMapView, "OrderViewMap")
                            .commit();
                    orderPresenter = new OrdersPresenter(mGetOrders,mAddOrdersState,mOrderMapView);
                    mOrderMapView.setPresenter(orderPresenter);
                }
                fm = getSupportFragmentManager();
                ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment1 = fm.findFragmentById(R.id.orders_view_container);
                if ((fragment1 instanceof OrdersListFragment)) {
                    //TODO: VER SI ACA LLAMO CON QUERY O CON NULL (XQ SI APRETA EL FILTRAR PODER HACER UN FILTRADO CON BUSCAR....
                    //mOrderView = OrdersListFragment.newInstance(mTipoCuadrilla, mEstado, mTiposTrabajoSeleccionados,mZonasSeleccionadas, mFechaInicioSeleccionada, mFechaFinSeleccionada,null);
                    mOrderView = OrdersListFragment.newInstance(mTipoCuadrilla, mEstado, mTiposTrabajoSeleccionados,
                            mZonasSeleccionadas, mFechaInicioSeleccionada, mFechaFinSeleccionada, mSearch);
                    ft.replace(R.id.orders_view_container, mOrderView, "OrderView")
                            .commit();
                    orderPresenter = new OrdersPresenter(mGetOrders, mAddOrdersState,mOrderView);
                    mOrderView.setPresenter(orderPresenter);
                }
            }
        }
    }

    @Override
    public void showTiposCuadrilla(List<String> tiposCuadrilla) {
        mTipoCuadrillaAdapter.addAll(tiposCuadrilla);
        // Valor inicial
        if (mOrderView==null) {
            mTipoCuadrilla = (String) mSpinner.getItemAtPosition(0);
            iniciarFragmento();
        }
    }

    @Override
    public void setPresenter(TiposCuadrillaToolbarMvp.Presenter presenter) {
        mTiposCuadrillasPresenter = Preconditions.checkNotNull(presenter);
    }
}
