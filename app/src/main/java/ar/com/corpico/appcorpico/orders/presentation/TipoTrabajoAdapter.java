package ar.com.corpico.appcorpico.orders.presentation;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;

public class TipoTrabajoAdapter extends ArrayAdapter<Tipo_Trabajo> {

    public TipoTrabajoAdapter(Context context, List<Tipo_Trabajo> objects) {
        super(context, 0, objects);
    }

    @Override
    public android.view.View getView(final int position, android.view.View convertView, ViewGroup parent) {
       /*
       Obtener el objeto procesado actualmente
        */
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
                    R.layout.custom_spinner_item,
                    parent,
                    false);
        }

        /*
        Instancias del Texto y el Icono
         */
        TextView name = (TextView) convertView.findViewById(R.id.tipo_trabajo);
        name.setTextColor(Color.parseColor("#000000")); //Texto color Negro
        // Tipo_Trabajo actual..
        tipoTrabajo =  getItem(position);

        /*
        Asignar valores
         */
        name.setText(tipoTrabajo.getTipoTrabajo());

        return convertView;
    }

    @Override
    public android.view.View getDropDownView(int position, android.view.View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }
}