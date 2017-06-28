package ar.com.corpico.appcorpico.orders.presentation;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import org.joda.time.DateTime;
import ar.com.corpico.appcorpico.R;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.R.attr.y;
import static ar.com.corpico.appcorpico.R.id.checkbox;
import static ar.com.corpico.appcorpico.R.id.seccion_tipotrabajo;
import static ar.com.corpico.appcorpico.R.id.tv_tipo;


/**
 * Created by Administrador on 21/05/2017.
 */

public class OrdersFilter extends DialogFragment {
    private ArrayList<String> mTipoTrabajo = new ArrayList<>();
    private String mState;
    private String mSector;
    private TextView mTipo;
    public OrdersFilter() {
    }
    public interface OnOrdersFilterListener {
        void onFilterPossitiveButtonClick(String estado, List<String> tipo, String sector, DateTime desde, DateTime hasta, Boolean estadoActual);// Eventos Botón Positivo
        void onNegativeButtonClick();// Eventos Botón Negativo
        void onCargarCuadrillasListner(String tipotrabajo);
        void onTipoTrabajoTextViewClick();
    }

    OnOrdersFilterListener listener;
    public static OrdersFilter newInstance(ArrayList<String> tipotrabajo, String estado, String sector) {
        OrdersFilter f = new OrdersFilter();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putStringArrayList("tipotrabajo", tipotrabajo);
        args.putString("estado", estado);
        args.putString("sector", sector);
        f.setArguments(args);

        return f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTipoTrabajo = getArguments().getStringArrayList("tipotrabajo");
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
        android.view.View v = inflater.inflate(R.layout.orders_filter,null);
        //LinearLayout seccionTipoTrabajo1 = (LinearLayout) findViewById(R.id.seccion_tipotrabajo);
        MultiSpinner milti = new MultiSpinner(getActivity());


        final ArrayList seletedTipoTrabajo=new ArrayList();
        final ArrayList seletedZona=new ArrayList();

        builder.setView(v);

        builder.setTitle("Filtro de búsqueda");

        final TextView mTipoTitulo = (TextView)v.findViewById(R.id.titulo_tipo);
        //final TextView mTipoTrabajo = (TextView)v.findViewById(R.id.tv_tipotrabajo);
        final TextView mZonaTitulo = (TextView)v.findViewById(R.id.titulo_zona);
        final TextView mZona = (TextView)v.findViewById(R.id.tv_zona);



        Button aplicar = (Button) v.findViewById(R.id.aplicar_boton);
        Button cancelar = (Button) v.findViewById(R.id.cancelar_boton);

        aplicar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        /*mSector = mSectorSpinner.getItemAtPosition(mSectorSpinner.getSelectedItemPosition()).toString();
                        listener.onFilterPossitiveButtonClick(mState,mTipoTrabajo,mSector,null,null,true);*/
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
    private void iniciarTipoTrabajo(android.view.View v) {
        mTipo = (TextView)v.findViewById(R.id.tv_tipotrabajo);
        mTipo.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        listener.onTipoTrabajoTextViewClick();
                    }
                }
        );
    }
}
