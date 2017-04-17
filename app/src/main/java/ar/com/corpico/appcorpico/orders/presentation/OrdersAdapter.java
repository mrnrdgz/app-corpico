package ar.com.corpico.appcorpico.orders.presentation;

import android.content.Context;
import android.content.DialogInterface;
import android.view.*;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

import static android.R.attr.onClick;
import static android.R.attr.order;
import static android.R.attr.targetActivity;
import static ar.com.corpico.appcorpico.R.drawable.ic_orders;
import android.graphics.drawable.RippleDrawable;
import android.widget.Toast;


/**
 * Created by Administrador on 07/01/2017.
 */

public class OrdersAdapter extends ArrayAdapter<Order> {
    OnAsignarListener listenerAdapter;

    public OrdersAdapter(Context context, List<Order> objects) {
        super(context,0,objects);
    }

    public interface OnAsignarListener {
        void onButtonClickListner(int position);
    }

    public void setCustomButtonListner(OnAsignarListener listener) {
        this.listenerAdapter = listener;
    }

    @Override
    public android.view.View getView(final int position, android.view.View convertView, ViewGroup parent) {
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
        ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.button);

        indicator.setFocusable(false);
        titular.setFocusable(false);
        domicilio.setFocusable(false);
        tipo.setFocusable(false);
        imageButton.setFocusable(true);


        imageButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
               if (listenerAdapter != null) {
                listenerAdapter.onButtonClickListner(position);
               }
            }
        });

        // Lead actual..
        Order order =  getItem(position);

        // Setup.
        //numero.setText(order.getNumero());
        titular.setText(order.getTitular());
        domicilio.setText(order.getDomicilio());
        tipo.setText(order.getTipo());

        String estado = order.getCurrentState(order.getEtapas());

        switch (estado) {
            case "Culminada":
                indicator.setBackgroundResource(R.drawable.green_indicator);
                break;
            case "Cerrada":
                indicator.setBackgroundResource(R.drawable.red_indicator);
                break;
            /*case "Asignadas a cuadrilla X":
                indicator.setBackgroundResource(R.drawable.red_indicator);
                break;*/
            case "Pendiente":
                indicator.setBackgroundResource(R.drawable.yellow_indicator);
                //imageButton.setBackgroundResource(R.drawable.ic_orders);
                break;
        }

        return convertView;
    }

}