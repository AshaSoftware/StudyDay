package com.ashasoftware.studyday;

/**
 * Created by natanmorais on 02/11/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "calendarSD";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MATERIA_TABLE = "CREATE TABLE materia ( " +
                "cod_materia INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome_materia TEXT NOT NULL, "+ "prof_materia TEXT, "+
                "cor_materia INTEGER NOT NULL, "+ "dif_prof INTEGER NOT NULL, "+ "dif_materia INTEGER NOT NULL )";

        String CREATE_NAOESCOLAR_TABLE = "CREATE TABLE nao_escolar ( " +
                "cod_ne INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome_ne TEXT NOT NULL, "+ "descricao_ne TEXT, "+
                "dia_ne INTEGER NOT NULL, "+ "dia_fim_ne INTEGER, "+ "hora_ini_ne INTEGER NOT NULL, "+ "hora_fim_ne INTEGER NOT NULL )";

        db.execSQL(CREATE_MATERIA_TABLE);
        db.execSQL(CREATE_NAOESCOLAR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Sem Upgrade no momento.
    }

    public void addMateria(Materia pMateria) {
        Log.d("addMateria", pMateria.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Nome", pMateria.getNome());
        values.put("Professor", pMateria.getProfessor());
        values.put("Cor", pMateria.getCor());
        values.put("Dificuldade Professor", pMateria.getDifProfessor());
        values.put("Dificuldade Materia", pMateria.getDifMateria());

        db.insert("materia", null, values);

        db.close();
    }

    public void addNaoEscolar(NaoEscolar pNaoEscolar) {
        Log.d("addNaoEscolar", pNaoEscolar.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Nome", pNaoEscolar.getNome());
        values.put("Descricao", pNaoEscolar.getDescricao());
        values.put("Dia", pNaoEscolar.getDia());
        values.put("Dia Final", pNaoEscolar.getDiaFim());
        values.put("Hora Inicial", pNaoEscolar.getHoraIni());
        values.put("Hora Final", pNaoEscolar.getHoraFim());

        db.insert("nao_escolar", null, values);

        db.close();
    }

    public Materia getMateria(int Codigo){
        String[] Colunas = {"cod_materia","nome_materia","prof_materia","cor_materia","dif_prof","dif_materia"};

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query("materia", // a. table
                        Colunas, // b. column names
                        " cod_materia = ?", // c. selections
                        new String[] { String.valueOf(Codigo) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Materia materia = new Materia();
        materia.setCodigo(Integer.parseInt(cursor.getString(0)));
        materia.setNome(cursor.getString(1));
        materia.setProfessor(cursor.getString(2));
        materia.setCor(Integer.parseInt(cursor.getString(3)));
        materia.setDifProfessor(Integer.parseInt(cursor.getString(4)));
        materia.setDifMateria(Integer.parseInt(cursor.getString(5)));

        //log
        Log.d("getMateria(" + Codigo + ")", materia.toString());

        // 5. return book
        return materia;
    }

    public NaoEscolar getNaoEscolar(int Codigo){
        String[] Colunas = {"cod_ne","nome_ne","descricao_ne","dia_ne","dia_fim_ne","hora_ini_ne","hora_fim_ne"};

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query("nao_escolar", // a. table
                        Colunas, // b. column names
                        " cod_ne = ?", // c. selections
                        new String[] { String.valueOf(Codigo) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        NaoEscolar naoescolar = new NaoEscolar();
        naoescolar.setCodigo(Integer.parseInt(cursor.getString(0)));
        naoescolar.setNome(cursor.getString(1));
        naoescolar.setDescricao(cursor.getString(2));
        naoescolar.setDia(Integer.parseInt(cursor.getString(3)));
        naoescolar.setDiaFim(Integer.parseInt(cursor.getString(4)));
        naoescolar.setHoraIni(Integer.parseInt(cursor.getString(5)));
        naoescolar.setHoraFim(Integer.parseInt(cursor.getString(6)));

        //log
        Log.d("getNaoEscolar(" + Codigo + ")", naoescolar.toString());

        // 5. return book
        return naoescolar;
    }

    public List<Materia> getAllMaterias() {
        List<Materia> materias = new LinkedList<Materia>();

        // 1. build the query
        String query = "SELECT  * FROM " + "materia";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Materia materia = null;
        if (cursor.moveToFirst()) {
            do {
                materia = new Materia();
                materia.setCodigo(Integer.parseInt(cursor.getString(0)));
                materia.setNome(cursor.getString(1));
                materia.setProfessor(cursor.getString(2));
                materia.setCor(Integer.parseInt(cursor.getString(3)));
                materia.setDifProfessor(Integer.parseInt(cursor.getString(4)));
                materia.setDifMateria(Integer.parseInt(cursor.getString(5)));

                // Add book to books
                materias.add(materia);
            } while (cursor.moveToNext());
        }

        Log.d("getAllMaterias()", materias.toString());

        // return books
        return materias;
    }

    public List<NaoEscolar> getAllNaoEscolares() {
        List<NaoEscolar> naoescolares = new LinkedList<NaoEscolar>();

        // 1. build the query
        String query = "SELECT  * FROM " + "nao_escolar";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        NaoEscolar naoescolar = null;
        if (cursor.moveToFirst()) {
            do {
                naoescolar = new NaoEscolar();
                naoescolar.setCodigo(Integer.parseInt(cursor.getString(0)));
                naoescolar.setNome(cursor.getString(1));
                naoescolar.setDescricao(cursor.getString(2));
                naoescolar.setDia(Integer.parseInt(cursor.getString(3)));
                naoescolar.setDiaFim(Integer.parseInt(cursor.getString(4)));
                naoescolar.setHoraIni(Integer.parseInt(cursor.getString(5)));
                naoescolar.setHoraFim(Integer.parseInt(cursor.getString(6)));

                // Add book to books
                naoescolares.add(naoescolar);
            } while (cursor.moveToNext());
        }

        Log.d("getAllNaoEscolares()", naoescolares.toString());

        // return books
        return naoescolares;
    }

    public int updateMateria(Materia materia) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("Nome", materia.getNome());
        values.put("Professor", materia.getProfessor());
        values.put("Cor", materia.getCor());
        values.put("Dificuldade Professor", materia.getDifProfessor());
        values.put("Dificuldade Materia", materia.getDifMateria());

        // 3. updating row
        int i = db.update("materia", //table
                values, // column/value
                "cod_materia"+" = ?", // selections
                new String[] { String.valueOf(materia.getCodigo()) }); //selection args

        // 4. close
        db.close();

        return i;
    }

    public int updateNaoEscolar(NaoEscolar naoescolar) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("Nome", naoescolar.getNome());
        values.put("Descricao", naoescolar.getDescricao());
        values.put("Dia", naoescolar.getDia());
        values.put("Dia Final", naoescolar.getDiaFim());
        values.put("Hora Inicial", naoescolar.getHoraIni());
        values.put("Hora Final", naoescolar.getHoraFim());

        // 3. updating row
        int i = db.update("nao_escolar", //table
                values, // column/value
                "cod_ne"+" = ?", // selections
                new String[] { String.valueOf(naoescolar.getCodigo()) }); //selection args

        // 4. close
        db.close();

        return i;
    }
}
