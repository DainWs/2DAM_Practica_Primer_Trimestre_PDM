package com.joseduarte.practicafinalprimertrimestres.db.tables;

public class ExposicionesTable {
    //NOMBRE TABLA
    public static final String NOMBRE_TABLA = "Exposiciones";
    //COLUMNAS SUELTAS
    public static final String ID_EXPOSICION_FIELD = "IdExposicion";
    public static final String NOMBRE_EXPOSICION_FIELD = "NombreExp";
    public static final String DESCRIPCION_EXPOSICION_FIELD = "Descripcion";
    public static final String FECHA_INICIO_EXPOSICION_FIELD = "FechaInicio";
    public static final String FECHA_FIN_EXPOSICION_FIELD = "FechaFin";

    //ARRAY COLUMNAS
    public static final String[] COLUMNAS_TABLA = new String[] {
            ID_EXPOSICION_FIELD,
            NOMBRE_EXPOSICION_FIELD,
            DESCRIPCION_EXPOSICION_FIELD,
            FECHA_INICIO_EXPOSICION_FIELD,
            FECHA_FIN_EXPOSICION_FIELD
    };

    //ORDEN PARA CREACION TABLA
    public static final String INS_EXPOSICIONES = "CREATE TABLE " + NOMBRE_TABLA + "( "+
            ID_EXPOSICION_FIELD + " int," +
            NOMBRE_EXPOSICION_FIELD + " varchar(100)," +
            DESCRIPCION_EXPOSICION_FIELD + " varchar(100)," +
            FECHA_INICIO_EXPOSICION_FIELD + " long," +
            FECHA_FIN_EXPOSICION_FIELD + " long,"+
            "CONSTRAINT PK_"+NOMBRE_TABLA +" PRIMARY KEY ("+ID_EXPOSICION_FIELD+"),"+
            "CONSTRAINT CH_"+NOMBRE_EXPOSICION_FIELD+"_EXPO_NN CHECK("+NOMBRE_EXPOSICION_FIELD+" IS NOT NULL),"+
            "CONSTRAINT CH_"+DESCRIPCION_EXPOSICION_FIELD+"_EXPO_NN CHECK("+DESCRIPCION_EXPOSICION_FIELD+" IS NOT NULL),"+
            "CONSTRAINT CH_"+FECHA_INICIO_EXPOSICION_FIELD+"_EXPO_NN CHECK("+FECHA_INICIO_EXPOSICION_FIELD+" IS NOT NULL),"+
            "CONSTRAINT CH_"+FECHA_FIN_EXPOSICION_FIELD+"_NN CHECK("+FECHA_FIN_EXPOSICION_FIELD+" IS NOT NULL)"+
            ")";


    //WHERE SENTENCES
    public static final String DEFAULT_WHERE_SENTENCE = makeWhereSentence(ID_EXPOSICION_FIELD);
    public static String makeWhereSentence(String fields) {
        return fields+"=?";
    }

    //SELECTS
    public static final String SELECT_ALL = "SELECT * FROM "+NOMBRE_TABLA;
    public static final String SELECT_BY_PRIMARY_KEY = "SELECT * FROM "+NOMBRE_TABLA+" " +
                                                       "WHERE "+DEFAULT_WHERE_SENTENCE;


    public static String[] getPrimaryKey() {
       return new String[] {ID_EXPOSICION_FIELD};
   }

   public static String[] getForeignKeys() {
       return new String[0];
   }
}
