package com.ashasoftware.studyday;

/**
 * Created by natanmorais on 02/11/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 4;
    // Database Name
    private static final String DATABASE_NAME = "calendarSD";

    public SQLiteHelper( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        String CREATE_MATERIA_TABLE = "CREATE TABLE materia ( " +
                                      "cod_materia INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                      "nome_materia TEXT NOT NULL, " +
                                      "prof_materia TEXT, " +
                                      "cor_materia INTEGER NOT NULL, " +
                                      "dif_prof INTEGER NOT NULL, " +
                                      "dif_materia INTEGER NOT NULL )";

        String CREATE_AVALIACAO_TABLE = "CREATE TABLE avaliacao ( " +
                                        "cod_aval INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        "cod_materia INTEGER NOT NULL, " +
                                        "nome_aval TEXT NOT NULL, " +
                                        "descricao_aval TEXT, " +
                                        "nota_aval INTEGER NOT NULL, " +
<<<<<<< HEAD
                                        "peso_aval INTEGER NOT NULL " +
                                        "FOREIGN KEY materia(`cod_materia`) REFERENCES cod_materia)";
=======
                                        "peso_aval INTEGER NOT NULL, " +
                                        "FOREIGN KEY (cod_materia) REFERENCES cod_materia)";
>>>>>>> layout

        String CREATE_AULA_TABLE = "CREATE TABLE aula (" +
                                   "cod_aula INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                                   "cod_materia INTEGER NOT NULL," +
                                   "dia_ini_aula INTEGER NOT NULL," +
                                   "dia_fim_aula INTEGER NOT NULL," +
<<<<<<< HEAD
                                   "FOREIGN KEY materia(`cod_materia`) REFERENCES cod_materia )";
=======
                                   "FOREIGN KEY (cod_materia) REFERENCES cod_materia )";
>>>>>>> layout

        String CREATE_NAOESCOLAR_TABLE = "CREATE TABLE nao_escolar ( " +
                                         "cod_ne INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                         "nome_ne TEXT NOT NULL, " +
                                         "descricao_ne TEXT, " +
                                         "dia_ini_ne INTEGER NOT NULL, " +
                                         "dia_fim_ne INTEGER )";

        db.execSQL( CREATE_MATERIA_TABLE );
        db.execSQL( CREATE_NAOESCOLAR_TABLE );
        db.execSQL( CREATE_AULA_TABLE );
        db.execSQL( CREATE_AVALIACAO_TABLE );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        //Sem Upgrade no momento.
    }

    public void addMateria( String nome, String professor, int cor, int difProfessor, int difMateria ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( "nome_materia", nome );
        values.put( "prof_materia", professor );
        values.put( "cor_materia", cor );
        values.put( "dif_prof", difProfessor );
        values.put( "dif_materia", difMateria );

        db.insert( "materia", null, values );

        db.close();
    }

    public void addAvaliacao( int CodigoMateria, String nome, String descricao, int nota, int peso ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( "cod_materia", CodigoMateria );
        values.put( "nome_aval", nome );
        values.put( "descricao_aval", descricao );
        values.put( "nota_aval", nota );
        values.put( "peso_aval", peso );

        db.insert( "avaliacao", null, values );

        db.close();
    }

    public void addNaoEscolar( String nome, String descricao, long inicio, long fim ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( "nome_ne", nome );
        values.put( "descricao_ne", descricao );
        values.put( "dia_ini_ne", inicio );
        values.put( "dia_fim_ne", fim );

        db.insert( "nao_escolar", null, values );

        db.close();
    }

    public void addAula( int materia, long inicio, long fim ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( "cod_materia", materia );
        values.put( "dia_ini_aula", inicio );
        values.put( "dia_fim_aula", fim );

        db.insert( "aula", null, values );

        db.close();
    }

    public Materia getMateria( int Codigo ) {
        String[] Colunas = { "cod_materia", "nome_materia", "prof_materia", "cor_materia", "dif_prof", "dif_materia" };

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query( "materia", // a. table
                          Colunas, // b. column names
                          " cod_materia = ?", // c. selections
                          new String[]{ String.valueOf( Codigo ) }, // d. selections args
                          null, // e. group by
                          null, // f. having
                          null, // g. order by
                          null ); // h. limit

        // 3. if we got results get the first one
        if( cursor != null )
            cursor.moveToFirst();

        // 4. build book object
        Materia materia = new Materia( cursor.getString( 1 ),
                                       cursor.getString( 2 ),
                                       cursor.getInt( 3 ),
                                       cursor.getInt( 4 ),
                                       cursor.getInt( 5 ) );
        materia.setCodigo( cursor.getInt( 0 ) );

        cursor.close();
        db.close();

        // 5. return book
        return materia;
    }

    public Avaliacao getAvaliacao( int Codigo ) {
        String[] Colunas = { "cod_aval", "cod_materia", "nome_aval", "descricao_aval", "nota_aval", "peso_aval" };

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query( "avaliacao", // a. table
                          Colunas, // b. column names
                          " cod_aval = ?", // c. selections
                          new String[]{ String.valueOf( Codigo ) }, // d. selections args
                          null, // e. group by
                          null, // f. having
                          null, // g. order by
                          null ); // h. limit

        // 3. if we got results get the first one
        if( cursor != null )
            cursor.moveToFirst();

        // 4. build book object
        Avaliacao avaliacao;
        try {
            avaliacao = new Avaliacao( cursor.getInt( 1 ),
                                       cursor.getString( 2 ),
                                       cursor.getString( 3 ),
                                       cursor.getInt( 4 ),
                                       cursor.getInt( 5 ) );
        } catch( Exception e ) {
            return null;
        }

        avaliacao.setCodigo( cursor.getInt( 0 ) );

        cursor.close();
        db.close();

        // 5. return book
        return avaliacao;
    }

    public NaoEscolar getNaoEscolar( int Codigo ) {
        String[] Colunas = { "cod_ne", "nome_ne", "descricao_ne", "dia_ne", "dia_fim_ne" };

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query( "nao_escolar", // a. table
                          Colunas, // b. column names
                          " cod_ne = ?", // c. selections
                          new String[]{ String.valueOf( Codigo ) }, // d. selections args
                          null, // e. group by
                          null, // f. having
                          null, // g. order by
                          null ); // h. limit

        // 3. if we got results get the first one
        if( cursor != null )
            cursor.moveToFirst();

        // 4. build book object
        NaoEscolar naoescolar = new NaoEscolar( cursor.getString( 1 ),
                                                cursor.getString( 2 ),
                                                cursor.getLong( 3 ),
                                                cursor.getLong( 4 ) );
        naoescolar.setCodigo( cursor.getInt( 0 ) );

        cursor.close();
        db.close();

        // 5. return book
        return naoescolar;
    }

    public Aula getAula( int Codigo ) {
        String[] Colunas = { "cod_aula", "cod_materia", "dia_ini_aula", "dia_fim_aula" };

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query( "aula", // a. table
                          Colunas, // b. column names
                          " cod_aula = ?", // c. selections
                          new String[]{ String.valueOf( Codigo ) }, // d. selections args
                          null, // e. group by
                          null, // f. having
                          null, // g. order by
                          null ); // h. limit

        // 3. if we got results get the first one
        if( cursor != null )
            cursor.moveToFirst();

        // 4. build book object
        Aula aula;
        try {
            aula = new Aula( cursor.getInt( 1 ),
                             cursor.getLong( 2 ),
                             cursor.getLong( 3 ) );
        } catch( Exception e ) {
            return null;
        }

        aula.setCodigo( cursor.getInt( 0 ) );

        cursor.close();
        db.close();

        // 5. return book
        return aula;
    }

    public List<Materia> getAllMaterias() {
        List<Materia> materias = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + "materia";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( query, null );

        // 3. go over each row, build book and add it to list
        Materia materia;
        if( cursor.moveToFirst() ) {
            do {
                materia = new Materia( cursor.getString( 1 ),
                                       cursor.getString( 2 ),
                                       cursor.getInt( 3 ),
                                       cursor.getInt( 4 ),
                                       cursor.getInt( 5 ) );
                materia.setCodigo( cursor.getInt( 0 ) );
                // Add book to books
                materias.add( materia );
            } while( cursor.moveToNext() );
        }

        cursor.close();
        db.close();

        // return books
        return materias;
    }

    public List<Avaliacao> getAllAvaliacoes() {
        List<Avaliacao> avaliacoes = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + "avaliacao";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( query, null );

        // 3. go over each row, build book and add it to list
        Avaliacao avaliacao;
        if( cursor.moveToFirst() ) {
            do {
                try {
                    avaliacao = new Avaliacao( cursor.getInt( 1 ),
                                               cursor.getString( 2 ),
                                               cursor.getString( 3 ),
                                               cursor.getInt( 4 ),
                                               cursor.getInt( 5 ) );
                } catch( Exception e ) {
                    continue;
                }
                avaliacao.setCodigo( cursor.getInt( 0 ) );

                // Add book to books
                avaliacoes.add( avaliacao );
            } while( cursor.moveToNext() );
        }

        cursor.close();
        db.close();

        // return books
        return avaliacoes;
    }

    public List<NaoEscolar> getAllNaoEscolares() {
        List<NaoEscolar> naoescolares = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + "nao_escolar";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( query, null );

        // 3. go over each row, build book and add it to list
        NaoEscolar naoescolar;
        if( cursor.moveToFirst() ) {
            do {
                naoescolar = new NaoEscolar( cursor.getString( 1 ),
                                             cursor.getString( 2 ),
                                             cursor.getLong( 3 ),
                                             cursor.getLong( 4 ) );
                naoescolar.setCodigo( cursor.getInt( 0 ) );

                // Add book to books
                naoescolares.add( naoescolar );
            } while( cursor.moveToNext() );
        }

        cursor.close();
        db.close();

        // return books
        return naoescolares;
    }

    public List<Aula> getAllAulas() {
        List<Aula> aulas = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + "aula";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( query, null );

        // 3. go over each row, build book and add it to list
        Aula aula;
        if( cursor.moveToFirst() ) {
            do {
                try {
                    aula = new Aula( cursor.getInt( 1 ),
                                     cursor.getLong( 2 ),
                                     cursor.getLong( 3 ) );
                } catch( Exception e ) {
                    continue;
                }
                aula.setCodigo( cursor.getInt( 0 ) );

                // Add book to books
                aulas.add( aula );
            } while( cursor.moveToNext() );
        }

        cursor.close();
        db.close();

        // return books
        return aulas;
    }

    public int updateMateria( Materia materia ) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put( "nome_materia", materia.getNome() );
        values.put( "prof_materia", materia.getProfessor() );
        values.put( "cor_materia", materia.getCor() );
        values.put( "dif_prof", materia.getDifProfessor() );
        values.put( "dif_materia", materia.getDifMateria() );

        // 3. updating row
        int i = db.update( "materia", //table
                           values, // column/value
                           "cod_materia" + " = ?", // selections
                           new String[]{ String.valueOf( materia.getCodigo() ) } ); //selection args

        // 4. close
        db.close();

        return i;
    }

    public int updateAvaliacao( Avaliacao avaliacao ) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put( "cod_materia", avaliacao.getMateria().getCodigo() );
        values.put( "nome_aval", avaliacao.getNome() );
        values.put( "descricao_aval", avaliacao.getDescricao() );
        values.put( "nota_aval", avaliacao.getNota() );
        values.put( "peso_aval", avaliacao.getPeso() );

        // 3. updating row
        int i = db.update( "avaliacao", //table
                           values, // column/value
                           "cod_aval" + " = ?", // selections
                           new String[]{ String.valueOf( avaliacao.getCodigo() ) } ); //selection args

        // 4. close
        db.close();

        return i;
    }

    public int updateNaoEscolar( NaoEscolar naoescolar ) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put( "nome_ne", naoescolar.getNome() );
        values.put( "descricao_ne", naoescolar.getDescricao() );
        values.put( "dia_ini_ne", naoescolar.getDiaIni().getTimeInMillis() );
        values.put( "dia_fim_ne", naoescolar.getDiaFim().getTimeInMillis() );

        // 3. updating row
        int i = db.update( "nao_escolar", //table
                           values, // column/value
                           "cod_ne" + " = ?", // selections
                           new String[]{ String.valueOf( naoescolar.getCodigo() ) } ); //selection args

        // 4. close
        db.close();

        return i;
    }

    public int updateAula( Aula aula ) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put( "cod_materia", aula.getMateria().getCodigo() );
        values.put( "dia_ini_aula", aula.getDiaIni().getTimeInMillis() );
        values.put( "dia_fim_aula", aula.getDiaFim().getTimeInMillis() );

        // 3. updating row
        int i = db.update( "aula", //table
                           values, // column/value
                           "cod_aula" + " = ?", // selections
                           new String[]{ String.valueOf( aula.getCodigo() ) } ); //selection args

        // 4. close
        db.close();

        return i;
    }

    public void deleteMateria( int codigo ) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete( "materia", //table name
                   "cod_materia" + " = ?",  // selections
                   new String[]{ String.valueOf( codigo ) } ); //selections args

        // 3. close
        db.close();
    }

    public void deleteAvaliacao( int codigo ) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete( "avaliacao", //table name
                   "cod_aval" + " = ?",  // selections
                   new String[]{ String.valueOf( codigo ) } ); //selections args

        // 3. close
        db.close();
    }

    public void deleteNaoEscolar( int codigo ) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete( "nao_escolar", //table name
                   "cod_ne" + " = ?",  // selections
                   new String[]{ String.valueOf( codigo ) } ); //selections args

        // 3. close
        db.close();
    }

    public void deleteAula( int codigo ) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete( "aula", //table name
                   "cod_aula" + " = ?",  // selections
                   new String[]{ String.valueOf( codigo ) } ); //selections args

        // 3. close
        db.close();
    }
}
