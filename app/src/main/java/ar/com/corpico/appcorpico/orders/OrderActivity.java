package ar.com.corpico.appcorpico.orders;

import android.os.Bundle;

import ar.com.corpico.appcorpico.NavitationDrawerActivity;
import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.data.OrdersRepository;
import ar.com.corpico.appcorpico.orders.data.OrdersRestStore;
import ar.com.corpico.appcorpico.orders.data.OrdersSqliteStore;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetOrders;
import ar.com.corpico.appcorpico.orders.presentation.OrdersFragment;
import ar.com.corpico.appcorpico.orders.presentation.OrdersPresenter;

public class OrderActivity extends NavitationDrawerActivity {

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

        /**
         * <<create>> Almacénes
         */
        OrdersRestStore restStore = new OrdersRestStore();
        OrdersSqliteStore sqliteStore = new OrdersSqliteStore();

        /**
         * <<create>> SessionsRepository
         */
        OrdersRepository repository = new OrdersRepository(restStore);

        /**
         * <<create>> LoginUser
         */
        GetOrders getOrders = new GetOrders(repository);

        /**
         * <<create>> LoginPresenter
         */
      OrdersPresenter orderPresenter = new OrdersPresenter(getOrders,orderView);


    }
}
