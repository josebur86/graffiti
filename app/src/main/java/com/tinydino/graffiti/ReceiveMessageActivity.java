package com.tinydino.graffiti;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drew.graffiti.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReceiveMessageActivity extends ActionBarActivity {

    public final static String apiURL = "https://desolate-coast-8534.herokuapp.com/api/v1/message/1";
    private Toast _toast;
    private String _message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);

        _toast = Toast.makeText(this, "", Toast.LENGTH_LONG);

        new CallAPI().execute(apiURL);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private class CallAPI extends AsyncTask<String, String, String> {
        protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
            InputStream in = entity.getContent();


            StringBuffer out = new StringBuffer();
            int n = 1;
            while (n>0) {
                byte[] b = new byte[4096];
                n =  in.read(b);


                if (n>0) out.append(new String(b, 0, n));
            }

            return out.toString();
        }

        @Override
        protected  String doInBackground(String... params) {
            String urlString = params[0]; // URL to call

            String resultToDisplay = "";

            InputStream in = null;

            // HTTP Get
            try {
                URL url = new URL(urlString);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());

                HttpClient httpClient = new DefaultHttpClient();
                HttpContext localContext = new BasicHttpContext();
                HttpGet httpGet = new HttpGet(apiURL);

                HttpResponse response = httpClient.execute(httpGet, localContext);

                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    String retSrc = EntityUtils.toString(entity);
                    JSONObject result = new JSONObject(retSrc); //Convert String to JSON Object

                    String receivedMessage = result.getString("message");

                    final TextView textViewMessage = (TextView) findViewById(R.id.textViewMessage);
                    textViewMessage.setText(receivedMessage);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());

                return e.getMessage();
            }

            return resultToDisplay;
        }

        protected void onPostExecute(String result) {
            _message = result;
        }
    }
}
