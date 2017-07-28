package ar.com.corpico.appcorpico.ordersDetail.presentation;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddOrdersState;

/**
 * Created by sistemas on 17/04/2017.
 */

public class AsignarAConexionesDetailDialog extends DialogFragment {
    private ArrayList<String> mNumeroOT = new ArrayList<>();
    private String mTipoCuadrilla;
    private String mObservation;
    private AddOrdersState mAddOrdersState;
    private OrdersDetailPresenter presenter;

    public interface OnAsignarAConexionesDetailListener {
        void onPossitiveButtonAsignarClick(String cuadrilla, ArrayList<String> numero, String observacion);// Eventos Botón Positivo
        //LO DEJO X SI MAS ADELANTE LO TENGO QUE DEFINIR
        void onNegativeButtonAsignarClick();// Eventos Botón Negativo
    }

    OnAsignarAConexionesDetailListener listener;

    public AsignarAConexionesDetailDialog() {
    }
    //TODO: HAGO CON ESTE ARGUNTO PARA PROBAR...LUEGO EL ARGUMENTO CREO Q
    // DEBERIA SER ORDER PARA CUANDO USE MAS DE UNA SELLECCION
    public static AsignarAConexionesDetailDialog newInstance(String tipoCuadrilla, ArrayList<String> numero) {
        AsignarAConexionesDetailDialog f = new AsignarAConexionesDetailDialog();

        Bundle args = new Bundle();
        args.putString("NUMERO", numero.get(0));
        args.putString("TIPO_CUADRILLA", tipoCuadrilla);

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
        mTipoCuadrilla = (getArguments().getString("TIPO_CUADRILLA"));
        //TODO: AGREGAR OBSERVACION?

        android.view.View v = inflater.inflate(R.layout.dialog_asignar_conexiones, null);
        builder.setView(v);
        builder.setTitle("Asignar a cuadrilla " + mTipoCuadrilla);


        final TextView mObservation = (TextView) v.findViewById(R.id.observacion_text);
        TextInputLayout mFloatLabelObservacion = (TextInputLayout) v.findViewById(R.id.float_label_observacion);
        Button asignar = (Button) v.findViewById(R.id.aplicar_boton);
        Button cancelar = (Button) v.findViewById(R.id.cancelar_boton);

        asignar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        listener.onPossitiveButtonAsignarClick(mTipoCuadrilla,mNumeroOT,mObservation.getText().toString());
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
            listener = (OnAsignarAConexionesDetailListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnSimpleDialogListener");

        }
    }
}

