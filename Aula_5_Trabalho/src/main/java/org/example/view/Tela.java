package org.example.view;

import org.example.controller.ContatoController;

import java.util.Scanner;

public class Tela {
    private static Scanner teclado = new Scanner(System.in);
    private static ContatoController contatoController = new ContatoController();

    public static void main(String[] args) {
        boolean sair = false;
        while (!sair) {
            exibirMenu();
            int escolha = teclado.nextInt();
            teclado.nextLine(); // Consumes newline character

            if (escolha == 1) {
                adicionarContato();
            } else if (escolha == 2) {
                contatoController.listarContatos();
            } else if (escolha == 3) {
                removerContato();
            } else if (escolha == 4) {
                editarContato();
            } else if (escolha == 5) {
                pesquisarContatosPorLetra();
            } else if (escolha == 6) {
                pesquisarContatosPorMes();
            } else if (escolha == 0) {
                sair = true;
                System.out.println("Saindo...");
            } else {
                System.out.println("Opção inválida. Escolha novamente.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("T----------------------T");
        System.out.println("|Escolha-opção-desejada|");
        System.out.println("+----------------------+");
        System.out.println("|1)Adiconar------------|");
        System.out.println("|2)Listar--------------|");
        System.out.println("|3)Remover-Contato-----|");
        System.out.println("|4)Editar-Contato------|");
        System.out.println("|5)Pesquisar-Por-Letra-|");
        System.out.println("|6)Pesquisar-Por-Mes---|");
        System.out.println("|0)Sair----------------|");
        System.out.println("|______________________|");
    }

    private static void adicionarContato() {
        System.out.println("=== Adicionar Contato ===");
        System.out.print("Insira o Nome: ");
        String nome = teclado.nextLine();

        System.out.print("Insira o Telefone: ");
        int telefone = teclado.nextInt();
        teclado.nextLine();

        System.out.print("Insira o E-mail: ");
        String email = teclado.nextLine();

        System.out.print("Insira o Ano de Nascimento: ");
        int ano = teclado.nextInt();

        System.out.print("Insira o Mês de Nascimento: ");
        int mes = teclado.nextInt();

        System.out.print("Insira o Dia de Nascimento: ");
        int dia = teclado.nextInt();
        teclado.nextLine();

        contatoController.adicionarContato(nome, telefone, email, ano, mes, dia);
    }

    private static void removerContato() {
        System.out.println("=== Remover Contato ===");
        if (contatoController.getContatos().isEmpty()) {
            System.out.println("Lista de contatos vazia.");
            return;
        }

        System.out.print("Informe o id do contato que deseja remover: ");
        int idDeletado = teclado.nextInt();
        teclado.nextLine();

        contatoController.removerContato(idDeletado);
    }

    private static void editarContato() {
        System.out.println("=== Editar Contato ===");
        if (contatoController.getContatos().isEmpty()) {
            System.out.println("Lista de contatos vazia.");
            return;
        }
        System.out.print("Informe o id do contato que deseja editar: ");
        int idEditado = teclado.nextInt();
        teclado.nextLine();

        System.out.println("Insira o Nome:");
        String nome = teclado.nextLine();

        System.out.println("Insira o Telefone:");
        Integer telefone = teclado.nextInt();
        teclado.nextLine();

        System.out.println("Insira o E-mail:");
        String email = teclado.nextLine();

        System.out.println("Insira o Ano de Nascimento:");
        int ano = teclado.nextInt();

        System.out.println("Insira o Mês de Nascimento:");
        int mes = teclado.nextInt();

        System.out.println("Insira o Dia de Nascimento:");
        int dia = teclado.nextInt();
        teclado.nextLine();

        contatoController.editarContato(1-idEditado, nome, telefone, email, ano, mes, dia);

    }

    private static void pesquisarContatosPorLetra() {
        System.out.println("=== Pesquisar por Letra ===");
        System.out.print("Informe a letra inicial do nome desejado: ");
        String letraInicial = teclado.nextLine().toLowerCase();
        contatoController.pesquisarContatosporLetra(letraInicial);
    }

    private static void pesquisarContatosPorMes() {
        System.out.println("=== Pesquisar por Mês ===");
        if (contatoController.getContatos().isEmpty()) {
            System.out.println("Lista de contatos vazia.");
            return;
        }

        System.out.print("Informe o número do mês: ");
        int mes = teclado.nextInt();
        teclado.nextLine();

        contatoController.pesquisarContatosporMes(mes);
    }
}
