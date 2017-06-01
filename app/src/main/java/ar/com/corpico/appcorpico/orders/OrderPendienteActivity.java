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

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.NavitationDrawerActivity;
import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.data.OrdersRepository;
import ar.com.corpico.appcorpico.orders.data.OrdersRestStore;
import ar.com.corpico.appcorpico.orders.data.OrdersSqliteStore;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddOrdersState;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetCuadrillaxTipo;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetTipoTrabajo;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetOrders;
import ar.com.corpico.appcorpico.orders.presentation.AsignarAConexiones;
import ar.com.corpico.appcorpico.orders.presentation.TipoTrabajoAdapter;
import ar.com.corpico.appcorpico.orders.presentation.DateDialog;
import ar.com.corpico.appcorpico.orders.presentation.OrdersAdapter;
import ar.com.corpico.appcorpico.orders.presentation.OrdersFilter;
import ar.com.corpico.appcorpico.orders.presentation.OrdersFilterAll;
import ar.com.corpico.appcorpico.orders.presentation.OrdersFragment;
import ar.com.corpico.appcorpico.orders.presentation.OrdersPresenter;
import ar.com.corpico.appcorpico.ordersmaps.OrdersMapsFragment;



/**
 * Created by sistemas on 11/04/2017.
 */

public class OrderPendienteActivity extends NavitationDrawerActivity implements OrdersAdapter.OnAsignarListener, OrdersFilterAll.OnFilterDialogListener,DatePickerDialog.OnDateSetListener,AsignarAConexiones.OnAsignarAConexionesListener,OrdersFilter.OnOrdersFilterListener,OrdersFragment.OnViewActivityListener{
    private String mTipoTrabajo;
    private String mSector;
    private TipoTrabajoAdapter mTipoTrabajoAdapter;
    private OrdersFragment mOrderView;
    private OrdersMapsFragment mOrderMapView;
    private GetOrders mGetOrders;
    private GetTipoTrabajo mGetTipoTrabajo;
    private AddOrdersState mAddOrdersState;
    private GetCuadrillaxTipo mGetCuadrillaxTipo;
    private boolean mViewMap = true;
    private OrdersPresenter orderPresenter;
    private String mEstado;
    private String mServicio;
    private ArrayList<String> mOrdenListNumero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_list_act);
        Bundle bundle = getIntent().getExtras();
        mEstado= bundle.getString("estado");

        mServicio= "Electrico";
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_toolBar);

        /*mTipoTrabajoAdapter = new TipoTrabajoAdapter(this,new ArrayList<Tipo_Cuadrilla>(0));
        orderPresenter.loadCuadrilla(mServicio);
        spinner.setAdapter(mTipoTrabajoAdapter);*/
        //ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_item,getResources().getStringArray(R.array.pendientes_tipos));
        //spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(spinnerAdapter);


        mOrderView = (OrdersFragment) getSupportFragmentManager()
                .findFragmentById(R.id.orders_view_container);

        if (mOrderView == null) {
            mOrderView = OrdersFragment.newInstance(mTipoTrabajo,mEstado,mSector);

            getSupportFragmentManager().beginTransaction()
            .add(R.id.orders_view_container, mOrderView,"OrderView")
            //.addToBackStack(null)
            .commit();

        }

        //SETEA LA LLAMADA PARA QUE LA ACTIVIDAD TENGA COMUNICACION CON ORDERADAPTER
        mOrderView.setListener(this);
        mOrderView.setActivityListener(this);

        /**
         * <<create>> Almacénes
         */
        OrdersRestStore restStore = new OrdersRestStore();
        OrdersSqliteStore sqliteStore = new OrdersSqliteStore();

        /**
         * <<create>> SessionsRepository
         */
        OrdersRepository repository = OrdersRepository.getInstance(restStore);

        /**
         * <<create>> CaseUser
         */
        //TODO: ACA DEBERIA USAR UNA VARIABLE PARA PONER EL CASO DE USO?
        mGetOrders = new GetOrders(repository);
        mAddOrdersState = new AddOrdersState(repository);

        mGetTipoTrabajo = new GetTipoTrabajo(repository);
        mGetCuadrillaxTipo = new GetCuadrillaxTipo(repository);

        /**
         * <<create>> Caso de uso Presenter
         */
        //TODO: ACA DEBERIA USAR UNA VARIABLE PARA PONER EL CASO DE USO?
        orderPresenter = new OrdersPresenter(mGetOrders, mAddOrdersState, mGetTipoTrabajo,mGetCuadrillaxTipo,mOrderView);
        mOrderView.setPresenter(orderPresenter);

       mTipoTrabajoAdapter = new TipoTrabajoAdapter(this,new ArrayList<Tipo_Trabajo>(0));
        //mTipoTrabajoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mTipoTrabajoAdapter);

        orderPresenter.loadTipoTrabajo(mServicio);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                View v = spinner.getSelectedView();
                ((TextView)v).setTextColor(Color.WHITE);
                Tipo_Trabajo item = (Tipo_Trabajo) spinner.getSelectedItem();
                mTipoTrabajo=item.getTipoTrabajo();
                if (mTipoTrabajo != null && mOrderView != null && mViewMap ){
                    mOrderView.setLoadOrderList(mTipoTrabajo);
                }
                if (mTipoTrabajo != null && mOrderMapView != null && mViewMap==false ){
                    mOrderMapView.setLoadOrderList(mTipoTrabajo);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        handleIntent(getIntent());

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
                    mOrderView.setLoadOrderList(mTipoTrabajo);
                }
                return false;
            }
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_filtrar:
                new OrdersFilter().newInstance(mTipoTrabajo,mEstado,mSector).show(getSupportFragmentManager(), "OrderFilterDialog");
                break;
            case R.id.action_map:
                mViewMap=false;
                invalidateOptionsMenu();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                //mOrderMapView = (OrdersMapsFragment) fm.findFragmentById(R.id.orders_view_container);
                Fragment fragment = fm.findFragmentById(R.id.orders_view_container);
                if(!(fragment instanceof OrdersMapsFragment)){
                    // TODO: SI es de estipo, entonces...
                    mOrderMapView = OrdersMapsFragment.newInstance(mTipoTrabajo,mEstado,mSector);
                    ft.replace(R.id.orders_view_container, mOrderMapView,"OrderViewMap")
                            //.addToBackStack("OrderViewMap")
                            .commit();
                    orderPresenter = new OrdersPresenter(mGetOrders, mAddOrdersState, mGetTipoTrabajo,mGetCuadrillaxTipo,mOrderMapView);
                    mOrderMapView.setPresenter(orderPresenter);
                }
                break;
            case R.id.action_list:
                mViewMap=true;
                invalidateOptionsMenu();
                fm = getSupportFragmentManager();
                ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment1 = fm.findFragmentById(R.id.orders_view_container);
                if(!(fragment1 instanceof OrdersFragment)){
                    mOrderView = OrdersFragment.newInstance(mTipoTrabajo,mEstado,mSector);
                    ft.replace(R.id.orders_view_container, mOrderView,"OrderView")
                            //.addToBackStack("OrderView")
                            .commit();
                    orderPresenter = new OrdersPresenter(mGetOrders, mAddOrdersState, mGetTipoTrabajo,mGetCuadrillaxTipo,mOrderView);
                    mOrderView.setPresenter(orderPresenter);
                }
                break;
            case R.id.action_settings:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPossitiveButtonClick(String estado, String tipo, String sector, DateTime desde, DateTime hasta, Boolean estadoActual) {
        OrdersFragment mOrderFragmen = (OrdersFragment) getSupportFragmentManager().findFragmentById(R.id.orders_view_container);
        mOrderFragmen.setOrderFilter(estado, tipo, sector, desde, hasta, null,estadoActual);
    }

    @Override
    public void onFilterPossitiveButtonClick(String estado, String tipo, String sector, DateTime desde, DateTime hasta, Boolean estadoActual) {
        mSector=sector;
        //TODO: VER PORQUE NO ANDA PARA EL MAPVIEW
        if (mViewMap){
            OrdersFragment mOrderFragmen = (OrdersFragment) getSupportFragmentManager().findFragmentById(R.id.orders_view_container);
            mOrderFragmen.setOrderFilter(mEstado, mTipoTrabajo, mSector, desde, hasta, null,estadoActual);
        }else{
            OrdersMapsFragment mOrderMapFragment = (OrdersMapsFragment)getSupportFragmentManager().findFragmentById(R.id.orders_view_container);
            mOrderMapFragment.setOrderFilter(mEstado, mTipoTrabajo, mSector, desde, hasta, null,estadoActual);
        }

    }

    @Override
    public void onNegativeButtonClick() {
        Toast.makeText(getApplicationContext(), "CHAU", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCargarCuadrillasListner(String tipotrabajo) {
        orderPresenter.loadCuadrillasXTipo(tipotrabajo);
    }

    @Override
    public void onFechaTextViewClick() {
        DateDialog fechaDialog = new DateDialog();
        fechaDialog.show(getSupportFragmentManager(),"datePicker");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //TODO: VER EN LA BUSQUEDA LA FEHCA...SI PONGO NULL ESTA CONTROLADO...PERO EN EL TIEMPO...PUEDE TRAER.
            //MUCHOS REGISTROS...COMO PODRIAMOS CONTROLAR ESO?
            if (mViewMap){
                OrdersFragment mOrderFragmen = (OrdersFragment) getSupportFragmentManager().findFragmentById(R.id.orders_view_container);
                mOrderFragmen.setOrderFilter(mEstado, mTipoTrabajo, mSector, null, null, query,true);
            }else{
                OrdersMapsFragment mOrderMapFragment = (OrdersMapsFragment)getSupportFragmentManager().findFragmentById(R.id.orders_view_container);
                mOrderMapFragment.setOrderFilter(mEstado, mTipoTrabajo, mSector, null, null, query,true);
            }
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        OrdersFilterAll fragment = (OrdersFilterAll) getSupportFragmentManager().findFragmentByTag("FilterDialog");
        if (fragment != null) {
            fragment.setDateDesdeView(i, i1, i2);
        }
    }

    @Override
    public void onButtonClickListner(ArrayList<String> numero) {
        //TODO: HACER EL DIALOG PARA ASIGNAR EL TRABAJO A LA CUADRILLA ESTO DE ABAJO ES UNA Prueba
         /*   FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag("AsignarconexionDialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);*/
            mOrdenListNumero = numero;
            orderPresenter.loadCuadrillasXTipo(mTipoTrabajo);
           /* DialogFragment newFragment = AsignarAConexiones.newInstance(mTipoTrabajo,numero);
            newFragment.show(ft, "AsignarconexionDialog");*/
    }

    @Override
    public void onPossitiveButtonAsignarClick(String cuadrilla, ArrayList<String> numero) {
        //TODO: HACER LA LLAMADA A LA VISTA PARA LLAMAR AL PRESENTARODOR Y EL CASO DE USO PARA ASIGNARACUADRILLA
        mOrderView.setAsignarOrder(cuadrilla,numero);
    }
    @Override
    public void onNegativeButtonAsignarClick() {
        Toast.makeText(this, "BOTON NEGATIVO", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(mViewMap) {
            MenuItem item = menu.findItem(R.id.action_list);
            item.setVisible(false);
            item = menu.findItem(R.id.action_map);
            item.setVisible(true);
        }else{
            MenuItem item = menu.findItem(R.id.action_list);
            item.setVisible(true);
            item = menu.findItem(R.id.action_map);
            item.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

     @Override
    public void onShowTipoTrabajo(List<Tipo_Trabajo> tipoTrabajo) {
        mTipoTrabajoAdapter.clear();
        mTipoTrabajoAdapter.addAll(tipoTrabajo);
        mTipoTrabajoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowTipoCuadrilla(List<Tipo_Cuadrilla> listtipocuadrilla) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag("AsignarconexionDialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

        DialogFragment newFragment = AsignarAConexiones.newInstance(listtipocuadrilla,mOrdenListNumero);
        newFragment.show(ft, "AsignarconexionDialog");
    }

}
