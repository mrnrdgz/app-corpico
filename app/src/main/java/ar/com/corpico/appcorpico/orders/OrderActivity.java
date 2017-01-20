package ar.com.corpico.appcorpico.orders;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import ar.com.corpico.appcorpico.NavitationDrawerActivity;
import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.data.OrdersRepository;
import ar.com.corpico.appcorpico.orders.data.OrdersRestStore;
import ar.com.corpico.appcorpico.orders.data.OrdersSqliteStore;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetOrders;
import ar.com.corpico.appcorpico.orders.presentation.OrdersFragment;
import ar.com.corpico.appcorpico.orders.presentation.OrdersPresenter;
import ar.com.corpico.appcorpico.orders.presentation.View;

public class OrderActivity extends NavitationDrawerActivity  implements OnQueryTextListener, OnActionExpandListener {
    private TextView mSearchView;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        OrdersFragment orderView;
        orderView = (OrdersFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_orderCL);

        if (orderView == null) {
            orderView = OrdersFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_orderCL, orderView)
                    .commit();
        }
        mSearchView = (TextView) findViewById(R.id.search);
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

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(searchItem, this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_filtrar) {
            return true;
        }
        if (id == R.id.action_map) {
            OrdersFragment morderFragmen =(OrdersFragment) getSupportFragmentManager().findFragmentById(R.id.activity_orderCL);
            morderFragmen.clickbtnMap();
            return true;
        }
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        Toast.makeText(getApplicationContext(), "Buscador activado", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        Toast.makeText(getApplicationContext(), "Buscador desactivado", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        mSearchView.setText("Texto buscado\n\n" + s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        mSearchView.setText("Escribiendo texto buscado...\n\n" + s);
        return false;
    }
}
