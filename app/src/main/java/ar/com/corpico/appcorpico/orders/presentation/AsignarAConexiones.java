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

import ar.com.corpico.appcorpico.R;

/**
 * Created by sistemas on 17/04/2017.
 */

public class AsignarAConexiones extends DialogFragment {
    private String mNumeroOT;
    public interface OnAsignarAConexionesListener {
        void onPossitiveButtonAsignarClick(String cuadrilla,String numero);// Eventos Botón Positivo
        //LO DEJO X SI MAS ADELANTE LO TENGO QUE DEFINIR
        void onNegativeButtonAsignarClick();// Eventos Botón Negativo
    }

    OnAsignarAConexionesListener listener;

    public AsignarAConexiones() {
    }
    //TODO: HAGO CON ESTE ARGUNTO PARA PROBAR...LUEGO EL ARGUMENTO CREO Q
    // DEBERIA SER ORDER PARA CUANDO USE MAS DE UNA SELLECCION
    public static AsignarAConexiones newInstance(String numero) {
        AsignarAConexiones f = new AsignarAConexiones();

        Bundle args = new Bundle();
        args.putString("NUMERO", numero);
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
        mNumeroOT = getArguments().getString("NUMERO");

        android.view.View v = inflater.inflate(R.layout.dialog_asignar_conexiones, null);
        builder.setView(v);
        builder.setTitle("Asignar a cuadrilla");

        final RadioButton mConexiones  = (RadioButton) v.findViewById(R.id.radioButtonCoonesiones);
        final RadioButton mAuxiliar  = (RadioButton) v.findViewById(R.id.radioButtonAuxliar);
        Button asignar = (Button) v.findViewById(R.id.aplicar_boton);
        Button cancelar = (Button) v.findViewById(R.id.cancelar_boton);

        asignar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        if (mConexiones.isChecked()){
                            listener.onPossitiveButtonAsignarClick(mConexiones.getText().toString(),mNumeroOT);
                            dismiss();
                        }else {
                            listener.onPossitiveButtonAsignarClick(mAuxiliar.getText().toString(),mNumeroOT);
                            dismiss();
                        }
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
