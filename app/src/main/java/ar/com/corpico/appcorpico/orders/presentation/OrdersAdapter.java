package ar.com.corpico.appcorpico.orders.presentation;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.*;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;


/**
 * Created by Administrador on 07/01/2017.
 */

public class OrdersAdapter extends ArrayAdapter<Order> {
    OnAsignarListener listenerAdapter;
    //TODO ESTA BIEN QUE DEBLARE ORDER ACA? XQ SINO NO LO VEIA DENTRO DEL ONCLICK DEL IMAGEBUTTON
    private Order order;
    private ArrayList<Integer> mSelection = new ArrayList<Integer>();
    private boolean hideAsignarButton = false;

    public OrdersAdapter(Context context, List<Order> objects) {
        super(context,0,objects);
    }

    public interface OnAsignarListener {
        void onButtonClickListner(ArrayList<String> numero);
    }

    public void setCustomButtonListner(OnAsignarListener listener) {
        this.listenerAdapter = listener;
    }

    public void setNewSelection(int position) {
        mSelection.add(position);
        notifyDataSetChanged();
    }
    public ArrayList<Integer> getCurrentCheckedPosition() {
        return mSelection;
    }
    public void removeSelection(int position) {
        mSelection.remove(Integer.valueOf(position));
        notifyDataSetChanged();
    }
    public void clearSelection() {
        mSelection = new ArrayList<Integer>();
        notifyDataSetChanged();
    }
    public int getSelectionCount() {
        return mSelection.size();
    }

    public boolean hideAsignarButton(){
        hideAsignarButton=true;
        return hideAsignarButton;
    }
    public boolean showAsignarButton(){
        hideAsignarButton=false;
        return hideAsignarButton;
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
        final TextView titular = (TextView) convertView.findViewById(R.id.titular_text);
        TextView domicilio = (TextView) convertView.findViewById(R.id.domicilio_text);
        TextView tipo = (TextView) convertView.findViewById(R.id.tipo_text);
        TextView fecha = (TextView) convertView.findViewById(R.id.fechaSolicitud_text);
        //ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.button);

        indicator.setFocusable(false);
        titular.setFocusable(false);
        domicilio.setFocusable(false);
        tipo.setFocusable(false);
        fecha.setFocusable(false);
        /*imageButton.setFocusable(true);

        if (hideAsignarButton){
            imageButton.setVisibility(View.INVISIBLE);
        }else{
            imageButton.setVisibility(View.VISIBLE);
        }
        imageButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
            if (listenerAdapter != null) {
                ArrayList<String> aux = new ArrayList();
                aux.add(0,getItem(position).getNumero());
                listenerAdapter.onButtonClickListner(aux);
            }
            }
        });*/

        // Orden actual..
        order =  getItem(position);

        //numero.setText(order.getNumero());
        titular.setText(order.getTitular());
        domicilio.setText(order.getDomicilio());
        tipo.setText(order.getTipo_Trabajo());

        String dia = order.getFechaSolicitud().substring(8,10);
        String mes = order.getFechaSolicitud().substring(5,7);
        String ano = order.getFechaSolicitud().substring(0,4);
        fecha.setText(dia + "/" + mes + "/" + ano);

        String estado = order.getCurrentState(order.getEtapas());

        switch (estado) {
            case "Pendiente":
                indicator.setBackgroundResource(R.drawable.pendientes_indicator);
                //imageButton.setBackgroundResource(R.drawable.ic_orders);
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


        if (mSelection.contains(position)) {
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(),  R.color.colorMark));
        }

        return convertView;
    }

}