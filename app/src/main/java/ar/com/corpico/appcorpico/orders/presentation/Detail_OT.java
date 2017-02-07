package ar.com.corpico.appcorpico.orders.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Gallery;
import android.widget.TextView;

import ar.com.corpico.appcorpico.R;

public class Detail_OT extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_ot);

        // Obtención de views
        TextView numero = (TextView)this.findViewById(R.id.numero_text);
        TextView fecha = (TextView)this.findViewById(R.id.fecha_text);
        TextView estado = (TextView)this.findViewById(R.id.estado_text);
        TextView tipo = (TextView)this.findViewById(R.id.tipo_text);
        TextView sector = (TextView)this.findViewById(R.id.sector_text);
        TextView observacion = (TextView)this.findViewById(R.id.observacion_text);
        Gallery simpleGallery = (Gallery) findViewById(R.id.simpleGallery);

        setToolbar();
        Intent intent=getIntent();
        Bundle extras =intent.getExtras();
        if (extras != null) {
            numero.setText((String)extras.get("Numero"));
            fecha.setText((String)extras.get("Fecha"));
            estado.setText((String)extras.get("Estado"));
            tipo.setText((String)extras.get("Tipo"));
            sector.setText((String)extras.get("Sector"));
            observacion.setText((String)extras.get("Observacion"));
        }
    }
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_ot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_editarOrder:

                break;
            case R.id.action_photoOrder:

                break;
            case R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
