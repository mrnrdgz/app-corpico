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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import org.joda.time.DateTime;
import ar.com.corpico.appcorpico.R;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class OrdersFilter extends DialogFragment{
    private ArrayList<String> mTipoTrabajo = new ArrayList<>();
    private String mState;
    private String mSector;
    private ArrayList<String> mZona = new ArrayList<>();
    private TextView mTipoTitulos;
    private TextView mZonaTitulos;
    private ImageButton mTipoTrabajoBt;
    public OrdersFilter() {
    }

    public interface OnOrdersFilterListener {
        void onFilterPossitiveButtonClick(String estado, List<String> tipo, List<String> zona, DateTime desde, DateTime hasta, Boolean estadoActual);// Eventos Botón Positivo
        void onNegativeButtonClick();// Eventos Botón Negativo
        void onCargarCuadrillasListner(String tipotrabajo);
        void onTipoTrabajoTextViewClick(ArrayList<String> tipotrabajo);
    }

    OnOrdersFilterListener listener;
    public static OrdersFilter newInstance(ArrayList<String> tipotrabajo, String estado, ArrayList<String>  zona) {
        OrdersFilter f = new OrdersFilter();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putStringArrayList("tipotrabajo", tipotrabajo);
        args.putString("estado", estado);
        args.putStringArrayList("zona", zona);
        f.setArguments(args);

        return f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTipoTrabajo = getArguments().getStringArrayList("tipotrabajo");
        mState = getArguments().getString("estado");
        mZona = getArguments().getStringArrayList("zona");
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
        //MultiSpinner milti = new MultiSpinner(getActivity());


        final ArrayList seletedTipoTrabajo=new ArrayList();
        final ArrayList seletedZona=new ArrayList();

        builder.setView(v);

        builder.setTitle("Filtro de búsqueda");

        //final TextView mTipoTitulo = (TextView)v.findViewById(R.id.titulo_tipo);
        //final ImageButton mTipoTrabajoBt = (ImageButton)v.findViewById(R.id.tipo_trabajo);
        final TextView mZonaTitulo = (TextView)v.findViewById(R.id.titulo_zona);
        final ImageButton mZonaBt = (ImageButton)v.findViewById(R.id.zona);

        iniciarTipoTrabajo(v);



        Button aplicar = (Button) v.findViewById(R.id.aplicar_boton);
        Button cancelar = (Button) v.findViewById(R.id.cancelar_boton);

        aplicar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        //mSector = mSectorSpinner.getItemAtPosition(mSectorSpinner.getSelectedItemPosition()).toString();
                        listener.onFilterPossitiveButtonClick(mState,mTipoTrabajo,mZona,null,null,true);
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
    //TODO: VER ESTO
    private void iniciarTipoTrabajo(android.view.View v) {
        mTipoTitulos = (TextView)v.findViewById(R.id.titulo_tipo);
        mTipoTrabajoBt = (ImageButton)v.findViewById(R.id.tipo_trabajo);
        mTipoTrabajoBt.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        listener.onTipoTrabajoTextViewClick(mTipoTrabajo);
                    }
                }
        );
    }
    public void setTipoTrabajoView(ArrayList<String> tipotrabajo) {
        String s = "";
        for(int i=0; i< tipotrabajo.size(); i++){
            if(s.equals("")){
                s = tipotrabajo.get(i);
            } else {
                s=  s + " - " + tipotrabajo.get(i);
            }
        }
        mTipoTitulos.setText(s);
    }
}
