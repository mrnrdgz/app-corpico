package ar.com.corpico.appcorpico.orders.ordersFilter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ar.com.corpico.appcorpico.R;

public class OrdersFilterActivity extends AppCompatActivity {
    private List<String> mTipoTrabajo = new ArrayList<>();
    private List<String> mTipoTrabajoSelected = new ArrayList<>();
    private List<Integer> mTipoTrabajoId = new ArrayList<>();
    private List<String> mZona = new ArrayList<>();
    private List<String> mZonaSelected = new ArrayList<>();
    private List<Integer> mZonaId = new ArrayList<>();
    private String mEstado;
    private String mTipoCuadrilla;
    private DateTime mFechaInicioSeleccionada;
    private DateTime mFechaFinSeleccionada;
    private AppCompatCheckBox[] TipoTrabajoChk;
    private AppCompatCheckBox[] ZonaChk;
    private LinearLayout seccionZona;
    private LinearLayout seccionTiposTrabajo;
    private TextView mFechaInicio;
    private TextView mFechaFin;
    private DatePickerDialog.OnDateSetListener dpd;
    private DatePickerDialog.OnDateSetListener dph;
    private Calendar cal = Calendar.getInstance();

    public static final String ARG_ESTADO = "orders.estado";
    public static final String ARG_TIPO_CUADRILLA = "orders.tipo_cuadrilla";
    private static final String ARG_TIPOS_TRABAJO_SELECCIONADOS = "orders.tipos_trabajo_seleccionados";
    public static final String ARG_ZONAS_SELECCIONADAS = "orders.zonas_seleccionadas";
    public static final String ARG_FECHA_INICIO = "orders.fecha_inicio";
    public static final String ARG_FECHA_FIN = "orders.fecha_fin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_filters);

        Bundle args = getIntent().getExtras();
        mEstado = args.getString(ARG_ESTADO);
        mTipoCuadrilla = args.getString(ARG_TIPO_CUADRILLA);
        mTipoTrabajoSelected = args.getStringArrayList(ARG_TIPOS_TRABAJO_SELECCIONADOS);
        mZonaSelected = args.getStringArrayList(ARG_ZONAS_SELECCIONADAS);
        mFechaInicioSeleccionada = (DateTime) args.getSerializable(ARG_FECHA_INICIO);
        mFechaFinSeleccionada = (DateTime) args.getSerializable(ARG_FECHA_FIN);

        mFechaInicio = (TextView) this.findViewById(R.id.desde_text);
        if(mFechaInicioSeleccionada!=null){
            Calendar c = mFechaInicioSeleccionada.toGregorianCalendar();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            mFechaInicio.setText(format.format(c.getTime()));
        }else{
            Calendar c = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            mFechaInicio.setText(format.format(c.getTime()));
        }
        mFechaInicio.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        new DatePickerDialog(OrdersFilterActivity.this, dpd,
                                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                                cal.get(Calendar.DAY_OF_MONTH)).show();

                    }
                }
        );
        dpd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthofyear,
                                  int dayofmonth) {
                setDateDesdeView(year,monthofyear,dayofmonth);

            }
        };
        mFechaFin = (TextView) this.findViewById(R.id.hasta_text);
        if(mFechaFinSeleccionada!=null){
            Calendar c = mFechaFinSeleccionada.toGregorianCalendar();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            mFechaFin.setText(format.format(c.getTime()));
        }else{
            Calendar c = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            mFechaFin.setText(format.format(c.getTime()));
        }
        mFechaFin.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        new DatePickerDialog(OrdersFilterActivity.this, dph,
                                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                                cal.get(Calendar.DAY_OF_MONTH)).show();

                    }
                }
        );

        dph = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthofyear,
                                  int dayofmonth) {
                setDateHastaView(year,monthofyear,dayofmonth);

            }
        };
        if(mFechaInicioSeleccionada==null){
            mFechaInicioSeleccionada=new DateTime(DateTime.now());
        }
        if(mFechaFinSeleccionada==null){
            mFechaFinSeleccionada=new DateTime(DateTime.now());
        }
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
                databack.putStringArrayListExtra(ARG_TIPOS_TRABAJO_SELECCIONADOS, (ArrayList<String>) mTipoTrabajoSelected);
                databack.putStringArrayListExtra(ARG_ZONAS_SELECCIONADAS, (ArrayList<String>) mZonaSelected);
                databack.putExtra(ARG_FECHA_INICIO, mFechaInicioSeleccionada);
                databack.putExtra(ARG_FECHA_FIN, mFechaFinSeleccionada);
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
                Calendar c = mFechaFinSeleccionada.toGregorianCalendar();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                mFechaInicio.setText(format.format(c.getTime()));
                mFechaFin.setText(format.format(c.getTime()));
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

   /* @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(view.getTag().equals("datePickerDesde")){
            setDateDesdeView(year, month,dayOfMonth );

        }
        if(view.getTag().equals("datePickerHasta")){
            setDateHastaView(year, month,dayOfMonth );
        }
    }*/
    public void setDateDesdeView(int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        mFechaInicio.setText(format.format(c.getTime()));
        //mFechaDesdeSelected= new  DateTime(year, monthOfYear, dayOfMonth, 0, 0, 0, 0);
        mFechaInicioSeleccionada= new DateTime(c);
    }

    public void setDateHastaView(int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        mFechaFin.setText(format.format(c.getTime()));
        //mFechaHastaSelected=new  DateTime(year, monthOfYear, dayOfMonth, 0, 0, 0, 0);
        mFechaFinSeleccionada = new DateTime(c);
    }
}
