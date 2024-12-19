package com.example.proyecto.util;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto.R;
import com.example.proyecto.models.Datos;
import com.example.proyecto.models.DatosRegistro;
import com.example.proyecto.services.LoginService;
import com.example.proyecto.services.RegistroService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrarUsuario extends AppCompatActivity{
    private EditText editTextUsername;
    private EditText editTextContraseña;
    private EditText editTextEmail;
    private EditText editTextConfirmacion;
    public static final String BASE_URI = "http://10.0.2.2:8080/";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextUsername = findViewById(R.id.usernameText);
        editTextContraseña = findViewById(R.id.ContraseñaText);
        editTextEmail = findViewById(R.id.MailText);
        editTextConfirmacion= findViewById(R.id.ConfirmacionText);

    }
    public void LoginOnClick(View v) {
        Intent intent = new Intent(RegistrarUsuario.this, LoginUsuario.class);
        startActivity(intent);
        finish();
    }
    public void RegisteronClick(View v) {

        String username = editTextUsername.getText().toString();
        String password = editTextContraseña.getText().toString();
        String confirmacion= editTextConfirmacion.getText().toString();
        String email = editTextEmail.getText().toString();
        //Validar el input
        if(!password.equals(confirmacion)) {
            Toast.makeText(this, "Contraseñas incorrectas", Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()||confirmacion.isEmpty()) {

            Toast.makeText(this, "Rellena los campos.", Toast.LENGTH_SHORT).show();
            return;

        }

        DatosRegistro d = new DatosRegistro(username, password, email);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URI)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService register = retrofit.create(LoginService.class);
        Call<DatosRegistro> call = register.newUser(d);
        String respuesta = null;

        call.enqueue(new Callback<DatosRegistro>() {

            @Override
            public void onResponse(Call<DatosRegistro> call, Response<DatosRegistro> response) {

                if (response.isSuccessful()) {
                    //Registro con éxito
                    DatosRegistro datosresponse = response.body();
                    int id= Integer.parseInt(datosresponse.getId());
                    Toast.makeText(RegistrarUsuario.this, "Registro completado.", Toast.LENGTH_SHORT).show();
                    SharedPreferences prefs= getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.putInt("id", id);
                    editor.apply();
                    Intent intent = new Intent(RegistrarUsuario.this, MenuUsuario.class);
                    startActivity(intent);
                    finish();


                } else {
                    //Falla el registro
                    Toast.makeText(RegistrarUsuario.this, "Registro fallido. Inténtalo otra vez.", Toast.LENGTH_SHORT).show();
                    editTextUsername.setText("");
                    editTextContraseña.setText("");
                    editTextEmail.setText("");
                    editTextConfirmacion.setText("");
                }

            }

            @Override
            public void onFailure(Call<DatosRegistro> call, Throwable t) {

                Toast.makeText(RegistrarUsuario.this, "Error de network:" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }

        })
        ;}
}

