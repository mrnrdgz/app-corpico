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
import java.util.Locale;

import ar.com.corpico.appcorpico.R;

/**
 * Created by sistemas on 15/09/2017.
 */

public class AsignarTurnoDialog extends DialogFragment {
    private TimePicker mTimePicker;
    private int mDia;
    private int mMes;
    private int mAnio;

    private int mHora;
    private int mMinutos;
    public AsignarTurnoDialog(){
    }

    public interface OnAsignarTurnoDialogListener {
        void onPossitiveButtonTurnoClick(DateTime turno);// Eventos Botón Positivo
    }

    OnAsignarTurnoDialogListener listener;
    public static AsignarTurnoDialog newInstance (Integer day, Integer month, Integer year, Integer hour, Integer minute){
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
        mTimePicker = (TimePicker) v.findViewById(R.id.timePicker);

        DateTime d = new DateTime(mAnio, mMes, mDia,mHora,mMinutos);
        Calendar c = d.toCalendar(Locale.getDefault());

        mDatePicker.init(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH),new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year,month,dayOfMonth);

                DateTime mFecha = new DateTime(c);

                mAnio = mFecha.getYear();
                mMes = mFecha.getMonthOfYear();
                mDia = mFecha.getDayOfMonth();
            }
        });

        mTimePicker.setIs24HourView(true);
        setTime(mHora,mMinutos);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //updateDisplay(hourOfDay, minute);
                mHora = hourOfDay;
                mMinutos = minute;
            }
        });

        Button asignar = (Button) v.findViewById(R.id.asignar_boton);
        Button cancelar = (Button) v.findViewById(R.id.cancelar_boton);

        asignar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        DateTime mTurno = new DateTime(mAnio,mMes,mDia,mHora,mMinutos);
                        listener.onPossitiveButtonTurnoClick(mTurno);
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
    private void setTime(int hour, int minute) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker.setHour(hour);
            mTimePicker.setMinute(minute);
        } else {
            mTimePicker.setCurrentHour(hour);
            mTimePicker.setCurrentMinute(minute);
        }
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
}
