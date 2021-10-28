package com.joseduarte.practicafinalprimertrimestres.appliers;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.joseduarte.practicafinalprimertrimestres.MainActivity;
import com.joseduarte.practicafinalprimertrimestres.R;
import com.joseduarte.practicafinalprimertrimestres.SourcesManager;
import com.joseduarte.practicafinalprimertrimestres.db.DBController;
import com.joseduarte.practicafinalprimertrimestres.models.Artistas;
import com.joseduarte.practicafinalprimertrimestres.models.Exposiciones;
import com.joseduarte.practicafinalprimertrimestres.models.Models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ArtistasApplier implements Applier {
    @Override
    public void applyTo(final View view, final Models models) {

        final Artistas artistas = (Artistas)models;

        ((TextView)view.findViewById(R.id.name_artistas_tv))
                .setText(artistas.getNombre());
        ((TextView)view.findViewById(R.id.dni_artist_tv))
                .setText(artistas.getDniPasaporte());;
        ((TextView)view.findViewById(R.id.fech_nac_artist_tv))
                .setText(
                        new SimpleDateFormat("dd/MM/yyyy")
                                .format(artistas.getFechaNacimiento())
                );

        ((TextView)view.findViewById(R.id.artist_email_tv))
                .setText(artistas.getEmail());;
        ((TextView)view.findViewById(R.id.artist_web_url_tv))
                .setText(artistas.getWebBlog());;

        ((TextView)view.findViewById(R.id.artist_work_phone_tv))
                .setText(
                        (artistas.getMovilTrabajo()==0) ? "00 000 000 000" : artistas.getMovilTrabajo()+""
                );;
        ((TextView)view.findViewById(R.id.artist_personal_phone_tv))
                .setText(
                        (artistas.getMovilPersonal()==0) ? "00 000 000 000" : artistas.getMovilPersonal()+""
                );;
        ((TextView)view.findViewById(R.id.artist_landline_tv))
                .setText(
                        (artistas.getTelefonoFijo()==0) ? "00 000 000 000" : artistas.getTelefonoFijo()+""
                );;

        ((TextView)view.findViewById(R.id.artist_address_tv))
                .setText(artistas.getDireccion());;
        ((TextView)view.findViewById(R.id.artist_town_tv))
                .setText(artistas.getPoblacion());;
        ((TextView)view.findViewById(R.id.artist_province_tv))
                .setText(artistas.getProvincia());;
        ((TextView)view.findViewById(R.id.artist_country_tv))
                .setText(artistas.getPais());;



        AppCompatImageButton callbtn = view.findViewById(R.id.call_btn);
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE )
                        == PackageManager.PERMISSION_GRANTED) {

                    PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                    popupMenu.getMenuInflater().inflate(R.menu.call_phone_menu, popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            int id = item.getItemId();

                            if(id == R.id.item_work_phone) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                                        .setTitle(R.string.call_tittle)
                                        .setMessage(R.string.call_messaje)
                                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+" + artistas.getMovilTrabajo()));
                                                MainActivity.mainActivity.startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });

                                final AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                            else if(id == R.id.item_personal_phone) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                                        .setTitle(R.string.call_tittle)
                                        .setMessage(R.string.call_messaje)
                                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+" + artistas.getMovilPersonal()));
                                                MainActivity.mainActivity.startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });

                                final AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                            else if(id == R.id.item_landline_phone) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                                        .setTitle(R.string.call_tittle)
                                        .setMessage(R.string.call_messaje)
                                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+" + artistas.getTelefonoFijo()));
                                                MainActivity.mainActivity.startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });

                                final AlertDialog dialog = builder.create();
                                dialog.show();


                            }

                            return true;

                        }
                    });
                    popupMenu.show();


                }
            }
        });

        AppCompatImageButton sendbtn = view.findViewById(R.id.send_btn);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.INTERNET )
                        == PackageManager.PERMISSION_GRANTED) {

                    Calendar artistBirthdaycalendar = Calendar.getInstance();
                    artistBirthdaycalendar.setTime(artistas.getFechaNacimiento());

                    Calendar currentDayCalendar = Calendar.getInstance();
                    currentDayCalendar.setTime(new Date());

                    Boolean isBirthday = (artistBirthdaycalendar.get(Calendar.DAY_OF_MONTH)==currentDayCalendar.get(Calendar.DAY_OF_MONTH));
                    isBirthday = isBirthday && (artistBirthdaycalendar.get(Calendar.MONTH)==currentDayCalendar.get(Calendar.MONTH));
                    if(isBirthday) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setData(Uri.parse("mailto:"+artistas.getEmail()));
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{artistas.getEmail()});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Exhibition Hall");
                        intent.putExtra(Intent.EXTRA_SUBJECT, view.getContext().getString(R.string.happy_birthday));
                        MainActivity.mainActivity.startActivity(intent);
                    } else {
                        Toast toast = Toast.makeText(view.getContext(),
                                view.getContext().getString(R.string.not_birthday),
                                Toast.LENGTH_LONG);

                        toast.show();
                    }



                }
            }
        });

    }
}
