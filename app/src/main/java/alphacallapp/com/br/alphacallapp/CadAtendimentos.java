package alphacallapp.com.br.alphacallapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import alphacallapp.com.br.DAO.AtendimentosDAO;
import alphacallapp.com.br.DAO.ClienteDAO;
import alphacallapp.com.br.DAO.FuncaoDAO;
import alphacallapp.com.br.DAO.ModuloDAO;
import alphacallapp.com.br.DAO.SubModuloDAO;
import alphacallapp.com.br.DAO.TAtendimentoDAO;
import alphacallapp.com.br.DAO.UsuarioDAO;
import alphacallapp.com.br.model.Atendimentos;
import alphacallapp.com.br.model.Funcao;
import alphacallapp.com.br.model.Submodulo;
import alphacallapp.com.br.util.Mensagem;

public class CadAtendimentos extends AppCompatActivity {
    // Criação de objetos
    private EditText descricao_att, parecer_att, observacoes_att;

    private Button data_inicial_att, hora_inicial_att, data_final_att, hora_final_att;

    private Spinner btn_tip_att, sub_modulo_att, funcao_att, modulo_att, cliente_att, situacao_att, usuario_att;

    private Atendimentos att;

    private Integer id_atendimento;

    private static final int DATE_INICIAL_DIALOG_ID = 0;

    private static final int HORA_INICIAL_DIALOG_ID = 1;

    private static final int DATE_FINAL_DIALOG_ID = 2;

    private static final int HORA_FINAL_DIALOG_ID = 3;

    private ArrayAdapter<String> arrayAdapterMod, arrayAdapterSubMod, arrayAdapterFun, arrayAdapterTAtt, arrayAdapterCli, arrayAdapterSit,
            arrayAdapterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_atendimentos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //declaração dos objetos

        Declarar();

        PrepararSpinners();

        id_atendimento = getIntent().getIntExtra("ATENDIMENTO_ID", 0);

        SelecionarModoTela();

    }

    //Após o método onCreate seguem abaixo os métodos em ordem alfabética

    private void Cadastrar() {

        AtendimentosDAO attdao = new AtendimentosDAO(this);
        ClienteDAO clienteDAO = new ClienteDAO(this);
        ModuloDAO moduloDAO = new ModuloDAO(this);
        SubModuloDAO subModuloDAO = new SubModuloDAO(this);
        FuncaoDAO funcaoDAO = new FuncaoDAO(this);
        TAtendimentoDAO tAtendimentosDAO = new TAtendimentoDAO(this);

        String usuario_atendimento = usuario_att.getSelectedItem().toString();

        String cliente_atendimento = cliente_att.getSelectedItem().toString();

        String data_inicial_atendimento = data_inicial_att.getText().toString();

        String hora_inicial_atendimento = hora_inicial_att.getText().toString();

        String data_final_atendimento = data_final_att.getText().toString();

        String hora_final_atendimento = hora_final_att.getText().toString();

        String descricao_atendimento = descricao_att.getText().toString().trim();

        String parecer_atendimento = parecer_att.getText().toString().trim();

        String observacoes_atendimento = observacoes_att.getText().toString().trim();

        String modulo_atendimento = modulo_att.getSelectedItem().toString();

        String sub_modulo_atendimento = sub_modulo_att.getSelectedItem().toString();

        String funcao_atendimento = funcao_att.getSelectedItem().toString();

        String situacao_atendimento = situacao_att.getSelectedItem().toString();

        String tipo_atendimento = btn_tip_att.getSelectedItem().toString();

        if (ValidarOperacao()) {

            att = new Atendimentos();

            att.setId_cliente_atd(clienteDAO.ConsultarNome(cliente_atendimento));
            att.setNusuario(usuario_atendimento);
            att.setDescricao(descricao_atendimento);
            att.setData_agd(data_inicial_atendimento);
            att.setHora_agd(hora_inicial_atendimento);
            att.setData_con(data_final_atendimento);
            att.setHora_agd(hora_inicial_atendimento);
            att.setHora_con(hora_final_atendimento);
            att.setId_modulo_atd(moduloDAO.ConsultarNome(modulo_atendimento));
            att.setParecer(parecer_atendimento);
            att.setObs(observacoes_atendimento);
            att.setSituacao(situacao_atendimento);
            att.setId_submodulo_atd(subModuloDAO.ConsultarNome(sub_modulo_atendimento));
            att.setId_funcao_atd(funcaoDAO.ConsultarNome(funcao_atendimento));
            att.setId_tatendimento(tAtendimentosDAO.ConsultarNome(tipo_atendimento));
            SharedPreferences sharedPref = this.getSharedPreferences("PREF_LOGIN", Context.MODE_PRIVATE);
            att.setId_usuario(sharedPref.getInt("ID_PREF", 0));

            //se for atualizar
            if (id_atendimento > 0) {
                //Verifica se o usuário vai editar ou duplicar, caso seja editar ele vai setar o número do atendimento, caso
                //não ele vai duplicar gerando um novo ID
                boolean editar = getIntent().getBooleanExtra("EDITAVEL", true);

                if (editar == true) {

                    att.setNumero(id_atendimento);

                }

            }

            long resultado = attdao.SalvarAtendimentos(att);

            if (resultado != -1) {
                if (id_atendimento > 1) {

                    Toast.makeText(getApplicationContext(), "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();

                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Cadastrado com sucesso!");
                builder.setMessage("Deseja lançar mais atendimentos?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LimparCampos();
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ChamarPrincipal();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getApplicationContext(), "Erro ao realizar esta operação!", Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void CapturarValorEdit() {
        AtendimentosDAO atdao = new AtendimentosDAO(this);
        SubModuloDAO subModuloDAO = new SubModuloDAO(this);
        FuncaoDAO funcaoDAO = new FuncaoDAO(this);
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        if (id_atendimento > 0) {

            Atendimentos model = atdao.ConsultarId(id_atendimento);

            usuario_att.setSelection(usuarioDAO.RetornaId(model.getNusuario()) - 1);

            cliente_att.setSelection(model.getId_cliente_atd() - 1);

            btn_tip_att.setSelection(model.getId_tatendimento() - 1);

            data_inicial_att.setText(model.getData_agd());

            hora_inicial_att.setText(model.getHora_agd());

            data_final_att.setText(model.getData_con());

            hora_final_att.setText(model.getHora_con());

            modulo_att.setSelection(model.getId_modulo_atd() - 1);

            sub_modulo_att.setSelection(model.getId_submodulo_atd() - subModuloDAO.ConsultarIndice(model.getId_modulo_atd()));

            funcao_att.setSelection(model.getId_funcao_atd() - funcaoDAO.ConsultarIndice(model.getId_submodulo_atd()));

            if (model.getSituacao() == "Aberto"){
                situacao_att.setSelection(0);
            }
            else if(model.getSituacao() == "Fechado"){
                situacao_att.setSelection(1);
            }
            else if(model.getSituacao() == "Em andamento"){
                situacao_att.setSelection(2);
            }
            else if(model.getSituacao() == "Cancelado"){
                situacao_att.setSelection(3);
            }
            else{
                situacao_att.setSelection(4);
            }

            descricao_att.setText(model.getDescricao());

            parecer_att.setText(model.getParecer());

            observacoes_att.setText(model.getObs());

            data_inicial_att.setEnabled(false);

            this.setTitle("Atualizar");

        }
    }

    private void CapturarValorView() {
        AtendimentosDAO atdao = new AtendimentosDAO(this);
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        SubModuloDAO subModuloDAO = new SubModuloDAO(this);
        FuncaoDAO funcaoDAO = new FuncaoDAO(this);

        if (id_atendimento > 0) {

            Atendimentos model = atdao.ConsultarId(id_atendimento);

            usuario_att.setSelection(usuarioDAO.RetornaId(model.getNusuario()) - 1);

            cliente_att.setSelection(model.getId_cliente_atd() - 1);

            btn_tip_att.setSelection(model.getId_tatendimento() - 1);

            data_inicial_att.setText(model.getData_agd());

            hora_inicial_att.setText(model.getHora_agd());

            data_final_att.setText(model.getData_con());

            hora_final_att.setText(model.getHora_con());

            modulo_att.setSelection(model.getId_modulo_atd() - 1);

            sub_modulo_att.setSelection(model.getId_submodulo_atd() - subModuloDAO.ConsultarIndice(model.getId_modulo_atd()));

            funcao_att.setSelection(model.getId_funcao_atd() - funcaoDAO.ConsultarIndice(model.getId_submodulo_atd()));

            if (model.getSituacao().equalsIgnoreCase("Aberto")){
                situacao_att.setSelection(0);
            }
            if(model.getSituacao().equalsIgnoreCase("Fechado")){
                situacao_att.setSelection(1);
            }
            if(model.getSituacao().equalsIgnoreCase("Em andamento")){
                situacao_att.setSelection(2);
            }
            if(model.getSituacao().equalsIgnoreCase("Cancelado")){
                situacao_att.setSelection(3);
            }
            if (model.getSituacao().equalsIgnoreCase("Adiado")){
                situacao_att.setSelection(4);
            }


            descricao_att.setText(model.getDescricao());

            parecer_att.setText(model.getParecer());

            observacoes_att.setText(model.getObs());


            usuario_att.setEnabled(false);
            cliente_att.setEnabled(false);
            data_inicial_att.setEnabled(false);
            data_inicial_att.setTextColor(getResources().getColor(R.color.preto));
            hora_inicial_att.setEnabled(false);
            hora_inicial_att.setTextColor(getResources().getColor(R.color.preto));
            data_final_att.setEnabled(false);
            data_final_att.setTextColor(getResources().getColor(R.color.preto));
            hora_final_att.setEnabled(false);
            hora_final_att.setTextColor(getResources().getColor(R.color.preto));
            descricao_att.setEnabled(false);
            descricao_att.setTextColor(getResources().getColor(R.color.preto));
            parecer_att.setEnabled(false);
            parecer_att.setTextColor(getResources().getColor(R.color.preto));
            observacoes_att.setEnabled(false);
            observacoes_att.setTextColor(getResources().getColor(R.color.preto));
            modulo_att.setEnabled(false);
            sub_modulo_att.setEnabled(false);
            funcao_att.setEnabled(false);
            situacao_att.setEnabled(false);
            btn_tip_att.setEnabled(false);

            this.setTitle("Visualizar");

        }

    }

    private void ChamarPrincipal() {
        finish();
        Intent intent = new Intent(getApplicationContext(), Menu_Principal.class);
        startActivity(intent);

    }

    private void Declarar() {

        modulo_att = (Spinner) findViewById(R.id.spn_modulo);

        usuario_att = (Spinner) findViewById(R.id.spn_operador);

        cliente_att = (Spinner) findViewById(R.id.spn_cliente);

        btn_tip_att = (Spinner) findViewById(R.id.spn_tip_att);

        data_inicial_att = (Button) findViewById(R.id.btn_data_inicial);

        hora_inicial_att = (Button) findViewById(R.id.btn_hora_inicial);

        data_final_att = (Button) findViewById(R.id.btn_data_final);

        hora_final_att = (Button) findViewById(R.id.btn_hora_final);

        descricao_att = (EditText) findViewById(R.id.txt_descricao);

        parecer_att = (EditText) findViewById(R.id.txt_parecer);

        observacoes_att = (EditText) findViewById(R.id.txt_observacoes);

        sub_modulo_att = (Spinner) findViewById(R.id.spn_sub_modulo);

        funcao_att = (Spinner) findViewById(R.id.spn_funcao);

        situacao_att = (Spinner) findViewById(R.id.situacao_dialog);

        //atribuição às funções dos botões que chamam dialogs
        data_inicial_att.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_INICIAL_DIALOG_ID);
            }
        });

        data_final_att.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_FINAL_DIALOG_ID);
            }
        });

        hora_inicial_att.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(HORA_INICIAL_DIALOG_ID);
            }
        });

        hora_final_att.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(HORA_FINAL_DIALOG_ID);
            }
        });
    }

    private void LimparCampos() {
        //Inserir índice 0 para todos os spinners
        usuario_att.setSelection(0);

        cliente_att.setSelection(0);

        modulo_att.setSelection(0);

        sub_modulo_att.setSelection(0);

        funcao_att.setSelection(0);

        btn_tip_att.setSelection(0);

        situacao_att.setSelection(0);

        descricao_att.setText("");

        parecer_att.setText("");

        observacoes_att.setText("");

        Toast.makeText(this, "Campos limpos!", Toast.LENGTH_SHORT).show();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cad_atendimentos, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean decisao_visualizar = getIntent().getBooleanExtra("VISUALIZAR", false);

        MenuItem salvar = menu.findItem(R.id.SalvaCadAtt);
        MenuItem limpar = menu.findItem(R.id.LimpaCadAtt);
        MenuItem trocarUser = menu.findItem(R.id.TrocarUserAtt);

        if (decisao_visualizar == true) {
            salvar.setVisible(false);
            limpar.setVisible(false);
            trocarUser.setVisible(false);
        } else {
            salvar.setVisible(true);
            limpar.setVisible(true);
            trocarUser.setVisible(true);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                if (this.getTitle() != "Visualizar") {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Você deseja salvar o atendimento?");
                    builder.setMessage("Aviso: Ao não salvar o atendimento será perdido");
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Cadastrar();
                        }
                    });
                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ChamarPrincipal();
                        }
                    });
                    builder.show();
                } else {
                    ChamarPrincipal();
                }
                break;
            case R.id.SalvaCadAtt:
                Cadastrar();
                break;

            case R.id.LimpaCadAtt:
                LimparCampos();
                break;
            case R.id.TrocarUserAtt:
                Intent intent = new Intent(this, Tela_Login.class);
                startActivity(intent);
                break;

        }
        return true;
    }

    private void PrepararSpinners() {
        ClienteDAO clienteDAO = new ClienteDAO(this);
        ModuloDAO moduloDAO = new ModuloDAO(this);
        TAtendimentoDAO tAtendimentoDAO = new TAtendimentoDAO(this);

        String[] usuarios = new UsuarioDAO(this).ConsultarNUsuario();
        arrayAdapterUser = new ArrayAdapter<String>(getApplicationContext(), R.layout.personaliza_spinner, usuarios);
        usuario_att.setAdapter(arrayAdapterUser);

        String[] clientes = clienteDAO.ConsultarNCliente();
        arrayAdapterCli = new ArrayAdapter<String>(getApplicationContext(), R.layout.personaliza_spinner, clientes);
        cliente_att.setAdapter(arrayAdapterCli);

        String[] tATT = tAtendimentoDAO.ConsultarNTAtendimento();
        arrayAdapterTAtt = new ArrayAdapter<String>(getApplicationContext(), R.layout.personaliza_spinner, tATT);
        btn_tip_att.setAdapter(arrayAdapterTAtt);

        String[] modulos = moduloDAO.ConsultarNModulo();
        arrayAdapterMod = new ArrayAdapter<String>(getApplicationContext(), R.layout.personaliza_spinner, modulos);
        modulo_att.setAdapter(arrayAdapterMod);
        String[] sub_modulos = new String[0];
        arrayAdapterSubMod = new ArrayAdapter<String>(getApplicationContext(), R.layout.personaliza_spinner, sub_modulos);
        sub_modulo_att.setAdapter(arrayAdapterSubMod);

        String[] funcao = new String[0];
        arrayAdapterFun = new ArrayAdapter<String>(getApplicationContext(), R.layout.personaliza_spinner, funcao);
        funcao_att.setAdapter(arrayAdapterFun);
        modulo_att.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] sub_modulos;
                sub_modulos = null;
                sub_modulos = new SubModuloDAO(getApplicationContext()).ConsultarNSubModulo(new ModuloDAO(getApplicationContext()).
                        ConsultarNome(modulo_att.getSelectedItem().toString()));
                arrayAdapterSubMod = new ArrayAdapter<String>(getApplicationContext(), R.layout.personaliza_spinner, sub_modulos);
                sub_modulo_att.setAdapter(arrayAdapterSubMod);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sub_modulo_att.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] funcoes;
                funcoes = null;
                funcoes = new FuncaoDAO(getApplicationContext()).ConsultarNFuncao(
                        new SubModuloDAO(getApplicationContext()).
                                ConsultarNome(sub_modulo_att.getSelectedItem().toString()));
                arrayAdapterFun = new ArrayAdapter<String>(getApplicationContext(), R.layout.personaliza_spinner, funcoes);
                funcao_att.setAdapter(arrayAdapterFun);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] situacoes = new String[5];
        situacoes[0] = "Aberto";
        situacoes[1] = "Fechado";
        situacoes[2] = "Em andamento";
        situacoes[3] = "Cancelado";
        situacoes[4] = "Adiado";
        arrayAdapterSit = new ArrayAdapter<String>(getApplicationContext(), R.layout.personaliza_spinner, situacoes);
        situacao_att.setAdapter(arrayAdapterSit);

    }

    private void SelecionarModoTela() {
        boolean decisao_visualizar = getIntent().getBooleanExtra("VISUALIZAR", false);

        boolean decisao_editar = getIntent().getBooleanExtra("EDITAR", false);

        boolean editar = getIntent().getBooleanExtra("EDITAVEL", true);

        if (decisao_visualizar == true) {
            CapturarValorView();
        }

        if (decisao_editar == true) {
            CapturarValorEdit();
        }

        if (editar == false) {
            data_inicial_att.setEnabled(true);
            this.setTitle("Duplicar");
        }
        if (decisao_editar == false && decisao_visualizar == false && editar == false) {
            this.setTitle("Cadastrar");
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendario = Calendar.getInstance();

        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);

        switch (id) {
            case DATE_INICIAL_DIALOG_ID:
                return new DatePickerDialog(this, IniDateSetListener, ano, mes, dia);
            case HORA_INICIAL_DIALOG_ID:
                return new TimePickerDialog(this, IniHoraSetListener, hora, minuto, true);
            case DATE_FINAL_DIALOG_ID:
                return new DatePickerDialog(this, FinDateSetListener, ano, mes, dia);
            case HORA_FINAL_DIALOG_ID:
                return new TimePickerDialog(this, FinHoraSetListener, hora, minuto, true);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener IniDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String data = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year);

            Toast.makeText(getApplicationContext(),
                    "Data inicial: " + data, Toast.LENGTH_SHORT).show();
            data_inicial_att.setText(data);
        }
    };

    private TimePickerDialog.OnTimeSetListener IniHoraSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hora, int minuto) {
            String time = String.valueOf(hora) + ":" + String.valueOf(minuto);

            Toast.makeText(getApplicationContext(),
                    "Hora inicial: " + time, Toast.LENGTH_SHORT).show();
            hora_inicial_att.setText(time);
        }
    };

    private DatePickerDialog.OnDateSetListener FinDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            String data = String.valueOf(dayOfMonth) + "/"
                    + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year);

            Toast.makeText(getApplicationContext(),
                    "Data final: " + data, Toast.LENGTH_SHORT).show();
            data_final_att.setText(data);
        }
    };

    private TimePickerDialog.OnTimeSetListener FinHoraSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hora, int minuto) {
            String hora_final = Integer.toString(hora);
            String minuto_final = Integer.toString(minuto);

            String time = String.valueOf(hora) + ":" + String.valueOf(minuto);

            Toast.makeText(getApplicationContext(),
                    "Data final: " + time, Toast.LENGTH_SHORT).show();
            hora_final_att.setText(time);
        }
    };

    private boolean ValidarOperacao() {
        boolean validar = true;

        String titulo = this.getTitle().toString();

        String usuario_atendimento = usuario_att.getSelectedItem().toString();

        String cliente_atendimento = cliente_att.getSelectedItem().toString();

        String data_inicial_atendimento = data_inicial_att.getText().toString();

        String hora_inicial_atendimento = hora_inicial_att.getText().toString();

        String data_final_atendimento = data_final_att.getText().toString();

        String hora_final_atendimento = hora_final_att.getText().toString();

        String descricao_atendimento = descricao_att.getText().toString().trim();

        String parecer_atendimento = parecer_att.getText().toString().trim();

        String observacoes_atendimento = observacoes_att.getText().toString().trim();

        String modulo_atendimento = modulo_att.getSelectedItem().toString();

        String sub_modulo_atendimento = sub_modulo_att.getSelectedItem().toString();

        String funcao_atendimento = funcao_att.getSelectedItem().toString();

        String situacao_atendimento = situacao_att.getSelectedItem().toString();

        String tipo_atendimento = btn_tip_att.getSelectedItem().toString();

        if (usuario_atendimento == null) {
            usuario_att.setPrompt("Nenhum item selecionado!");
            validar = false;
        }
        if (cliente_atendimento.trim().isEmpty()) {
            usuario_att.setPrompt("Nenhum item selecionado!");
            validar = false;
        }
        if (data_inicial_att.getText().toString() == "DATA") {
            data_inicial_att.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            validar = false;
        }
        if (hora_inicial_atendimento == "HORA") {
            hora_inicial_att.setTextColor(getResources().getColor(R.color.colorPrimary));
            validar = false;
        }
        if (data_final_atendimento == "DATA") {
            data_final_att.setTextColor(getResources().getColor(R.color.colorPrimary));
            validar = false;
        }
        if (hora_final_atendimento == "HORA") {
            hora_final_att.setTextColor(getResources().getColor(R.color.colorPrimary));
            validar = false;
        }
        if (descricao_atendimento.trim().isEmpty()) {
            descricao_att.setError("Campo Vazio!");
            validar = false;
        }
        if (parecer_atendimento.trim().isEmpty() && titulo == "Atualizar") {
            parecer_att.setError("Campo vazio!");
            validar = false;
        }
        if (observacoes_atendimento.trim().isEmpty() && titulo == "Atualizar") {
            observacoes_att.setError("Campo vazio!");
            validar = false;
        }
        if (modulo_atendimento == null) {
            CheckedTextView checkedTextView = (CheckedTextView) findViewById(R.id.text1);
            checkedTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            validar = false;
        }
        if (sub_modulo_atendimento == "Nenhum") {
            CheckedTextView checkedTextView = (CheckedTextView) findViewById(R.id.text1);
            checkedTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            validar = false;
        }
        if (funcao_atendimento == "Nenhum") {
            CheckedTextView checkedTextView = (CheckedTextView) findViewById(R.id.text1);
            checkedTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            validar = false;
        }
        if (situacao_atendimento.trim().isEmpty()) {
            situacao_att.setPrompt("Nenhum item selecionado!");
            validar = false;
        }
        if (tipo_atendimento.trim().isEmpty()) {
            btn_tip_att.setPrompt("Nenhum item selecionado!");
            validar = false;
        }
        if (validar == false) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Erro!");
            builder.setMessage("Existem campos não preenchidos no formulário!");
            builder.show();
        }

        return validar;
    }
}








