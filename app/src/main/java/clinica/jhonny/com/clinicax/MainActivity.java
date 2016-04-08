package clinica.jhonny.com.clinicax;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import clinica.jhonny.com.adapters.CustomAdapterBuscadorClientes;
import clinica.jhonny.com.model.Cliente;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private EditText textBuscar;
    private Button btnBuscar;
    private ListView listaResultados;
    public MainActivity customListView = null;
    private ListView listView;
    private CustomAdapterBuscadorClientes adapter;

    private Connection conexion = null;
    private String query = "SELECT * FROM clientes WHERE nombre like %?% ";

    public ArrayList<Cliente> customListViewValuesArr = new ArrayList<Cliente>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            setContentView(R.layout.activity_nav_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            customListView = this;

            final Resources res = getResources();
            listView = (ListView)findViewById(R.id.ListView01);  // List defined in XML ( See Below )

            /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
            setListData();

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // muestra un mensaje en la parte inferior de la pantalla
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });

            textBuscar = (EditText) findViewById(R.id.editBuscarCliente);
            listaResultados = (ListView) findViewById(R.id.ListView01);
            listaResultados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(), "Click ListItem Number " + position, Toast.LENGTH_LONG).show();
                }
            });

            btnBuscar = (Button) findViewById(R.id.btnBuscarCliente);
            btnBuscar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View vista) {
                    try {
                        String texto = textBuscar.getText().toString();
                        if(texto != null) {
                            Class.forName("com.mysql.jdbc.Driver").newInstance();
                            final String url = "jdbc:mysql://sql8.freesqldatabase.com:3306/sql8108831";
                            conexion = DriverManager.getConnection(url, "sql8108831", "cbfV41GyyX");

                            if (conexion != null) {
                                PreparedStatement ps = conexion.prepareStatement(query);
                                ps.setString(1, texto);
                                ResultSet rs = ps.executeQuery();

                                int contador = 0;
                                if(rs != null) {
                                    while (rs.next()) {
                                        // setear los resultados en la lista a mostrar en pantalla
                                        Integer idCliente = rs.getInt("idCliente");
                                        String nombre = rs.getString("nombre");
                                        String apellidos = rs.getString("apellidos");
                                        String direccion = rs.getString("direccion");
                                        String cp = rs.getString("cp");
                                        String telefono = rs.getString("telefono");
                                        String email = rs.getString("email");

                                        Cliente cli = new Cliente(idCliente, nombre, apellidos, direccion, cp, telefono, email);
                                        customListViewValuesArr.add(cli);
                                        contador++;
                                    }
                                }

                                //final StableArrayAdapter adapter = new StableArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, list);
                                //listaResultados.setAdapter(adapter);

                                adapter = new CustomAdapterBuscadorClientes(customListView, customListViewValuesArr, res);
                                listView.setAdapter(adapter);

                                //listaResultados.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listview_array));
                            }
                        }
                    }catch(Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /****** Function to set data in ArrayList *************/
    public void setListData() {

        for(int i = 0; i < 11; i++) {
            final Cliente sched = new Cliente();

            /******* Firstly take data in model object ******/
            sched.setNombre("Nombre " + i);
            sched.setApellidos("Apellidos " + i);
            sched.setEmail("Email " + i);

            /******** Take Model Object in ArrayList **********/
            customListViewValuesArr.add(sched);
        }
    }

    /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition) {
        Cliente tempValues = (Cliente)customListViewValuesArr.get(mPosition);

        // SHOW ALERT
        Toast.makeText(customListView, "" + tempValues.getNombre() +
                tempValues.getApellidos() + " - Email:" + tempValues.getEmail(), Toast.LENGTH_LONG).show();
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
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
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
                destino = ListaProductosActivity.class;
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
