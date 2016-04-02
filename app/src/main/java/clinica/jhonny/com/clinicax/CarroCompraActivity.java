package clinica.jhonny.com.clinicax;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import clinica.jhonny.com.Util;
import clinica.jhonny.com.adapters.CustomAdapterCarroCompra;
import clinica.jhonny.com.model.ItemCesta;


public class CarroCompraActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView lista = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_carro_compra);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Carro de la compra");
            setSupportActionBar(toolbar);

            TextView textSubTotal = (TextView)findViewById(R.id.textViewSubtotal);
            textSubTotal.setText("0 EUR");
            TextView textTotal = (TextView)findViewById(R.id.textViewTotal);
            textTotal.setText("0 EUR");

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            if(Util.getCarroDeLaCompra() != null && !Util.getCarroDeLaCompra().isEmpty()) {
                lista = (ListView) findViewById(R.id.listView);
                CustomAdapterCarroCompra customAdapter = new CustomAdapterCarroCompra(this, Util.getCarroDeLaCompra(), textSubTotal, textTotal);
                Util.setCustomAdapter(customAdapter);
                lista.setAdapter(customAdapter);

            }else {
                Toast.makeText(this, "Cesta vacia", Toast.LENGTH_SHORT).show();
            }

            Util.actualizaTotales(textSubTotal, textTotal);

        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.carro_compra, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        try {
            // Handle navigation view item clicks here.
            int id = item.getItemId();
            Class destino = null;

            if (id == R.id.nav_login) {
                destino = LoginActivity.class;
            } else if (id == R.id.nav_continuar) {
                destino = MainActivity.class;
            } else if (id == R.id.nav_paypal) {
                destino = PaypalActivity.class;
            } else if (id == R.id.nav_navegacion) {
                destino = NavDrawerActivity.class;
            } else if (id == R.id.nav_colapsable) {
                destino = ToolBarColapsable.class;
            } else if (id == R.id.nav_share) {
                destino = FullscreenActivity.class;
            } else if (id == R.id.nav_send) {
                destino = FullscreenActivity.class;
            } else if (id == R.id.nav_dev) {
                destino = FullscreenActivity.class;
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            Intent intent = new Intent(this, destino);
            startActivity(intent);

        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
}
