package com.ashasoftware.studyday;

/**
 * Created by natanmorais on 02/11/15.
 */
public class NaoEscolar {
    int Codigo;
    String Nome, Descricao;
    int dia, diaFim, horaIni, horaFim;

    public NaoEscolar(){}

    public NaoEscolar(int pCodigo, String pNome, String pDescricao, int pDia, int pDiaFim, int pHoraIni, int pHoraFim) {
        Codigo = pCodigo;
        Nome = pNome;
        Descricao = pDescricao;
        dia = pDia;
        diaFim = pDiaFim;
        horaIni = pHoraIni;
        horaFim = pHoraFim;
    }

    public int getCodigo() {
        return Codigo;
    }

    public String getNome() {
        return Nome;
    }

    public String getDescricao() {
        return Descricao;
    }

    public int getDia() {
        return dia;
    }

    public int getDiaFim() {
        return diaFim;
    }

    public int getHoraIni() {
        return horaIni;
    }

    public int getHoraFim() {
        return horaFim;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public void setDiaFim(int diaFim) {
        this.diaFim = diaFim;
    }

    public void setHoraIni(int horaIni) {
        this.horaIni = horaIni;
    }

    public void setHoraFim(int horaFim) {
        this.horaFim = horaFim;
    }
}
