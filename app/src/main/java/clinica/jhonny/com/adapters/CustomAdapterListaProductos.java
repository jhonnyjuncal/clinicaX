package clinica.jhonny.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import clinica.jhonny.com.clinicax.R;
import clinica.jhonny.com.model.Producto;


/**
 * Created by jhonny on 04/04/2016.
 */
public class CustomAdapterListaProductos extends ArrayAdapter<Producto> {

    private Context context;


    public CustomAdapterListaProductos(Context context, ArrayList<Producto> productos) {
        super(context, R.layout.presentacion_articulo, productos);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //Salvando la referencia del View de la fila
        View listItemView = convertView;

        try {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Comprobando si el View no existe
            if (null == convertView) {
                //Si no existe, entonces inflarlo con two_line_list_item.xml
                listItemView = inflater.inflate(R.layout.presentacion_articulo, parent, false);
            }

            //Obteniendo instancia de la Tarea en la posición actual
            final Producto item = getItem(position);

            ImageView img = (ImageView)listItemView.findViewById(R.id.imageView1);
            TextView txtTitulo = (TextView)listItemView.findViewById(R.id.txtTitulo);
            TextView txtDescripcion = (TextView)listItemView.findViewById(R.id.txtDescripcion);
            TextView txtPrecio = (TextView)listItemView.findViewById(R.id.txtPrecio);
            Button boton = (Button)listItemView.findViewById(R.id.button1);
            boton.setId(position);

            // imagen del articulo
            img.setImageDrawable(item.getImagen().getDrawable());

            // primer titulo del articulo
            txtTitulo.setText(item.getTitulo1());

            // descripcion del articulo
            txtDescripcion.setText(item.getTitulo2());

            // descripcion del articulo
            txtPrecio.setText(item.getPrecio() + " E");

            // incrementar cantidad de articulo
            /*
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int id = v.getId();

                    }catch(Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            */
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return listItemView;
    }



}
