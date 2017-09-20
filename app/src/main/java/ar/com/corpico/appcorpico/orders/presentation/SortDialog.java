package ar.com.corpico.appcorpico.orders.presentation;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.ArrayList;

import ar.com.corpico.appcorpico.ordersDetail.presentation.AsignarAConexionesDetailDialog;

/**
 * Fragmento con un diálogo que muestra radio buttons
 */

/**Todo: escribo un comentario para ver si funciona
 * separar conceptualmente las fuentes de datos (datos orden, datos asociado,detalle medidor,
 * hacer un respositorio x cada caracteristica
 * hacer el caso de uso asignar turno...
 * ver si de aca puedo hacer el web service y probar todo el circuito
 * Donde voy a mostrar las etpas?
 */

public class SortDialog extends DialogFragment {
    private String mfieldSort;
    public SortDialog() {
    }

    public interface SortDialogListener {
        public void onDialogSortClick(String sort);
    }

    public static SortDialog newInstance (String fieldSorto){
        SortDialog f = new SortDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("FIELD_SORT", fieldSorto);
        f.setArguments(args);

        return f;
    }

    // Use this instance of the interface to deliver action events
    SortDialogListener mListener;
    @NonNull
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mfieldSort = getArguments().getString("FIELD_SORT");
    }

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

        int selected=0;
        final CharSequence[] items = new CharSequence[3];

        items[0] = "Fecha Solicitud";
        items[1] = "Ruta";
        items[2] = "Turno";

        for(int i =0; items.length > i ; i++){
            if (items[i].equals(mfieldSort) ){
                selected = i;
            }
        }
        builder.setTitle("Ordenar por:")
                .setSingleChoiceItems(items, selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = (String) items[which];
                        mListener.onDialogSortClick(s);
                        dismiss();
                    }
                });
        builder.setNegativeButton("CANCELAR",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
