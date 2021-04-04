package com.empresadelivery.empresadelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.TimePickerDialog;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.empresadelivery.empresadelivery.modelos.Proveedores;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class Entradas extends AppCompatActivity implements
        View.OnClickListener {
    ArrayList<Proveedores> listadeproveedores = new ArrayList<>();
    private String[] strarrayproveedor = {"No Suggestions"};
    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    String FileName = "myfile";
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entradas);
        btnDatePicker=(Button)findViewById(R.id.btn_date);
               txtDate=(EditText)findViewById(R.id.in_date);
        btnDatePicker.setOnClickListener(this);
          prefs = this.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        String idempresa=prefs.getString("idempresa","");


        new todoslosproveedores().execute(idempresa);

    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

    }

    private class todoslosproveedores extends AsyncTask<String, String, String> {
        HttpURLConnection conne;
        URL url = null;
        ArrayList<Proveedores> listaalmaceno = new ArrayList<Proveedores>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("https://sodapop.pe/sugest/apitraerproveedoresempresa.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {
                conne = (HttpURLConnection) url.openConnection();
                conne.setReadTimeout(READ_TIMEOUT);
                conne.setConnectTimeout(CONNECTION_TIMEOUT);
                conne.setRequestMethod("POST");
                conne.setDoInput(true);
                conne.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("idempresa", params[0]);


                String query = builder.build().getEncodedQuery();
                OutputStream os = conne.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conne.connect();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }
            try {
                int response_code = conne.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream input = conne.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return (
                            result.toString()
                    );
                } else {
                    return ("Connection error");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conne.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result2) {
            ArrayList<String> dataList2 = new ArrayList<String>();
            Proveedores meso2;
            if (result2.equals("no rows")) {
            } else {
                try {
                    JSONArray jArray = new JSONArray(result2);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data2 = jArray.optJSONObject(i);


                        meso2 = new Proveedores(json_data2.getInt("idproveedor"), json_data2.getString("nombre"),
                                json_data2.getString("razonsocial"), json_data2.getString("direccion"),
                                json_data2.getString("telefono"), json_data2.getString("etado"),json_data2.getInt("idempresa"));

                        listadeproveedores.add(meso2);
                    }
                    strarrayproveedor = dataList2.toArray(new String[dataList2.size()]);
                    Spinner spnproveedor = (Spinner) findViewById(R.id.spinproveedor);
                    ArrayAdapter<Proveedores> adaptadorl2 = new ArrayAdapter<Proveedores>(Entradas.this, android.R.layout.simple_spinner_item, listadeproveedores);
                    spnproveedor.setAdapter(adaptadorl2);
                } catch (JSONException e) {

                }

            }

        }

    }

}