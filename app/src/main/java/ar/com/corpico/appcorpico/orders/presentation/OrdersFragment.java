package ar.com.corpico.appcorpico.orders.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.OrderPendienteActivity;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

import static android.R.attr.x;
import static android.view.View.GONE;

/**
 * Created by Administrador on 07/01/2017.
 */

public class OrdersFragment extends Fragment implements ar.com.corpico.appcorpico.orders.presentation.View{
    private Presenter mOrdersPresenter;
    private ListView mOrderList;
    private OrdersAdapter mOrdersAdapter;
    private TextView mEmptyView;
    private android.view.View mProgressView;
    private String mOrderType;

    private OrdersAdapter.OnAsignarListener listener;

    public OrdersFragment() {
        // Required empty public constructor
    }

    public void setListener(OrdersAdapter.OnAsignarListener listener) {
        this.listener=listener;
    }

    //Aca va sin parametros o que parametros irian?
    public static OrdersFragment newInstance(String tipo) {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();
        args.putString("tipo", tipo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Toman parámetros
           mOrderType = getArguments().getString("tipo");
        }
    }

    @Override
    public void setLoadOrderList(String tipo) {
        if (tipo.equals("Conexiones")){
            mOrdersPresenter.loadOrderList("Pendiente","Colocacion de Medidor","Todos",null,null,null,true);
        }
        if (tipo.equals("Desconexiones")){
            mOrdersPresenter.loadOrderList("Pendiente","Retiro de Medidor","Todos",null,null,null,true);
        }
        if (tipo.equals("Varios")){
            mOrdersPresenter.loadOrderList("Pendiente","Varios","Todos",null,null,null,true);
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

        mOrderList.setTextFilterEnabled(true);
        mOrdersAdapter = new OrdersAdapter(getActivity(),new ArrayList<Order>(0));
        mOrderList.setAdapter(mOrdersAdapter);

        //SETEA LA ESCUCHA PARA EL BOTON ASIGNAR A CUADRILLA
        mOrdersAdapter.setCustomButtonListner(listener);

        mOrderList.setFocusable(false);

        //mOrderList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //Infla las cabeceras de OrderList
        //LayoutInflater minflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //android.view.View headerView = minflater.inflate(R.layout.list_cabecera_order, null);
        //mOrderList.addHeaderView(headerView);

        /*mOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //mOrderList.setOnItemClickLister()(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                //Order currentOrder = mOrdersAdapter.getItem(i);
                Intent intent = new Intent(getActivity(), Detail_OT.class);

                Order currentOrder = (Order)mOrderList.getAdapter().getItem(i);

                intent.putExtra("Numero",currentOrder.getNumero().toString());
                //intent.putExtra("Fecha",currentOrder.getFecha().toString());
                intent.putExtra("Etapa",currentOrder.getEtapas().toString());
                intent.putExtra("Tipo",currentOrder.getTipo().toString());
                intent.putExtra("Sector",currentOrder.getSector().toString());
                intent.putExtra("Observacion",currentOrder.getObservacion().toString());
                startActivity(intent);
            }
        });*/

        setLoadOrderList(mOrderType);
        return root;
    }

    @Override
    public void showMensage() {
        //Toast.makeText(getActivity(), "ESTO ES UNA PRUEBA", Toast.LENGTH_SHORT).show();
        new AsignarAConexiones().show(getFragmentManager(), "FilterDialog");
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
    public void showOrdesEmpty() {
        mOrderList.setEmptyView(mEmptyView);
    }

    @Override
    public void showProgressIndicator(boolean show) {
        mProgressView.setVisibility(show ? android.view.View.VISIBLE : GONE);
    }

    @Override
    public void setOrderFilter(String estado, String tipo, String sector, DateTime desde, DateTime hasta, String search,Boolean estadoActual) {
        mOrdersPresenter.loadOrderList(estado,tipo,sector,desde,hasta,search,estadoActual);
    }

}
