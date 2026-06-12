package com.agenda.dao;

/**
 * Excecao personalizada para indicar que um contato nao foi encontrado
 * durante a busca na agenda telefonica.
 */
public class ContatoNaoEncontradoException extends Exception {

    public ContatoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
