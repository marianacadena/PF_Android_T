package com.example.mari.pf_transitois;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mari.pf_transitois.ws.HttpUtils;
import com.example.mari.pf_transitois.ws.Response;
import com.example.mari.pf_transitois.ws.pojos.Conductor;
import com.example.mari.pf_transitois.ws.pojos.Reporte;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class HistorialActivity extends AppCompatActivity {
    private Conductor conductor;
    private ListView lst_reportes;
    private List<Reporte> lista_reportes;
    private Response resws;
    private Reporte reporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        lst_reportes = (ListView)findViewById(R.id.list_reportes);

        lst_reportes.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        detalleReporte(position);
                    }
                });

        conductor = new Conductor(1);
        //parametrosIntent();
        cargarNotas();
    }

    private void detalleReporte(int index){
        Reporte r = this.lista_reportes.get(index);
        //Intent i = new Intent(this,DetalleReporteActivity.class);
        //i.putExtra("json_reporte", new Gson().toJson(r));
        //i.putExtra("json_conductor", new Gson().toJson(conductor));
        //startActivity(i);
    }

    private void parametrosIntent(){
        Intent intent = getIntent();
        String json = intent.getStringExtra("json_conductor");
        conductor = new Gson().fromJson(json, Conductor.class);
    }

    private void cargarNotas(){
        if(isOnline()){
            WSGETNotasTask task = new WSGETNotasTask();
            task.execute(conductor);
        }
    }

    private void resultadoReportes(){
        if(resws!=null && !resws.isError() && resws.getResult()!=null) {
            //Se mapea el json a una Lista de objetos
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            lista_reportes = gson.fromJson(resws.getResult(),
                    new TypeToken<List<Reporte>>() {}.getType());

            List<String> nombres = new ArrayList<>();
            if (lista_reportes != null) {
                for (Reporte r : lista_reportes) {
                    nombres.add(r.getFechaHora());
                }
            }
            //Para pasar la lista de Strings al ListView se requiere un Adaptador
            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1, nombres);
            this.lst_reportes.setAdapter(itemsAdapter);
            //----------------------------------------------------------------//
        }else{//No se pudieron obtener los datos del WS
            mostrarAlertDialog("Error", resws.getResult());
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);
        boolean online = (cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnected());
        if(!online){
            mostrarAlertDialog("Sin conexión",
                    "No se encontró ninguna conexión a Internet, " +
                            "conectate a alguna red para continuar utilizando la " +
                            "aplicación");
        }
        return online;
    }

    private void mostrarAlertDialog(String titulo, String mensaje){
        final AlertDialog dialog = new AlertDialog.Builder(HistorialActivity.this).create();
        dialog.setMessage(mensaje);
        dialog.setTitle(titulo);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //-------------------INICIA CLASE INTERNA-----------------------//
    class WSGETNotasTask extends AsyncTask<Object, String, String> {
        @Override
        protected String doInBackground(Object... params) {
            resws = HttpUtils.obtenerReportes((Conductor) params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            resultadoReportes();
        }
    }
    //-------------------TERMINA CLASE INTERNA-----------------------//

}
