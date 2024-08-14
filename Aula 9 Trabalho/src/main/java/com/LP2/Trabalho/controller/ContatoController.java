package com.LP2.Trabalho.controller;

import com.LP2.Trabalho.exception.ParametroInvalidoException;
import com.LP2.Trabalho.exception.RegistroNaoEcontradoException;
import com.LP2.Trabalho.model.ContatoModel;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "contatos")
public class ContatoController {
    private List<ContatoModel> contatos;

    public ContatoController() {
        contatos = new ArrayList<>();
        contatos.add(new ContatoModel("Daniele", 97312274, "daniele@gmail.com", new GregorianCalendar(2006, Calendar.OCTOBER, 25)));
        contatos.add(new ContatoModel("Rafael", 97312274, "rafael@gmail.com", new GregorianCalendar(2006, Calendar.JANUARY, 25)));
        contatos.add(new ContatoModel("Davi", 97312274, "Davi@gmail.com", new GregorianCalendar(2006, Calendar.OCTOBER, 25)));

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContatoModel> adicionar(@RequestBody @Valid ContatoModel contatoModel) {
        contatos.add(contatoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(contatoModel);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ContatoModel>> get() {
        return ResponseEntity.status(HttpStatus.OK).body(contatos);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContatoModel> remover(@PathVariable("id") Integer id) {
        ContatoModel contatoBuscar = contatos.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);

        if (contatoBuscar == null) {
            throw new RegistroNaoEcontradoException("Contato ID: " + id + " não encontrado");
        }

        contatos.remove(contatoBuscar);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContatoModel> editar(@PathVariable("id") Integer id, @RequestBody ContatoModel contatoModel) {
        ContatoModel contatoBuscar = contatos.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);

        if (contatoBuscar == null) {
            throw new RegistroNaoEcontradoException("Contato ID: " + id + " não encontrado");
        }

        contatoBuscar.setNome(contatoModel.getNome());
        contatoBuscar.setTelefone(contatoModel.getTelefone());
        contatoBuscar.setEmail(contatoModel.getEmail());
        contatoBuscar.setNascimento(contatoModel.getNascimento());

        return ResponseEntity.status(HttpStatus.OK).body(contatoBuscar);
    }

    @GetMapping(value = "/consulta", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ContatoModel>> consultarPorNome(@RequestParam("Nome") String nome) {
        List<ContatoModel> contatosBusca = contatos.stream()
                .filter(c -> c.getNome().toLowerCase().startsWith(nome.toLowerCase()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(contatosBusca);
    }

    // Método para consultar contatos por mês de aniversário
    @GetMapping(value = "/consulta", params = {"Mes"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ContatoModel>> consultarAniversariantes(@RequestParam("Mes") int mes) {
        List<ContatoModel> contatosBusca = contatos.stream()
                .filter(c -> c.getNascimento() != null && c.getNascimento().get(Calendar.MONTH) == mes - 1)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(contatosBusca);
    }
}


