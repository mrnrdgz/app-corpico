package ar.com.corpico.appcorpico.orders.presentation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import java.util.StringTokenizer;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.ordersDetail.presentation.AsignarAConexionesDetailDialog;
import ar.com.corpico.appcorpico.ordersDetail.presentation.OrderDetailActivity;

import static android.R.attr.fragment;
import static android.view.View.GONE;

/**
 * Created by Administrador on 07/01/2017.
 */

public class OrdersListFragment extends Fragment implements ar.com.corpico.appcorpico.orders.presentation.View{
    private Presenter mOrdersPresenter;
    private ListView mOrderList;
    private OrdersAdapter mOrdersAdapter;
    private TextView mEmptyView;
    private android.view.View mProgressView;
    private String mTipoCuadrilla;
    private List<String> mTipoTrabajo= new ArrayList<>();
    private List<String> mTipoTrabajoSelected= new ArrayList<>();
    private List<String> mZona= new ArrayList<>();
    private List<String> mZonaSelected= new ArrayList<>();
    private DateTime mDesde;
    private DateTime mHasta;
    private String mEstado;
    private Activity mActivity;
    private ArrayList<String> list_items = new ArrayList<>();
    private int count;
    private boolean hideAsignarButton;

    private OrdersAdapter.OnAsignarListener listener;

    public OrdersListFragment() {
        // Required empty public constructor
    }

   public interface OnViewActivityListener {
        void onShowTipoCuadrilla(List<Tipo_Cuadrilla> listorder);
        //void onShowTipoCuadrilla(List<Tipo_Cuadrilla> listtipocuadrilla);
       void onAsignarCuadrillaContextual(String cuadrilla, ArrayList<String> numeros);
   }

    private OnViewActivityListener listenerViewActivity;


    public void setActivityListener(OnViewActivityListener listener) {
        this.listenerViewActivity=listener;
    }

    public void setListener(OrdersAdapter.OnAsignarListener listener) {
        this.listener=listener;
    }

    //Aca va sin parametros o que parametros irian?
    public static OrdersListFragment newInstance(String tipocuadrilla, String estado, List<String> zona,DateTime desde, DateTime hasta){
        OrdersListFragment fragment = new OrdersListFragment();
        Bundle args = new Bundle();
        args.putString("tipocuadrilla", tipocuadrilla);
        args.putString("estado", estado);
        args.putStringArrayList("zona", (ArrayList<String>) zona);
        args.putSerializable("desde", desde);
        args.putSerializable("hasta", hasta);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Toman parámetros
           mTipoCuadrilla = getArguments().getString("tipocuadrilla");
           mEstado= getArguments().getString("estado");
           mZona= getArguments().getStringArrayList("zona");
           mDesde = (DateTime) getArguments().get("desde");
           mHasta = (DateTime) getArguments().get("hasta");
           Spinner activitySpinner = (Spinner) getActivity().findViewById(R.id.spinner_toolBar);
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
                    Order item= (Order) mOrderList.getAdapter().getItem(position);
                    String numero = item.getNumero();
                    list_items.add(numero);
                }else{
                    count--;
                    mOrdersAdapter.removeSelection(position);
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
                        listenerViewActivity.onAsignarCuadrillaContextual(mTipoCuadrilla,list_items);
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

                Order currentOrder = (Order)mOrderList.getAdapter().getItem(i);

                intent.putExtra("TIPO_CUADRILLA",mTipoCuadrilla);
                intent.putExtra("NUMERO",currentOrder.getNumero().toString());
                intent.putExtra("FECHA",currentOrder.getFechaSolicitud().toString());
                intent.putExtra("ETAPA",currentOrder.getEtapas().toString());
                intent.putExtra("TIPO_TRABAJO",currentOrder.getTipo_Trabajo().toString());
                intent.putExtra("MOTIVO",currentOrder.getMotivo().toString());
                intent.putExtra("TITULAR",currentOrder.getTitular().toString());
                intent.putExtra("ASOCIADO",currentOrder.getAsociado().toString()+ "/" + currentOrder.getSuministro().toString());
                intent.putExtra("DOMICILIO",currentOrder.getDomicilio().toString());
                intent.putExtra("ANEXO",currentOrder.getAnexo().toString());
                intent.putExtra("TIPO_USUARIO",currentOrder.getTipo_Usuario().toString());
                intent.putExtra("TARIFA",currentOrder.getTarifa().toString());
                intent.putExtra("POTENCIA_DECLARADA",currentOrder.getPotencia_Declarada().toString());
                intent.putExtra("MEDIDOR",currentOrder.getMedidor().toString());
                intent.putExtra("MARCA",currentOrder.getMarca().toString());
                intent.putExtra("MODELO",currentOrder.getModelo().toString());
                intent.putExtra("FACTOR_M",currentOrder.getFactorM().toString());
                intent.putExtra("CAPACIDAD",currentOrder.getCapacidad().toString());
                intent.putExtra("TENSION",currentOrder.getTension().toString());

                intent.putExtra("LAT",currentOrder.getLatitud().toString());
                intent.putExtra("LNG",currentOrder.getLongitud().toString());
                intent.putExtra("OBSERVACION",currentOrder.getObservacion().toString());
                startActivity(intent);
            }
        });

        setLoadOrderList(mTipoCuadrilla);
        return root;
    }

    @Override
    public void setLoadOrderList(String tipocuadrilla) {
        mTipoCuadrilla=tipocuadrilla;
        if (mTipoCuadrilla!=null){
            mOrdersPresenter.setLoadTipoTrabajos(mTipoCuadrilla);
            mOrdersPresenter.setLoadZonas();
            if(mTipoTrabajoSelected.size()==0 && mZonaSelected.size()==0){
                mOrdersPresenter.loadOrderList(mEstado,mTipoTrabajo,mZona,mDesde,mHasta,null,true);
            }
            if(mTipoTrabajoSelected.size()!=0 && mZonaSelected.size()==0){
                mOrdersPresenter.loadOrderList(mEstado,mTipoTrabajoSelected,mZona,mDesde,mHasta,null,true);
            }
            if(mTipoTrabajoSelected.size()==0 && mZonaSelected.size()!=0){
                mOrdersPresenter.loadOrderList(mEstado,mTipoTrabajoSelected,mZonaSelected,mDesde,mHasta,null,true);
            }
            if(mTipoTrabajoSelected.size()!=0 && mZonaSelected.size()!=0){
                mOrdersPresenter.loadOrderList(mEstado,mTipoTrabajoSelected,mZonaSelected,mDesde,mHasta,null,true);
            }
        }
    }
    @Override
    public void setAsignarOrder(String cuadrilla, List<String> listorder) {
        mOrdersPresenter.asignarOrder(cuadrilla,listorder,"");
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
    public void setTipoTrabajo(List<String> tipoTrabajo) {
        mTipoTrabajo=tipoTrabajo;
        //mTipoTrabajoSelected = new ArrayList<>();
    }


    @Override
    public void setZonas(List<String> zona) {
        mZona=zona;
        //mZonaSelected = new ArrayList<>();
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
    public void setOrderFilter(String estado, List<String> tipo, List<String> zona, DateTime desde, DateTime hasta, String search,Boolean estadoActual) {
        //TODO: VER DE PONER OTRA VARIABLE O COMO HACERLO XQ SINO ME DEJA MARCADOS TODOS LOS CHECKS PARA LA PROXIMA
        if (tipo.size() == 0){
            mTipoTrabajoSelected= mTipoTrabajo;
        }else{
            mTipoTrabajoSelected = tipo;
        }
        if (zona.size() == 0){
            mZonaSelected= mZona;
        }else{
            mZonaSelected = zona;
        }
        mDesde=desde;
        mHasta=hasta;
        mOrdersPresenter.loadOrderList(mEstado,mTipoTrabajoSelected,mZona,mDesde,mHasta,search,estadoActual);
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
        mDesde=null;
        mHasta=null;
        mZonaSelected=new ArrayList<>();
        mTipoTrabajoSelected=new ArrayList<>();
    }

}
