package org.example.controller;

import org.example.model.Contato;

import java.util.*;
import java.util.stream.Collectors;

public class ContatoController {
    Scanner teclado=new Scanner(System.in);
    private List<Contato> contatos;


    public void adicionarContato(String nome, Integer telefone, String email, int ano, int mes, int dia) {
        if (contatos == null) {
            contatos = new ArrayList<>();
        }

        Contato novoContato = new Contato(nome, telefone, email, new GregorianCalendar(ano, mes-1, dia));

        contatos.add(novoContato);

        System.out.println("Contato adicionado com sucesso.");
    }

    public void listarContatos(){
        for (Contato contato : contatos) {
            String nascimento = (contato.getNascimento() != null) ? formatDate(contato.getNascimento()) : "Data de nascimento não especificada";
            System.out.println("ID: " + contato.getId() + ", Nome: " + contato.getNome() + ", Telefone: " + contato.getNumero() + ", Email: " + contato.getEmail() + ", Nascimento: " + nascimento);
        }
    }
    private String formatDate(Calendar calendar) {
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        return String.format("%02d/%02d/%04d", dia, mes+1, ano);
    }

    public void removerContato(int idDeletado) {
        Optional<Contato> contatoParaRemover = contatos.stream()
                .filter(c -> c.getId() == idDeletado)
                .findFirst();

        if (contatoParaRemover.isPresent()) {
            contatos.remove(contatoParaRemover.get());
            System.out.println("Contato removido com sucesso.");
        } else {
            System.out.println("ID de contato inválido ou contato não encontrado.");
        }
    }

    public void editarContato(int idEditado, String nome, int telefone, String email, int ano, int mes, int dia) {
        if (idEditado >= 0 && idEditado < contatos.size()) {

            contatos.get(idEditado).setNome(nome);
            contatos.get(idEditado).setNumero(telefone);
            contatos.get(idEditado).setEmail(email);
            contatos.get(idEditado).setNascimento(new GregorianCalendar(ano, mes-1, dia));

            System.out.println("Contato editado com sucesso.");
        } else {
            System.out.println("ID de contato inválido.");
        }
    }


    public void pesquisarContatosporLetra(String sequenciaInicial) {
        if (contatos != null) {
            List<Contato> contatosBusca = contatos.stream()
                    .filter(c -> c.getNome().toLowerCase().startsWith(sequenciaInicial.toLowerCase()))
                    .collect(Collectors.toList());

            if (!contatosBusca.isEmpty()) {
                contatosBusca.forEach(c -> {
                    System.out.println("ID: " + c.getId() +
                            ", Nome: " + c.getNome() +
                            ", Telefone: " + c.getNumero() +
                            ", Email: " + c.getEmail());
                });
            } else {
                System.out.println("Nenhum contato encontrado para a sequência inicial '" + sequenciaInicial + "'.");
            }
        } else {
            System.out.println("Lista de contatos não inicializada.");
        }
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }

    public void pesquisarContatosporMes(int mes) {
        if (contatos != null) {
            List<Contato> contatosBusca = contatos.stream()
                    .filter(c -> {
                        if (c.getNascimento() != null) {
                            int mesNascimento = c.getNascimento().get(Calendar.MONTH) + 1;
                            return mesNascimento == mes;
                        }
                        return false;
                    })
                    .collect(Collectors.toList());

            if (!contatosBusca.isEmpty()) {
                contatosBusca.forEach(c -> {
                    System.out.println("ID: " + c.getId() +
                            ", Nome: " + c.getNome() +
                            ", Telefone: " + c.getNumero() +
                            ", Email: " + c.getEmail());
                });
            } else {
                System.out.println("Nenhum contato encontrado para o mês " + mes + ".");
            }
        } else {
            System.out.println("Lista de contatos não inicializada.");
        }
    }




}
