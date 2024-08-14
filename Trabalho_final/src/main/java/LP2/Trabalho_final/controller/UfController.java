package LP2.Trabalho_final.controller;

import LP2.Trabalho_final.model.Uf;
import LP2.Trabalho_final.service.UfService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "ufs")
public class UfController {

    @Autowired
    private UfService ufService;

    // Cadastrar UF
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Uf> cadastrar(@RequestBody @Valid Uf uf) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ufService.salvar(uf));
    }

    // Listar todas as UFs
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Uf>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(ufService.buscaTodos());
    }
}
