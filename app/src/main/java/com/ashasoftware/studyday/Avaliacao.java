package com.ashasoftware.studyday;

/**
 * Created by natanmorais on 07/11/15.
 */
public class Avaliacao {

    int Codigo;
    String Nome, Descricao;
    int Nota, Peso;
    Materia materia;

    public Avaliacao( int codigoMateria, String nome, String descricao, int nota, int peso ) throws Exception {

        for( Materia m : App.getDatabase().getAllMaterias() ) {
            if( m.getCodigo() == codigoMateria ) {
                this.materia = m;
                break;
            }
        }

        if( this.materia == null ) {
            throw new Exception();
        }

        Nome = nome;
        Descricao = descricao;
        Nota = nota;
        Peso = peso;
    }

    public Avaliacao( Materia materia, String nome, String descricao, int nota, int peso ) throws Exception {
        if( materia == null ) {
            throw new Exception();
        }

        this.materia = materia;
        Nome = nome;
        Descricao = descricao;
        Nota = nota;
        Peso = peso;
    }

    public int getCodigo() {
        return Codigo;
    }

    public Materia getMateria() {
        return materia;
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

    public void setMateria( Materia materia ) {
        this.materia = materia;
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
