package com.joseduarte.practicafinalprimertrimestres.db.tables;

public class ComentariosTable {

    //NOMBRE TABLA
    public static final String NOMBRE_TABLA = "Comentarios";

    //COLUMNAS SUELTAS
    public static final String ID_EXPOSICION_FIELD = "IdExposicion";
    public static final String NOMBRE_TRABAJO_FIELD = "NombreTrab";
    public static final String COMENTARIO_COMENTARIOS_FIELD = "Comentario";

    //ARRAY COLUMNAS
    public static final String[] COLUMNAS_TABLA =  new String[] {
            ID_EXPOSICION_FIELD,
            NOMBRE_TRABAJO_FIELD,
            COMENTARIO_COMENTARIOS_FIELD
    };

    //ORDEN PARA CREACION TABLA
    public static final String INS_COMENTARIOS = "CREATE TABLE "+NOMBRE_TABLA+"("+
            ID_EXPOSICION_FIELD+" int," +
            NOMBRE_TRABAJO_FIELD+" VARCHAR(30)," +
            COMENTARIO_COMENTARIOS_FIELD+" VARCHAR(100),"+
            "CONSTRAINT PK_"+NOMBRE_TABLA+" PRIMARY KEY ("+ID_EXPOSICION_FIELD+", "+NOMBRE_TRABAJO_FIELD+"),"+
            "CONSTRAINT CH_"+COMENTARIO_COMENTARIOS_FIELD+"_COM_NN CHECK ("+COMENTARIO_COMENTARIOS_FIELD+" IS NOT NULL),"+

            //resumen Constraint: CONSTRAINT FK_Comentarios_Exposiciones FOREIGN KEY (IdExposicion) REFERENCES Exposiciones(IdExposicion)
            "CONSTRAINT FK_"+NOMBRE_TABLA+"_"+ExposicionesTable.NOMBRE_TABLA+" FOREIGN KEY ("+ID_EXPOSICION_FIELD+") " +
            "REFERENCES "+ExposicionesTable.NOMBRE_TABLA+"("+ExposicionesTable.ID_EXPOSICION_FIELD+") " +
            "ON DELETE CASCADE,"+

            //resumen Constraint: CONSTRAINT FK_Comentarios_Trabajos FOREIGN KEY (NombreTrab) REFERENCES Trabajos(NombreTrab)
            "CONSTRAINT FK_"+NOMBRE_TABLA+"_"+TrabajosTable.NOMBRE_TABLA+" FOREIGN KEY ("+NOMBRE_TRABAJO_FIELD+") " +
            "REFERENCES "+TrabajosTable.NOMBRE_TABLA+"("+TrabajosTable.NOMBRE_TRABAJO_FIELD+") " +
            "ON DELETE CASCADE"+
            ")";

    //WHERE SENTENCES
    public static final String DEFAULT_WHERE_SENTENCE = makeWhereSentence(getPrimaryKey());
    public static String makeWhereSentence(String[] fields) {
        return ExposicionesTable.makeWhereSentence(fields[0]) + " AND " +
                TrabajosTable.makeWhereSentence(fields[1]);
    }

    public static String makeQuery(String where) {
        return "SELECT * FROM " +NOMBRE_TABLA+ " " + where;
    }

    //SELECTS
    public static final String SELECT_ALL = "SELECT * FROM "+NOMBRE_TABLA;
    public static final String SELECT_BY_PRIMARY_KEY = "SELECT * FROM "+NOMBRE_TABLA+" " +
                                                       "WHERE "+DEFAULT_WHERE_SENTENCE;

    public static String[] getPrimaryKey() {
        return new String[] {ID_EXPOSICION_FIELD, NOMBRE_TRABAJO_FIELD};
    }

    public static String[] getForeignKeys() {
        return new String[] {ID_EXPOSICION_FIELD, NOMBRE_TRABAJO_FIELD};
    }
}
