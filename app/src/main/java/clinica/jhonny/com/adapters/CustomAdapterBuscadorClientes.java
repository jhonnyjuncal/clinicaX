package clinica.jhonny.com.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import clinica.jhonny.com.clinicax.MainActivity;
import clinica.jhonny.com.clinicax.R;
import clinica.jhonny.com.model.Cliente;

/**
 * Created by jhonny on 06/03/2016.
 */
public class CustomAdapterBuscadorClientes extends BaseAdapter implements View.OnClickListener {

    private Activity activity;
    private ArrayList lista;
    private Resources recurso;
    private static LayoutInflater inflater = null;
    private Cliente tempValues = null;


    public CustomAdapterBuscadorClientes(Activity activity, ArrayList lista, Resources recurso) {
        /********** Take passed values **********/
        this.activity = activity;
        this.lista = lista;
        this.recurso = recurso;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        if(lista.size() <= 0)
            return 1;
        return lista.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView == null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.tabitem, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/
            holder = new ViewHolder();
            holder.text = (TextView) vi.findViewById(R.id.text);
            holder.text1 = (TextView)vi.findViewById(R.id.text1);

            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(lista.size() <= 0) {
            holder.text.setText("No Data");

        }else {
            /***** Get each Model object from Arraylist ********/
            tempValues = null;
            tempValues = (Cliente)lista.get(position);

            /************  Set Model values in Holder elements ***********/

            holder.text.setText(tempValues.getNombre() + " " + tempValues.getApellidos());
            holder.text1.setText(tempValues.getEmail());

            /******** Set Item Click Listner for LayoutInflater for each row *******/
            vi.setOnClickListener(new ClickListenerPersonalizado(position));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }





    /********* Called when Item click in ListView ************/
    private class ClickListenerPersonalizado  implements View.OnClickListener {
        private int mPosition;

        public ClickListenerPersonalizado(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            MainActivity sct = (MainActivity)activity;

            /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/
            sct.onItemClick(mPosition);
        }
    }





    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView text;
        public TextView text1;
        public TextView textWide;
    }
}
