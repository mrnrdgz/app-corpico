package ar.com.corpico.appcorpico.orders.presentation;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.*;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;

import static ar.com.corpico.appcorpico.R.id.imageView;

/**
 * Created by sistemas on 17/04/2017.
 */

public class AsignarAConexiones extends DialogFragment {
    private ArrayList<String> mNumeroOT = new ArrayList<>();
    private ArrayList<Tipo_Cuadrilla> mTipoCuadrilla = new ArrayList<>();
    //private ArrayList<String> mTipoCuadrilla = new ArrayList<>();
    private ListView mCuadrillaList;
    private CuadrillaAdapter mCuadrillaAdapter;
    public interface OnAsignarAConexionesListener {
        void onPossitiveButtonAsignarClick(String cuadrilla, ArrayList<String> numero);// Eventos Botón Positivo
        //LO DEJO X SI MAS ADELANTE LO TENGO QUE DEFINIR
        void onNegativeButtonAsignarClick();// Eventos Botón Negativo
    }

    OnAsignarAConexionesListener listener;

    public AsignarAConexiones() {
    }
    //TODO: HAGO CON ESTE ARGUNTO PARA PROBAR...LUEGO EL ARGUMENTO CREO Q
    // DEBERIA SER ORDER PARA CUANDO USE MAS DE UNA SELLECCION
    public static AsignarAConexiones newInstance(ArrayList<Tipo_Cuadrilla> tipocuadrilla, ArrayList<String> numero) {
        AsignarAConexiones f = new AsignarAConexiones();

        ArrayList[] myArray = new ArrayList[10];
        //arguments.putParcelableArray("key"myArray);

        Bundle args = new Bundle();
        args.putString("NUMERO", numero.get(0));
        //args.putString("TIPO_CUADRILLA",tipocuadrilla.get(0));
        args.putParcelableArrayList("TIPO_CUADRILLA",myArray);

        f.setArguments(args);

        return f;

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createAsignarAConexiones();

    }

    /**
     * Crea un diálogo con una lista de radios
     *
     * @return Diálogo
     */
    public AlertDialog createAsignarAConexiones() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        mNumeroOT.add(getArguments().getString("NUMERO"));
        //mTipoCuadrilla.add (getArguments().getString("TIPO_CUADRILLA"));
        mTipoCuadrilla= getArguments().getArrayList("TIPO_CUADRILLA");


        if (mTipoCuadrilla != null && !mTipoCuadrilla.isEmpty()) {
            for (Tipo_Cuadrilla tipocuadrilla : mTipoCuadrilla) {
                mTipoCuadrilla.add(tipocuadrilla);
            }


        android.view.View v = inflater.inflate(R.layout.dialog_asignar_conexiones, null);
        builder.setView(v);
        builder.setTitle("Asignar a cuadrilla");

        mCuadrillaList = (ListView) v.findViewById(R.id.cuadrillaslist);
        mCuadrillaAdapter = new CuadrillaAdapter(getActivity(),new ArrayList<Tipo_Cuadrilla>());
        mCuadrillaList.setAdapter(mCuadrillaAdapter);

        mCuadrillaAdapter.clear();
        mCuadrillaAdapter.addAll(mTipoCuadrilla);
        mCuadrillaAdapter.notifyDataSetChanged();

        Button asignar = (Button) v.findViewById(R.id.aplicar_boton);
        Button cancelar = (Button) v.findViewById(R.id.cancelar_boton);

        asignar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        listener.onPossitiveButtonAsignarClick(mTipoTrabajo,mNumeroOT);
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
            listener = (OnAsignarAConexionesListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnSimpleDialogListener");

        }
    }
}

