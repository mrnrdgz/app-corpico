package ar.com.corpico.appcorpico.orders.presentation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Button;
import ar.com.corpico.appcorpico.R;


/**
 * Created by Administrador on 24/01/2017.
 */

public class OrdersFilterDialog extends DialogFragment {
    public OrdersFilterDialog() {
    }
    public interface OnFilterDialogListener {
        void onPossitiveButtonClick();// Eventos Botón Positivo
        void onNegativeButtonClick();// Eventos Botón Negativo
    }

    OnFilterDialogListener listener;

        @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return onCreateOrdersFilterDialog();
    }
    public AlertDialog onCreateOrdersFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        android.view.View v = inflater.inflate(R.layout.dialog_orders_filter, null);

        builder.setView(v);

        /*Button mAplicarButton = (Button) v.findViewById(R.id.filtrar_boton);
        Button mCancelarButton = (Button) v.findViewById(R.id.cancelar_boton);*/

        builder.setTitle("Filtar búsqueda")
                .setPositiveButton("APLICAR", new DialogInterface.OnClickListener() {
                        @Override
                         public void onClick(DialogInterface dialog, int which) {
                            /*ACA TENDRIA Q MANDAR LA CAPTURA DE LOS FILTROS ELEGIDOS
                            Y PASARLOS X PARAMETROS AL METODO DE ABAJO
                            PARA ARMAR LA LLAMADA AL METODO DE FILTRADO*/
                            listener.onPossitiveButtonClick();
                         }
                        })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.onNegativeButtonClick();
                            }
                        });

        /*mAplicarButton.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        // Aplicar
                        listener.onPossitiveButtonClick();
                    }
                }
        );

        mCancelarButton.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        // Cancelar
                        listener.onNegativeButtonClick();
                    }
                }

        );*/

        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OnFilterDialogListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnSimpleDialogListener");

        }
    }
}
