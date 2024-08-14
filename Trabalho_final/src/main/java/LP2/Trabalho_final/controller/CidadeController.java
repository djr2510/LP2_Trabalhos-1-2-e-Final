package LP2.Trabalho_final.controller;

import LP2.Trabalho_final.model.Cidade;
import LP2.Trabalho_final.model.Uf;
import LP2.Trabalho_final.service.CidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    // Cadastrar Cidade
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cidade> cadastrar(@RequestBody @Valid Cidade cidade) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cidadeService.salvar(cidade));
    }

    // Listar todas as cidades
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cidade>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(cidadeService.buscaTodos());
    }

    // Listar cidades por UF
    @GetMapping(value = "por-uf", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cidade>> listarPorUf(@RequestParam("sigla") String siglaUf) {
        Uf uf = new Uf();
        uf.setSigla(siglaUf);
        return ResponseEntity.status(HttpStatus.OK).body(cidadeService.buscaPorUf(uf));
    }
}
