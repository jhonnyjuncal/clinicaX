package clinica.jhonny.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import clinica.jhonny.com.Util;
import clinica.jhonny.com.clinicax.R;
import clinica.jhonny.com.model.Producto;


/**
 * Created by jhonny on 21/03/2016.
 */
public class CustomAdapterCarroCompra extends ArrayAdapter<Producto> {

    private final Context context;
    private List<Producto> productos;


    public CustomAdapterCarroCompra(Context context, List<Producto> productos) {
        super(context, R.layout.item_carro_compra);
        this.context = context;
        this.productos = productos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Salvando la referencia del View de la fila
        View listItemView = convertView;

        //Comprobando si el View no existe
        if(null == convertView) {
            //Si no existe, entonces inflarlo con two_line_list_item.xml
            listItemView = inflater.inflate(R.layout.item_carro_compra, parent, false);
        }

        Producto producto = productos.get(position);
        Integer codigo = producto.getCodigo();
        Integer cantidad = 0;
        if(Util.mapaCarro.containsKey(codigo))
            cantidad = Util.mapaCarro.get(codigo);

        // datos de item_carro_compra
        ImageView imageView = (ImageView)listItemView.findViewById(R.id.imageView);
        TextView textView1 = (TextView)listItemView.findViewById(R.id.textView1);
        textView1.setText(producto.getTitulo1());
        TextView textView2 = (TextView)listItemView.findViewById(R.id.textView2);
        textView2.setText(producto.getTitulo2());
        Button boton1 = (Button)listItemView.findViewById(R.id.button1);
        Button boton2 = (Button)listItemView.findViewById(R.id.button2);
        TextView textView3 = (TextView)listItemView.findViewById(R.id.textView3);
        textView3.setText(cantidad + " articulo(s)");
        Button boton3 = (Button)listItemView.findViewById(R.id.button3);
        Button boton4 = (Button)listItemView.findViewById(R.id.button4);
        TextView textView4 = (TextView)listItemView.findViewById(R.id.textView4);
        textView4.setText(producto.getPrecio() + " E");

        //Obteniendo instancia de la Tarea en la posición actual
        getItem(position);


        return listItemView;
    }
}
