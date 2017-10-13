package ar.com.corpico.appcorpico.ordersDetail.presentation;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;


/**
 * Created by Administrador on 07/01/2017.
 */

public class EtapasAdapter extends ArrayAdapter<Etapa> {

    //TODO ESTA BIEN QUE DEBLARE ORDER ACA? XQ SINO NO LO VEIA DENTRO DEL ONCLICK DEL IMAGEBUTTON
    private Etapa etapa;

    public EtapasAdapter(Context context, List<Etapa> objects) {
        super(context,0,objects);
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_etapas,
                    parent,
                    false);
        }

        // Referencias UI.
        View indicator = convertView.findViewById(R.id.indicator);
        final TextView fechaEtapa = (TextView) convertView.findViewById(R.id.fechaEtapa_text);
        TextView operarioEtapa = (TextView) convertView.findViewById(R.id.operarioEtapa_text);
        TextView observacionEtapa = (TextView) convertView.findViewById(R.id.observacionEtapa_text);

        indicator.setFocusable(false);

        // Orden actual..
        etapa =  getItem(position);

        fechaEtapa.setText(etapa.getFecha().toString("dd-MM-yyyy"));
        operarioEtapa.setText(etapa.getUsuario());
        observacionEtapa.setText(etapa.getObservacion());

        String estado = etapa.getEstado();

        switch (estado) {
            case "Pendiente":
                indicator.setBackgroundResource(R.drawable.pendientes_indicator);
                break;
            case "Asignada":
                indicator.setBackgroundResource(R.drawable.asignadas_indicator);
                break;
            case "Culminada":
                indicator.setBackgroundResource(R.drawable.culminadas_indicator);
                break;
            case "No Culminada":
                indicator.setBackgroundResource(R.drawable.noculminadas_indicator);
                break;
            case "Supervisadas":
                indicator.setBackgroundResource(R.drawable.supervisadas_indicator);
                break;
        }

        convertView.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));

        return convertView;
    }

}