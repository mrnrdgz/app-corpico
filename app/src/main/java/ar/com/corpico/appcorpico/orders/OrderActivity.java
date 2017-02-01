package ar.com.corpico.appcorpico.orders;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import org.joda.time.DateTime;

import ar.com.corpico.appcorpico.NavitationDrawerActivity;
import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.data.OrdersRepository;
import ar.com.corpico.appcorpico.orders.data.OrdersRestStore;
import ar.com.corpico.appcorpico.orders.data.OrdersSqliteStore;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetOrders;
import ar.com.corpico.appcorpico.orders.presentation.OrdersFilterDialog;
import ar.com.corpico.appcorpico.orders.presentation.OrdersFilterDialog.OnFilterDialogListener;
import ar.com.corpico.appcorpico.orders.presentation.OrdersFragment;
import ar.com.corpico.appcorpico.orders.presentation.OrdersPresenter;
import ar.com.corpico.appcorpico.orders.presentation.View;
//, SearchView.OnQueryTextListener
public class OrderActivity extends NavitationDrawerActivity  implements OnFilterDialogListener{
    private View mView;
    private OrdersFilterDialog dialogOrdersFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        OrdersFragment orderView;
        orderView = (OrdersFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_order);

        if (orderView == null) {
            orderView = OrdersFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_order, orderView)
                    .commit();
        }
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
         * <<create>> LoginUser
         */
        GetOrders getOrders = new GetOrders(repository);

        /**
         * <<create>> LoginPresenter
         */
      OrdersPresenter orderPresenter = new OrdersPresenter(getOrders,orderView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ot, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)) {
                    mView.clearOrderSearch();
                }
                else {
                    mView.showOrderSearch(s.toString());
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_search:
                //TODO: Que hace aca? llama a algun intent?
                break;
            case R.id.action_filtrar:
                new OrdersFilterDialog().show(getSupportFragmentManager(), "FilterDialog");
                break;
            case R.id.action_map:
                OrdersFragment mOrderFragmen =(OrdersFragment) getSupportFragmentManager().findFragmentById(R.id.activity_order);
                mOrderFragmen.clickbtnMap();
                break;
            case R.id.action_settings:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPossitiveButtonClick(String estado, String tipo, String sector, DateTime desde, DateTime hasta) {
       OrdersFragment mOrderFragmen =(OrdersFragment) getSupportFragmentManager().findFragmentById(R.id.activity_order);
       mOrderFragmen.setOrderFilter(estado,tipo,sector,desde,hasta);
    }

    @Override
    public void onNegativeButtonClick() {
        Toast.makeText(getApplicationContext(), "CHAU", Toast.LENGTH_SHORT).show();
    }

}
