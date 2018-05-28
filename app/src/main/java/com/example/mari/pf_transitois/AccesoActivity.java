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
import android.widget.EditText;


import com.example.mari.pf_transitois.ws.HttpUtils;
import com.example.mari.pf_transitois.ws.Response;
import com.example.mari.pf_transitois.ws.pojos.Mensaje;
import com.google.gson.Gson;

public class AccesoActivity extends AppCompatActivity {
    private String telefono;
    private String contrasena;
    private EditText txtTelefono;
    private EditText txtCont;
    private Response resws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso);

        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        txtCont = (EditText) findViewById(R.id.txtCont);
    }

    /*
    public void registrar(View v){
        Intent intent = new Intent(AccesoActivity.this,RegistroConductorActivity.class);
        startActivity(intent);
    }*/

    public void entrar(View view) {
        if(validar() && isOnline()) {
            telefono = txtTelefono.getText().toString();
            contrasena = txtCont.getText().toString();
            WSPostLoginTask task = new WSPostLoginTask();
            task.execute(telefono, contrasena);
        }
    }

    public boolean validar(){
        boolean ok = true;
        if(txtTelefono.getText() == null || txtTelefono.getText().toString().isEmpty()){
            txtTelefono.setError("Debes introducir el telefono de acceso");
            ok = false;
        }
        if(txtCont.getText() == null || txtCont.getText().toString().isEmpty()){
            txtCont.setError("Debes introducir la contraseña de acceso");
            ok = false;
        }
        return ok;
    }

    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean online = (cm.getActiveNetworkInfo() != null) && cm.getActiveNetworkInfo().isConnected();
        if(!online){
            mostrarAlertDialog("Sin conexión", "No se encontró ninguna conexión a internet, conectate a " +
                    "alguna red para continuar utilizando la aplicación");
        }
        return online;
    }

    private void mostrarAlertDialog(String titulo, String mensaje){
        final AlertDialog dialog = new AlertDialog.Builder(AccesoActivity.this).create();
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

    class WSPostLoginTask extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String ... params){
            resws = HttpUtils.accesoConductor(params[0], params[1]);
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            resultadoEntrar();
        }
    }

    public void resultadoEntrar(){
        if(resws != null && !resws.isError() && resws.getResult() != null){
            //Validar si es json contiene el texto "idConductor"
            //i lo contiense intenta mapear el json a un objeto Conductor
            if (resws.getResult().contains("idConductor")){
                Intent intent = new Intent(this,MenuActivity.class);
                intent.putExtra("json_conductor", resws.getResult());
                startActivity(intent);
            }else{ // Sino se intenta mapear a un objeto Mensaje y se muestra el mensaje
                Mensaje mensaje = new Gson().fromJson(resws.getResult(), Mensaje.class);
                mostrarAlertDialog((mensaje.isError()) ? "Error" : "Aviso", mensaje.getMensaje());
            }
        }else{ // No se pudieron obtener los datos del WS
            mostrarAlertDialog("Error", resws.getResult());
        }
    }
}
