package br.senai.sc.cadastroeventos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.senai.sc.cadastroeventos.database.EventoDAO;
import br.senai.sc.cadastroeventos.database.LocalDAO;
import br.senai.sc.cadastroeventos.database.contract.EventoContract;
import br.senai.sc.cadastroeventos.modelo.Evento;
import br.senai.sc.cadastroeventos.modelo.Local;

public class MainActivity extends AppCompatActivity {

    private ListView listViewEventos;
    private ArrayAdapter<Evento> adapterEventos;
    private int id = 0;
    private EditText editTextPesquisa;
    private Spinner spinnerCidade;
    private ArrayAdapter<String> adapterCidades;
    private List<String> listaQuery = new ArrayList<String>() {{
        add("");
        add("");
        add("ASC");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Eventos");
        listViewEventos = findViewById(R.id.list_view_eventos);
        editTextPesquisa = findViewById(R.id.editText_pesquisa_nome);
        spinnerCidade = findViewById(R.id.spinner_cidade);
        editTextPesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                listaQuery.set(0, query);
                EventoDAO eventoDao = new EventoDAO(getBaseContext());
                adapterEventos = new ArrayAdapter<Evento>(MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        eventoDao.listar(listaQuery));
                listViewEventos.setAdapter(adapterEventos);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        spinnerCidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String query = spinnerCidade.getSelectedItem().toString();
                if (!query.equals("Todas")) {
                    listaQuery.set(1, query);
                } else {
                    listaQuery.set(1, "");
                }
                EventoDAO eventoDao = new EventoDAO(getBaseContext());
                adapterEventos = new ArrayAdapter<Evento>(MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        eventoDao.listar(listaQuery));
                listViewEventos.setAdapter(adapterEventos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        registerForContextMenu(listViewEventos);
        definirOnClickListenerListView();
        carregarCidades();
    }

    @Override
    protected void onResume() {
        super.onResume();
        spinnerCidade.setSelection(0);
        EventoDAO eventoDao = new EventoDAO(getBaseContext());
        adapterEventos = new ArrayAdapter<Evento>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                eventoDao.listar(listaQuery));
        listViewEventos.setAdapter(adapterEventos);
    }

    public void onClickDescendente(View v) {
        listaQuery.set(2, "DESC");
        EventoDAO eventoDao = new EventoDAO(getBaseContext());
        adapterEventos = new ArrayAdapter<Evento>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                eventoDao.listar(listaQuery));
        listViewEventos.setAdapter(adapterEventos);
    }

    public void onClickAscendente(View v) {
        listaQuery.set(2, "ASC");
        EventoDAO eventoDao = new EventoDAO(getBaseContext());
        adapterEventos = new ArrayAdapter<Evento>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                eventoDao.listar(listaQuery));
        listViewEventos.setAdapter(adapterEventos);
    }

    public void carregarCidades() {
        LocalDAO localDao = new LocalDAO(getBaseContext());
        adapterCidades = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,
                localDao.listarCidades());
        spinnerCidade.setAdapter(adapterCidades);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Editar");
        menu.add(0, v.getId(), 0, "Excluir");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        Evento eventoClicado = adapterEventos.getItem(position);
        if (item.getTitle() == "Editar") {
            Intent intent = new Intent(MainActivity.this, CadastrarEventoActivity.class);
            intent.putExtra("eventoEdicao", eventoClicado);
            startActivity(intent);
        } else if (item.getTitle() == "Excluir") {
            EventoDAO eventoDao = new EventoDAO(getBaseContext());
            adapterEventos.remove(eventoClicado);
            return eventoDao.excluir(eventoClicado.getId());
        }
        return true;
    }

    private void definirOnClickListenerListView() {
        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evento eventoClicado = adapterEventos.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastrarEventoActivity.class);
                intent.putExtra("eventoEdicao", eventoClicado);
                startActivity(intent);
            }
        });
    }

    public void onClickNovoEvento(View v) {
        Intent intent = new Intent(MainActivity.this, CadastrarEventoActivity.class);
        startActivity(intent);
    }

    public void onClickLocal(View v) {
        Intent intent = new Intent(MainActivity.this, ListarLocalActivity.class);
        startActivity(intent);
        finish();
    }
}