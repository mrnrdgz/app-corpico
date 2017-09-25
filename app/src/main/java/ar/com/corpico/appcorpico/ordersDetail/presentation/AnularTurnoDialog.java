package ar.com.corpico.appcorpico.ordersDetail.presentation;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Locale;

import ar.com.corpico.appcorpico.R;

/**
 * Created by sistemas on 22/09/2017.
 */

public class AnularTurnoDialog extends DialogFragment {
    private TimePicker mTimePicker;
    private int mDia;
    private int mMes;
    private int mAnio;

    private int mHora;
    private int mMinutos;
    public AnularTurnoDialog(){
    }

    public interface OnAsignarTurnoDialogListener {
        void onPossitiveButtonTurnoClick(DateTime turno);// Eventos Botón Positivo
    }

    AsignarTurnoDialog.OnAsignarTurnoDialogListener listener;
    public static AnularTurnoDialog newInstance (int day, int month, int year, int hour, int minute){
        AnularTurnoDialog f = new AnularTurnoDialog();

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
        return onCreateAnularTurnoDialog();
    }

    public AlertDialog onCreateAnularTurnoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.datePicker);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        android.view.View v = inflater.inflate(R.layout.dialog_anular_turno, null);
        builder.setView(v);
        builder.setTitle("Anular Turno?");

        TextView turno = (TextView) v.findViewById(R.id.turno_label);
        final DateTime mTurno = new DateTime(mAnio,mMes,mDia,mHora,mMinutos);
        turno.setText(mTurno.toString("dd-MM-yyyy HH:mm"));

        Button anular = (Button) v.findViewById(R.id.anular_boton);
        Button cancelar = (Button) v.findViewById(R.id.cancelar_boton);

        anular.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        listener.onPossitiveButtonTurnoClick(null);
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
}
