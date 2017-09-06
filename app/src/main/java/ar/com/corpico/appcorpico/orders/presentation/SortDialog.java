package ar.com.corpico.appcorpico.orders.presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by sistemas on 06/09/2017.
 */

public class SortDialog {
    public AlertDialog createRadioListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder();

        final CharSequence[] items = new CharSequence[3];

        items[0] = "Soltero/a";
        items[1] = "Casado/a";
        items[2] = "Divorciado/a";

        builder.setTitle("Estado Civil")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
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
