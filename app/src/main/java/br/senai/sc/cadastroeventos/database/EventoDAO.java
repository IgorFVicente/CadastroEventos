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

public class EventoDAO {

    private final String SQL_LISTAR_TODOS = "SELECT evento._id, evento.nome, data_evento, idlocal, nome_local, bairro, cidade, capacidade FROM " +
            EventoEntity.TABLE_NAME + " INNER JOIN " + LocalEntity.TABLE_NAME + " ON " + EventoEntity.COLUMN_NAME_ID_LOCAL +
            " = " + LocalEntity.TABLE_NAME + "." + LocalEntity._ID;
    private DBGateway dbGateway;

    public EventoDAO(Context context) {
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean salvar(Evento evento) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventoEntity.COLUMN_NAME_NOME, evento.getNome());
        contentValues.put(EventoEntity.COLUMN_NAME_DATA, evento.getData());
        contentValues.put(EventoEntity.COLUMN_NAME_ID_LOCAL, evento.getLocal().getId());
        if (evento.getId() > 0) {
            return dbGateway.getDatabase().update(EventoEntity.TABLE_NAME,
                    contentValues,
                    EventoEntity._ID + "=?",
                    new String[]{String.valueOf(evento.getId())}) > 0;
        }
        return dbGateway.getDatabase().insert(EventoEntity.TABLE_NAME,
                null, contentValues) > 0;
    }

    public boolean excluir(int id) {
        return dbGateway.getDatabase().delete(EventoEntity.TABLE_NAME,
                EventoEntity._ID + "=?",
                new String[]{String.valueOf(id)}) > 0;
    }

    public List<Evento> listar() {
        List<Evento> eventos = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_LISTAR_TODOS, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventoEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_NOME));
            String data = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_DATA));
            int idLocal = cursor.getInt(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_ID_LOCAL));
            String nomeLocal = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_NOME));
            String bairro = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CIDADE));
            int capacidade = cursor.getInt(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CAPACIDADE));
            Local local = new Local(idLocal, nomeLocal, bairro, cidade, capacidade);
            eventos.add(new Evento(id, nome, data, local));
        }
        cursor.close();
        return eventos;
    }

    public List<Evento> listarAscendente() {
        List<Evento> eventos = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_LISTAR_TODOS + " ORDER BY " +
                EventoEntity.COLUMN_NAME_NOME + " COLLATE NOCASE ASC", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventoEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_NOME));
            String data = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_DATA));
            int idLocal = cursor.getInt(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_ID_LOCAL));
            String nomeLocal = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_NOME));
            String bairro = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CIDADE));
            int capacidade = cursor.getInt(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CAPACIDADE));
            Local local = new Local(idLocal, nomeLocal, bairro, cidade, capacidade);
            eventos.add(new Evento(id, nome, data, local));
        }
        cursor.close();
        return eventos;
    }

    public List<Evento> listarDescendente() {
        List<Evento> eventos = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_LISTAR_TODOS + " ORDER BY " +
                EventoEntity.COLUMN_NAME_NOME + " COLLATE NOCASE DESC", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventoEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_NOME));
            String data = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_DATA));
            int idLocal = cursor.getInt(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_ID_LOCAL));
            String nomeLocal = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_NOME));
            String bairro = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CIDADE));
            int capacidade = cursor.getInt(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CAPACIDADE));
            Local local = new Local(idLocal, nomeLocal, bairro, cidade, capacidade);
            eventos.add(new Evento(id, nome, data, local));
        }
        cursor.close();
        return eventos;
    }


    public List<Evento> filtrarNomeEvento(String nome_evento) {
        String SQL_FILTRAR = "SELECT evento._id, evento.nome, data_evento, idlocal, nome_local, bairro, cidade, capacidade FROM " +
                EventoEntity.TABLE_NAME + " INNER JOIN " + LocalEntity.TABLE_NAME + " ON " + EventoEntity.COLUMN_NAME_ID_LOCAL +
                " = " + LocalEntity.TABLE_NAME + "." + LocalEntity._ID + " WHERE " + EventoEntity.COLUMN_NAME_NOME + " LIKE '" +
                nome_evento + "%'";
        List<Evento> eventos = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_FILTRAR, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventoEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_NOME));
            String data = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_DATA));
            int idLocal = cursor.getInt(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_ID_LOCAL));
            String nomeLocal = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_NOME));
            String bairro = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CIDADE));
            int capacidade = cursor.getInt(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CAPACIDADE));
            Local local = new Local(idLocal, nomeLocal, bairro, cidade, capacidade);
            eventos.add(new Evento(id, nome, data, local));
        }
        cursor.close();
        return eventos;
    }

    public List<Evento> filtrarCidade(String filtro_cidade) {
        String SQL_FILTRAR = "SELECT evento._id, evento.nome, data_evento, idlocal, nome_local, bairro, cidade, capacidade FROM " +
                EventoEntity.TABLE_NAME + " INNER JOIN " + LocalEntity.TABLE_NAME + " ON " + EventoEntity.COLUMN_NAME_ID_LOCAL +
                " = " + LocalEntity.TABLE_NAME + "." + LocalEntity._ID + " WHERE " + LocalEntity.COLUMN_NAME_CIDADE + " = '" +
                filtro_cidade + "'";
        List<Evento> eventos = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_FILTRAR, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventoEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_NOME));
            String data = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_DATA));
            int idLocal = cursor.getInt(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_ID_LOCAL));
            String nomeLocal = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_NOME));
            String bairro = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CIDADE));
            int capacidade = cursor.getInt(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CAPACIDADE));
            Local local = new Local(idLocal, nomeLocal, bairro, cidade, capacidade);
            eventos.add(new Evento(id, nome, data, local));
        }
        cursor.close();
        return eventos;
    }
}
