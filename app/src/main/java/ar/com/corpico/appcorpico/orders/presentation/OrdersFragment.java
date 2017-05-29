package ar.com.corpico.appcorpico.orders.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;

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
    private String mTipoTrabajo;
    private String mEstado;
    private Activity mActivity;
    private ArrayList<String> list_items = new ArrayList<>();
    private int count;
    private boolean hideAsignarButton;

    private OrdersAdapter.OnAsignarListener listener;

    public OrdersFragment() {
        // Required empty public constructor
    }

   public interface OnViewActivityListener {
        void onShowTipoTrabajo(List<Tipo_Trabajo> listorder);// Eventos Botón Positivo
    }

    private OnViewActivityListener listenerViewActivity;


    public void setActivityListener(OnViewActivityListener listener) {
        this.listenerViewActivity=listener;
    }


    public void setListener(OrdersAdapter.OnAsignarListener listener) {
        this.listener=listener;
    }

    //Aca va sin parametros o que parametros irian?
    public static OrdersFragment newInstance(String tipotrabajo,String estado) {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();
        // TODO: Pasar los demás parámetros de la Action Bar
        args.putString("tipotrabajo", tipotrabajo);
        args.putString("estado", estado);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Toman parámetros
           mTipoTrabajo = getArguments().getString("tipotrabajo");
           mEstado= getArguments().getString("estado");
           Spinner activitySpinner = (Spinner) getActivity().findViewById(R.id.spinner_toolBar);
           /*if (mTipoTrabajo.equals("Conexiones")){*/
               activitySpinner.setSelection(0);
          /* }
           if (mCuadrilla.equals("Desconexiones")){
               activitySpinner.setSelection(1);
           }
           if (mCuadrilla.equals("Varios")){
               activitySpinner.setSelection(2);
           }*/
        }
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        android.view.View root = inflater.inflate(R.layout.orders_list_frag, container, false);

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

        // Tomar referencia de la Actividad contenedora del fragmento para encontrar la toolbar
        // con el indicador que tiene en su layout
        Toolbar activityToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        mOrderList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mOrderList.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                if (checked){
                    count++;
                    mOrdersAdapter.setNewSelection(position);
                   // mode.setTitle(count+" Seleccionadas");
                    Order item= (Order) mOrderList.getAdapter().getItem(position);
                    String numero = item.getNumero();
                    list_items.add(numero);
                }else{
                    count--;
                    /*if (count==0){

                    }*/
                    mOrdersAdapter.removeSelection(position);
                    //mode.setTitle(count+" Seleccionadas");
                    Order item= (Order) mOrderList.getAdapter().getItem(position);
                    String numero = item.getNumero();
                    list_items.remove(numero);
                }
                mode.setTitle(mOrdersAdapter.getSelectionCount() + " Seleccionadas");

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                count = 0;
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu_opendientes, menu);
                mOrdersAdapter.hideAsignarButton();
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                //mOrdersAdapter.hideAsignarButton();
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.select_all:
                        count=0;
                        mOrdersAdapter.clearSelection();
                        list_items.clear();
                        for ( int i=0; i < mOrderList.getAdapter().getCount(); i++) {
                            mOrderList.setItemChecked(i, true);
                        }
                        return true;
                    case R.id.action_asignaracuadrilla:
                        mOrdersPresenter.asignarOrder(mTipoTrabajo,list_items);
                        count=0;
                        mOrdersAdapter.clearSelection();
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mOrdersAdapter.clearSelection();
                mOrdersAdapter.showAsignarButton();

            }
        });

        //Infla las cabeceras de OrderList
        //LayoutInflater minflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //android.view.View headerView = minflater.inflate(R.layout.list_cabecera_order, null);
        //mOrderList.addHeaderView(headerView);

        mOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //mOrderList.setOnItemClickLister()(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                //Order currentOrder = mOrdersAdapter.getItem(i);
                Intent intent = new Intent(getActivity(), Detail_OT.class);

                Order currentOrder = (Order)mOrderList.getAdapter().getItem(i);

                intent.putExtra("Numero",currentOrder.getNumero().toString());
                //intent.putExtra("Fecha",currentOrder.getFecha().toString());
                intent.putExtra("Etapa",currentOrder.getEtapas().toString());
                intent.putExtra("Tipo_Trabajo",currentOrder.getTipo().toString());
                intent.putExtra("Sector",currentOrder.getSector().toString());
                intent.putExtra("Observacion",currentOrder.getObservacion().toString());
                startActivity(intent);
            }
        });

        setLoadOrderList(mTipoTrabajo);
        return root;
    }

    @Override
    public void setLoadOrderList(String tipotrabajo) {
        mTipoTrabajo=tipotrabajo;
        mOrdersPresenter.loadOrderList(mEstado,mTipoTrabajo,"Todos",null,null,null,true);
    }
    @Override
    public void setAsignarOrder(String cuadrilla, List<String> listorder) {
        mOrdersPresenter.asignarOrder(cuadrilla,listorder);
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

    @Override
    public void showTipoTrabajoList(List<Tipo_Trabajo> listorder) {
        listenerViewActivity.onShowTipoTrabajo(listorder);
    }
}
