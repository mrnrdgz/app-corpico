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
    private String mTipoTrabajo;
    private String mState;
    private String mSector;
    public OrdersFilter() {
    }
    public interface OnOrdersFilterListener {
        void onFilterPossitiveButtonClick(String estado, String tipo, String sector, DateTime desde, DateTime hasta, Boolean estadoActual);// Eventos Botón Positivo
        void onNegativeButtonClick();// Eventos Botón Negativo
        void onCargarCuadrillasListner(String tipotrabajo);
        //void loadType();
    }

    OnOrdersFilterListener listener;
    public static OrdersFilter newInstance(String tipotrabajo,String estado,String sector) {
        OrdersFilter f = new OrdersFilter();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("tipotrabajo", tipotrabajo);
        args.putString("estado", estado);
        args.putString("sector", sector);
        f.setArguments(args);

        return f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTipoTrabajo = getArguments().getString("tipotrabajo");
        mState = getArguments().getString("estado");
        mSector = getArguments().getString("sector");
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
        /*if(mType.equals("Conexiones") || mType.equals("Desconexiones")){*/
        mTipoTitulo.setVisibility(View.GONE);
        mTipoSpinner.setVisibility(View.GONE);
        for (int i = 0; i < mSectorSpinner.getCount(); i++) {
            if (mSectorSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(mSector)) {
                mSectorSpinner.setSelection(i);
            }
        }
        //mSector=mSectorSpinner.getSelectedItem().toString();
        /*if(!mSector.equals(mSectorSpinner.getSelectedItem().toString())){
            mSector=mSectorSpinner.getSelectedItem().toString();
        }*/

        /*}
        if(mType.equals("Conexiones") ){
            mTipo = "Colocacion de Medidor";
        }
        if(mType.equals("Desconexiones") ){
            mTipo = "Retiro de Medidor";
        }
        if(!mCuadrilla.equals("Conexiones") && !mCuadrilla.equals("Desconexiones")){
            mTipo= mTipoSpinner.getItemAtPosition(mTipoSpinner.getSelectedItemPosition()).toString();
        }*/

        Button aplicar = (Button) v.findViewById(R.id.aplicar_boton);
        Button cancelar = (Button) v.findViewById(R.id.cancelar_boton);

        aplicar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        mSector = mSectorSpinner.getItemAtPosition(mSectorSpinner.getSelectedItemPosition()).toString();
                        listener.onFilterPossitiveButtonClick(mState,mTipoTrabajo,mSector,null,null,true);
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
