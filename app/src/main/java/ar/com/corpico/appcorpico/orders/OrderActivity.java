package ar.com.corpico.appcorpico.orders;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.widget.DatePicker;
import android.widget.Toast;
import org.joda.time.DateTime;

import java.util.ArrayList;

import ar.com.corpico.appcorpico.NavitationDrawerActivity;
import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.data.OrdersRepository;
import ar.com.corpico.appcorpico.orders.data.FuenteOrdenesServidor;
import ar.com.corpico.appcorpico.orders.data.OrdersSqliteStore;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddOrdersState;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetCuadrillaxTipo;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetTipoCuadrilla;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetOrders;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetTipoTrabajo;
import ar.com.corpico.appcorpico.orders.presentation.DateDialog;
import ar.com.corpico.appcorpico.orders.presentation.OrdersFilterAll;
import ar.com.corpico.appcorpico.orders.presentation.OrdersFilterAll.OnFilterAllDialogListener;
import ar.com.corpico.appcorpico.orders.presentation.OrdersFragment;
import ar.com.corpico.appcorpico.orders.presentation.OrdersPresenter;

public class OrderActivity extends NavitationDrawerActivity implements OnFilterAllDialogListener,DatePickerDialog.OnDateSetListener {
    private OrdersFilterAll dialogOrdersFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_list_act);

        OrdersFragment orderView;

        orderView = (OrdersFragment) getSupportFragmentManager()
                .findFragmentById(R.id.orders_view_container);

        if (orderView == null) {
            orderView = OrdersFragment.newInstance("Todas","Todos","Todos");

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.orders_view_container, orderView)
                    .commit();
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
         * <<create>> LoginUser
         */
        GetOrders getOrders = new GetOrders(repository);
        AddOrdersState addOrderState = new AddOrdersState(repository);
        GetTipoCuadrilla getTipoCuadrilla = new GetTipoCuadrilla(repository);
        GetCuadrillaxTipo getCuadrillaxTipo= new GetCuadrillaxTipo(repository);
        GetTipoTrabajo mGetTipoTrabajo = new GetTipoTrabajo(repository);

        /**
         * <<create>> LoginPresenter
         */
        //OrdersPresenter orderPresenter = new OrdersPresenter(getOrders,addOrderState, getTipoCuadrilla,getCuadrillaxTipo,mGetTipoTrabajo,orderView);

        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ot, menu);

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

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_filtrar:
                new OrdersFilterAll().show(getSupportFragmentManager(), "FilterDialog");
                break;
            case R.id.action_map:
                OrdersFragment mOrderFragmen = (OrdersFragment) getSupportFragmentManager().findFragmentById(R.id.orders_view_container);
                //mOrderFragmen.clickbtnMap();
                break;
            case R.id.action_settings:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPossitiveButtonClick(String estado, ArrayList<String> tipo, String sector, DateTime desde, DateTime hasta, Boolean estadoActual) {
        OrdersFragment mOrderFragmen = (OrdersFragment) getSupportFragmentManager().findFragmentById(R.id.orders_view_container);
        mOrderFragmen.setOrderFilter(estado, tipo, sector, desde, hasta, null,estadoActual);
    }

    @Override
    public void onNegativeButtonClick() {
        Toast.makeText(getApplicationContext(), "CHAU", Toast.LENGTH_SHORT).show();
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
            OrdersFragment mOrderFragmen = (OrdersFragment) getSupportFragmentManager().findFragmentById(R.id.orders_view_container);
            //mOrderFragmen.setOrderFilter("Todos", "Todos", "Todos", null, null, query,false);
        }
    }

   @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        OrdersFilterAll fragment = (OrdersFilterAll) getSupportFragmentManager().findFragmentByTag("FilterDialog");
        if (fragment != null) {
            fragment.setDateDesdeView(i, i1, i2);
        }
    }
}
