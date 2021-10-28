package com.joseduarte.practicafinalprimertrimestres.db.tables;

public class ExponenTable {

    //NOMBRE TABLA
    public static final String NOMBRE_TABLA = "Exponen";

    //COLUMNAS SUELTAS
    public static final String ID_EXPOSICION_FIELD = "IdExposicion";
    public static final String DNI_ARTISTA_FIELD = "DniPasaporte";

    //ARRAY COLUMNAS
    public static final String[] COLUMNAS_TABLA =  new String[] {
            ID_EXPOSICION_FIELD,
            DNI_ARTISTA_FIELD
    };

    //ORDEN PARA CREACION TABLA
    public static final String INS_EXPONEN = "CREATE TABLE " + NOMBRE_TABLA + "("+
            ID_EXPOSICION_FIELD+" int," +
            DNI_ARTISTA_FIELD+" VARCHAR(20),"+
            "CONSTRAINT PK_" + NOMBRE_TABLA + " PRIMARY KEY ("+ID_EXPOSICION_FIELD+", "+DNI_ARTISTA_FIELD+"),"+

            //resumen Constraint: CONSTRAINT FK_Exponen_Exposiciones FOREIGN KEY (IdExposicion) REFERENCES Exposiciones(IdExposicion)
            "CONSTRAINT FK_" + NOMBRE_TABLA + "_" + ExposicionesTable.NOMBRE_TABLA + " FOREIGN KEY ("+ID_EXPOSICION_FIELD+") " +
            "REFERENCES " + ExposicionesTable.NOMBRE_TABLA + "("+ExposicionesTable.ID_EXPOSICION_FIELD+") " +
            "ON DELETE CASCADE,"+

            //resumen Constraint: CONSTRAINT FK_Exponen_Artistas FOREIGN KEY (DniPasaporte) REFERENCES Artistas(DniPasaporte)
            "CONSTRAINT FK_" + NOMBRE_TABLA + "_" + ArtistasTable.NOMBRE_TABLA + " FOREIGN KEY ("+DNI_ARTISTA_FIELD+") " +
            "REFERENCES " + ArtistasTable.NOMBRE_TABLA + "("+ArtistasTable.DNI_ARTISTA_FIELD+") " +
            "ON DELETE CASCADE"+
            ")";

    //WHERE SENTENCES
    public static final String DEFAULT_WHERE_SENTENCE = makeWhereSentence(getPrimaryKey());
    public static String makeWhereSentence(String[] fields) {
        return ExposicionesTable.makeWhereSentence(fields[0]) + " AND " +
               ArtistasTable.makeWhereSentence(fields[1]);
    }

    public static String makeQuery(String where) {
        return "SELECT * FROM " +NOMBRE_TABLA+ " " + where;
    }

    //SELECTS
    public static final String SELECT_ALL = "SELECT * FROM "+NOMBRE_TABLA;
    public static final String SELECT_BY_PRIMARY_KEY = "SELECT * FROM "+NOMBRE_TABLA+" " +
                                                       "WHERE "+DEFAULT_WHERE_SENTENCE;

    public static String[] getPrimaryKey() {
        return new String[] {ID_EXPOSICION_FIELD, DNI_ARTISTA_FIELD};
    }

    public static String[] getForeignKeys() {
        return new String[] {ID_EXPOSICION_FIELD, DNI_ARTISTA_FIELD};
    }

}
