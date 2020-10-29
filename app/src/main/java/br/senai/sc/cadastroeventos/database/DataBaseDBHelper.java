package br.senai.sc.cadastroeventos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import br.senai.sc.cadastroeventos.database.contract.EventoContract;
import br.senai.sc.cadastroeventos.database.contract.LocalContract;
import br.senai.sc.cadastroeventos.modelo.Local;

public class DataBaseDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "evento.db";
    private static final int DATABASE_VERSION = 6;

    public DataBaseDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LocalContract.criarTabela());
        db.execSQL(EventoContract.criarTabela());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(EventoContract.removerTabela());
        db.execSQL(LocalContract.removerTabela());
        db.execSQL(LocalContract.criarTabela());
        db.execSQL(EventoContract.criarTabela());
    }
}
