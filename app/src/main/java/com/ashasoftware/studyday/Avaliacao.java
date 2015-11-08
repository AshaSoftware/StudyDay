package com.ashasoftware.studyday;

/**
 * Created by natanmorais on 07/11/15.
 */
public class Avaliacao {

    int Codigo, CodigoMateria;
    String Nome, Descricao;
    int Nota, Peso;

    public Avaliacao( int codigoMateria, String nome, String descricao, int nota, int peso ) {
        CodigoMateria = codigoMateria;
        Nome = nome;
        Descricao = descricao;
        Nota = nota;
        Peso = peso;
    }

    public int getCodigo() {
        return Codigo;
    }

    public int getCodigoMateria() {
        return CodigoMateria;
    }

    public String getNome() {
        return Nome;
    }

    public String getDescricao() {
        return Descricao;
    }

    public int getNota() {
        return Nota;
    }

    public int getPeso() {
        return Peso;
    }

    public void setCodigo( int codigo ) {
        Codigo = codigo;
    }

    public void setCodigoMateria( int codigoMateria ) {
        CodigoMateria = codigoMateria;
    }

    public void setNome( String nome ) {
        Nome = nome;
    }

    public void setDescricao( String descricao ) {
        Descricao = descricao;
    }

    public void setNota( int nota ) {
        Nota = nota;
    }

    public void setPeso( int peso ) {
        Peso = peso;
    }
}
