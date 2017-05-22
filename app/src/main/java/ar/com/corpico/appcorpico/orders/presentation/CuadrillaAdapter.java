package ar.com.corpico.appcorpico.orders.presentation;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

import static android.R.attr.id;
import static android.R.attr.name;
import static ar.com.corpico.appcorpico.R.id.cuadrilla;

public class CuadrillaAdapter extends ArrayAdapter<Cuadrilla> {

    public CuadrillaAdapter(Context context, List<Cuadrilla> objects) {
        super(context, 0, objects);
    }

    @Override
    public android.view.View getView(final int position, android.view.View convertView, ViewGroup parent) {
       /*
       Obtener el objeto procesado actualmente
        */
        Cuadrilla cuadrilla;

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
        TextView name = (TextView) convertView.findViewById(R.id.cuadrilla);

        if (position == 0) { //Primer elemento
            name.setTextColor(Color.parseColor("#FFFFFF")); //Texto color Blanco
        } else { //Otros elementos ...
            name.setTextColor(Color.parseColor("#000000")); //Texto color Negro
        }

        // Cuadrilla actual..
        cuadrilla =  getItem(position);

        /*
        Asignar valores
         */
        name.setText(cuadrilla.getCuadrilla());

        return convertView;
    }

    @Override
    public android.view.View getDropDownView(int position, android.view.View convertView, ViewGroup parent) {
        /*
        Debido a que deseamos usar spinner_item.xml para inflar los
        items del Spinner en ambos casos, entonces llamamos a getView()
         */
        return getView(position,convertView,parent);
    }
}