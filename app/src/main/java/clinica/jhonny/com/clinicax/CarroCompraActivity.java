package clinica.jhonny.com.clinicax;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import clinica.jhonny.com.Util;
import clinica.jhonny.com.adapters.CustomAdapterCarroCompra;


public class CarroCompraActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * - Set to PayPalConfiguration.ENVIRONMENT_PRODUCTION to move real money.
     *
     * - Set to PayPalConfiguration.ENVIRONMENT_SANDBOX to use your test credentials
     * from https://developer.paypal.com
     *
     * - Set to PayPalConfiguration.ENVIRONMENT_NO_NETWORK to kick the tires
     * without communicating to PayPal's servers.
     */
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    private static final String CONFIG_CLIENT_ID = "AWRrMhQWjVL9HrgLxMpd8Ww93mISAyVr4_4Mga7YuEdUtMKxsYc-fsYrrhpSh_xhPek63PblcwvJRzYA";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final int REQUEST_CODE_PROFILE_SHARING = 3;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
                    // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Jhonny")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

    private static final String CONFIG_RECEIVER_EMAIL = "jhonnyjuncal@gmail.com";

    private ListView lista = null;
    private PayPalPayment thingToBuy = null;
    private static final String TAG = "CarroCompraActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_carro_compra);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Carro de la compra");
            setSupportActionBar(toolbar);

            inicializarServicioPaypal();

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

    private void inicializarServicioPaypal() {
        try {
            Intent intent = new Intent(this, PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            startService(intent);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onFuturePaymentPressed(View pressed) {
        Intent intent = new Intent(CarroCompraActivity.this, PayPalFuturePaymentActivity.class);
        startActivityForResult(intent, REQUEST_CODE_FUTURE_PAYMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i(TAG, confirm.toJSONObject().toString(4));
                        Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));
                        Toast.makeText(getApplicationContext(), "PaymentConfirmation info received from PayPal", Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i(TAG, "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(TAG,"An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }

        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject().toString(4));
                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(getApplicationContext(), "Future Payment code received from PayPal", Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("FuturePaymentExample", "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }

    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {
        // envio de la autorizacion a el servidor (al vendedor)
    }

    public void onFuturePaymentPurchasePressed(View pressed) {
        // Get the Application Correlation ID from the SDK
        String correlationId = PayPalConfiguration.getApplicationCorrelationId(this);
        Log.i("FuturePaymentExample", "Application Correlation ID: " + correlationId);

        // TODO: Send correlationId and transaction details to your server for processing with
        // PayPal...
        Toast.makeText(getApplicationContext(), "App Correlation ID received from SDK", Toast.LENGTH_LONG).show();
    }
}
