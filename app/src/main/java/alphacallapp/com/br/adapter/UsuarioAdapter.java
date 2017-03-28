package alphacallapp.com.br.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import alphacallapp.com.br.alphacallapp.R;
import alphacallapp.com.br.model.Usuario;

/**
 * Created by Igor Bueno on 25/01/2017.
 */

public class UsuarioAdapter extends BaseAdapter {

    private Context context;
    private List<Usuario> ListUser;

    public UsuarioAdapter(Context ctx, List<Usuario> lista) {

        this.context = ctx;
        this.ListUser = lista;

    }

    @Override
    public int getCount() {
        return ListUser.size();
    }

    @Override
    public Object getItem(int position) {
        return ListUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Usuario user = ListUser.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_usuarios, null);
        }

        TextView txt_id_user = (TextView) convertView.findViewById(R.id.usuario_lista_id);

        TextView txt_nome = (TextView) convertView.findViewById(R.id.usuario_lista_nome);


        txt_id_user.setText(user.getId_user().toString());

        txt_nome.setText(user.getLogin_user());


        return convertView;
    }
}
