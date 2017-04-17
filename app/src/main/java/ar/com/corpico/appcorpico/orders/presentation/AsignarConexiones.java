package ar.com.corpico.appcorpico.orders.presentation;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by sistemas on 17/04/2017.
 */

public class AsignarConexiones {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


    final CharSequence[] items = new CharSequence[3];

    items[0] = "Conexiones";
    items[1] = "Auxiliar";

    builder.setTitle("Estado Civil")
            .

    AsignarConexiones(items, 0, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(
                    getActivity(),
                    "Seleccionaste: " + items[which],
                    Toast.LENGTH_SHORT)
                    .show();
        }
    });

    return builder.create();
}
