package ar.com.corpico.appcorpico.orders.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;


/**
 * Created by Administrador on 07/01/2017.
 */

public class OrdersAdapter extends ArrayAdapter<Order>{
    public OrdersAdapter(Context context, List<Order> objects) {
        super(context,0,objects);
    }
    @Override
    public android.view.View getView(int position, android.view.View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_order,
                    parent,
                    false);
        }

        // Referencias UI.
        //TextView numero = (TextView) convertView.findViewById(R.id.tv_numero);
        android.view.View indicator = convertView.findViewById(R.id.indicator);
        TextView titular = (TextView) convertView.findViewById(R.id.titular_text);
        TextView domicilio = (TextView) convertView.findViewById(R.id.domicilio_text);
        TextView tipo = (TextView) convertView.findViewById(R.id.tipo_text);

        // Lead actual.
        Order order =  getItem(position);

        // Setup.
        //numero.setText(order.getNumero());
        titular.setText(order.getTitular());
        domicilio.setText(order.getDomicilio());
        tipo.setText(order.getTipo());
        String estado = order.getEstado();

        switch (estado) {
            case "Culminada":
                indicator.setBackgroundResource(R.drawable.green_indicator);
                break;
            case "Asignadas a cuadrilla X":
                indicator.setBackgroundResource(R.drawable.red_indicator);
                break;
            case "Pendiente":
                indicator.setBackgroundResource(R.drawable.yellow_indicator);
                break;
        }

        return convertView;
    }
}