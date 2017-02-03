package ar.com.corpico.appcorpico.orders.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 07/01/2017.
 */

public class OrdersAdapter extends ArrayAdapter<Order> implements Filterable{
    private List<Order> objects;

    public OrdersAdapter(Context context, int textViewResourceId, List<Order> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
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
        TextView numero = (TextView) convertView.findViewById(R.id.tv_numero);
        TextView titular = (TextView) convertView.findViewById(R.id.tv_titular);
        TextView domicilio = (TextView) convertView.findViewById(R.id.tv_domicilio);
        TextView tipo = (TextView) convertView.findViewById(R.id.tv_tipo);

        // Lead actual.
        Order order =  getItem(position);


        // Setup.
        numero.setText(order.getNumero());
        titular.setText(order.getTitular());
        domicilio.setText(order.getDomicilio());
        tipo.setText(order.getTipo());

        return convertView;
    }
    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<Order> tempList=new ArrayList<Order>();
            //constraint is the result from text you want to filter against.
            //objects is your data set you will filter from
            if(constraint != null && objects!=null) {
                int length=objects.size();
                int i=0;
                while(i<length){
                    Order item=objects.get(i);          //startsWith/contains
                    if (item.getTitular().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        tempList.add(item);
                    }
                    i++;
                }
                //following two lines is very important
                //as publish result can only take FilterResults objects
                filterResults.values = tempList;
                filterResults.count = tempList.size();
            }else{
                filterResults.values = objects;
                filterResults.count = objects.size();
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {
            objects = (ArrayList<Order>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    };
    @Override
    public Filter getFilter() {
        return myFilter;
    }
}