package com.example.proyecto.util;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_DURATION = 1000;
    private static final String PREF_NAME= "LoginPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    Intent intent;
    public static final String BASE_URI = "http://10.0.2.2:8080/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        new Handler(Looper.getMainLooper()).postDelayed(() ->{
            SharedPreferences prefs =getSharedPreferences(PREF_NAME,MODE_PRIVATE);
            boolean isLoggedIn = prefs.getBoolean(KEY_IS_LOGGED_IN,false);
            if(isLoggedIn){
              String username = prefs.getString(KEY_USERNAME, "");
              String password = prefs.getString(KEY_PASSWORD,"");
              validarLogin(username,password);
            }
            else{
                new Handler(Looper.getMainLooper()).postDelayed(this::goToLogin, SPLASH_DURATION);
            }
        }, SPLASH_DURATION);
    }
    private void validarLogin(String username, String password){
        Datos datoslogin = new Datos(username,password);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URI)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginService login = retrofit.create(LoginService.class);
        Call<Datos> call= login.loginUser(datoslogin);
        call.enqueue(new Callback<Datos>() {
            @Override
            public void onResponse(Call<Datos> call, Response<Datos> response) {
                if (response.isSuccessful()) {
                    // Login válido, ir a Shop
                    goToMenu();
                } else {
                    goToLogin();
                }
            }

            @Override
            public void onFailure(Call<Datos> call, Throwable t) {
                // Error de red, nos redirige al loigin
                Log.e("SplashScreen", "Network error: " + t.getMessage());
                Toast.makeText(SplashScreen.this,  "Error de conexión, por favor intenta más tarde", Toast.LENGTH_LONG).show();
                goToLogin();
            }
        });
    }

    private void goToLogin() {
        intent = new Intent(SplashScreen.this, LoginUsuario.class);
        startActivity(intent);
        finish();
    }

    private void goToMenu() {
        intent = new Intent(SplashScreen.this, MenuUsuario.class);
        startActivity(intent);
        finish();
    }



}
