package com.agenda;

import com.agenda.dao.AgendaTelefonica;
import com.agenda.dao.ContatoNaoEncontradoException;
import com.agenda.modelo.Contato;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe Principal (AgendaTeste).
 *
 * Contem o metodo main e oferece um menu simples no terminal para o usuario
 * interagir com a agenda telefonica. Demonstra o CRUD completo:
 *
 *   1. Adicionar um novo contato  (CREATE)
 *   2. Remover um contato         (DELETE)
 *   3. Buscar um contato pelo nome (READ)
 *   4. Listar todos os contatos    (READ)
 *   5. Atualizar um contato        (UPDATE)
 *   6. Sair do programa
 *
 * Todo o codigo tem tratamento de excecoes para evitar que o programa quebre
 * diante de entradas invalidas ou problemas de banco de dados.
 */
public class AgendaTeste {

    // Objeto que faz o gerenciamento dos contatos (acessa o banco).
    private static final AgendaTelefonica agenda = new AgendaTelefonica();

    // Scanner unico para ler tudo o que o usuario digita.
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = -1;

        System.out.println("=========================================");
        System.out.println("        AGENDA TELEFONICA - CRUD         ");
        System.out.println("=========================================");

        // Laco principal: repete o menu ate o usuario escolher Sair (6).
        do {
            exibirMenu();
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    adicionar();
                    break;
                case 2:
                    remover();
                    break;
                case 3:
                    buscar();
                    break;
                case 4:
                    listar();
                    break;
                case 5:
                    atualizar();
                    break;
                case 6:
                    System.out.println("\nEncerrando o programa. Ate logo!");
                    break;
                default:
                    System.out.println("\n[!] Opcao invalida. Escolha um numero de 1 a 6.");
            }
        } while (opcao != 6);

        scanner.close();
    }

    /** Mostra as opcoes do menu na tela. */
    private static void exibirMenu() {
        System.out.println("\n--------------- MENU --------------------");
        System.out.println("1 - Adicionar um novo contato");
        System.out.println("2 - Remover um contato existente");
        System.out.println("3 - Buscar um contato pelo nome");
        System.out.println("4 - Listar todos os contatos");
        System.out.println("5 - Atualizar um contato");
        System.out.println("6 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    /**
     * Le a opcao do menu com seguranca. Se o usuario digitar letra/texto em vez
     * de numero, captura a excecao e devolve -1 (opcao invalida) sem travar.
     */
    private static int lerOpcao() {
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpa o "enter" que sobra no buffer
            return opcao;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // descarta a entrada invalida
            return -1;
        }
    }

    /** Opcao 1 - CREATE: pede os dados e adiciona um novo contato. */
    private static void adicionar() {
        System.out.println("\n--- ADICIONAR CONTATO ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine().trim();
        System.out.print("E-mail: ");
        String email = scanner.nextLine().trim();

        // Validacao simples: o nome nao pode ficar em branco.
        if (nome.isEmpty()) {
            System.out.println("[!] O nome e obrigatorio. Operacao cancelada.");
            return;
        }

        try {
            Contato contato = new Contato(nome, telefone, email);
            agenda.adicionarContato(contato);
            System.out.println("[OK] Contato adicionado com sucesso! (ID " + contato.getId() + ")");
        } catch (SQLException e) {
            System.out.println("[ERRO] Nao foi possivel salvar o contato: " + e.getMessage());
        }
    }

    /** Opcao 2 - DELETE: remove um contato pelo nome. */
    private static void remover() {
        System.out.println("\n--- REMOVER CONTATO ---");
        System.out.print("Nome do contato a remover: ");
        String nome = scanner.nextLine().trim();

        try {
            agenda.removerContato(nome);
            System.out.println("[OK] Contato removido com sucesso!");
        } catch (ContatoNaoEncontradoException e) {
            System.out.println("[!] " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[ERRO] Falha ao remover: " + e.getMessage());
        }
    }

    /** Opcao 3 - READ: busca e mostra um contato pelo nome. */
    private static void buscar() {
        System.out.println("\n--- BUSCAR CONTATO ---");
        System.out.print("Nome do contato a buscar: ");
        String nome = scanner.nextLine().trim();

        try {
            Contato contato = agenda.buscarContato(nome);
            System.out.println("Contato encontrado:");
            System.out.println("  " + contato);
        } catch (ContatoNaoEncontradoException e) {
            System.out.println("[!] " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[ERRO] Falha na busca: " + e.getMessage());
        }
    }

    /** Opcao 4 - READ: lista todos os contatos. */
    private static void listar() {
        System.out.println("\n--- LISTA DE CONTATOS ---");
        try {
            List<Contato> contatos = agenda.listarContatos();
            if (contatos.isEmpty()) {
                System.out.println("Nenhum contato cadastrado.");
            } else {
                for (Contato c : contatos) {
                    System.out.println("  " + c);
                }
                System.out.println("Total: " + contatos.size() + " contato(s).");
            }
        } catch (SQLException e) {
            System.out.println("[ERRO] Falha ao listar: " + e.getMessage());
        }
    }

    /** Opcao 5 - UPDATE: atualiza telefone e email de um contato. */
    private static void atualizar() {
        System.out.println("\n--- ATUALIZAR CONTATO ---");
        System.out.print("Nome do contato a atualizar: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Novo telefone: ");
        String novoTelefone = scanner.nextLine().trim();
        System.out.print("Novo e-mail: ");
        String novoEmail = scanner.nextLine().trim();

        try {
            agenda.atualizarContato(nome, novoTelefone, novoEmail);
            System.out.println("[OK] Contato atualizado com sucesso!");
        } catch (ContatoNaoEncontradoException e) {
            System.out.println("[!] " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[ERRO] Falha ao atualizar: " + e.getMessage());
        }
    }
}
