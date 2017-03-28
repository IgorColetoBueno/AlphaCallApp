package alphacallapp.com.br.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

import alphacallapp.com.br.DAO.ClienteDAO;
import alphacallapp.com.br.alphacallapp.R;
import alphacallapp.com.br.model.Atendimentos;

/**
 * Created by Igor Bueno on 25/01/2017.
 */

public class AtendimentoAdapter extends BaseAdapter {

    private Context context;
    private LinkedList<Atendimentos> ListAtt;

    public AtendimentoAdapter(Context ctx, LinkedList<Atendimentos> lista){

        this.context = ctx;
        this.ListAtt = lista;

    }

    @Override
    public int getCount() {
        return ListAtt.size();
    }

    @Override
    public Object getItem(int position) {
        return ListAtt.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Atendimentos att = ListAtt.get(position);

        if (convertView ==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_atendimentos, null);
        }

        TextView txt_cli = (TextView) convertView.findViewById(R.id.atendimento_lista_cli);

        TextView txt_data_ini = (TextView) convertView.findViewById(R.id.lista_txt_data_con);

        TextView txt_hora_ini = (TextView) convertView.findViewById(R.id.lista_txt_hora_con);

        TextView txt_id = (TextView) convertView.findViewById(R.id.atendimento_lista_id);

        ClienteDAO clienteDAO = new ClienteDAO(context);
        txt_cli.setText(clienteDAO.ConsultarCliente(att.getId_cliente_atd()));
        txt_data_ini.setText(att.getData_agd());
        txt_hora_ini.setText(att.getHora_agd());
        txt_id.setText( Integer.toString(att.getNumero()));

        return convertView;
    }
/*
    private int PegarMarcador(Button marcador, String situacao) {
        int color = 0;
        if (situacao == "Aberto") {
            marcador.setBackgroundColor(Color.parseColor("#fff"));
        }
        if (situacao == "Fechado"){
            marcador.setBackgroundColor(Color.parseColor("#32CD32"));
        }if (situacao == "Cancelado"){
            marcador.setBackgroundColor(Color.parseColor("#FF0000"));
        }if (situacao == "Em andamento"){
            marcador.setBackgroundColor(Color.parseColor("#FFFF00"));
        }if (situacao == "Adiado"){
            marcador.setBackgroundColor(Color.parseColor("#FF7F00"));
        }
        return color;
    }*/
}
