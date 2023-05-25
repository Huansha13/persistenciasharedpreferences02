package xyz.android.persistenciasharedpreferences02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        Button crearCuentaButton = findViewById(R.id.crearCuentaButton);

        sharedPreferences = getSharedPreferences(Constante.NAME_SHARED_PREFERENCE, MODE_PRIVATE);
        boolean isAppLogueado = sharedPreferences.getBoolean(Constante.NAME_KEY_LOGGED_IN, false);
        if (isAppLogueado) {
            goToWelcomeActivity();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                String savedUsername = sharedPreferences.getString(Constante.USERNAME_KEY, "admin");
                String savedPassword = sharedPreferences.getString(Constante.PASSWORD_KEY, "123");

                if (username.equals(savedUsername) && password.equals(savedPassword)) {
                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    goToWelcomeActivity();
                } else {
                    // Credenciales inválidas
                    Toast.makeText(MainActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        crearCuentaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Completa los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constante.USERNAME_KEY, username);
                editor.putString(Constante.PASSWORD_KEY, password);
                editor.apply();
                if (editor.commit()) {
                    Toast.makeText(MainActivity.this, "Cuenta creado", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void goToWelcomeActivity() {
        // Guarda el estado de inicio de sesion
        sharedPreferences.edit().putBoolean(Constante.NAME_KEY_LOGGED_IN, true).apply();

        // Abrir la actividad de bienvenida
        WelcomeActivity.start(this);
        finish();

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}