package xyz.android.persistenciasharedpreferences02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button logoutButton = findViewById(R.id.salirButton);
        sharedPreferences = getSharedPreferences(Constante.NAME_SHARED_PREFERENCE, MODE_PRIVATE);

        String username = sharedPreferences.getString(Constante.USERNAME_KEY, "admin");
        TextView bienvenidaTextView = findViewById(R.id.welcomeTextView);
        bienvenidaTextView.setText(String.format("¡Bienvenido %s!", username));

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Borrar el estado de inicio de sesion
                sharedPreferences.edit().remove(Constante.NAME_KEY_LOGGED_IN).apply();

                // Volver a la actividad de inicio de sesion
                MainActivity.start(WelcomeActivity.this);
                finish();
            }
        });

    }

   public static void start(Context context) {
       Intent intent = new Intent(context, WelcomeActivity.class);
       context.startActivity(intent);
   }

}