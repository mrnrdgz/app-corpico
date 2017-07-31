package ar.com.corpico.appcorpico.orders.presentation;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.OrderPendienteActivity;
import ar.com.corpico.appcorpico.orders.domain.entity.Zona;

import static java.security.AccessController.getContext;

public class OrdersFilterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private List<String> mTipoTrabajo = new ArrayList<>();
    private List<String> mTipoTrabajoSelected = new ArrayList<>();
    private List<Integer> mTipoTrabajoId = new ArrayList<>();
    private List<String> mZona = new ArrayList<>();
    private List<String> mZonaSelected = new ArrayList<>();
    private List<Integer> mZonaId = new ArrayList<>();
    private String mEstado;
    private AppCompatCheckBox[] TipoTrabajoChk;
    private AppCompatCheckBox[] ZonaChk;
    private LinearLayout seccionZona;
    private LinearLayout seccionTiposTrabajo;
    TextView mDesdeFecha;
    TextView mHastaFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_filters);

        Bundle bundle = getIntent().getExtras();
        mEstado = bundle.getString("ESTADO");
        mTipoTrabajo= bundle.getStringArrayList("TIPO_TRABAJO");
        mTipoTrabajoSelected= bundle.getStringArrayList("TIPO_TRABAJO_SELECTED");
        mZona= bundle.getStringArrayList("ZONA");
        mZonaSelected= bundle.getStringArrayList("ZONA_SELECTED");

        mDesdeFecha = (TextView) this.findViewById(R.id.desde_text);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        mDesdeFecha.setText(format.format(c.getTime()));
        mDesdeFecha.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        DateDialog fechaDialogDesde = new DateDialog();
                        fechaDialogDesde.show(getSupportFragmentManager(),"datePickerDesde");
                    }
                }
        );
        mHastaFecha = (TextView) this.findViewById(R.id.hasta_text);
        Calendar d = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        mHastaFecha.setText(format1.format(d.getTime()));
        mHastaFecha.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        DateDialog fechaDialogHasta = new DateDialog();
                        fechaDialogHasta.show(getSupportFragmentManager(),"datePickerHasta");
                    }
                }
        );

        //LAYOUT para los CheckBox Tipos de Trabajos
        seccionTiposTrabajo = (LinearLayout) findViewById(R.id.Seccion_TipoTrabajo);

        //CHECKBOX para Tipos de Trabajo
        TipoTrabajoChk= new AppCompatCheckBox[mTipoTrabajo.size()];

        for (int i=0; i< mTipoTrabajo.size(); i++) {
            TipoTrabajoChk[i]= new AppCompatCheckBox(this);
            TipoTrabajoChk[i].setText(mTipoTrabajo.get(i));
            TipoTrabajoChk[i].setId(Integer.valueOf(i));
            TipoTrabajoChk[i].setOnClickListener(ckListenerTipo);
            //Marca los Tipos de Trabajo que estan seleccionados previamente
            if(mTipoTrabajoSelected!=null){
                for(int j=0; j< mTipoTrabajoSelected.size(); j++) {
                    if(TipoTrabajoChk[i].getText().equals(mTipoTrabajoSelected.get(j))){
                        TipoTrabajoChk[i].setChecked(true);
                    }
                }
            }
            //Define Layout
            TipoTrabajoChk[i].setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            TipoTrabajoChk[i].setPadding(16,16,16,16);
            //Agrega los CHECBBOX al Layout
            seccionTiposTrabajo.addView(TipoTrabajoChk[i]);
        }

        //LAYOUT para los CheckBox Zona
        seccionZona = (LinearLayout) findViewById(R.id.Seccion_Zona);

        //CHECKBOX para Zona
        ZonaChk = new AppCompatCheckBox[mZona.size()];
        for (int i=0; i< mZona.size(); i++) {
            ZonaChk[i] = new AppCompatCheckBox(this);
            //setAppCompatCheckBoxColors((AppCompatCheckBox) Zona,Color.BLACK,Color.GREEN);
            ZonaChk[i].setText(mZona.get(i));
            ZonaChk[i].setId(Integer.valueOf(i));
            ZonaChk[i].setOnClickListener(ckListenerZona);
            //Marca las Zonas que estan seleccionados previamente
            if(mZonaSelected!=null){
                for(int j=0; j< mZonaSelected.size(); j++) {
                    if(ZonaChk[i].getText().equals(mZonaSelected.get(j))){
                        ZonaChk[i].setChecked(true);
                    }
                }
            }
            //Define Layout
            ZonaChk[i].setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ZonaChk[i].setPadding(16,16,16,16);
            //Agrega los CHECBBOX al Layout
            seccionZona.addView(ZonaChk[i]);
        }

        setToolbar();
    }
    //Funcion para setear los colores de checkbox
    public static void setAppCompatCheckBoxColors(final AppCompatCheckBox  _checkbox, final int _uncheckedColor, final int _checkedColor) {
        int[][] states = new int[][]{new int[]{-android.R.attr.state_checked}, new int[]{android.R.attr.state_checked}};
        int[] colors = new int[]{_uncheckedColor, _checkedColor};
        _checkbox.setSupportButtonTintList(new ColorStateList(states, colors));
    }
    //Evento click de checkbox Tipo
    private android.view.View.OnClickListener ckListenerTipo = new android.view.View.OnClickListener() {
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
                ((CheckBox) v).setChecked(false);
            }
        }
    };
    //Evento click de checkbox Zona
    private android.view.View.OnClickListener ckListenerZona = new android.view.View.OnClickListener() {
        @Override
        public void onClick(android.view.View v) {
            int id = v.getId();
            boolean checked = ((CheckBox) v).isChecked();
            if(checked){
                mZonaId.add(id);
                mZonaSelected.add(mZona.get(id).toString());
            }else{
                mZonaId.remove(new Integer(id));
                mZonaSelected.remove(mZona.get(id));
                ((CheckBox) v).setChecked(false);
                 v.invalidate();
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
                databack.putStringArrayListExtra("ZONA_SELECTED", (ArrayList<String>) mZonaSelected);
                setResult(RESULT_OK,databack);
                finish();
                break;
            case R.id.action_limpiar:
                for (int i=0; i< mTipoTrabajo.size(); i++) {
                    TipoTrabajoChk[i].setChecked(false);
                }
                mTipoTrabajoSelected = new ArrayList<>();
                for (int i=0; i< mZona.size(); i++) {
                    ZonaChk[i].setChecked(false);
                }
                mZonaSelected =new ArrayList<>();

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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(view.getTag().equals("datePickerDesde")){
            setDateDesdeView(year, month,dayOfMonth );
        }
        if(view.getTag().equals("datePickerHasta")){
            setDateHastaView(year, month,dayOfMonth );
        }
    }
    public void setDateDesdeView(int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        mDesdeFecha.setText(format.format(c.getTime()));
    }
    public void setDateHastaView(int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        mHastaFecha.setText(format.format(c.getTime()));
    }
}
