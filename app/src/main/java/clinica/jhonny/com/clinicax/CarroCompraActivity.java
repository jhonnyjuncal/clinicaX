package clinica.jhonny.com.clinicax;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import clinica.jhonny.com.adapters.CustomAdapterCarroCompra;
import clinica.jhonny.com.model.Producto;


public class CarroCompraActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView lista = null;
    private List<Producto> listaProductos = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro_compra);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Carro de la compra");
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listaProductos = getListaProductos();

        lista = (ListView)findViewById(R.id.listView);
        lista.setAdapter(new CustomAdapterCarroCompra(this, listaProductos));
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private List<Producto> getListaProductos() {
        List<Producto> lista = new ArrayList<Producto>();
        try {
            Producto pro1 = new Producto();
            pro1.setCodigo(0001);
            pro1.setTitulo1("Playmobil 1");
            pro1.setTitulo2("Playmobil 1 de juguete");
            pro1.setPrecio(20.14);
            ImageView img1 = new ImageView(this);
            img1.setImageResource(R.mipmap.producto1);
            pro1.setImagen(img1);
            lista.add(pro1);

            Producto pro2 = new Producto();
            pro2.setCodigo(0002);
            pro2.setTitulo1("Playmobil 2");
            pro2.setTitulo2("Playmobil 2 de juguete");
            pro2.setPrecio(20.14);
            ImageView img2 = new ImageView(this);
            img2.setImageResource(R.mipmap.producto2);
            pro2.setImagen(img2);
            lista.add(pro1);

        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}
