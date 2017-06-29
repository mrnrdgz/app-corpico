package ar.com.corpico.appcorpico.orders.presentation;

/**
 * Created by sistemas on 28/06/2017.
 */

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Diálogo con checkboxes
 */
public class TipoTrabajoDialog extends DialogFragment {
    private ArrayList<String> mTipoTrabajo = new ArrayList<>();
    private ArrayList<String> mTipoTrabajoSelect = new ArrayList<>();


    public interface TipoTrabajoListener{
        void SetTipoTrabajo(ArrayList<String> mTipoTrabajo);
    }
    TipoTrabajoListener listener;
    public TipoTrabajoDialog() {
    }
    public static TipoTrabajoDialog newInstance (ArrayList<String> tipotrabajo){
        TipoTrabajoDialog f = new TipoTrabajoDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putStringArrayList("tipotrabajo", tipotrabajo);
        f.setArguments(args);

        return f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTipoTrabajo = getArguments().getStringArrayList("tipotrabajo");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createMultipleListDialog();
    }

    /**
     * Crea un diálogo con una lista de checkboxes
     * de selección multiple
     *
     * @return Diálogo
     */
    public AlertDialog createMultipleListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final ArrayList itemsSeleccionados = new ArrayList();

        CharSequence[] items = new CharSequence[mTipoTrabajo.size()];
        int i;
        for(i=0;i< mTipoTrabajo.size();i++){
            items[i]= mTipoTrabajo.get(i).toString();
        }

        builder.setTitle("Tipos de Trabajo")
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            // Guardar indice seleccionado
                            itemsSeleccionados.add(which);
                        } else if (itemsSeleccionados.contains(which)) {
                            // Remover indice sin selección
                            itemsSeleccionados.remove(Integer.valueOf(which));
                        }
                    }
                })
                .setPositiveButton("ACEPTAR",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int j=0; j < itemsSeleccionados.size(); j++){
                            mTipoTrabajoSelect.add(mTipoTrabajo.get(((Integer) itemsSeleccionados.get(j))));
                        }
                        listener.SetTipoTrabajo(mTipoTrabajoSelect);
                        dismiss();
                    }
                })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //listener.onNegativeButtonClick();
                                dismiss();
                            }
                        });

        return builder.create();
    }

}
