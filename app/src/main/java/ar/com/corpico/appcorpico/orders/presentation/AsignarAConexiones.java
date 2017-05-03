package ar.com.corpico.appcorpico.orders.presentation;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;

import ar.com.corpico.appcorpico.R;

/**
 * Created by sistemas on 17/04/2017.
 */

public class AsignarAConexiones extends DialogFragment {
    private ArrayList<String> mNumeroOT = new ArrayList<>();
    private String mCuadrilla;
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
    public static AsignarAConexiones newInstance(String cuadrilla, ArrayList<String> numero) {
        AsignarAConexiones f = new AsignarAConexiones();

        Bundle args = new Bundle();
        args.putString("NUMERO", numero.get(0));
        args.putString("CUADRILLA",cuadrilla);
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
        mCuadrilla = getArguments().getString("CUADRILLA");


        android.view.View v = inflater.inflate(R.layout.dialog_asignar_conexiones, null);
        builder.setView(v);
        builder.setTitle("Asignar a cuadrilla");

        final RadioButton mConexiones  = (RadioButton) v.findViewById(R.id.radioButtonConexiones);
        final RadioButton mDesconexiones  = (RadioButton) v.findViewById(R.id.radioButtonDesconexiones);
        final RadioButton mVariosMañana  = (RadioButton) v.findViewById(R.id.radioButtonMañana);
        final RadioButton mVariosTarde  = (RadioButton) v.findViewById(R.id.radioButtonTarde);
        final RadioButton mAuxiliar  = (RadioButton) v.findViewById(R.id.radioButtonAuxliar);
        if (mCuadrilla.equals("Conexiones")){
            mConexiones.setVisibility(v.VISIBLE);
            mAuxiliar.setVisibility(v.VISIBLE);
        }
        if (mCuadrilla.equals("Desconexiones")){
            mDesconexiones.setVisibility(v.VISIBLE);
            mAuxiliar.setVisibility(v.VISIBLE);
        }

        if (mCuadrilla.equals("Varios")){
            mVariosMañana.setVisibility(v.VISIBLE);
            mVariosTarde.setVisibility(v.VISIBLE);
            mAuxiliar.setVisibility(v.VISIBLE);
        }
        Button asignar = (Button) v.findViewById(R.id.aplicar_boton);
        Button cancelar = (Button) v.findViewById(R.id.cancelar_boton);

        asignar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        listener.onPossitiveButtonAsignarClick(mCuadrilla,mNumeroOT);
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

