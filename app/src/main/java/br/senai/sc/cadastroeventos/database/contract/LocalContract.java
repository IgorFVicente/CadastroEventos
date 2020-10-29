package br.senai.sc.cadastroeventos.database.contract;

import br.senai.sc.cadastroeventos.database.entity.LocalEntity;

public class LocalContract {

    private LocalContract() {}

    public static final String criarTabela() {
        return "CREATE TABLE " + LocalEntity.TABLE_NAME + " (" +
                LocalEntity._ID + " INTEGER PRIMARY KEY, " +
                LocalEntity.COLUMN_NAME_NOME + " TEXT, " +
                LocalEntity.COLUMN_NAME_BAIRRO + " TEXT, " +
                LocalEntity.COLUMN_NAME_CIDADE + " TEXT, " +
                LocalEntity.COLUMN_NAME_CAPACIDADE + " INTEGER)";
    }

    public static final String removerTabela() {
        return "DROP TABLE IF EXISTS " + LocalEntity.TABLE_NAME;
    }
}
