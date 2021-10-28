package com.joseduarte.practicafinalprimertrimestres;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private String savedPassword = "";
    private boolean hasSingInApp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        savedPassword = prefs.getString("password","");

        if(savedPassword != null && !savedPassword.isEmpty()) {
            hasSingInApp = false;
            setContentView(R.layout.activity_logging);
        }
        else {
            hasSingInApp = true;
            setContentView(R.layout.activity_logging_new_account);
        }
    }

    public void onClickLoginButton(View view) {
        EditText passwordTV = (EditText)findViewById(R.id.login_password);
        String password = passwordTV.getText().toString();

        if(!password.isEmpty()) {//Si el campo no esta vacio
            if (savedPassword.equals(password)) { //y si la contraseña introducida es correcta
                startAPP();
            } else {
                //si la contraseña es incorrecta
                passwordTV.setBackgroundColor(0xFFC2C2);
                ((TextView) findViewById(R.id.login_error)).setText(R.string.login_error_password);
            }
        }
        else {
            //si el campo esta en blanco
            passwordTV.setBackgroundColor(0xFFC2C2);
            ((TextView)findViewById(R.id.obligatory_field_pass))
                    .setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.login_error))
                    .setText(R.string.login_error_password_is_empty);
        }
    }

    public void onClickSingInButton(View view) {
        EditText passwordTV = (EditText)findViewById(R.id.login_password);
        EditText confirmedPasswordTV = (EditText)findViewById(R.id.login_confirm_password);
        String password = passwordTV.getText().toString();
        String confirmedPassword = confirmedPasswordTV.getText().toString();

        if(
            !password.isEmpty() && //Si el primer campo no esta vacio
            !confirmedPassword.isEmpty() && //Si el campo de confirmacion no esta vacio
            password.equals(confirmedPassword) //si las contraseñas coinciden
        ) {
            //Si todo va bien
            SharedPreferences.Editor prefsEditor = prefs.edit();
            prefsEditor.putString("password", password);
            prefsEditor.commit();

            startAPP();
        }
        else if (!password.equals(confirmedPassword)) {
            //si no coinciden las contraseñas
            passwordTV.setBackgroundColor(0xFFC2C2);
            ((TextView)findViewById(R.id.login_error_textview))
                    .setText(R.string.login_error_password);
        }
        else {
            //Cuando se deja en blanco la contraseña
            if (password.isEmpty()) {
                passwordTV.setBackgroundColor(0xFFC2C2);
                findViewById(R.id.obligatory_field_new_pass)
                        .setVisibility(View.VISIBLE);
            }

            //Cuando se deja en blanco la contraseña de confirmacion
            if(confirmedPassword.isEmpty()) {
                confirmedPasswordTV.setBackgroundColor(0xFFC2C2);
                findViewById(R.id.obligatory_field_confirm_pass)
                        .setVisibility(View.VISIBLE);
            }

            //mostrar mensaje de que se dejo una contraseña en blanco
            ((TextView)findViewById(R.id.login_error_textview))
                    .setText(R.string.login_error_password_is_empty);
        }
    }

    public void clear() {
        ((TextView)findViewById(R.id.login_password)).setText("");
        if(hasSingInApp) {
            ((TextView)findViewById(R.id.login_confirm_password)).setText("");
        }
    }

    public void startAPP() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        if(hasSingInApp) {
            hasSingInApp = false;
            setContentView(R.layout.activity_logging);
        }
        clear();
    }
}
