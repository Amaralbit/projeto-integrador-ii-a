package com.agenda.modelo;

/**
 * Classe Contato.
 *
 * Representa um contato da agenda telefonica conforme solicitado na proposta.
 * Possui os atributos: nome, telefone e email (todos do tipo String).
 *
 * O atributo "id" nao foi pedido explicitamente, mas e gerado automaticamente
 * pelo banco de dados (chave primaria AUTO_INCREMENT) para identificar cada
 * registro de forma unica. Ele e util internamente, mas o usuario continua
 * interagindo com a agenda pelo NOME, como pede a proposta.
 */
public class Contato {

    private int id;
    private String nome;
    private String telefone;
    private String email;

    // Construtor vazio
    public Contato() {
    }

    // Construtor sem id (usado ao criar um novo contato antes de salvar no banco)
    public Contato(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    // Construtor completo (usado ao ler um contato vindo do banco)
    public Contato(int id, String nome, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna uma representacao legivel do contato, usada na hora de listar
     * ou exibir os dados na tela.
     */
    @Override
    public String toString() {
        return String.format(
                "ID: %d | Nome: %s | Telefone: %s | E-mail: %s",
                id, nome, telefone, email);
    }
}
