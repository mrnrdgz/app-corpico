package ar.com.corpico.appcorpico.orders.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

import static android.view.View.GONE;

/**
 * Created by Administrador on 07/01/2017.
 */

public class OrdersFragment extends Fragment implements ar.com.corpico.appcorpico.orders.presentation.View {
    private Presenter mOrdersPresenter;
    private ListView mOrderList;
    private OrdersAdapter mOrdersAdapter;
    private TextView mEmptyView;
    private android.view.View mProgressView;

    public OrdersFragment() {
        // Required empty public constructor
    }

    //Aca va sin parametros o que parametros irian?
    public static OrdersFragment newInstance() {
        OrdersFragment fragment = new OrdersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Toman parámetros
        }
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        android.view.View root = inflater.inflate(R.layout.list_order, container, false);

        // Obtención de referencias UI
        mOrderList = (ListView) root.findViewById(R.id.orders_list);
        mEmptyView = (TextView) root.findViewById(R.id.orders_empty);
        mProgressView = root.findViewById(R.id.orders_progress);

        mOrdersAdapter = new OrdersAdapter(getActivity(), new ArrayList<Order>(0));
        mOrderList.setAdapter(mOrdersAdapter);

        //Infla las cabeceras de OrderList
        LayoutInflater minflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        android.view.View headerView = minflater.inflate(R.layout.list_cabecera_order, null);
        mOrderList.addHeaderView(headerView);

        //Llama al metodo del Presentador para que muestre
        mOrdersPresenter.loadOrderList();

        return root;
    }

    @Override
    public void showOrderList(List<Order> orders) {
        mOrdersAdapter.clear();
        mOrdersAdapter.addAll(orders);
        mOrdersAdapter.notifyDataSetChanged();
    }

    @Override
    public void showOrderError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mOrdersPresenter = presenter;
    }

    @Override
    public void clickbtnMap() {
        mOrdersPresenter.BtnMap();
    }

    @Override
    public void showOrderMsgMap() {
        Toast.makeText(getActivity(), "Bonton Map Presionado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clickbtnFilter() {
        mOrdersPresenter.btnFilter();
    }
    @Override
    public void showOrderMsgFilter() {
        Toast.makeText(getActivity(), "Bonton Aplicar Filtro", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showOrdesEmpty() {
        mOrderList.setEmptyView(mEmptyView);
    }

    @Override
    public void showProgressIndicator(boolean show) {
        mProgressView.setVisibility(show ? android.view.View.VISIBLE : GONE);
    }
}
