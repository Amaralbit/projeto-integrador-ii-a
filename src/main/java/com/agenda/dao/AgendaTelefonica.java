package com.agenda.dao;

import com.agenda.modelo.Contato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe AgendaTelefonica.
 *
 * E o "cerebro" da aplicacao: faz o gerenciamento dos contatos conversando
 * diretamente com o banco de dados MySQL via JDBC.
 *
 * Aqui estao implementadas as quatro operacoes do CRUD:
 *   - Create  -> adicionarContato()
 *   - Read    -> buscarContato() e listarContatos()
 *   - Update  -> atualizarContato()
 *   - Delete  -> removerContato()
 *
 * Os metodos pedidos na proposta (adicionar, remover, buscar e listar) estao
 * todos presentes. Foi acrescentado o atualizarContato() para completar o CRUD.
 */
public class AgendaTelefonica {

    /**
     * CREATE - Adiciona um novo contato a agenda (insere no banco).
     *
     * @param contato objeto Contato a ser salvo (nome, telefone e email).
     * @throws SQLException se ocorrer erro de banco de dados.
     */
    public void adicionarContato(Contato contato) throws SQLException {
        String sql = "INSERT INTO contato (nome, telefone, email) VALUES (?, ?, ?)";

        // try-with-resources: a conexao e o statement sao fechados automaticamente.
        try (Connection conexao = ConexaoBD.conectar();
             PreparedStatement stmt = conexao.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            // Usamos PreparedStatement (com "?") para evitar SQL Injection.
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());
            stmt.executeUpdate();

            // Recupera o id gerado automaticamente pelo banco e devolve no objeto.
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    contato.setId(rs.getInt(1));
                }
            }
        }
    }

    /**
     * READ - Busca e retorna um contato pelo nome.
     *
     * @param nome nome do contato a procurar.
     * @return o Contato encontrado.
     * @throws ContatoNaoEncontradoException se nenhum contato tiver esse nome.
     * @throws SQLException                  se ocorrer erro de banco de dados.
     */
    public Contato buscarContato(String nome)
            throws ContatoNaoEncontradoException, SQLException {

        String sql = "SELECT id, nome, telefone, email FROM contato WHERE nome = ?";

        try (Connection conexao = ConexaoBD.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, nome);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Contato(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("telefone"),
                            rs.getString("email"));
                } else {
                    throw new ContatoNaoEncontradoException(
                            "Nenhum contato encontrado com o nome: " + nome);
                }
            }
        }
    }

    /**
     * READ - Lista todos os contatos armazenados na agenda.
     *
     * @return lista de contatos (vazia se nao houver nenhum).
     * @throws SQLException se ocorrer erro de banco de dados.
     */
    public List<Contato> listarContatos() throws SQLException {
        String sql = "SELECT id, nome, telefone, email FROM contato ORDER BY nome";
        List<Contato> contatos = new ArrayList<>();

        try (Connection conexao = ConexaoBD.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                contatos.add(new Contato(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email")));
            }
        }
        return contatos;
    }

    /**
     * UPDATE - Atualiza o telefone e o email de um contato existente,
     * localizado pelo nome.
     *
     * @param nome        nome do contato que sera atualizado.
     * @param novoTelefone novo numero de telefone.
     * @param novoEmail    novo e-mail.
     * @throws ContatoNaoEncontradoException se o contato nao existir.
     * @throws SQLException                  se ocorrer erro de banco de dados.
     */
    public void atualizarContato(String nome, String novoTelefone, String novoEmail)
            throws ContatoNaoEncontradoException, SQLException {

        String sql = "UPDATE contato SET telefone = ?, email = ? WHERE nome = ?";

        try (Connection conexao = ConexaoBD.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novoTelefone);
            stmt.setString(2, novoEmail);
            stmt.setString(3, nome);

            int linhasAfetadas = stmt.executeUpdate();

            // Se nenhuma linha foi alterada, o contato nao existia.
            if (linhasAfetadas == 0) {
                throw new ContatoNaoEncontradoException(
                        "Nao foi possivel atualizar: contato '" + nome + "' nao existe.");
            }
        }
    }

    /**
     * DELETE - Remove um contato da agenda pelo nome.
     *
     * @param nome nome do contato a remover.
     * @throws ContatoNaoEncontradoException se o contato nao existir.
     * @throws SQLException                  se ocorrer erro de banco de dados.
     */
    public void removerContato(String nome)
            throws ContatoNaoEncontradoException, SQLException {

        String sql = "DELETE FROM contato WHERE nome = ?";

        try (Connection conexao = ConexaoBD.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, nome);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new ContatoNaoEncontradoException(
                        "Nao foi possivel remover: contato '" + nome + "' nao existe.");
            }
        }
    }
}
