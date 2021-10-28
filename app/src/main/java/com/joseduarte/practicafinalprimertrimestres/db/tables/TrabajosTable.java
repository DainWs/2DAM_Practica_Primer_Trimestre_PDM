package com.joseduarte.practicafinalprimertrimestres.db.tables;

public class TrabajosTable {
    //NOMBRE TABLA
    public static final String NOMBRE_TABLA = "Trabajos";

    //COLUMNAS SUELTAS
    public static final String NOMBRE_TRABAJO_FIELD = "NombreTrab";
    public static final String DESCRIPCION_TRABAJO_FIELD = "Descripcion";
    public static final String TAMANIO_TRABAJO_FIELD = "Tamanio";
    public static final String PESO_TRABAJO_FIELD = "Peso";
    public static final String DNI_ARTISTA_FIELD = "DniPasaporte";
    public static final String FOTO_TRABAJO_FIELD = "Foto";


    //ARRAY COLUMNAS
    public static final String[] COLUMNAS_TABLA = new String[] {
            NOMBRE_TRABAJO_FIELD,
            DESCRIPCION_TRABAJO_FIELD,
            TAMANIO_TRABAJO_FIELD,
            PESO_TRABAJO_FIELD,
            DNI_ARTISTA_FIELD,
            FOTO_TRABAJO_FIELD
    };

    //ORDEN PARA CREACION TABLA
    public static final String INS_TRABAJOS = "CREATE TABLE " + NOMBRE_TABLA + "("+
            NOMBRE_TRABAJO_FIELD+" VARCHAR(30)," +
            DESCRIPCION_TRABAJO_FIELD+" VARCHAR(30)," +
            TAMANIO_TRABAJO_FIELD+" VARCHAR(30)," +
            PESO_TRABAJO_FIELD+" REAL," +
            DNI_ARTISTA_FIELD+" VARCHAR(20)," +
            FOTO_TRABAJO_FIELD+" VARCHAR(100),"+
            "CONSTRAINT PK_"+NOMBRE_TABLA+" PRIMARY KEY ("+NOMBRE_TRABAJO_FIELD+","+DNI_ARTISTA_FIELD+"),"+
            "CONSTRAINT CH_"+DESCRIPCION_TRABAJO_FIELD+"_TRAB_NN CHECK ("+DESCRIPCION_TRABAJO_FIELD+" IS NOT NULL),"+
            "CONSTRAINT CH_"+FOTO_TRABAJO_FIELD+"_TRAB_NN CHECK ("+FOTO_TRABAJO_FIELD+" IS NOT NULL),"+

            //resumen Constraint: CONSTRAINT FK_Trabajos_Artistas FOREIGN KEY (DniPasaporte) REFERENCES Artistas(DniPasaporte)
            "CONSTRAINT FK_" + NOMBRE_TABLA + "_" + ArtistasTable.NOMBRE_TABLA + " FOREIGN KEY ("+DNI_ARTISTA_FIELD+") " +
            "REFERENCES " + ArtistasTable.NOMBRE_TABLA + "("+ArtistasTable.DNI_ARTISTA_FIELD+") " +
            "ON DELETE CASCADE"+
            ")";

    //WHERE SENTENCES
    public static final String DEFAULT_WHERE_SENTENCE = makeWhereSentence(getPrimaryKey());
    public static String makeWhereSentence(String[] fields) {
        return  makeWhereSentence(fields[0]);
    }

    public static String makeWhereSentence(String fields) {
        return "UPPER("+fields+") LIKE UPPER(?)";
    }

    //SELECTS
    public static final String SELECT_ALL = "SELECT * FROM "+NOMBRE_TABLA;
    public static final String SELECT_BY_PRIMARY_KEY = "SELECT * FROM "+NOMBRE_TABLA+" " +
                                                       "WHERE "+DEFAULT_WHERE_SENTENCE;

    public static String[] getPrimaryKey() {
        return new String[] {NOMBRE_TRABAJO_FIELD, DNI_ARTISTA_FIELD};
    }

    public static String[] getForeignKeys() {
        return new String[] {DNI_ARTISTA_FIELD};
    }
}
