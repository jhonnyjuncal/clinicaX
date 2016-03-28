package clinica.jhonny.com.clinicax;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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


/**
 * Created by jhonny on 16/03/2016.
 */
public class PaypalActivity extends AppCompatActivity {

    private static final String TAG = "PaypalActivity";

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

    private String precio1 = "";
    private String precio2 = "";

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
                    // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Jhonny")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

    private static final String CONFIG_RECEIVER_EMAIL = "jhonnyjuncal@gmail.com";

    PayPalPayment thingToBuy = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_paypal);

            TextView text1 = (TextView) findViewById(R.id.textView1);
            precio1 = text1.getText().toString();
            TextView text2 = (TextView) findViewById(R.id.textView2);
            precio2 = text2.getText().toString();

            inicializarServicioPaypal();

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // muestra un mensaje en la parte inferior de la pantalla
                    //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    // mostrar la ventana del carro de la compra
                    Intent intent = new Intent(PaypalActivity.this, CarroCompraActivity.class);
                    startActivity(intent);
                }
            });

        }catch(Exception ex) {
            ex.printStackTrace();
        }
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


    public void onBuyPressed(View pressed) {
        try {
            /*
            if (pressed.getId() == R.id.button1) {
                thingToBuy = new PayPalPayment(new BigDecimal(precio1), "EUR", "Painting 1", PayPalPayment.PAYMENT_INTENT_SALE);
            } else if (pressed.getId() == R.id.button2) {
                thingToBuy = new PayPalPayment(new BigDecimal(precio2), "EUR", "Painting 2", PayPalPayment.PAYMENT_INTENT_SALE);
            }

            Intent intent = new Intent(PaypalActivity.this, PaymentActivity.class);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
            //intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            startActivityForResult(intent, REQUEST_CODE_PAYMENT);
            */

            Integer cantidadAnterior = 0;
            if(pressed.getId() == R.id.button1) {
                if(Util.getCarroDeLaCompra().containsKey(1))
                    cantidadAnterior = Util.getCarroDeLaCompra().get(1);
                Util.getCarroDeLaCompra().put(1, ++cantidadAnterior);

            }else if(pressed.getId() == R.id.button2) {
                if(Util.getCarroDeLaCompra().containsKey(2))
                    cantidadAnterior = Util.getCarroDeLaCompra().get(2);
                Util.getCarroDeLaCompra().put(2, ++cantidadAnterior);

            }else if(pressed.getId() == R.id.button3) {
                if(Util.getCarroDeLaCompra().containsKey(3))
                    cantidadAnterior = Util.getCarroDeLaCompra().get(3);
                Util.getCarroDeLaCompra().put(3, ++cantidadAnterior);

            }else if(pressed.getId() == R.id.button4) {
                if(Util.getCarroDeLaCompra().containsKey(4))
                    cantidadAnterior = Util.getCarroDeLaCompra().get(4);
                Util.getCarroDeLaCompra().put(4, ++cantidadAnterior);
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }


    public void onFuturePaymentPressed(View pressed) {
        Intent intent = new Intent(PaypalActivity.this, PayPalFuturePaymentActivity.class);
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


    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
