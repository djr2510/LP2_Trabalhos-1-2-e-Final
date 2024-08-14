package LP2.Trabalho_final.controller;

import LP2.Trabalho_final.model.Cep;
import LP2.Trabalho_final.service.CepService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "ceps")
public class CepController {

    @Autowired
    private CepService cepService;

    // Cadastrar CEP
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cep> cadastrar(@RequestBody @Valid Cep cep) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cepService.salvar(cep));
    }

    // Remover CEP
    @DeleteMapping(value = "{numCep}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> remover(@PathVariable("numCep") Integer numCep) {
        cepService.remover(numCep);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Listar todos os CEPs
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cep>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(cepService.buscaTodos());
    }
    // Listar CEPs por Cidade
    @GetMapping(value = "por-cidade", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cep>> listarPorCidade(@RequestParam("cidade") String nomeCidade) {
        return ResponseEntity.status(HttpStatus.OK).body(cepService.buscaPorNomeCidade(nomeCidade));
    }

    // Listar CEPs por UF
    @GetMapping(value = "por-uf", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cep>> listarPorUf(@RequestParam("uf") String siglaUf) {
        return ResponseEntity.status(HttpStatus.OK).body(cepService.buscaPorUf(siglaUf));
    }
}
