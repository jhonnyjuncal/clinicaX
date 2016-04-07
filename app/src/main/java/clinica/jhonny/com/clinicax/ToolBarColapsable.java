package clinica.jhonny.com.clinicax;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import clinica.jhonny.com.Util;
import clinica.jhonny.com.model.Producto;


public class ToolBarColapsable extends AppCompatActivity {

    private Producto producto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tool_bar_colapsable);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            collapsingToolbar.setTitle("Colapsable");

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                Integer codigo = bundle.getInt("codProducto");
                producto = Util.obtenerProductoPorCodigo(codigo);
            }

            rellenarDatosDescripcion();

        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void rellenarDatosDescripcion() {
        try {
            /*
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View listItemView = inflater.inflate(R.layout.presentacion_articulo, parent, false);
            */

            /*
            RelativeLayout rl = (RelativeLayout)findViewById(R.id.contenidoColapasable);
            TextView tv1 = (TextView)rl.findViewById(R.id.textView13);
            tv1.setText("Titulo 1");

            TextView tv2 = (TextView)rl.findViewById(R.id.textView13);
            tv2.setText("Aqui va una descripcion del articulo en cuestion");

            TextView tv3 = (TextView)rl.findViewById(R.id.textView13);
            tv3.setText("Aqui se añadiran algunas especificaciones tecnicas");
            */

            LayoutInflater linf;
            CoordinatorLayout rl;

            linf = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            linf = LayoutInflater.from(this);

            rl = (CoordinatorLayout)findViewById(R.id.coordinatorDescripcion);

            ImageView imgPrincipal = (ImageView)rl.findViewById(R.id.imagenPrincipalDescripcion);
            imgPrincipal.setImageDrawable(producto.getImagen().getDrawable());

            NestedScrollView nested = (NestedScrollView)rl.findViewById(R.id.nestedDescripcion);

            TextView tv1 = (TextView)rl.findViewById(R.id.textView13);
            tv1.setText(producto.getTitulo1());

            TextView tv2 = (TextView)rl.findViewById(R.id.textView14);
            tv2.setText(producto.getTitulo2());

            TextView tv3 = (TextView)rl.findViewById(R.id.textView17);
            tv3.setText("Precio del produto: " + producto.getPrecio() + " EUR");

            ImageView img1 = (ImageView)rl.findViewById(R.id.imageView5);
            img1.setImageDrawable(producto.getImagen().getDrawable());

            TextView tv4 = (TextView)rl.findViewById(R.id.textView18);
            tv4.setText("");

            TextView tv5 = (TextView)rl.findViewById(R.id.textView19);
            tv5.setText("");

            ImageView img2 = (ImageView)rl.findViewById(R.id.imageView6);
            img2.setImageDrawable(producto.getImagen().getDrawable());

            TextView tv6 = (TextView)rl.findViewById(R.id.textView20);
            tv6.setText("");

            TextView tv7 = (TextView)rl.findViewById(R.id.textView21);
            tv7.setText("");

            if(rl != null) {
                final View v = linf.inflate(R.layout.content_tool_bar_colapsable, null);
                nested.addView(v);
            }

        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}
