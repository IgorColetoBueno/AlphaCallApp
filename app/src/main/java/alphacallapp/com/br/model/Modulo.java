package alphacallapp.com.br.model;

/**
 * Created by Igor Bueno on 01/03/2017.
 */

public class Modulo {
    public Integer id_modulo;

    public String nmodulo;

    public Modulo() {
    }

    public Modulo(Integer id_modulo, String nmodulo) {
        this.id_modulo = id_modulo;
        this.nmodulo = nmodulo;
    }

    public Integer getId_modulo() {
        return id_modulo;
    }

    public void setId_modulo(Integer id_modulo) {
        this.id_modulo = id_modulo;
    }

    public String getNmodulo() {
        return nmodulo;
    }

    public void setNmodulo(String nmodulo) {
        this.nmodulo = nmodulo;
    }
}
