package alphacallapp.com.br.model;

/**
 * Created by Igor Bueno on 01/03/2017.
 */

public class TAtendimento {
    public Integer id_tatendimento;

    public String natendimento;

    public String eimplantacao;

    public TAtendimento() {
    }

    public TAtendimento(Integer id_tatendimento, String natendimento, String eimplantacao) {
        this.id_tatendimento = id_tatendimento;
        this.natendimento = natendimento;
        this.eimplantacao = eimplantacao;
    }

    public Integer getId_tatendimento() {
        return id_tatendimento;
    }

    public void setId_tatendimento(Integer id_tatendimento) {
        this.id_tatendimento = id_tatendimento;
    }

    public String getNatendimento() {
        return natendimento;
    }

    public void setNatendimento(String natendimento) {
        this.natendimento = natendimento;
    }

    public String getEimplantacao() {
        return eimplantacao;
    }

    public void setEimplantacao(String eimplantacao) {
        this.eimplantacao = eimplantacao;
    }

    @Override
    public String toString() {
        return "TAtendimento{" +
                "id_tatendimento=" + id_tatendimento +
                ", natendimento='" + natendimento + '\'' +
                ", eimplantacao='" + eimplantacao + '\'' +
                '}';
    }
}
