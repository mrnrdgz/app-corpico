package ar.com.corpico.appcorpico.orders;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
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
import ar.com.corpico.appcorpico.orders.presentation.OrdersFilterDialog;
import ar.com.corpico.appcorpico.orders.presentation.OrdersFilterDialog.OnFilterDialogListener;
import ar.com.corpico.appcorpico.orders.presentation.OrdersFragment;
import ar.com.corpico.appcorpico.orders.presentation.OrdersPresenter;
import ar.com.corpico.appcorpico.orders.presentation.View;

public class OrderActivity extends NavitationDrawerActivity  implements OnFilterDialogListener {
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_search:
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
    public void onPossitiveButtonClick(String estado, String tipo, String sector) {
        //Toast.makeText(getApplicationContext(), "Hola", Toast.LENGTH_SHORT).show();
       OrdersFragment mOrderFragmen =(OrdersFragment) getSupportFragmentManager().findFragmentById(R.id.activity_order);
       mOrderFragmen.setOrderFilter(estado,tipo,sector);
    }

    @Override
    public void onNegativeButtonClick() {
        Toast.makeText(getApplicationContext(), "CHAU", Toast.LENGTH_SHORT).show();
    }
}
