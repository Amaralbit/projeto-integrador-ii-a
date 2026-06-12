package com.agenda.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexaoBD {

    // Endereco do banco. "localhost:3306" = MySQL rodando na propria maquina.
    // "agenda_telefonica" e o nome do banco (veja o arquivo banco/agenda_telefonica.sql).
    private static final String URL_PADRAO =
            "jdbc:mysql://localhost:3306/agenda_telefonica"
            + "?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";

    /**
     * Abre e retorna uma conexao com o banco.
     *
     * @return objeto Connection pronto para uso.
     * @throws SQLException caso nao seja possivel conectar (banco desligado,
     *                      senha errada, driver ausente, etc.).
     */
    public static Connection conectar() throws SQLException {
        try {
            // Carrega o driver JDBC do MySQL. A partir do connector 8+ o nome
            // da classe e "com.mysql.cj.jdbc.Driver".
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Se cair aqui, o .jar do mysql-connector nao esta no classpath.
            throw new SQLException(
                    "Driver JDBC do MySQL nao encontrado. "
                    + "Verifique se o mysql-connector-j esta no projeto.", e);
        }
        String url = System.getenv().getOrDefault("AGENDA_DB_URL", URL_PADRAO);
        String usuario = exigirVariavel("AGENDA_DB_USUARIO");
        String senha = exigirVariavel("AGENDA_DB_SENHA");
        return DriverManager.getConnection(url, usuario, senha);
    }

    private static String exigirVariavel(String nome) throws SQLException {
        String valor = System.getenv(nome);
        if (valor == null || valor.isBlank()) {
            throw new SQLException("Defina a variavel de ambiente " + nome
                    + " antes de executar a aplicacao.");
        }
        return valor;
    }
}
