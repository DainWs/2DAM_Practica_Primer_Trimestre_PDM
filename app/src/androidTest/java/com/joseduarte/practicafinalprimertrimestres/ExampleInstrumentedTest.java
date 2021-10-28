package com.joseduarte.practicafinalprimertrimestres;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.joseduarte.practicafinalprimertrimestres.db.DBController;
import com.joseduarte.practicafinalprimertrimestres.models.Artistas;
import com.joseduarte.practicafinalprimertrimestres.models.Comentarios;
import com.joseduarte.practicafinalprimertrimestres.models.Exponen;
import com.joseduarte.practicafinalprimertrimestres.models.Exposiciones;
import com.joseduarte.practicafinalprimertrimestres.models.Trabajos;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DBController dbController = new DBController(appContext);

        System.out.println("STARTING TEST...");
        Exposiciones exposiciones = new Exposiciones(3,"Arbol1","los arboles vuelas",new Date(), new Date());
        Exposiciones exposiciones2 = new Exposiciones(4,"Arbol12","los arboles vuelas",new Date(), new Date());
        Exposiciones exposiciones3 = new Exposiciones(5,"Arbol123","los arboles vuelas",new Date(), new Date());

        System.out.println(dbController.add(Exposiciones.class, exposiciones)&&
                dbController.add(Exposiciones.class, exposiciones2)&&
                dbController.add(Exposiciones.class, exposiciones3));

        exposiciones2.setDescripcion("Adios");

        System.out.println(dbController.update(Exposiciones.class, exposiciones2));

        Artistas artistas = new Artistas.ArtistasBuilder("12345678F")
                .setNombre("Michael")
                .setDireccion("Clle Almueca")
                .setEmail("x.a@gmail.com")
                .build();
        Artistas artistas2 = new Artistas.ArtistasBuilder("12340000F")
                .setNombre("Alexa")
                .setDireccion("Clle Runez")
                .setEmail("x.a@gmail.com")
                .build();
        System.out.println(artistas.getPrimaryKeys().length + " " + artistas2.getPrimaryKeys().length);
        System.out.println(dbController.add(Artistas.class, artistas) &&
                dbController.add(Artistas.class, artistas2));

        artistas2.setMovilPersonal(999888777);

        System.out.println(dbController.update(Artistas.class, artistas2));

        Trabajos tabajo = new Trabajos.TrabajosBuilder("Ramon")
                .setDescripcion("Protagonista")
                .setFoto(".\\ramon.png")
                .setPeso(10.4f)
                .setArtista(artistas2)
                .build();

        System.out.println(dbController.add(Trabajos.class, tabajo));
        tabajo.setTamanio(1.88f);
        System.out.println(dbController.update(Trabajos.class, tabajo));

        Comentarios comentario = new Comentarios(exposiciones3, tabajo, "MyComentario");

        System.out.println(dbController.add(Comentarios.class, comentario));

        comentario.setComentario("adios");

        System.out.println(dbController.update(Comentarios.class, comentario));

        Exponen exponen = new Exponen(exposiciones3, artistas2);

        System.out.println(dbController.add(Exponen.class, exponen));

        System.out.println(dbController.remove(Exponen.class, exponen));
        System.out.println(dbController.remove(Comentarios.class, comentario));
        System.out.println(dbController.remove(Trabajos.class, tabajo));
        System.out.println(dbController.remove(Artistas.class, artistas));
        System.out.println(dbController.remove(Artistas.class, artistas2));
        System.out.println(dbController.remove(Exposiciones.class, exposiciones));
        System.out.println(dbController.remove(Exposiciones.class, exposiciones2));
        System.out.println(dbController.remove(Exposiciones.class, exposiciones3));
    }
}