package com.example.proyecto.util;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto.R;
import com.example.proyecto.models.Datos;
import com.example.proyecto.services.LoginService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginUsuario extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    public static final String BASE_URI = "http://10.0.2.2:8080/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Validamos los campos de EditText
        editTextUsername = findViewById(R.id.name);
        editTextPassword = findViewById(R.id.password);

    }

    public void LoginOnClick(View v) {
        String name = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        //Validar el input
        if (name.isEmpty() || password.isEmpty()) {

            Toast.makeText(this, "Rellena los campos.", Toast.LENGTH_SHORT).show();
            return;

        }

        Datos datosLogin = new Datos(name, password);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URI)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService login = retrofit.create(LoginService.class);
        Call<Datos> call = login.loginUser(datosLogin);
        String respuesta = null;

        call.enqueue(new Callback <Datos>() {

            @Override
            public void onResponse(Call<Datos> call, Response<Datos> response) {

                if (response.isSuccessful()) {
                    Datos datosresponse = response.body();
                    int id= Integer.parseInt(datosresponse.getId());
                    int money= Integer.parseInt(datosresponse.getMoney());
                    String email = datosresponse.getEmail();
                    //Login exitoso
                    Toast.makeText(LoginUsuario.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                    Log.i("INFO", "Sesion Iniciada");
                    SharedPreferences prefs= getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("username", name);
                    editor.putString("password", password);
                    editor.putInt("id", id);
                    editor.apply();
                    //Empezar ShopActivity
                    Intent intent = new Intent(LoginUsuario.this, MenuUsuario.class);
                    startActivity(intent);
                    finish();


                }
                else {
                    //Por si falla el login
                    Toast.makeText(LoginUsuario.this, "Ha fallado el login. Inténtalo otra vez.", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Datos> call, Throwable t) {

                Toast.makeText(LoginUsuario.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Error", t.getMessage());
            }

        });
    }


  public void registerOnClick(View v){
      Intent intent = new Intent (LoginUsuario.this, RegistrarUsuario.class);
      startActivity(intent);
      finish();
    }
}




