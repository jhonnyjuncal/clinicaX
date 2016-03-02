package clinica.jhonny.com.clinicax;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText textBuscar;
    private Button btnBuscar;
    private ListView listaResultados;

    private Connection conexion = null;
    private String query = "SELECT * FROM clientes WHERE nombre like ? ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

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
                                final ArrayList<Cliente> list = new ArrayList<Cliente>();
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
                                        list.add(cli);
                                        contador++;
                                    }
                                }

                                final StableArrayAdapter adapter = new StableArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, list);
                                listaResultados.setAdapter(adapter);

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

    private class StableArrayAdapter extends ArrayAdapter<Cliente> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId, List<Cliente> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                Cliente cli = (Cliente)objects.get(i);
                String nombreCompleto = cli.getNombre() + " " + cli.getApellidos();
                mIdMap.put(nombreCompleto, i);
            }
        }

        @Override
        public long getItemId(int position) {
            Cliente item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
