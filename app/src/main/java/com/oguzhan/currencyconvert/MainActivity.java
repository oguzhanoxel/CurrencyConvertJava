package com.oguzhan.currencyconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView textView,textView1,textView2,textView3,textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView1);
        textView1 = findViewById(R.id.textView2);
        textView2 = findViewById(R.id.textView3);
        textView3 = findViewById(R.id.textView4);
        textView4 = findViewById(R.id.textView5);

    }

    public void getRates(View view){
        DownloadData downloadData = new DownloadData();
        try {
            String url = "http://data.fixer.io/api/latest?access_key=2553e57b39f56111a1d257dd4e2b4701&format=1";
            downloadData.execute(url);
        }catch (Exception e){
        }
    }

    private class DownloadData extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try{

               url = new URL(strings[0]);
               httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while(data>0){

                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();
                }

                return result;

            }catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                String rates = jsonObject.getString("rates");
                JSONObject jsonObject1 = new JSONObject(rates);
                String turkishLira = jsonObject1.getString("TRY");
                textView.setText("TRY: "+turkishLira);
                String usd = jsonObject1.getString("USD");
                textView1.setText("USD: "+usd);
                String cad = jsonObject1.getString("CAD");
                textView2.setText("CAD: "+cad);
                String chf = jsonObject1.getString("CHF");
                textView3.setText("CHF: "+chf);
                String jpy = jsonObject1.getString("JPY");
                textView4.setText("JPY: "+jpy);
            }catch (Exception e)
            {

            }
        }
    }

}
