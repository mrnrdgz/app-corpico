package ar.com.corpico.appcorpico.orders.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class OrdersAdapter extends BaseAdapter implements Filterable{
        //ArrayAdapter<Order> implements Filterable{
    private OrderFilter mOrderFilter;
    private List<Order> mOrderList;
    private List<Order> mFilteredList;
    private LayoutInflater inflater;

   /*public OrdersAdapter(Context context, List<Order> objects) {
      super(context, 0, objects);
    }*/
    public OrdersAdapter(Context context, List<Order> objects) {
        //super(context, 0, objects);
        this.mOrderList = objects;
        this.mOrderList = objects;
        this.inflater = LayoutInflater.from(context);
        getFilter();
    }
    @Override
    public int getCount() {
        return mFilteredList.size();
    }

    /**
     * Get specific item from user list
     * @param i item index
     * @return list item
     */
    @Override
    public Object getItem(int i) {
        return mFilteredList.get(i);
    }

    /**
     * Get user list item id
     * @param i item index
     * @return current item id
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final Order order = (Order) getItem(position);
        // Obtener inflater.
        /*LayoutInflater inflater = (LayoutInflater) getContext();
                                     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_order,
                    parent,
                    false);

            holder = new ViewHolder();
            // Referencias UI.
            holder.numero = (TextView) convertView.findViewById(R.id.tv_numero);
            holder.titular = (TextView) convertView.findViewById(R.id.tv_titular);
            holder.domicilio = (TextView) convertView.findViewById(R.id.tv_domicilio);
            holder.tipo = (TextView) convertView.findViewById(R.id.tv_tipo);

            // Lead actual.
            //Order order = getItem(position);

            // Setup.
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
            holder.numero.setText(order.getNumero());
            holder.titular.setText(order.getTitular());
            holder.domicilio.setText(order.getDomicilio());
            holder.tipo.setText(order.getTipo());

            return convertView;
    }

    @Override
    public Filter getFilter() {
        if (mOrderFilter == null) {
            mOrderFilter = new OrderFilter();
        }

        return mOrderFilter;
    }

    private class OrderFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<Order> tempList = new ArrayList<Order>();

                // search content in friend list
                for (Order order : mOrderList) {
                    if (order.getTitular().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(order);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = mOrderList.size();
                filterResults.values = mOrderList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mFilteredList = (ArrayList<Order>) results.values;
            notifyDataSetChanged();
        }
    }
    static class ViewHolder {
        TextView numero;
        TextView titular;
        TextView domicilio;
        TextView tipo;
    }
}