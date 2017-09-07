package ar.com.corpico.appcorpico.orders.presentation;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import ar.com.corpico.appcorpico.ordersDetail.presentation.AsignarAConexionesDetailDialog;

/**
 * Fragmento con un diálogo que muestra radio buttons
 */
public class SortDialog extends DialogFragment {

    public SortDialog() {
    }

    public interface SortDialogListener {
        public void onDialogSortClick(String sort);
    }

    // Use this instance of the interface to deliver action events
    SortDialogListener mListener;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createRadioListDialog();
    }

    /**
     * Crea un diálogo con una lista de radios
     *
     * @return Diálogo
     */
    public AlertDialog createRadioListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final CharSequence[] items = new CharSequence[3];

        items[0] = "Fecha Solicitud";
        items[1] = "Ruta";
        items[2] = "Turno";

        builder.setTitle("Ordenar por:")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Toast.makeText(
                                getActivity(),
                                "Seleccionaste: " + items[which],
                                Toast.LENGTH_SHORT)
                                .show();*/
                        String s = (String) items[which];
                        mListener.onDialogSortClick(s);
                        dismiss();
                    }
                });

        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (SortDialogListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnSimpleDialogListener");

        }
    }
}
