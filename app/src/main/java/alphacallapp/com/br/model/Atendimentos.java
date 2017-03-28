package alphacallapp.com.br.model;

import java.lang.String;
import java.lang.String;

/**
 * Created by Igor Bueno on 17/01/2017.
 */

public class Atendimentos {
    public Integer numero;

    public Integer id_cliente_atd;

    public String nusuario;

    public String descricao;

    public String data_agd;

    public String hora_agd;

    public Integer id_modulo_atd;

    public String parecer;

    public String data_con;

    public String hora_con;

    public String obs;

    public String situacao;

    public Integer id_submodulo_atd;

    public Integer id_funcao_atd;

    public Integer id_usuario;

    public Integer id_tatendimento;

    public Atendimentos() {
    }

    public Atendimentos(Integer numero, Integer id_cliente_atd, String nusuario, String descricao, String data_agd, String hora_agd,
                        Integer id_modulo_atd, String parecer, String data_con, String hora_con, String obs, String situacao,
                        Integer id_submodulo_atd, Integer id_funcao_atd, Integer id_usuario, Integer id_tatendimento) {
        this.numero = numero;
        this.id_cliente_atd = id_cliente_atd;
        this.nusuario = nusuario;
        this.descricao = descricao;
        this.data_agd = data_agd;
        this.hora_agd = hora_agd;
        this.id_modulo_atd = id_modulo_atd;
        this.parecer = parecer;
        this.data_con = data_con;
        this.hora_con = hora_con;
        this.obs = obs;
        this.situacao = situacao;
        this.id_submodulo_atd = id_submodulo_atd;
        this.id_funcao_atd = id_funcao_atd;
        this.id_usuario = id_usuario;
        this.id_tatendimento = id_tatendimento;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getId_cliente_atd() {
        return id_cliente_atd;
    }

    public void setId_cliente_atd(Integer id_cliente_atd) {
        this.id_cliente_atd = id_cliente_atd;
    }

    public String getNusuario() {
        return nusuario;
    }

    public void setNusuario(String nusuario) {
        this.nusuario = nusuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData_agd() {
        return data_agd;
    }

    public void setData_agd(String data_agd) {
        this.data_agd = data_agd;
    }

    public String getHora_agd() {
        return hora_agd;
    }

    public void setHora_agd(String hora_agd) {
        this.hora_agd = hora_agd;
    }

    public Integer getId_modulo_atd() {
        return id_modulo_atd;
    }

    public void setId_modulo_atd(Integer id_modulo_atd) {
        this.id_modulo_atd = id_modulo_atd;
    }

    public String getParecer() {
        return parecer;
    }

    public void setParecer(String parecer) {
        this.parecer = parecer;
    }

    public String getData_con() {
        return data_con;
    }

    public void setData_con(String data_con) {
        this.data_con = data_con;
    }

    public String getHora_con() {
        return hora_con;
    }

    public void setHora_con(String hora_con) {
        this.hora_con = hora_con;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Integer getId_submodulo_atd() {
        return id_submodulo_atd;
    }

    public void setId_submodulo_atd(Integer id_submodulo_atd) {
        this.id_submodulo_atd = id_submodulo_atd;
    }

    public Integer getId_funcao_atd() {
        return id_funcao_atd;
    }

    public void setId_funcao_atd(Integer id_funcao_atd) {
        this.id_funcao_atd = id_funcao_atd;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getId_tatendimento() {
        return id_tatendimento;
    }

    public void setId_tatendimento(Integer id_tatendimento) {
        this.id_tatendimento = id_tatendimento;
    }
}
