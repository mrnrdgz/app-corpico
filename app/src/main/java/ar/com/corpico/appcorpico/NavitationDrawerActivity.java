package ar.com.corpico.appcorpico;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ar.com.corpico.appcorpico.home.HomeActivity;
import ar.com.corpico.appcorpico.orders.OrderActivity;

public class NavitationDrawerActivity extends AppCompatActivity {
    /**
     * Instancia del drawer
     */
    private DrawerLayout drawerLayout;

    /**
     * Titulo inicial del drawer
     */
    private String drawerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main2);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        setToolbar(); // Setear Toolbar como action bar

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        drawerTitle = getResources().getString(R.string.inicio_item);
       /* if (savedInstanceState == null) {
            selectItem(R.id.nav_inicio);
        }*/
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Marcar item presionado
                        menuItem.setChecked(true);

                        int opcion = menuItem.getItemId();
                        selectItem(opcion);
                        return true;
                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectItem(int opcion) {

        if (opcion == R.id.nav_inicio){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        if (opcion == R.id.nav_ordenes){
            Intent intent = new Intent(this, OrderActivity.class);
            startActivity(intent);
        }
        if (opcion == R.id.nav_log_out){
            finish();
        }

        drawerLayout.closeDrawers(); // Cerrar drawer

        setTitle(opcion); // Setear título actual

    }


}