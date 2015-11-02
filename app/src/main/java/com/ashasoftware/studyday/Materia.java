package com.ashasoftware.studyday;

/**
 * Created by natanmorais on 02/11/15.
 */
public class Materia {
    int Codigo;
    String Nome, Professor;
    int Cor, difProfessor, difMateria;

    public Materia(){}

    public Materia(int pCodigo, String pNome, String pProfessor, int pCor, int pDifProfessor, int pDifMateria) {
        Codigo = pCodigo;
        Nome = pNome;
        Professor = pProfessor;
        Cor = pCor;
        difProfessor = pDifProfessor;
        difMateria = pDifMateria;
    }

    public int getCodigo() {
        return Codigo;
    }

    public String getNome() {
        return Nome;
    }

    public String getProfessor() {
        return Professor;
    }

    public int getCor() {
        return Cor;
    }

    public int getDifProfessor() {
        return difProfessor;
    }

    public int getDifMateria() {
        return difMateria;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public void setProfessor(String professor) {
        Professor = professor;
    }

    public void setCor(int cor) {
        Cor = cor;
    }

    public void setDifProfessor(int difProfessor) {
        this.difProfessor = difProfessor;
    }

    public void setDifMateria(int difMateria) {
        this.difMateria = difMateria;
    }
}
