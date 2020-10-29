package br.senai.sc.cadastroeventos;

import androidx.annotation.Nullable;
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

import java.util.ArrayList;

import br.senai.sc.cadastroeventos.database.EventoDAO;
import br.senai.sc.cadastroeventos.database.contract.EventoContract;
import br.senai.sc.cadastroeventos.modelo.Evento;

public class MainActivity extends AppCompatActivity {

    private ListView listViewEventos;
    private ArrayAdapter<Evento> adapterEventos;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Eventos");
        listViewEventos = findViewById(R.id.list_view_eventos);
        registerForContextMenu(listViewEventos);
        definirOnClickListenerListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventoDAO eventoDao = new EventoDAO(getBaseContext());
        adapterEventos = new ArrayAdapter<Evento>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                eventoDao.listar());
        listViewEventos.setAdapter(adapterEventos);
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