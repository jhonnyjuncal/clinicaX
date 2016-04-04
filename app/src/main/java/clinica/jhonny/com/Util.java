package clinica.jhonny.com;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import clinica.jhonny.com.adapters.CustomAdapterCarroCompra;
import clinica.jhonny.com.clinicax.R;
import clinica.jhonny.com.model.ItemCesta;
import clinica.jhonny.com.model.Producto;


/**
 * Created by jhonny on 20/03/2016.
 */
public class Util {

    private static ArrayList<ItemCesta> listaCesta = new ArrayList<ItemCesta>();
    private static ArrayList<Producto> productos = new ArrayList<Producto>();
    private static CustomAdapterCarroCompra customAdapter;


    public static ArrayList<Producto> getListaDeProductos(Context context) {
        if(productos.isEmpty())
            cargaDeDatos(context);
        return productos;
    }

    public static ArrayList<ItemCesta> getCarroDeLaCompra() {
        return listaCesta;
    }

    public static synchronized void decrementarArticulo(int position, TextView textView1, TextView textView2,
            Double precio, TextView textSubTotal, TextView textTotal) {

        try {
            try {
                ItemCesta ic = listaCesta.get(position);
                Integer cantidad = ic.getCantidad();
                ic.setCantidad(--cantidad);
                listaCesta.set(position, ic);

                textView1.setText(cantidad + " articulo(s)");
                textView2.setText((precio * cantidad) + " EUR");

                actualizaTotales(textSubTotal, textTotal);

            }catch(Exception ex) {
                ex.printStackTrace();
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static synchronized void incrementarArticulo(int position, TextView textView1, TextView textView2,
            Double precio, TextView textSubTotal, TextView textTotal) {

        try {
            ItemCesta ic = listaCesta.get(position);
            Integer cantidad = ic.getCantidad();
            ic.setCantidad(++cantidad);
            listaCesta.set(position, ic);

            textView1.setText(cantidad + " articulo(s)");
            textView2.setText((precio * cantidad) + " EUR");

            actualizaTotales(textSubTotal, textTotal);

        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean existeProductoEnElCarroDeLaCompra(Producto producto) {
        try {
            if(listaCesta == null || listaCesta.isEmpty()) {
                return false;

            }else {
                for(int i=0; i<listaCesta.size(); i++) {
                    ItemCesta ic = listaCesta.get(i);
                    if(ic.getProducto().getCodigo().equals(producto.getCodigo()))
                        return true;
                }
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void actualizaTotales(TextView textSubTotal, TextView textTotal) {
        try {
            Double subtotal = 0.0;
            Double total = 0.0;

            if(Util.getCarroDeLaCompra() != null && !Util.getCarroDeLaCompra().isEmpty()) {
                for(ItemCesta ic : Util.getCarroDeLaCompra()) {
                    subtotal += ic.getCantidad() * ic.getProducto().getPrecio();
                    total += ic.getCantidad() * ic.getProducto().getPrecio();
                }
            }

            textSubTotal.setText(subtotal + " EUR");
            textTotal.setText(total + " EUR");

        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void eliminaProductoDeLaCesta(int posicion, TextView textSubTotal, TextView textTotal) {
        try {
            ItemCesta ic = listaCesta.get(posicion);
            listaCesta.remove(ic);
            customAdapter.remove(ic);

            //actualizaAdaptador();
            actualizaTotales(textSubTotal, textTotal);

        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setCustomAdapter(CustomAdapterCarroCompra customAdapter) {
        Util.customAdapter = customAdapter;
    }

    private static void actualizaAdaptador() {
        try {
            if(customAdapter != null) {
                customAdapter.clear();
                customAdapter.addAll(listaCesta);
                customAdapter.notifyDataSetChanged();
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }






    private static void cargaDeDatos(Context context) {
        Producto pro1 = new Producto();
        pro1.setCodigo(0001);
        pro1.setTitulo1("Playmobil 1");
        pro1.setTitulo2("Pirata con barba");
        pro1.setPrecio(20.14);
        ImageView img1 = new ImageView(context);
        img1.setImageResource(R.mipmap.producto1);
        pro1.setImagen(img1);
        productos.add(pro1);

        Producto pro2 = new Producto();
        pro2.setCodigo(0002);
        pro2.setTitulo1("Playmobil 2");
        pro2.setTitulo2("Muñeca de juguete");
        pro2.setPrecio(18.44);
        ImageView img2 = new ImageView(context);
        img2.setImageResource(R.mipmap.producto2);
        pro2.setImagen(img2);
        productos.add(pro2);

        Producto pro3 = new Producto();
        pro3.setCodigo(0003);
        pro3.setTitulo1("Playmobil 3");
        pro3.setTitulo2("Un caballo");
        pro3.setPrecio(30.00);
        ImageView img3 = new ImageView(context);
        img3.setImageResource(R.mipmap.producto3);
        pro3.setImagen(img3);
        productos.add(pro3);

        Producto pro4 = new Producto();
        pro4.setCodigo(0004);
        pro4.setTitulo1("Playmobil 4");
        pro4.setTitulo2("Carro de caballos");
        pro4.setPrecio(50.0);
        ImageView img4 = new ImageView(context);
        img4.setImageResource(R.mipmap.producto4);
        pro4.setImagen(img4);
        productos.add(pro4);
    }
}
