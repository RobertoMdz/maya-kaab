package com.coopera.mayakaab.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coopera.mayakaab.MainActivity;
import com.coopera.mayakaab.R;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtxtCorreo, edtxtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        edtxtCorreo = findViewById(R.id.edtxtCorreoLogin);
        edtxtPass = findViewById(R.id.edtxtPasswordLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

        ///ESCUCHADOR DEL EDIT TEXTS COORREO//
        edtxtCorreo.addTextChangedListener(loginTextWatcher);
        edtxtPass.addTextChangedListener(loginTextWatcher);

    }

    private void iniciarSesion(){
        String urlLogin = "";
        saveLoginUser();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

        /*StringRequest request = new StringRequest(Request.Method.POST, urlLogin, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String respuestaLogin = response;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Compruba tu conexión a internet", Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                String correo = edtxtCorreo.getText().toString();
                String pass = edtxtPass.getText().toString();

                Map <String,String> dataLogin= new HashMap<>();

                dataLogin.put("username",correo);
                dataLogin.put("password",pass);
                return dataLogin;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);*/
    }

    // guardar inicio de sesion true //
    private void saveLoginUser() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefsLogin",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("userLogged", true);
        editor.putString("email", edtxtCorreo.getText().toString());
        editor.apply();
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String emailImput = edtxtCorreo.getText().toString().trim();
            String passwordImput = edtxtPass.getText().toString().trim();

            btnLogin.setEnabled(!emailImput.isEmpty() && !passwordImput.isEmpty());
            if (btnLogin.isEnabled()){
                btnLogin.setTextColor(Color.parseColor("#FFFFFF"));
            }else {
                btnLogin.setTextColor(Color.parseColor("#CCCBCB"));
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

}