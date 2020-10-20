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

import java.util.ArrayList;

import br.senai.sc.cadastroeventos.modelo.Evento;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_NOVO_EVENTO = 1;
    private final int RESULT_CODE_NOVO_EVENTO = 10;
    private final int REQUEST_CODE_EDITAR_EVENTO = 2;
    private final int RESULT_CODE_EVENTO_EDITADO = 11;
    private final int RESULT_CODE_EVENTO_EXCLUIDO = 12;

    private ListView listViewEventos;
    private ArrayAdapter<Evento> adapterEventos;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Eventos");

        listViewEventos = findViewById(R.id.list_view_eventos);
        ArrayList<Evento> eventos = new ArrayList<Evento>();

        adapterEventos = new ArrayAdapter<Evento>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                eventos);
        listViewEventos.setAdapter(adapterEventos);

        registerForContextMenu(listViewEventos);
        definirOnClickListenerListView();
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
            startActivityForResult(intent, REQUEST_CODE_EDITAR_EVENTO);
        } else if (item.getTitle() == "Excluir") {
            adapterEventos.remove(eventoClicado);
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
                startActivityForResult(intent, REQUEST_CODE_EDITAR_EVENTO);
            }
        });
    }

    public void onClickNovoEvento(View v) {
        Intent intent = new Intent(MainActivity.this, CadastrarEventoActivity.class);
        startActivityForResult(intent, REQUEST_CODE_NOVO_EVENTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_NOVO_EVENTO && resultCode == RESULT_CODE_NOVO_EVENTO) {
            Evento evento = (Evento) data.getExtras().getSerializable("novoEvento");
            evento.setId(++id);
            this.adapterEventos.add(evento);
        } else if (requestCode == REQUEST_CODE_EDITAR_EVENTO && resultCode == RESULT_CODE_EVENTO_EDITADO) {
            Evento eventoEditado = (Evento) data.getExtras().getSerializable("eventoEditado");
            for (int i = 0; i < adapterEventos.getCount(); i++) {
                Evento evento = adapterEventos.getItem(i);
                if (evento.getId() == eventoEditado.getId()) {
                    adapterEventos.remove(evento);
                    adapterEventos.insert(eventoEditado, i);
                    break;
                }
            }
        } else if (requestCode == REQUEST_CODE_EDITAR_EVENTO && resultCode == RESULT_CODE_EVENTO_EXCLUIDO){
            int idExcluir = data.getExtras().getInt("eventoExcluido");
            for (int i = 0; i < adapterEventos.getCount(); i++) {
                Evento evento = adapterEventos.getItem(i);
                if (evento.getId() == idExcluir) {
                    adapterEventos.remove(evento);
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}