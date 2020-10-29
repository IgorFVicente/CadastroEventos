package br.senai.sc.cadastroeventos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.senai.sc.cadastroeventos.database.EventoDAO;
import br.senai.sc.cadastroeventos.database.LocalDAO;
import br.senai.sc.cadastroeventos.modelo.Evento;
import br.senai.sc.cadastroeventos.modelo.Local;

public class ListarLocalActivity extends AppCompatActivity {

    public ListView listViewLocal;
    private ArrayAdapter<Local> adapterLocal;
    private EventoDAO eventoDao = new EventoDAO(getBaseContext());
    private List<Evento> eventos = eventoDao.listar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_local);
        setTitle("Locais");
        listViewLocal = findViewById(R.id.list_view_local);
        registerForContextMenu(listViewLocal);
        definirOnClickListenerListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalDAO localDao = new LocalDAO(getBaseContext());
        adapterLocal = new ArrayAdapter<Local>(ListarLocalActivity.this,
                android.R.layout.simple_list_item_1,
                localDao.listar());
        listViewLocal.setAdapter(adapterLocal);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Editar");
        menu.add(0, v.getId(), 0, "Excluir");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        Local localClicado = adapterLocal.getItem(position);
        if (item.getTitle() == "Editar") {
            Intent intent = new Intent(ListarLocalActivity.this, CadastroLocalActivity.class);
            intent.putExtra("localEdicao", localClicado);
            startActivity(intent);
        } else if (item.getTitle() == "Excluir") {
            LocalDAO localDao = new LocalDAO(getBaseContext());
            if (!localDao.excluir(localClicado.getId(), eventos)) {
                Toast.makeText(ListarLocalActivity.this, "Erro ao excluir. Primeiro remova os eventos ligados a este local", Toast.LENGTH_LONG).show();
            } else {
                adapterLocal.remove(localClicado);
            }
        }
        return true;
    }

    private void definirOnClickListenerListView() {
        listViewLocal.setOnItemClickListener((parent, view, position, id) -> {
            Local localClicado = adapterLocal.getItem(position);
            Intent intent = new Intent(ListarLocalActivity.this, CadastroLocalActivity.class);
            intent.putExtra("localEdicao", localClicado);
            startActivity(intent);
        });
    }

    public void onClickNovoLocal(View v) {
        Intent intent = new Intent(ListarLocalActivity.this, CadastroLocalActivity.class);
        startActivity(intent);
    }

    public void onClickEventos(View v) {
        Intent intent = new Intent(ListarLocalActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}