package ar.com.corpico.appcorpico.orders.presentation;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.Spinner;
import org.joda.time.DateTime;
import ar.com.corpico.appcorpico.R;
import android.view.View;
import android.widget.TextView;

import static ar.com.corpico.appcorpico.R.id.tv_tipo;


/**
 * Created by Administrador on 21/05/2017.
 */

public class OrdersFilter extends DialogFragment {
    private String mCuadrilla;
    private String mTipo;
    private String mState;
    public OrdersFilter() {
    }
    public interface OnOrdersFilterListener {
        void onFilterPossitiveButtonClick(String estado, String tipo, String sector, DateTime desde, DateTime hasta, Boolean estadoActual);// Eventos Botón Positivo
        void onNegativeButtonClick();// Eventos Botón Negativo
        //void loadType();
    }
    OnOrdersFilterListener listener;
    public static OrdersFilter newInstance(String cuadrilla,String estado) {
        OrdersFilter f = new OrdersFilter();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("cuadrilla", cuadrilla);
        args.putString("estado", estado);
        f.setArguments(args);

        return f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCuadrilla = getArguments().getString("cuadrilla");
        mState = getArguments().getString("estado");
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return onCreateOrdersFilter();
    }
    public AlertDialog onCreateOrdersFilter() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        android.view.View v = inflater.inflate(R.layout.orders_filter, null);
        builder.setView(v);
        builder.setTitle("Filtro de búsqueda");

        final TextView mTipoTitulo = (TextView)v.findViewById(R.id.tv_tipo);
        final Spinner mTipoSpinner = (Spinner)v.findViewById(R.id.tipo_spinner);
        final Spinner mSectorSpinner = (Spinner)v.findViewById(R.id.sector_spinner);
        //listener.LoadTipos(mType);
        /*if(mType.equals("Conexiones") || mType.equals("Desconexiones")){
            mTipoTitulo.setVisibility(View.GONE);
            mTipoSpinner.setVisibility(View.GONE);
        }
        if(mType.equals("Conexiones") ){
            mTipo = "Colocacion de Medidor";
        }
        if(mType.equals("Desconexiones") ){
            mTipo = "Retiro de Medidor";
        }*/
        if(!mCuadrilla.equals("Conexiones") && !mCuadrilla.equals("Desconexiones")){
            mTipo= mTipoSpinner.getItemAtPosition(mTipoSpinner.getSelectedItemPosition()).toString();
        }

        Button aplicar = (Button) v.findViewById(R.id.aplicar_boton);
        Button cancelar = (Button) v.findViewById(R.id.cancelar_boton);

        aplicar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {

                        listener.onFilterPossitiveButtonClick(mState,mTipo,
                                mSectorSpinner.getItemAtPosition(mSectorSpinner.getSelectedItemPosition()).toString(),
                                null,null,true);
                        dismiss();
                    }
                }
        );
        cancelar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        dismiss();
                    }
                }

        );

        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OnOrdersFilterListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnSimpleDialogListener");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
