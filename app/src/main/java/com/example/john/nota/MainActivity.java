package com.example.john.nota;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Objetos.Datos;

public class MainActivity extends Activity {

    public TextView txtNombre, txtApellido, txtAnio, txtTermino;
    public Button btnObtener;
    public ArrayList<Datos> datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.txtApellido = (TextView) findViewById(R.id.txtApellido);
        this.txtNombre = (TextView) findViewById(R.id.txtNombre);
        this.txtAnio = (TextView) findViewById(R.id.txtAnio);
        this.txtTermino = (TextView) findViewById(R.id.txtTermino);
        this.btnObtener = (Button) findViewById(R.id.btnObtener);

        this.btnObtener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ObtDatos().execute(txtNombre.getText().toString(), txtApellido.getText().toString());
            }
        });

    }

    public class ObtDatos extends AsyncTask<String, Void, ArrayList<Datos>> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Cargando...");
            dialog.setCancelable(false);
            dialog.show();
        }

        private void MostrarDialogo(final ArrayList<Datos> datos){

            //Creamos un nuevo AlertDialog.Builder pasandole como parametro el contexto
            AlertDialog.Builder ADBuilder = new AlertDialog.Builder(MainActivity.this);

            ADBuilder.setIcon(R.mipmap.ic_launcher);//Definimos el icono
            ADBuilder.setTitle("Opciones ");//Asignamos un titulo al mensaje

            //Creamos un nuevo ArrayAdapter de 'Strings' y pasamos como parametros (Contexto, int id "Referencia a layout");
            final ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.select_dialog_singlechoice);

            //Añadimos los elementos a mostrar
            for (Datos d: datos){
                arrayAdapter.add(d.getNombres() + " " + d.getApellidos());
            }

            //Creamos un boton para cancelar el dialog
            ADBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();//Cerramos el dialogo
                }
            });

            //Capturamos el evento 'OnClick' de los elementos en el dialogo
            ADBuilder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int _item) {
                    Intent i = new Intent(MainActivity.this, CalificacionActivity.class);
                    i.putExtra("codigo", datos.get(_item).getMatricula());
                    i.putExtra("anio", txtAnio.getText().toString());
                    i.putExtra("termino", txtTermino.getText().toString());
                    startActivity(i);

                }
            });

            ADBuilder.show();//Mostramos el dialogo
        }

        @Override
        protected void onPostExecute(ArrayList<Datos> datoses) {
            super.onPostExecute(datoses);
            datos = datoses;
            dialog.dismiss();

            MostrarDialogo(datoses);

        }

        protected ArrayList<Datos> doInBackground(String... params) {
            String NAMESPACE = "http://tempuri.org/";
            String URL="https://ws.espol.edu.ec/saac/wsandroid.asmx";
            String METHOD_NAME = "wsConsultarPersonaPorNombres";
            String SOAP_ACTION = "http://tempuri.org/wsConsultarPersonaPorNombres";
            HttpTransportSE httpTransport = new HttpTransportSE(URL);
            SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
            request.addProperty("nombre",params[0]);
            request.addProperty("apellido", params[1]);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            SoapObject response;
            try{
                httpTransport.call(SOAP_ACTION,envelope);
                response = (SoapObject) envelope.getResponse();
                response = (SoapObject) response.getProperty("diffgram");
                response = (SoapObject) response.getProperty("NewDataSet");
                int num = response.getPropertyCount();
                ArrayList<Datos> datos = new ArrayList<Datos>();

                for (int i=0; i<num; i++) {
                    try {
                        String nombre, apellido, cedula, matricula;
                        SoapObject ic = (SoapObject) response.getProperty(i);

                        matricula = ic.getProperty("CODESTUDIANTE").toString();
                        cedula = ic.getProperty("NUMEROIDENTIFICACION").toString();
                        nombre = ic.getProperty("NOMBRES").toString();
                        apellido = ic.getProperty("APELLIDOS").toString();
                        datos.add(new Datos(nombre, apellido, matricula, cedula));
                    }catch (Exception e){}
                }

                return datos;
            }
            catch (Exception e){
                ArrayList<Datos>datos = null;
                return datos;
            }
        }

    }
}
