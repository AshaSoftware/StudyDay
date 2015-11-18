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
import android.widget.Toast;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 4;
    // Database Name
    private static final String DATABASE_NAME = "calendarSD";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INTERVAL_TABLE = "CREATE TABLE interval ( " +
                "cod_interval INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ini_interval INTEGER NOT NULL, " +
                "fim_interval INTEGER NOT NULL )";

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
                "peso_aval INTEGER NOT NULL, " +
                "FOREIGN KEY (cod_materia) REFERENCES cod_materia)";

        String CREATE_AULA_TABLE = "CREATE TABLE aula (" +
                "cod_aula INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "cod_materia INTEGER NOT NULL," +
                "hora_ini_aula INTEGER NOT NULL," +
                "hora_fim_aula INTEGER NOT NULL," +
                "dia_aula INTEGER NOT NULL," +
                "FOREIGN KEY (cod_materia) REFERENCES cod_materia )";

        String CREATE_NAOESCOLAR_TABLE = "CREATE TABLE nao_escolar ( " +
                "cod_ne INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome_ne TEXT NOT NULL, " +
                "descricao_ne TEXT, " +
                "dia_ini_ne INTEGER NOT NULL, " +
                "dia_fim_ne INTEGER )";

        String CREATE_ESTUDO_TABLE = "CREATE TABLE estudo ( " +
                "cod_estudo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cod_materia INTEGER NOT NULL, " +
                "descricao_estudo TEXT, " +
                "dia_ini_estudo INTEGER NOT NULL, " +
                "dia_fim_estudo INTEGER, " +
                "FOREIGN KEY (cod_materia) REFERENCES cod_materia )";

        db.execSQL(CREATE_INTERVAL_TABLE);
        db.execSQL(CREATE_MATERIA_TABLE);
        db.execSQL(CREATE_NAOESCOLAR_TABLE);
        db.execSQL(CREATE_AULA_TABLE);
        db.execSQL(CREATE_AVALIACAO_TABLE);
        db.execSQL(CREATE_ESTUDO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Sem Upgrade no momento.
    }

    public void addInterval(long ini, long fim) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ini_interval", ini);
        values.put("fim_interval", fim);

        db.insert("interval", null, values);

        Log.v("Tag_addInt", "Add com sucesso");

        db.close();
    }

    public void addMateria(String nome, String professor, int cor, int difProfessor, int difMateria) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome_materia", nome);
        values.put("prof_materia", professor);
        values.put("cor_materia", cor);
        values.put("dif_prof", difProfessor);
        values.put("dif_materia", difMateria);

        db.insert("materia", null, values);

        db.close();
    }

    public void addAvaliacao(int CodigoMateria, String nome, String descricao, int nota, int peso) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("cod_materia", CodigoMateria);
        values.put("nome_aval", nome);
        values.put("descricao_aval", descricao);
        values.put("nota_aval", nota);
        values.put("peso_aval", peso);

        db.insert("avaliacao", null, values);

        db.close();
    }

    public void addNaoEscolar(String nome, String descricao, long inicio, long fim) {

        try {
            Log.v("Tag_AddNE", "SQLiteHelper, vamos add?");
            App.getInterval().add(inicio, fim);
            Log.v("Tag_AddNE", "SQLiteHelper, você conseguiu");
        } catch (Exception e) {
            Log.v("Tag_AddNE", "SQLiteHelper, seu troxa");
            Toast.makeText(App.getContext(), R.string.show_naoescolar_timeerror, Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome_ne", nome);
        values.put("descricao_ne", descricao);
        values.put("dia_ini_ne", inicio);
        values.put("dia_fim_ne", fim);

        db.insert("nao_escolar", null, values);

        db.close();
    }

    public void addEstudo(int materia, String descricao, long inicio, long fim) throws Exception {

        try {
            App.getInterval().add(inicio, fim);
        } catch (Exception e) {
            throw e;
        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("cod_materia", materia);
        values.put("descricao_estudo", descricao);
        values.put("dia_ini_estudo", inicio);
        values.put("dia_fim_estudo", fim);

        db.insert("estudo", null, values);

        db.close();
    }

    public void addAula(int materia, long inicio, long fim, int dia) {

        try {
            App.getInterval().add(new Aula(materia, inicio, fim, dia));
        } catch (Exception ignored) {
            return;
        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("cod_materia", materia);
        values.put("hora_ini_aula", inicio);
        values.put("hora_fim_aula", fim);
        values.put("dia_aula", dia);

        db.insert("aula", null, values);

        db.close();
    }

    public Interval getInterval(int Codigo) {
        String[] Colunas = {"cod_interval", "ini_interval", "fim_interval"};

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query("interval", // a. table
                        Colunas, // b. column names
                        " cod_interval = ?", // c. selections
                        new String[]{String.valueOf(Codigo)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Interval interval = new Interval(cursor.getLong(1),
                cursor.getLong(2));
        interval.setCodigo(cursor.getInt(0));

        cursor.close();
        db.close();

        // 5. return book
        return interval;
    }

    public Materia getMateria(int Codigo) {
        String[] Colunas = {"cod_materia", "nome_materia", "prof_materia", "cor_materia", "dif_prof", "dif_materia"};

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query("materia", // a. table
                        Colunas, // b. column names
                        " cod_materia = ?", // c. selections
                        new String[]{String.valueOf(Codigo)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Materia materia = new Materia(cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getInt(4),
                cursor.getInt(5));
        materia.setCodigo(cursor.getInt(0));

        cursor.close();
        db.close();

        // 5. return book
        return materia;
    }

    public Avaliacao getAvaliacao(int Codigo) {
        String[] Colunas = {"cod_aval", "cod_materia", "nome_aval", "descricao_aval", "nota_aval", "peso_aval"};

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query("avaliacao", // a. table
                        Colunas, // b. column names
                        " cod_aval = ?", // c. selections
                        new String[]{String.valueOf(Codigo)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Avaliacao avaliacao;
        try {
            avaliacao = new Avaliacao(cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getInt(5));
        } catch (Exception e) {
            return null;
        }

        avaliacao.setCodigo(cursor.getInt(0));

        cursor.close();
        db.close();

        // 5. return book
        return avaliacao;
    }

    public NaoEscolar getNaoEscolar(int Codigo) {
        String[] Colunas = {"cod_ne", "nome_ne", "descricao_ne", "dia_ini_ne", "dia_fim_ne"};

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query("nao_escolar", // a. table
                        Colunas, // b. column names
                        " cod_ne = ?", // c. selections
                        new String[]{String.valueOf(Codigo)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        NaoEscolar naoescolar = new NaoEscolar(cursor.getString(1),
                cursor.getString(2),
                cursor.getLong(3),
                cursor.getLong(4));
        naoescolar.setCodigo(cursor.getInt(0));

        cursor.close();
        db.close();

        // 5. return book
        return naoescolar;
    }

    public Estudo getEstudo(int Codigo) {
        String[] Colunas = {"cod_estudo", "cod_materia", "descricao_estudo", "dia_ini_estudo", "dia_fim_estudo"};

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query("estudo", // a. table
                        Colunas, // b. column names
                        " cod_estudo = ?", // c. selections
                        new String[]{String.valueOf(Codigo)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Estudo estudo = new Estudo();
        try {
            estudo = new Estudo(cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getLong(3),
                    cursor.getLong(4));
            estudo.setCodigo(cursor.getInt(0));
        } catch (Exception e) {
        }
        cursor.close();
        db.close();

        // 5. return book
        return estudo;
    }

    public Aula getAula(int Codigo) {
        String[] Colunas = {"cod_aula", "cod_materia", "hora_ini_aula", "hora_fim_aula", "dia_aula"};

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query("aula", // a. table
                        Colunas, // b. column names
                        " cod_aula = ?", // c. selections
                        new String[]{String.valueOf(Codigo)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Aula aula;
        try {
            aula = new Aula(cursor.getInt(1),
                    cursor.getLong(2),
                    cursor.getLong(3),
                    cursor.getInt(4));
        } catch (Exception e) {
            return null;
        }

        aula.setCodigo(cursor.getInt(0));

        cursor.close();
        db.close();

        // 5. return book
        return aula;
    }

    public List<Interval> getAllIntervals() {
        List<Interval> intervals = new LinkedList<>();
        Log.v("Tag_isFree", "Entrou no getAll");
        // 1. build the query
        String query = "SELECT * FROM " + "interval";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Interval interval;
        if (cursor.moveToFirst()) {
            Log.v("Tag_isFree", "Entrou no if do getAll");
            do {
                Log.v("Tag_isFree", "Entrou no while do getAll");
                interval = new Interval(cursor.getLong(1),
                        cursor.getLong(2));
                interval.setCodigo(cursor.getInt(0));
                // Add book to books
                intervals.add(interval);
            } while (cursor.moveToNext());
        }
        Log.v("Tag_isFree", "Saindo do getAll, elementos: " + intervals.size());

        cursor.close();
        db.close();

        // return books
        return intervals;
    }

    public List<Materia> getAllMaterias() {
        List<Materia> materias = new LinkedList<>();

        // 1. build the query
        String query = "SELECT * FROM " + "materia";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Materia materia;
        if (cursor.moveToFirst()) {
            do {
                materia = new Materia(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5));
                materia.setCodigo(cursor.getInt(0));
                // Add book to books
                materias.add(materia);
            } while (cursor.moveToNext());
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
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Avaliacao avaliacao;
        if (cursor.moveToFirst()) {
            do {
                try {
                    avaliacao = new Avaliacao(cursor.getInt(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getInt(4),
                            cursor.getInt(5));
                } catch (Exception e) {
                    continue;
                }
                avaliacao.setCodigo(cursor.getInt(0));

                // Add book to books
                avaliacoes.add(avaliacao);
            } while (cursor.moveToNext());
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
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        NaoEscolar naoescolar;
        if (cursor.moveToFirst()) {
            do {
                naoescolar = new NaoEscolar(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getLong(3),
                        cursor.getLong(4));
                naoescolar.setCodigo(cursor.getInt(0));

                // Add book to books
                naoescolares.add(naoescolar);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return books
        return naoescolares;
    }

    public List<Estudo> getAllEstudos() {
        List<Estudo> estudos = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + "estudo";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Estudo estudo;
        if (cursor.moveToFirst()) {
            do {
                estudo = new Estudo(cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getLong(3),
                        cursor.getLong(4));
                estudo.setCodigo(cursor.getInt(0));

                // Add book to books
                estudos.add(estudo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return books
        return estudos;
    }

    public List<Aula> getAllAulas() {
        List<Aula> aulas = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + "aula";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Aula aula;
        if (cursor.moveToFirst()) {
            do {
                try {
                    aula = new Aula(cursor.getInt(1),
                            cursor.getLong(2),
                            cursor.getLong(3),
                            cursor.getInt(4));
                } catch (Exception e) {
                    continue;
                }
                aula.setCodigo(cursor.getInt(0));

                // Add book to books
                aulas.add(aula);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return books
        return aulas;
    }

    public int updateInterval(Interval interval) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("ini_interval", interval.getIni());
        values.put("fim_interval", interval.getFim());

        // 3. updating row
        int i = db.update("interval", //table
                values, // column/value
                "cod_interval" + " = ?", // selections
                new String[]{String.valueOf(interval.getCodigo())}); //selection args

        // 4. close
        db.close();

        return i;
    }

    public int updateMateria(Materia materia) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("nome_materia", materia.getNome());
        values.put("prof_materia", materia.getProfessor());
        values.put("cor_materia", materia.getCor());
        values.put("dif_prof", materia.getDifProfessor());
        values.put("dif_materia", materia.getDifMateria());

        // 3. updating row
        int i = db.update("materia", //table
                values, // column/value
                "cod_materia" + " = ?", // selections
                new String[]{String.valueOf(materia.getCodigo())}); //selection args

        // 4. close
        db.close();

        return i;
    }

    public int updateAvaliacao(Avaliacao avaliacao) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("cod_materia", avaliacao.getMateria().getCodigo());
        values.put("nome_aval", avaliacao.getNome());
        values.put("descricao_aval", avaliacao.getDescricao());
        values.put("nota_aval", avaliacao.getNota());
        values.put("peso_aval", avaliacao.getPeso());

        // 3. updating row
        int i = db.update("avaliacao", //table
                values, // column/value
                "cod_aval" + " = ?", // selections
                new String[]{String.valueOf(avaliacao.getCodigo())}); //selection args

        // 4. close
        db.close();

        return i;
    }

    public int updateNaoEscolar(NaoEscolar naoescolar) {

        NaoEscolar aC = new NaoEscolar();
        for (NaoEscolar ne : App.getDatabase().getAllNaoEscolares()) {
            if (ne.getCodigo() == naoescolar.getCodigo()) {
                Log.v("Tag_updateNE", "Consegui achar quem é");
                aC = ne;
            }
        }
        for (Interval it : App.getDatabase().getAllIntervals()) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(it.getIni());
            if (aC.getDiaIni().get(Calendar.DAY_OF_YEAR) == c.get(Calendar.DAY_OF_YEAR) && aC.getDiaIni().get(Calendar.HOUR) == c.get(Calendar.HOUR) && aC.getDiaIni().get(Calendar.MINUTE) == c.get(Calendar.MINUTE)) {
                Log.v("Tag_updateAula", "Consegui achar igual");
                App.getDatabase().deleteInterval(it.getCodigo());
                long start = naoescolar.getDiaIni().getTimeInMillis(),end = naoescolar.getDiaFim().getTimeInMillis();
                try {
                    App.getInterval().add(start,end);
                } catch (Exception e) {
                    Toast.makeText(App.getContext(), R.string.show_naoescolar_timeerror, Toast.LENGTH_SHORT).show();
                    return -1;
                }
            }
        }

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("nome_ne", naoescolar.getNome());
        values.put("descricao_ne", naoescolar.getDescricao());
        values.put("dia_ini_ne", naoescolar.getDiaIni().getTimeInMillis());
        values.put("dia_fim_ne", naoescolar.getDiaFim().getTimeInMillis());

        // 3. updating row
        int i = db.update("nao_escolar", //table
                values, // column/value
                "cod_ne" + " = ?", // selections
                new String[]{String.valueOf(naoescolar.getCodigo())}); //selection args

        // 4. close
        db.close();

        return i;
    }

    public int updateEstudo(Estudo estudo) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("cod_materia", estudo.getCodigoMateria());
        values.put("descricao_estudo", estudo.getDescricao());
        values.put("dia_ini_estudo", estudo.getDiaIni().getTimeInMillis());
        values.put("dia_fim_estudo", estudo.getDiaFim().getTimeInMillis());

        // 3. updating row
        int i = db.update("estudo", //table
                values, // column/value
                "cod_estudo" + " = ?", // selections
                new String[]{String.valueOf(estudo.getCodigo())}); //selection args

        // 4. close
        db.close();

        return i;
    }

    public int updateAula(Aula aula) {

        Calendar aC = Calendar.getInstance();
        for (Aula clas : App.getDatabase().getAllAulas()) {
            if (clas.getCodigo() == aula.getCodigo()) {
                Log.v("Tag_updateAula", "Consegui achar quem é");
                aC = clas.getIni();
            }
        }
        int i = 0;
        for (Interval it : App.getDatabase().getAllIntervals()) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(it.getIni());
            Log.v("Tag_updateAula", "Dias: " + aula.getDia() + " | " + c.get(Calendar.DAY_OF_WEEK));
            Log.v("Tag_updateAula", "Horas: " + aC.get(Calendar.HOUR) + " | " + c.get(Calendar.HOUR));
            Log.v("Tag_updateAula", "Minutos: " + aC.get(Calendar.MINUTE) + " | " + c.get(Calendar.MINUTE));
            if (aula.getDia() == c.get(Calendar.DAY_OF_WEEK) && aC.get(Calendar.HOUR) == c.get(Calendar.HOUR) && aC.get(Calendar.MINUTE) == c.get(Calendar.MINUTE)) {
                Log.v("Tag_updateAula", "Consegui achar igual");
                App.getDatabase().deleteInterval(it.getCodigo());
                i++;
            }
        }

        if (i > 0) {
            App.getInterval().add(aula);
        }

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("cod_materia", aula.getMateria().getCodigo());
        values.put("hora_ini_aula", aula.getIni().getTimeInMillis());
        values.put("hora_fim_aula", aula.getFim().getTimeInMillis());
        values.put("dia_aula", aula.getDia());

        // 3. updating row
        int j = db.update("aula", //table
                values, // column/value
                "cod_aula" + " = ?", // selections
                new String[]{String.valueOf(aula.getCodigo())}); //selection args

        // 4. close
        db.close();

        return j;
    }

    public void deleteInterval(int codigo) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete("interval", //table name
                "cod_interval" + " = ?",  // selections
                new String[]{String.valueOf(codigo)}); //selections args

        // 3. close
        db.close();
    }

    public void deleteMateria(int codigo) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete("materia", //table name
                "cod_materia" + " = ?",  // selections
                new String[]{String.valueOf(codigo)}); //selections args

        // 3. close
        db.close();
    }

    public void deleteAvaliacao(int codigo) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete("avaliacao", //table name
                "cod_aval" + " = ?",  // selections
                new String[]{String.valueOf(codigo)}); //selections args

        // 3. close
        db.close();
    }

    public void deleteNaoEscolar(int codigo) {
        NaoEscolar aC = new NaoEscolar();
        for (NaoEscolar ne : App.getDatabase().getAllNaoEscolares()) {
            if (ne.getCodigo() == codigo) {
                Log.v("Tag_updateNE", "Consegui achar quem é");
                aC = ne;
            }
        }
        for (Interval it : App.getDatabase().getAllIntervals()) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(it.getIni());
            if (aC.getDiaIni().get(Calendar.DAY_OF_YEAR) == c.get(Calendar.DAY_OF_YEAR) && aC.getDiaIni().get(Calendar.HOUR) == c.get(Calendar.HOUR) && aC.getDiaIni().get(Calendar.MINUTE) == c.get(Calendar.MINUTE)) {
                Log.v("Tag_updateAula", "Consegui achar igual");
                App.getDatabase().deleteInterval(it.getCodigo());
            }
        }

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete("nao_escolar", //table name
                "cod_ne" + " = ?",  // selections
                new String[]{String.valueOf(codigo)}); //selections args

        // 3. close
        db.close();
    }

    public void deleteEstudo(int codigo) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. delete
        db.delete("estudo", //table name
                "cod_estudo" + " = ?",  // selections
                new String[]{String.valueOf(codigo)}); //selections args

        // 3. close
        db.close();
    }

    public void deleteAula(int codigo) {
        Calendar aC = Calendar.getInstance();
        Aula aux = new Aula();
        for (Aula clas : App.getDatabase().getAllAulas()) {
            if (clas.getCodigo() == codigo) {
                aC = clas.getIni();
                aux = clas;
            }
        }
        for (Interval it : App.getDatabase().getAllIntervals()) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(it.getIni());
            if (aux.getDia() == c.get(Calendar.DAY_OF_WEEK) && aC.get(Calendar.HOUR) == c.get(Calendar.HOUR) && aC.get(Calendar.MINUTE) == c.get(Calendar.MINUTE)) {
                App.getDatabase().deleteInterval(it.getCodigo());
            }
        }

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete("aula", //table name
                "cod_aula" + " = ?",  // selections
                new String[]{String.valueOf(codigo)}); //selections args

        // 3. close
        db.close();
    }

    public void deleteAllEstudos() {
        List<Estudo> estudos = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + "estudo";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Estudo estudo;
        if (cursor.moveToFirst()) {
            do {
                estudo = new Estudo(cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getLong(3),
                        cursor.getLong(4));
                estudo.setCodigo(cursor.getInt(0));

                // Add book to books
                estudos.add(estudo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        for (Estudo estudoAux : estudos) {
            deleteEstudo(estudoAux.getCodigo());
        }

        db.close();
    }

    public void deleteAllAulas(int materia) {
        List<Aula> aulas = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + "aula";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Aula aula;
        if (cursor.moveToFirst()) {
            do {
                try {
                    aula = new Aula(cursor.getInt(1),
                            cursor.getLong(2),
                            cursor.getLong(3), cursor.getInt(4));
                    aula.setCodigo(cursor.getInt(0));

                    // Add book to books
                    aulas.add(aula);
                } catch (Exception e) {
                }
            } while (cursor.moveToNext());
        }
        for (Aula aulaAux : aulas) {
            if (aulaAux.getMateria().getCodigo() == materia) {
                deleteAula(aulaAux.getCodigo());
            }
        }
        cursor.close();
        db.close();
    }

    public void deleteAllAvaliacoes(int materia) {
        List<Avaliacao> avaliacoes = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + "avaliacao";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Avaliacao avaliacao;
        if (cursor.moveToFirst()) {
            do {
                try {
                    avaliacao = new Avaliacao(cursor.getInt(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getInt(4), cursor.getInt(5));
                    avaliacao.setCodigo(cursor.getInt(0));

                    // Add book to books
                    avaliacoes.add(avaliacao);
                } catch (Exception e) {
                }
            } while (cursor.moveToNext());
        }
        for (Avaliacao avaliacaoAux : avaliacoes) {
            if (avaliacaoAux.getMateria().getCodigo() == materia) {
                deleteAvaliacao(avaliacaoAux.getCodigo());
            }
        }
        cursor.close();
        db.close();
    }
}
