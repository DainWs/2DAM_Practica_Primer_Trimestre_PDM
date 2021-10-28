package com.joseduarte.practicafinalprimertrimestres.ui.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.joseduarte.practicafinalprimertrimestres.R;

public class SettingsFragment extends Fragment {

    private SharedPreferences prefs;
    private EditText password;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        password = ((EditText) root.findViewById(R.id.conf_pass_field));
        password.setText(prefs.getString("password", ""));

        Button btn = root.findViewById(R.id.edit_pass_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickEditPassword(v);
            }
        });

        return root;
    }

    public void onClickEditPassword(View view) {

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_edit_password, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
            .setView(dialogView)
            .setPositiveButton(R.string.confirm,
                new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText oldPassET = (EditText)dialogView.findViewById(R.id.edit_old_password);
                            EditText newPassET = (EditText)dialogView.findViewById(R.id.edit_new_password);
                            EditText confirmPassET = (EditText)dialogView.findViewById(R.id.edit_confirm_password);
                            String oldPass = oldPassET.getText().toString();
                            String newPass = newPassET.getText().toString();
                            String confirmPass = newPassET.getText().toString();

                            if(!oldPass.isEmpty() && !newPass.isEmpty() && !confirmPass.isEmpty()) {

                                if(newPass.equals(confirmPass)) {
                                    if (password.getText().toString().equals(oldPass)) {
                                        SharedPreferences.Editor prefsEditor = prefs.edit();
                                        prefsEditor.putString("password",newPass);
                                        prefsEditor.commit();
                                        password.setText(newPass);
                                    }
                                }
                                else {
                                    newPassET.setBackgroundColor(0xFFC2C2);
                                    confirmPassET.setBackgroundColor(0xFFC2C2);
                                    Toast.makeText(getContext(),R.string.login_error_confirmation_password, Toast.LENGTH_LONG);
                                }
                            }
                            else {
                                if(oldPass.isEmpty()) {oldPassET.setBackgroundColor(0xFFC2C2);}
                                if(newPass.isEmpty()) {newPassET.setBackgroundColor(0xFFC2C2);}
                                if(confirmPass.isEmpty()) {confirmPassET.setBackgroundColor(0xFFC2C2);}
                            }

                        }
                }
            )
            .setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
            );

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showPassword(View view) {

    }
}