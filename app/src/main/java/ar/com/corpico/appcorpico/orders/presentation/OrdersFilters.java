package ar.com.corpico.appcorpico.orders.presentation;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.OrderPendienteActivity;

public class OrdersFilters extends AppCompatActivity {
    private List<String> mTipoTrabajo = new ArrayList<>();
    private List<String> mTipoTrabajoSelected = new ArrayList<>();
    private String mEstado;
    private CheckBox Tipo;

    public interface TipoTrabajoListener{
        void SetTipoTrabajo(ArrayList<String> mTipoTrabajo);
    }
    TipoTrabajoListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_filters);

        Bundle bundle = getIntent().getExtras();
        mEstado = bundle.getString("ESTADO");
        mTipoTrabajo= bundle.getStringArrayList("TIPO_TRABAJO");
        mTipoTrabajoSelected= bundle.getStringArrayList("TIPO_TRABAJO_SELECT");

        LinearLayout seccionTiposTrabajo = (LinearLayout) findViewById(R.id.Seccion_TipoTrabajo);
        for (int i=0; i< mTipoTrabajo.size(); i++) {
            Tipo = new CheckBox(this);
            Tipo.setText(mTipoTrabajo.get(i));
            if(mTipoTrabajoSelected!=null){
                for(int j=0; j< mTipoTrabajoSelected.size(); j++) {
                    if(Tipo.getText().equals(mTipoTrabajoSelected.get(j))){
                        Tipo.setChecked(true);
                    }
                }
            }
            Tipo.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            Tipo.setPadding(16,16,16,16);
            seccionTiposTrabajo.addView(Tipo);
        }
        Tipo.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if (Tipo.isChecked()) {
                    // Guardar indice seleccionado
                    mTipoTrabajoSelected.add((String) Tipo.getText());
                } else if (mTipoTrabajoSelected.contains(Tipo.getText())){
                    // Remover indice sin selección
                    //Arrays.binarySearch(mTipoTrabajoSelected.toArray(),Tipo.getText());
                    mTipoTrabajoSelected.remove(Arrays.binarySearch(mTipoTrabajoSelected.toArray(),Tipo.getText()));
                }
            }
        });
        setToolbar();
    }
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Habilitar el Up Button
            ab.setDisplayHomeAsUpEnabled(true);
            // Cambiar icono del Up Button
            ab.setHomeAsUpIndicator(R.drawable.ic_close);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ordersfilsters, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_aplicar:
                //listener.SetTipoTrabajo();
                OrderPendienteActivity mAct = new OrderPendienteActivity();
                mAct.SetTipoTrabajo(mTipoTrabajo);
                break;
            case R.id.action_limpiar:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (TipoTrabajoListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnSimpleDialogListener");
        }
    }
}
