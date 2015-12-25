package com.example.john.nota;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Objetos.Calificacion;
import Objetos.Datos;

/**
 * Created by john on 24/12/15.
 */
public class CalificacionActivity extends Activity {
    ListView lista;
    CalificacionAdapter adapter;
    ArrayList<Calificacion> calificaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado);
        String matricula = getIntent().getStringExtra("codigo");
        String anio = getIntent().getStringExtra("anio");
        String termino = getIntent().getStringExtra("termino");

        calificaciones=new ArrayList<Calificacion>();
        adapter = new CalificacionAdapter(getApplicationContext(), R.layout.entrada, calificaciones);
        lista = (ListView) findViewById(R.id.ListView_listado);
        lista.setAdapter(adapter);

        new ObtCalificaion().execute(anio,termino, matricula);

    }


    public class ObtCalificaion extends AsyncTask<String, Void, ArrayList<Calificacion>> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(CalificacionActivity.this);
            dialog.setMessage("Cargando...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<Calificacion> calificacions) {
            super.onPostExecute(calificacions);
            dialog.dismiss();
            adapter.getData().clear();
            adapter.getData().addAll(calificacions);
            adapter.notifyDataSetChanged();
        }

        protected ArrayList<Calificacion> doInBackground(String... params) {
            String NAMESPACE = "http://tempuri.org/";
            String URL="https://ws.espol.edu.ec/saac/wsandroid.asmx";
            String METHOD_NAME = "wsConsultaCalificaciones";
            String SOAP_ACTION = "http://tempuri.org/wsConsultaCalificaciones";
            HttpTransportSE httpTransport = new HttpTransportSE(URL);
            SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
            request.addProperty("anio", params[0]);
            request.addProperty("termino", params[1]);
            request.addProperty("estudiante", params[2]);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            SoapObject response;
            ArrayList<Calificacion> calificaciones = new ArrayList<Calificacion>();
            try{
                httpTransport.call(SOAP_ACTION,envelope);
                response = (SoapObject) envelope.getResponse();
                response = (SoapObject) response.getProperty("diffgram");
                response = (SoapObject) response.getProperty("NewDataSet");
                int num = response.getPropertyCount();


                for (int i=0; i<num; i++){
                    String nombre, apellido, cedula, matricula;
                    Calificacion c=new Calificacion();
                    SoapObject ic = (SoapObject)response.getProperty(i);

                    c.setEstado(ic.getProperty("ESTADO").toString());
                    c.setMateria(ic.getProperty("MATERIA").toString());
                    c.setNota1(ic.getProperty("NOTA1").toString());
                    c.setNota2(ic.getProperty("NOTA2").toString());
                    c.setNota3(ic.getProperty("NOTA3").toString());
                    c.setParalelo(ic.getProperty("PARALELO").toString());
                    c.setPromedio(ic.getProperty("PROMEDIO").toString());
                    c.setVez(ic.getProperty("VEZ").toString());
                    calificaciones.add(c);
                }

                return calificaciones;
            }
            catch (Exception e){
                return calificaciones;
            }
        }

    }
}
