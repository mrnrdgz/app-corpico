package ar.com.corpico.appcorpico.orders.presentation;

import android.content.Context;
import android.graphics.Color;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;


public class CuadrillaAdapter extends ArrayAdapter<Tipo_Trabajo> {

    public CuadrillaAdapter(Context context, List<Tipo_Trabajo> objects) {
        super(context, 0, objects);
    }

    @Override
    public android.view.View getView(final int position, android.view.View convertView, ViewGroup parent) {
       /*
       Obtener el objeto procesado actualmente
        */
        //Tipo_Cuadrilla tipoCuadrilla;
        Tipo_Trabajo tipoTrabajo;

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

        final RadioButton cuadrillaRadioButton = (RadioButton)convertView.findViewById(R.id.CuadrillaradioButton);
        //TextView cuadrilla = (TextView) convertView.findViewById(R.id.Cuadrilla_text);
        // Tipo_Trabajo actual..
        tipoTrabajo = getItem(position);

        /*
        Asignar valores
         */
        cuadrillaRadioButton.setText(tipoTrabajo.getTipoCuadrilla());

        cuadrillaRadioButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {;
                //TODO:
                ArrayList<String> aux = new ArrayList();
                aux.add(0,getItem(position).getTipoCuadrilla());
                //listenerAdapter.onButtonClickListner(aux);
            }
        });

        return convertView;
    }

}