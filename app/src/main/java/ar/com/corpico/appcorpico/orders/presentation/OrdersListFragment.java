package ar.com.corpico.appcorpico.orders.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
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
    public static final String ARG_SEARCH = "orders.search";
    private static final String ARG_TIPOS_TRABAJO_SELECCIONADOS = "orders.tipos_trabajo_seleccionados";
    private static final String ARG_FIELD_SORT = "orders.field_sort";


    // Dependencias
    private OrdersListMvp.Presenter mOrdersPresenter;


    // Views
    private ListView mOrderList;
    private OrdersAdapter mOrdersAdapter;
    private TextView mEmptyView;
    private View mProgressView;


    // Argumentos
    private String mTipoCuadrilla;
    private String mEstado;
    private List<String> mTiposTrabajoSeleccionados;
    private List<String> mZonasSeleccionadas;
    private DateTime mFechaInicio;
    private DateTime mFechaFin;
    private String mSearch;
    private String mFieldSort;


    private ArrayList<String> list_items = new ArrayList<>();

    // Lógica
    private int count;

    public OrdersListFragment() {
        // Required empty public constructor
    }

    public static OrdersListFragment newInstance(
            String tipoCuadrilla, String estado,
            ArrayList<String> tiposTrabajoSeleccionados, ArrayList<String> zonasSeleccionadas,
            DateTime fechaInicio, DateTime fechaFin, String search,String fieldSort) {

        OrdersListFragment fragment = new OrdersListFragment();
        Bundle args = new Bundle();

        args.putString(ARG_TIPO_CUADRILLA, tipoCuadrilla);
        args.putString(ARG_ESTADO, estado);
        args.putStringArrayList(ARG_ZONAS_SELECCIONADAS, (ArrayList<String>) zonasSeleccionadas);
        args.putStringArrayList(ARG_TIPOS_TRABAJO_SELECCIONADOS, tiposTrabajoSeleccionados);
        args.putSerializable(ARG_FECHA_INICIO, fechaInicio);
        args.putSerializable(ARG_FECHA_FIN, fechaFin);
        args.putString(ARG_SEARCH, search);
        args.putString(ARG_FIELD_SORT,fieldSort);

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
            mSearch = arguments.getString(ARG_SEARCH);
            mFieldSort = arguments.getString(ARG_FIELD_SORT);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.orders_list_frag, container, false);

        // Obtención de referencias UI
        mOrderList = (ListView) root.findViewById(R.id.orders_list);
        mEmptyView = (TextView) root.findViewById(R.id.orders_empty);
        mProgressView = root.findViewById(R.id.orders_progress);

        mOrderList.setTextFilterEnabled(true);
        mOrdersAdapter = new OrdersAdapter(getActivity(), new ArrayList<Order>(0));
        mOrderList.setAdapter(mOrdersAdapter);

        mOrderList.setFocusable(false);


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
                        mOrdersPresenter.asignarOrder(mTipoCuadrilla, list_items,"");
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

        // TODO: Eliminar extras hacia el detalle y cargar la orden desde ese mismo lugar
        mOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //mOrderList.setOnItemClickLister()(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Order currentOrder = mOrdersAdapter.getItem(i);
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);

                Order currentOrder = (Order) mOrderList.getAdapter().getItem(i);

                intent.putExtra("TIPO_CUADRILLA", mTipoCuadrilla);
                intent.putExtra("NUMERO", currentOrder.getNumero().toString());
                intent.putExtra("FECHA", currentOrder.getFechaSolicitud());
                intent.putExtra("TURNO", currentOrder.getTurno());
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

        mOrdersPresenter.loadOrders(mTipoCuadrilla, mEstado, mTiposTrabajoSeleccionados,
                mZonasSeleccionadas, mFechaInicio, mFechaFin, mSearch, true,mFieldSort);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void close() {
        mOrdersPresenter.loadOrders(mTipoCuadrilla, mEstado, mTiposTrabajoSeleccionados,
                mZonasSeleccionadas, mFechaInicio, mFechaFin, mSearch, true,mFieldSort);
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
        mOrdersPresenter = Preconditions.checkNotNull(presenter, "mOrdersPresenter no puede ser null");
    }

    @Override
    public void showOrdesEmpty() {
        mOrderList.setEmptyView(mEmptyView);
    }

    @Override
    public void showProgressIndicator(boolean show) {
        if (mProgressView!=null){
            mProgressView.setVisibility(show ? View.VISIBLE : GONE);
        }
    }
}
