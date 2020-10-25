package br.senai.sc.cadastroeventos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

import br.senai.sc.cadastroeventos.database.EventoDAO;
import br.senai.sc.cadastroeventos.modelo.DatePickerFragment;
import br.senai.sc.cadastroeventos.modelo.Evento;

public class CadastrarEventoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_evento);
        setTitle("Cadastro de Evento");
        carregarEvento();

        Button btn_data = (Button) findViewById(R.id.btn_data);
        btn_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String valorData = DateFormat.getDateInstance().format(c.getTime());
        TextView show_data = (TextView) findViewById(R.id.show_data);
        show_data.setText(valorData);
    }

    private void carregarEvento() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null &&
                intent.getExtras().get("eventoEdicao") != null) {
            Evento evento = (Evento) intent.getExtras().get("eventoEdicao");
            EditText editTextNome = findViewById(R.id.field_nome);
            TextView textViewData = findViewById(R.id.show_data);
            EditText editTextValor = findViewById(R.id.field_local);

            editTextNome.setText(evento.getNome());
            textViewData.setText(evento.getData());
            editTextValor.setText(String.valueOf(evento.getLocal()));
            id = evento.getId();
        }
    }

    public void onClickVoltar(View v) {
        finish();
    }

    public void onClickSalvar(View v) {
        EditText editTextNome = findViewById(R.id.field_nome);
        EditText editTextValor = findViewById(R.id.field_local);
        TextView textViewData = findViewById(R.id.show_data);

        String nome = editTextNome.getText().toString();
        String data = textViewData.getText().toString();
        String local = editTextValor.getText().toString();

        if (nome.equals("")) {
            Toast.makeText(CadastrarEventoActivity.this, "O campo NOME é obrigatório", Toast.LENGTH_LONG).show();
            return;
        }

        if (data.equals("")) {
            Toast.makeText(CadastrarEventoActivity.this, "O campo DATA é obrigatório", Toast.LENGTH_LONG).show();
            return;
        }

        if (local.equals("")) {
            Toast.makeText(CadastrarEventoActivity.this, "O campo LOCAL é obrigatório", Toast.LENGTH_LONG).show();
            return;
        }

        Evento evento = new Evento(id, nome, data, local);
        EventoDAO eventoDao = new EventoDAO(getBaseContext());
        boolean salvou = eventoDao.salvar(evento);
        if (salvou) {
            finish();
        } else {
            Toast.makeText(CadastrarEventoActivity.this, "Erro ao salvar", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickExcluir(View v) {
        EventoDAO eventoDao = new EventoDAO(getBaseContext());
        boolean excluiu = eventoDao.excluir(id);
        if (excluiu) {
            finish();
        } else {
            Toast.makeText(CadastrarEventoActivity.this, "Erro ao excluir", Toast.LENGTH_LONG).show();
        }
    }
}