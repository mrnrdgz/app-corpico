package ar.com.corpico.appcorpico.orders.presentation;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;

public class CuadrillaAdapter extends ArrayAdapter<Tipo_Cuadrilla> {

    public CuadrillaAdapter(Context context, List<Tipo_Cuadrilla> objects) {
        super(context, 0, objects);
    }

    @Override
    public android.view.View getView(final int position, android.view.View convertView, ViewGroup parent) {
       /*
       Obtener el objeto procesado actualmente
        */
        Tipo_Cuadrilla tipoCuadrilla;

        /*
        Obtener LayoutInflater de la actividad
         */
        LayoutInflater inflater = (LayoutInflater) parent.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        /*
        Evitar inflar de nuevo un elemento previamente inflado
         */
        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.list_item_cuadrilla,
                    parent,
                    false);
        }

        RadioButton cuadrillaRadioButton = (RadioButton)convertView.findViewById(R.id.CuadrillaradioButton);
        //TextView cuadrilla = (TextView) convertView.findViewById(R.id.Cuadrilla_text);
        // Tipo_Trabajo actual..
        tipoCuadrilla =  getItem(position);

        /*
        Asignar valores
         */
        cuadrillaRadioButton.setText(tipoCuadrilla.getTipo_cuadrilla());

        return convertView;
    }

}