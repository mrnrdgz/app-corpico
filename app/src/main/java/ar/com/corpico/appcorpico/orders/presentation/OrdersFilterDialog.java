package ar.com.corpico.appcorpico.orders.presentation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.*;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.OrderActivity;

import static android.R.attr.format;


/**
 * Created by Administrador on 24/01/2017.
 */
//TODO: mejorar el diseño del layut...hacer que recuerde el seteo (fechas, etc) cuando vuelve a abrir el filtro
// TODO:...controlar las consultas y los estados...esta confusa la consulta y los colores...(no figura fecha en la respuesta.??)

public class OrdersFilterDialog extends DialogFragment{
    private TextView mDesdeFecha;
    private TextView mHastaFecha;
    public OrdersFilterDialog() {
    }

    public interface OnFilterDialogListener {
        void onPossitiveButtonClick(String estado, String tipo, String sector, DateTime desde, DateTime hasta,Boolean estadoActual);// Eventos Botón Positivo
        void onNegativeButtonClick();// Eventos Botón Negativo
        void onFechaTextViewClick();
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
        builder.setTitle("Filtro de búsqueda");

        final CheckBox mEstadoActual = (CheckBox) v.findViewById(R.id.chk_estado_actual);
        final Spinner mStateSpinner = (Spinner)v.findViewById(R.id.estado_spinner);
        final Spinner mTipoSpinner = (Spinner)v.findViewById(R.id.tipo_spinner);
        final Spinner mSectorSpinner = (Spinner)v.findViewById(R.id.sector_spinner);
        Button aplicar = (Button) v.findViewById(R.id.aplicar_boton);
        Button cancelar = (Button) v.findViewById(R.id.cancelar_boton);

        iniciarFechaDesde(v);
        iniciarFechaHasta(v);
        aplicar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        String mDesdeString = mDesdeFecha.getText().toString();
                        String mHastaString = mHastaFecha.getText().toString();
                        DateTime mDesde = DateTime.parse(mDesdeString, DateTimeFormat.forPattern("dd-MM-yyyy"));
                        DateTime mHasta = DateTime.parse(mHastaString, DateTimeFormat.forPattern("dd-MM-yyyy"));
                        listener.onPossitiveButtonClick(mStateSpinner.getItemAtPosition(mStateSpinner.getSelectedItemPosition()).toString(),
                                mTipoSpinner.getItemAtPosition(mTipoSpinner.getSelectedItemPosition()).toString(),
                                mSectorSpinner.getItemAtPosition(mSectorSpinner.getSelectedItemPosition()).toString(),
                                mDesde,mHasta,mEstadoActual.isChecked());
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

     private void iniciarFechaDesde(android.view.View v) {
        mDesdeFecha = (TextView) v.findViewById(R.id.desde_text);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        mDesdeFecha.setText(format.format(c.getTime()));
        mDesdeFecha.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        listener.onFechaTextViewClick();
                    }
                }
        );
    }
    private void iniciarFechaHasta(android.view.View v) {
        mHastaFecha = (TextView) v.findViewById(R.id.hasta_text);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        mHastaFecha.setText(format.format(c.getTime()));
        mHastaFecha.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        listener.onFechaTextViewClick();
                    }
                }
        );
    }
    public void setDateDesdeView(int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        mDesdeFecha.setText(format.format(c.getTime()));
    }
    public void setDateHastaView(int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        mHastaFecha.setText(format.format(c.getTime()));
    }
}
