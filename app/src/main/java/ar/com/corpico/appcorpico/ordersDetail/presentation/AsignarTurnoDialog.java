package ar.com.corpico.appcorpico.ordersDetail.presentation;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.*;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.joda.time.DateTime;

import java.util.Calendar;

import ar.com.corpico.appcorpico.R;

/**
 * Created by sistemas on 15/09/2017.
 */

public class AsignarTurnoDialog extends DialogFragment implements
        TimePicker.OnTimeChangedListener,
        DatePicker.OnDateChangedListener{
    private DateTime mTurno;
    private int mDia;
    private int mMes;
    private int mAnio;

    private int mHora;
    private int mMinutos;
    public AsignarTurnoDialog(){
    }
    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar mFecha = Calendar.getInstance();

        mFecha.set(Calendar.YEAR, year);
        mFecha.set(Calendar.MONTH, monthOfYear);
        mFecha.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }
    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        Calendar mHora =Calendar.getInstance();

        mHora.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mHora.set(Calendar.MINUTE, minute);
        mHora.set(Calendar.SECOND, 0);
    }

    public interface OnAsignarTurnoDialogListener {
        void onPossitiveButtonClick(DateTime turno);// Eventos Botón Positivo
    }

    OnAsignarTurnoDialogListener listener;
    public static AsignarTurnoDialog newInstance (int day, int month, int year, int hour, int minute){
        AsignarTurnoDialog f = new AsignarTurnoDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("DIA", day);
        args.putInt("MES", month);
        args.putInt("ANIO", year);

        args.putInt("HORA", hour);
        args.putInt("MINUTOS", minute);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDia = getArguments().getInt("DIA");
        mMes = getArguments().getInt("MES");
        mAnio = getArguments().getInt("ANIO");
        mHora = getArguments().getInt("HORA");
        mMinutos = getArguments().getInt("MINUTOS");
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
            return onCreateAsignarTurnoDialog();
    }
    public AlertDialog onCreateAsignarTurnoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.datePicker);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        android.view.View v = inflater.inflate(R.layout.dialog_asignar_turno, null);
        builder.setView(v);
        builder.setTitle("Asignar Turno");

        final DatePicker mDatePicker = (DatePicker) v.findViewById(R.id.datePicker);
        final TimePicker mTimePicker = (TimePicker) v.findViewById(R.id.timePicker);

        Calendar c = Calendar.getInstance();
        c.set(mAnio, mMes, mDia,mHora,mMinutos);

        mDatePicker.init(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH),null);
        mTimePicker.setIs24HourView(true);
        mTimePicker.setHour(mHora);
        mTimePicker.setMinute(mMinutos);

        Button asignar = (Button) v.findViewById(R.id.asignar_boton);
        Button cancelar = (Button) v.findViewById(R.id.cancelar_boton);

        /*iniciarFecha(v);
        iniciarHora(v);*/

        asignar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        /*String mDesdeString = mDesdeFecha.getText().toString();
                        String mHastaString = mHastaFecha.getText().toString();
                        DateTime mDesde = DateTime.parse(mDesdeString, DateTimeFormat.forPattern("dd-MM-yyyy"));
                        DateTime mHasta = DateTime.parse(mHastaString, DateTimeFormat.forPattern("dd-MM-yyyy"));*/
                        listener.onPossitiveButtonClick(DateTime.now());
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
            listener = (AsignarTurnoDialog.OnAsignarTurnoDialogListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnSimpleDialogListener");
        }
    }

    @Override
    public void onViewCreated(android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /*private void iniciarFecha(android.view.View v) {
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
    private void iniciarHora(android.view.View v) {
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
    public void setDateView(int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        mDesdeFecha.setText(format.format(c.getTime()));
    }
    public void setHoraView(int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        mHastaFecha.setText(format.format(c.getTime()));
    }*/
}
