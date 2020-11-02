package br.senai.sc.cadastroeventos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.senai.sc.cadastroeventos.database.entity.EventoEntity;
import br.senai.sc.cadastroeventos.database.entity.LocalEntity;
import br.senai.sc.cadastroeventos.modelo.Evento;
import br.senai.sc.cadastroeventos.modelo.Local;

public class LocalDAO {

    private final String SQL_LISTAR_TODOS = "SELECT * FROM " + LocalEntity.TABLE_NAME;
    private DBGateway dbGateway;

    public LocalDAO(Context context) {
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean salvar (Local local) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LocalEntity.COLUMN_NAME_BAIRRO, local.getBairro());
        contentValues.put(LocalEntity.COLUMN_NAME_CAPACIDADE, local.getCapacidade());
        contentValues.put(LocalEntity.COLUMN_NAME_CIDADE, local.getCidade());
        contentValues.put(LocalEntity.COLUMN_NAME_NOME, local.getNome());
        if (local.getId() > 0) {
            return dbGateway.getDatabase().update(LocalEntity.TABLE_NAME,
                    contentValues,
                    LocalEntity._ID + "=?",
                    new String[]{String.valueOf(local.getId())}) > 0;
        }
        return dbGateway.getDatabase().insert(LocalEntity.TABLE_NAME,
                null,
                contentValues) > 0;
    }

    public boolean excluir(int id, List<Evento> eventos) {
        for (int posicao = 0; posicao < eventos.size(); posicao++) {
            if (eventos.get(posicao).getLocal().getId() == id) {
                return false;
            }
        }
        return dbGateway.getDatabase().delete(LocalEntity.TABLE_NAME,
                LocalEntity._ID + "=?",
                new String[]{String.valueOf(id)}) > 0;
    }

    public List<Local> listar() {
        List<Local> locais = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_LISTAR_TODOS, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(LocalEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_NOME));
            String bairro = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CIDADE));
            int capacidade = cursor.getInt(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CAPACIDADE));
            locais.add(new Local(id, nome, bairro, cidade, capacidade));
        }
        cursor.close();
        return locais;
    }

    public List<String> listarCidades() {
        List<String> cidades = new ArrayList<>();
        cidades.add("Todas");
        String query = "SELECT DISTINCT cidade FROM " + LocalEntity.TABLE_NAME;
        Cursor cursor = dbGateway.getDatabase().rawQuery(query, null);
        while (cursor.moveToNext()) {
            String cidade = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CIDADE));
            cidades.add(cidade);
        }
        cursor.close();
        return cidades;
    }
}
