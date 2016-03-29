package clinica.jhonny.com.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import clinica.jhonny.com.Util;
import clinica.jhonny.com.clinicax.R;
import clinica.jhonny.com.model.ItemCesta;
import clinica.jhonny.com.model.Producto;


/**
 * Created by jhonny on 21/03/2016.
 */
public class CustomAdapterCarroCompra extends ArrayAdapter<ItemCesta> {

    private Context context;
    private TextView textSubTotal = null;
    private TextView textTotal = null;


    public CustomAdapterCarroCompra(Context context, ArrayList<ItemCesta> listaCesta, TextView textSubTotal, TextView textTotal) {
        super(context, R.layout.item_carro_compra, listaCesta);
        this.context = context;
        this.textSubTotal = textSubTotal;
        this.textTotal = textTotal;
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
                listItemView = inflater.inflate(R.layout.item_carro_compra, parent, false);
            }

            //Obteniendo instancia de la Tarea en la posición actual
            final ItemCesta item = getItem(position);

            Integer cantidad = 0;
            if (Util.getCarroDeLaCompra() != null && !Util.getCarroDeLaCompra().isEmpty()) {
                for (ItemCesta ic : Util.getCarroDeLaCompra()) {
                    if (item.getProducto().getCodigo().equals(ic.getProducto().getCodigo())) {
                        cantidad = ic.getCantidad();
                    }
                }
            }

            ImageView imageView = (ImageView) listItemView.findViewById(R.id.imageView);
            TextView textView1 = (TextView) listItemView.findViewById(R.id.textView1);
            TextView textView2 = (TextView) listItemView.findViewById(R.id.textView2);
            Button boton1 = (Button) listItemView.findViewById(R.id.button1);
            Button boton2 = (Button) listItemView.findViewById(R.id.button2);
            final TextView textView3 = (TextView) listItemView.findViewById(R.id.textView3);
            Button boton3 = (Button) listItemView.findViewById(R.id.button3);
            Button boton4 = (Button) listItemView.findViewById(R.id.button4);
            final TextView textView4 = (TextView) listItemView.findViewById(R.id.textView4);

            // imagen del articulo
            imageView.setImageDrawable(item.getProducto().getImagen().getDrawable());

            // primer titulo del articulo
            textView1.setText(item.getProducto().getTitulo1());

            // segundo titulo del articulo
            textView2.setText(item.getProducto().getTitulo2());

            // decrementar cantidad de articulo
            boton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View vista) {
                    Util.decrementarArticulo(position, textView3, textView4, item.getProducto().getPrecio(), textSubTotal, textTotal);
                }
            });

            // incrementar cantidad de articulo
            boton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.incrementarArticulo(position, textView3, textView4, item.getProducto().getPrecio(), textSubTotal, textTotal);
                }
            });

            // cantidad de articulos a comprar
            textView3.setText(cantidad + " articulo(s)");

            // borrar de la cesta de productos
            boton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        new AlertDialog.Builder(context)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Eliminar")
                                .setMessage("¿Seguro de que desea eliminar este articulo?")
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            Util.eliminaProductoDeLaCesta(position, textSubTotal, textTotal);

                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    this.finalize();

                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            }
                        }).show();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            // añadir a la lista de deseos
            boton4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            // total de los productos
            textView4.setText((item.getProducto().getPrecio() * cantidad) + " E");

        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return listItemView;
    }
}
