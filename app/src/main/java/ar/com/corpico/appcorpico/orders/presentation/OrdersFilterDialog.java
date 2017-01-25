package ar.com.corpico.appcorpico.orders.presentation;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

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

        /*Button aplicar = (Button) v.findViewById(R.id.filtrar_boton);
        Button cancelar = (Button) v.findViewById(R.id.cancelar_boton);*/

        builder.setView(inflater.inflate(R.layout.dialog_orders_filter, null))
                .setPositiveButton(R.string.filtrar_boton, new DialogInterface.OnClickListener() {
                        @Override
                         public void onClick(DialogInterface dialog, int which) {
                            listener.onPossitiveButtonClick();
                         }
                        })
                .setNegativeButton(R.string.cancelar_boton, new DialogInterface.OnClickListener() {
                            @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.onNegativeButtonClick();
                            }
                        });

        return builder.create();
    }

}
