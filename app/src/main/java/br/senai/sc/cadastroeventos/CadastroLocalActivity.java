package br.senai.sc.cadastroeventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.senai.sc.cadastroeventos.database.EventoDAO;
import br.senai.sc.cadastroeventos.database.LocalDAO;
import br.senai.sc.cadastroeventos.modelo.Evento;
import br.senai.sc.cadastroeventos.modelo.Local;

public class CadastroLocalActivity extends AppCompatActivity {

    private int id = 0;
    private EditText editTextNome;
    private EditText editTextBairro;
    private EditText editTextCidade;
    private EditText editTextCapacidade;
    private EventoDAO eventoDao = new EventoDAO(getBaseContext());
    private List<String> listaQuery = new ArrayList<String>() {{
        add("");
        add("");
        add("ASC");
    }};
    private List<Evento> eventos = eventoDao.listar(listaQuery);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_local);
        setTitle("Cadastro de Local");
        editTextNome = findViewById(R.id.editTextText_local_nome);
        editTextBairro = findViewById(R.id.editText_local_bairro);
        editTextCidade = findViewById(R.id.editText_local_cidade);
        editTextCapacidade = findViewById(R.id.editText_local_capacidade);
        carregarLocal();
    }

    public void carregarLocal() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null &&
                intent.getExtras().get("localEdicao") != null) {
            Local local = (Local) intent.getExtras().get("localEdicao");
            editTextNome.setText(local.getNome());
            editTextBairro.setText(local.getBairro());
            editTextCidade.setText(local.getCidade());
            editTextCapacidade.setText(String.valueOf(local.getCapacidade()));
            id = local.getId();
        }
    }

    public void onClickVoltar(View v) {
        finish();
    }

    public void onClickExcluir(View v) {
        LocalDAO localDao = new LocalDAO(getBaseContext());
        boolean excluiu = localDao.excluir(id, eventos);
        if (excluiu) {
            finish();
        } else {
            Toast.makeText(CadastroLocalActivity.this, "Erro ao excluir. Primeiro remova os eventos ligados a este local", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickSalvar(View v) {
        String nome = editTextNome.getText().toString();
        String bairro = editTextBairro.getText().toString();
        String cidade = editTextCidade.getText().toString();
        int capacidade = Integer.parseInt(editTextCapacidade.getText().toString());
        Local local = new Local(id, nome, bairro, cidade, capacidade);
        LocalDAO localDao = new LocalDAO(getBaseContext());
        boolean salvou = localDao.salvar(local);
        if (salvou) {
            finish();
        } else {
            Toast.makeText(CadastroLocalActivity.this, "Erro ao salvar", Toast.LENGTH_SHORT).show();
        }
    }
}