package ar.com.corpico.appcorpico.orders.presentation;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.widget.Spinner;

import org.joda.time.DateTime;

import java.util.Calendar;

import ar.com.corpico.appcorpico.R;


/**
 * Created by Administrador on 24/01/2017.
 */

public class OrdersFilterDialog extends DialogFragment {
    public OrdersFilterDialog() {
    }
    public interface OnFilterDialogListener {
        void onPossitiveButtonClick(String estado, String tipo, String sector, String desde, String hasta);// Eventos Botón Positivo
        void onNegativeButtonClick();// Eventos Botón Negativo
    }

    OnFilterDialogListener listener;

        @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return onCreateOrdersFilterDialog();
    }
    public AlertDialog onCreateOrdersFilterDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        android.view.View v = inflater.inflate(R.layout.dialog_orders_filter, null);

        builder.setView(v);
        final Spinner mStateSpinner = (Spinner)v.findViewById(R.id.estado_spinner);
        final Spinner mTipoSpinner = (Spinner)v.findViewById(R.id.tipo_spinner);
        final Spinner mSectorSpinner = (Spinner)v.findViewById(R.id.sector_spinner);
        final DatePicker mDesdePicker = (DatePicker)v.findViewById(R.id.desde_Picker);
        final DatePicker mHastaPicker = (DatePicker)v.findViewById(R.id.hasta_Picker);


        builder.setTitle("Filtro de búsqueda")
                .setPositiveButton("APLICAR", new DialogInterface.OnClickListener() {
                        @Override
                         public void onClick(DialogInterface dialog, int which) {
                           // Filtrado con la selecciona de los Spinner
                            Calendar c = Calendar.getInstance();
                            c.set(mDesdePicker.getYear(),mDesdePicker.getMonth(),mDesdePicker.getDayOfMonth());
                            Calendar d = Calendar.getInstance() ;
                            d.set(mHastaPicker.getYear(),mHastaPicker.getMonth(),mHastaPicker.getDayOfMonth());
                            final DateTime mDesde = new DateTime(c);
                            final DateTime mHasta = new DateTime(d);
                            listener.onPossitiveButtonClick(mStateSpinner.getItemAtPosition(mStateSpinner.getSelectedItemPosition()).toString(),mTipoSpinner.getItemAtPosition(mTipoSpinner.getSelectedItemPosition()).toString(),mSectorSpinner.getItemAtPosition(mSectorSpinner.getSelectedItemPosition()).toString(),mDesde.toString(),mHasta.toString());
                         }
                        })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //listener.onNegativeButtonClick();
                                dialog.dismiss();
                            }
                        });

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
