package ar.com.corpico.appcorpico.orders.presentation;

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
import ar.com.corpico.appcorpico.ordersDetail.presentation.OrderDetailActivity;

import static android.view.View.GONE;

/**
 * Fragmento para lista de ordenes
 */

public class OrdersListFragment extends Fragment implements OrdersListMvp.View {

    // Keys de argumentos
    public static final String ARG_TIPO_CUADRILLA = "orders.tipo_cuadrilla";
    public static final String ARG_ESTADO = "orders.estado";
    public static final String ARG_ZONAS_SELECCIONADAS = "orders.zonas_seleccionadas";
    public static final String ARG_FECHA_INICIO = "orders.fecha_inicio";
    public static final String ARG_FECHA_FIN = "orders.fecha_fin";
    private static final String ARG_TIPOS_TRABAJO_SELECCIONADOS = "orders.tipos_trabajo_seleccionados";


    // Dependencias
    private OrdersListMvp.Presenter mOrdersPresenter;


    // Views
    private ListView mOrderList;
    private OrdersAdapter mOrdersAdapter;
    private TextView mEmptyView;
    private android.view.View mProgressView;


    // Argumentos
    private String mTipoCuadrilla;
    private String mEstado;
    private List<String> mTiposTrabajoSeleccionados;
    private List<String> mZonasSeleccionadas;
    private DateTime mFechaInicio;
    private DateTime mFechaFin;


    private ArrayList<String> list_items = new ArrayList<>();

    // Lógica
    private int count;

    private OrdersAdapter.OnAsignarListener listener;


    public OrdersListFragment() {
        // Required empty public constructor
    }

    public static OrdersListFragment newInstance(
            String tipoCuadrilla, String estado,
            ArrayList<String> tiposTrabajoSeleccionados, List<String> zonasSeleccionadas,
            DateTime fechaInicio, DateTime fechaFin) {

        OrdersListFragment fragment = new OrdersListFragment();
        Bundle args = new Bundle();

        args.putString(ARG_TIPO_CUADRILLA, tipoCuadrilla);
        args.putString(ARG_ESTADO, estado);
        args.putStringArrayList(ARG_ZONAS_SELECCIONADAS, (ArrayList<String>) zonasSeleccionadas);
        args.putStringArrayList(ARG_TIPOS_TRABAJO_SELECCIONADOS, tiposTrabajoSeleccionados);
        args.putSerializable(ARG_FECHA_INICIO, fechaInicio);
        args.putSerializable(ARG_FECHA_FIN, fechaFin);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();

        if (arguments != null) {
            // Toman parámetros
            mTipoCuadrilla = arguments.getString(ARG_TIPO_CUADRILLA);
            mEstado = arguments.getString(ARG_ESTADO);
            mTiposTrabajoSeleccionados = arguments.getStringArrayList(ARG_TIPOS_TRABAJO_SELECCIONADOS);
            mZonasSeleccionadas = arguments.getStringArrayList(ARG_ZONAS_SELECCIONADAS);
            mFechaInicio = (DateTime) arguments.get(ARG_FECHA_INICIO);
            mFechaFin = (DateTime) arguments.get(ARG_FECHA_FIN);
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
        mOrdersAdapter = new OrdersAdapter(getActivity(), new ArrayList<Order>(0));
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
                if (checked) {
                    count++;
                    mOrdersAdapter.setNewSelection(position);
                    Order item = (Order) mOrderList.getAdapter().getItem(position);
                    String numero = item.getNumero();
                    list_items.add(numero);
                } else {
                    count--;
                    mOrdersAdapter.removeSelection(position);
                    Order item = (Order) mOrderList.getAdapter().getItem(position);
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
                switch (item.getItemId()) {
                    case R.id.select_all:
                        count = 0;
                        mOrdersAdapter.clearSelection();
                        list_items.clear();
                        for (int i = 0; i < mOrderList.getAdapter().getCount(); i++) {
                            mOrderList.setItemChecked(i, true);
                        }
                        return true;
                    case R.id.action_asignaracuadrilla:
                        listenerViewActivity.onAsignarCuadrillaContextual(mTipoCuadrilla, list_items);
                        /*mOrdersPresenter.asignarOrder(mTipoCuadrilla,list_items,"");
                        count=0;
                        mOrdersAdapter.clearSelection();*/
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
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);

                Order currentOrder = (Order) mOrderList.getAdapter().getItem(i);

                intent.putExtra("TIPO_CUADRILLA", mTipoCuadrilla);
                intent.putExtra("NUMERO", currentOrder.getNumero().toString());
                intent.putExtra("FECHA", currentOrder.getFechaSolicitud().toString());
                intent.putExtra("ETAPA", currentOrder.getEtapas().toString());
                intent.putExtra("TIPO_TRABAJO", currentOrder.getTipo_Trabajo().toString());
                intent.putExtra("MOTIVO", currentOrder.getMotivo().toString());
                intent.putExtra("TITULAR", currentOrder.getTitular().toString());
                intent.putExtra("ASOCIADO", currentOrder.getAsociado().toString() + "/" + currentOrder.getSuministro().toString());
                intent.putExtra("DOMICILIO", currentOrder.getDomicilio().toString());
                intent.putExtra("ANEXO", currentOrder.getAnexo().toString());
                intent.putExtra("TIPO_USUARIO", currentOrder.getTipo_Usuario().toString());
                intent.putExtra("TARIFA", currentOrder.getTarifa().toString());
                intent.putExtra("POTENCIA_DECLARADA", currentOrder.getPotencia_Declarada().toString());
                intent.putExtra("MEDIDOR", currentOrder.getMedidor().toString());
                intent.putExtra("MARCA", currentOrder.getMarca().toString());
                intent.putExtra("MODELO", currentOrder.getModelo().toString());
                intent.putExtra("FACTOR_M", currentOrder.getFactorM().toString());
                intent.putExtra("CAPACIDAD", currentOrder.getCapacidad().toString());
                intent.putExtra("TENSION", currentOrder.getTension().toString());

                intent.putExtra("LAT", currentOrder.getLatitud().toString());
                intent.putExtra("LNG", currentOrder.getLongitud().toString());
                intent.putExtra("OBSERVACION", currentOrder.getObservacion().toString());
                startActivity(intent);
            }
        });

        setLoadOrderList(mTipoCuadrilla);
        return root;
    }

    public interface OnViewActivityListener {
        void onShowTipoCuadrilla(List<Tipo_Cuadrilla> listorder);

        //void onShowTipoCuadrilla(List<Tipo_Cuadrilla> listtipocuadrilla);
        void onAsignarCuadrillaContextual(String cuadrilla, ArrayList<String> numeros);


    }

    private OnViewActivityListener listenerViewActivity;

    public void setActivityListener(OnViewActivityListener listener) {
        this.listenerViewActivity = listener;
    }

    public void setListener(OrdersAdapter.OnAsignarListener listener) {
        this.listener = listener;
    }


    @Override
    public void setLoadOrderList(String tipoCuadrilla) {
        mTipoCuadrilla = tipoCuadrilla;

        if (mTipoCuadrilla != null) {

            mOrdersPresenter.setLoadTipoTrabajos(mTipoCuadrilla);
            mOrdersPresenter.setLoadZonas();

            if (mTiposTrabajoSeleccionados.size() == 0 && mZonasSeleccionadas.size() == 0) {
                mOrdersPresenter.loadOrderList(mEstado, mTipoTrabajo, mZona, mFechaInicio, mFechaFin, null, true);
            }
            if (mTiposTrabajoSeleccionados.size() != 0 && mZonasSeleccionadas.size() == 0) {
                mOrdersPresenter.loadOrderList(mEstado, mTiposTrabajoSeleccionados, mZona, mFechaInicio, mFechaFin, null, true);
            }
            if (mTiposTrabajoSeleccionados.size() == 0 && mZonasSeleccionadas.size() != 0) {
                mOrdersPresenter.loadOrderList(mEstado, mTipoTrabajo, mZonasSeleccionadas, mFechaInicio, mFechaFin, null, true);
            }
            if (mTiposTrabajoSeleccionados.size() != 0 && mZonasSeleccionadas.size() != 0) {
                mOrdersPresenter.loadOrderList(mEstado, mTiposTrabajoSeleccionados, mZonasSeleccionadas, mFechaInicio, mFechaFin, null, true);
            }
        }
    }

    @Override
    public void setAsignarOrder(String cuadrilla, List<String> listorder) {
        mOrdersPresenter.asignarOrder(cuadrilla, listorder, "");
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
    public void setPresenter(OrdersListMvp.Presenter presenter) {
        mOrdersPresenter = presenter;
    }

    @Override
    public void setTipoTrabajo(List<String> tipoTrabajo) {
        //mTiposTrabajoSeleccionados = new ArrayList<>();
    }


    @Override
    public void setZonas(List<String> zona) {
        //mZonasSeleccionadas = new ArrayList<>();
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
    public void setOrderFilter(String estado, List<String> tipo, List<String> zona, DateTime desde, DateTime hasta, String search, Boolean estadoActual) {
        //TODO: VER DE PONER OTRA VARIABLE O COMO HACERLO XQ SINO ME DEJA MARCADOS TODOS LOS CHECKS PARA LA PROXIMA
        if (tipo.size() == 0) {
            mTiposTrabajoSeleccionados = mTipoTrabajo;
        } else {
            mTiposTrabajoSeleccionados = tipo;
        }
        if (zona.size() == 0) {
            mZonasSeleccionadas = mZona;
        } else {
            mZonasSeleccionadas = zona;
        }
        mFechaInicio = desde;
        mFechaFin = hasta;
        mOrdersPresenter.loadOrderList(mEstado, mTiposTrabajoSeleccionados, mZonasSeleccionadas, mFechaInicio, mFechaFin, search, estadoActual);
    }

    @Override
    public void showTipoCuadrillaList(List<Tipo_Cuadrilla> listorder) {
        listenerViewActivity.onShowTipoCuadrilla(listorder);
    }

    @Override
    public void showCuadrillaxTipoList(List<Tipo_Cuadrilla> listcuadrilla) {
        //TODO: ACA LLAMO A UN LISTERNER QUE ME CONECTE CON LA ACTIVITY Y LE PASO LAS CUADRILLAS
        listenerViewActivity.onShowTipoCuadrilla(listcuadrilla);
    }

    @Override
    public List<String> getTipoTrabajo() {
        return mTipoTrabajo;
    }

    @Override
    public List<String> getZona() {
        return mZona;
    }

    @Override
    public void cleanData() {
        mFechaInicio = null;
        mFechaFin = null;
        mZonasSeleccionadas = new ArrayList<>();
        mTiposTrabajoSeleccionados = new ArrayList<>();
    }

}
