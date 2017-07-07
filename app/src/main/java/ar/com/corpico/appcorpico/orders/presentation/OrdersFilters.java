package ar.com.corpico.appcorpico.orders.presentation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.*;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.OrderPendienteActivity;

public class OrdersFilters extends AppCompatActivity {
    private List<String> mTipoTrabajo = new ArrayList<>();
    private List<String> mTipoTrabajoSelected = new ArrayList<>();
    private List<Integer> mTipoTrabajoId = new ArrayList<>();
    private String mEstado;
    private CheckBox Tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_filters);

        Bundle bundle = getIntent().getExtras();
        mEstado = bundle.getString("ESTADO");
        mTipoTrabajo= bundle.getStringArrayList("TIPO_TRABAJO");
        mTipoTrabajoSelected= bundle.getStringArrayList("TIPO_TRABAJO_SELECTED");

        LinearLayout seccionTiposTrabajo = (LinearLayout) findViewById(R.id.Seccion_TipoTrabajo);
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_enabled}, // enabled
                new int[] {-android.R.attr.state_enabled}, // disabled
                new int[] {-android.R.attr.state_checked}, // unchecked
                new int[] { android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[] {
                Color.BLACK,
                Color.RED,
                Color.GREEN,
                Color.BLUE
        };

        /*int state[][] = {{android.R.attr.state_checked}, {}};
        int color[] = {color_for_state_checked, color_for_state_normal} */


        ColorStateList myList = new ColorStateList(states, colors);
        for (int i=0; i< mTipoTrabajo.size(); i++) {
            Tipo = new CheckBox(this);
            CompoundButtonCompat.setButtonTintList(Tipo, myList);
            Tipo.setText(mTipoTrabajo.get(i));
            Tipo.setId(Integer.valueOf(i));
            Tipo.setOnClickListener(ckListener);
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

        setToolbar();
    }
    private android.view.View.OnClickListener ckListener = new android.view.View.OnClickListener() {
        @Override
        public void onClick(android.view.View v) {
            int id = v.getId();
            boolean checked = ((CheckBox) v).isChecked();
            if(checked){
                mTipoTrabajoId.add(id);
                mTipoTrabajoSelected.add(mTipoTrabajo.get(id).toString());
            }else{
                mTipoTrabajoId.remove(new Integer(id));
                mTipoTrabajoSelected.remove(mTipoTrabajo.get(id));
            }
        }
    };

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
                Intent databack = new Intent();
                databack.putStringArrayListExtra("TIPO_TRABAJO_SELECTED", (ArrayList<String>) mTipoTrabajoSelected);
                setResult(RESULT_OK,databack);
                finish();
                break;
            case R.id.action_limpiar:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private ArrayList<String> SetTipoSelected(){
        for(int i= 0; i< mTipoTrabajoId.size();i++){
            int pos = mTipoTrabajoId.get(i);
            String mTipoT= mTipoTrabajo.get(pos);
            mTipoTrabajoSelected.add(mTipoT);
        }
        return (ArrayList<String>) mTipoTrabajoSelected;
    }
}
