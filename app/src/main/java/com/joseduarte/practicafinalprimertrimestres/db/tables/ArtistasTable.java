package com.joseduarte.practicafinalprimertrimestres.db.tables;

public class ArtistasTable {

    //NOMBRE TABLA
    public static final String NOMBRE_TABLA = "Artistas";

    //COLUMNAS SUELTAS
    public static final String DNI_ARTISTA_FIELD = "DniPasaporte";
    public static final String NOMBRE_ARTISTA_FIELD = "Nombre";
    public static final String DIRECCION_ARTISTA_FIELD = "Direccion";
    public static final String POBLACION_ARTISTA_FIELD = "Poblacion";
    public static final String PROVINCIA_ARTISTA_FIELD = "Provincia";
    public static final String PAIS_ARTISTA_FIELD = "Pais";
    public static final String MOVIL_TRABAJO_ARTISTA_FIELD = "MovilTrabajo";
    public static final String MOVIL_PERSONAL_ARTISTA_FIELD = "MovilPersonal";
    public static final String TELEFONO_FIJO_ARTISTA_FIELD = "TelefonoFijo";
    public static final String EMAIL_ARTISTA_FIELD = "Email";
    public static final String WEBBLOG_ARTISTA_FIELD = "WebBlog";
    public static final String FECHA_NACIMIENTO_ARTISTA_FIELD = "FechaNacimiento";

    //ARRAY COLUMNAS
    public static final String[] COLUMNAS_TABLA =  new String[] {
            DNI_ARTISTA_FIELD,
            NOMBRE_ARTISTA_FIELD,
            DIRECCION_ARTISTA_FIELD,
            POBLACION_ARTISTA_FIELD,
            PROVINCIA_ARTISTA_FIELD,
            PAIS_ARTISTA_FIELD,
            MOVIL_TRABAJO_ARTISTA_FIELD,
            MOVIL_PERSONAL_ARTISTA_FIELD,
            TELEFONO_FIJO_ARTISTA_FIELD,
            EMAIL_ARTISTA_FIELD,
            WEBBLOG_ARTISTA_FIELD,
            FECHA_NACIMIENTO_ARTISTA_FIELD
    };

    //ORDEN PARA CREACION TABLA
    public static final String INS_ARTISTAS = "CREATE TABLE " + NOMBRE_TABLA + "("+
            DNI_ARTISTA_FIELD+" VARCHAR(20)," +
            NOMBRE_ARTISTA_FIELD+" VARCHAR(20)," +
            DIRECCION_ARTISTA_FIELD+" VARCHAR(20)," +
            POBLACION_ARTISTA_FIELD+" VARCHAR(20)," +
            PROVINCIA_ARTISTA_FIELD+" VARCHAR(20)," +
            PAIS_ARTISTA_FIELD+" VARCHAR(20)," +
            MOVIL_TRABAJO_ARTISTA_FIELD+" int," +
            MOVIL_PERSONAL_ARTISTA_FIELD+" int," +
            TELEFONO_FIJO_ARTISTA_FIELD+" int," +
            EMAIL_ARTISTA_FIELD+" VARCHAR(30)," +
            WEBBLOG_ARTISTA_FIELD+" VARCHAR(100)," +
            FECHA_NACIMIENTO_ARTISTA_FIELD+" long,"+
            "CONSTRAINT PK_"+ NOMBRE_TABLA + " PRIMARY KEY ("+DNI_ARTISTA_FIELD+"),"+
            "CONSTRAINT CH_"+NOMBRE_ARTISTA_FIELD+"_ART_NN CHECK ("+NOMBRE_ARTISTA_FIELD+" IS NOT NULL),"+
            "CONSTRAINT CH_"+DIRECCION_ARTISTA_FIELD+"_ART_NN CHECK ("+DIRECCION_ARTISTA_FIELD+" IS NOT NULL),"+
            "CONSTRAINT CH_"+MOVIL_TRABAJO_ARTISTA_FIELD+"_ART_NN CHECK ("+MOVIL_TRABAJO_ARTISTA_FIELD+" IS NOT NULL),"+
            "CONSTRAINT CH_"+EMAIL_ARTISTA_FIELD+"_ART_NN CHECK ("+EMAIL_ARTISTA_FIELD+" IS NOT NULL),"+
            "CONSTRAINT CH_"+FECHA_NACIMIENTO_ARTISTA_FIELD+"_ART_NN CHECK ("+FECHA_NACIMIENTO_ARTISTA_FIELD+" IS NOT NULL)"+
            ")";

    //WHERE SENTENCES
    public static final String DEFAULT_WHERE_SENTENCE = makeWhereSentence(DNI_ARTISTA_FIELD);
    public static String makeWhereSentence(String fields) {
        return "UPPER("+fields+") LIKE UPPER(?)";
    }

    public static String makeQuery(String where) {
        return "SELECT * FROM " +NOMBRE_TABLA+ " " + where;
    }

    //SELECTS
    public static final String SELECT_ALL = "SELECT * FROM "+NOMBRE_TABLA;
    public static final String SELECT_BY_PRIMARY_KEY = "SELECT * FROM "+NOMBRE_TABLA+" "+
                                                       "WHERE "+DEFAULT_WHERE_SENTENCE;

    public static String[] getPrimaryKey() {
        return new String[] {DNI_ARTISTA_FIELD};
    }

    public static String[] getForeignKeys() {
        return new String[0];
    }
}
