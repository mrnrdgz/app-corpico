package ar.com.corpico.appcorpico.orders.presentation;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.*;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ar.com.corpico.appcorpico.R;


/**
 * Created by Administrador on 24/01/2017.
 */
//TODO: ....
public class OrdersFilterDialog extends DialogFragment {
    private TextView textFecha;
    public OrdersFilterDialog() {
    }
    public interface OnFilterDialogListener {
        void onPossitiveButtonClick(String estado, String tipo, String sector, DateTime desde, DateTime hasta,Boolean estadoActual);// Eventos Botón Positivo
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
        final TextView textFecha = (TextView) v.findViewById(R.id.fecha_text);
        //iniciarFecha(v);

        builder.setTitle("Filtro de búsqueda")
                .setPositiveButton("APLICAR", new DialogInterface.OnClickListener() {
                        @Override
                         public void onClick(DialogInterface dialog, int which) {
                           // Filtrado con la selecciona de los Spinner
                            final DateTime mDesde = new DateTime(mDesdePicker.getYear(),mDesdePicker.getMonth()+1,mDesdePicker.getDayOfMonth(),0,0,0);
                            final DateTime mHasta = new DateTime(mHastaPicker.getYear(),mHastaPicker.getMonth()+1,mHastaPicker.getDayOfMonth(),0,0,0);
                            listener.onPossitiveButtonClick(mStateSpinner.getItemAtPosition(mStateSpinner.getSelectedItemPosition()).toString(),
                                    mTipoSpinner.getItemAtPosition(mTipoSpinner.getSelectedItemPosition()).toString(),
                                    mSectorSpinner.getItemAtPosition(mSectorSpinner.getSelectedItemPosition()).toString(),
                                    mDesde,mHasta,false);
                         }
                        })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //listener.onNegativeButtonClick();
                                dialog.dismiss();
                            }
                        });
        //iniciarFecha(v);
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

     private void iniciarFecha(android.view.View view) {
        textFecha = (TextView) view.findViewById(R.id.fecha_text);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("E MMM d yyyy", Locale.getDefault());
        textFecha.setText(format.format(c.getTime()));

        textFecha.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        new DateDialog().show(getFragmentManager(), "DatePickerInFull");
                        // TODO: Comunicarle a la actividad que inicie el diálogo de fecha
                    }
                }
        );
    }
}
