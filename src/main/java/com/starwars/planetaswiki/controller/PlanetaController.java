package com.starwars.planetaswiki.controller;

import com.starwars.planetaswiki.model.Planeta;
import com.starwars.planetaswiki.model.Response;
import com.starwars.planetaswiki.service.PlanetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/planetas")
@CrossOrigin(origins = "*")
public class PlanetaController {

    private final PlanetaService planetaService;

    @PostMapping
    @Operation(summary = "Adicione um novo planeta ao banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Novo registro criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro durante a requisição")
    })
    public ResponseEntity<Response> adicionarPlaneta(@RequestBody Planeta planeta){
        Response planetaSalvo = planetaService.salvar(planeta);
        return new ResponseEntity<>(planetaSalvo, HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Atualize um planeta existente no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso na requisição"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro durante a requisição")
    })
    public ResponseEntity<Response> atualizarPlaneta(@RequestBody @Valid Planeta planeta){
        Response planetaAtualizado = planetaService.atualizar(planeta);
        return ResponseEntity.ok(planetaAtualizado);
    }

    @GetMapping
    @Operation(summary = "Solicite a lista de planetas salvos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso na requisição"),
            @ApiResponse(responseCode = "404", description = "Registros não encontrados"),
            @ApiResponse(responseCode = "500", description = "Erro durante a requisição")
    })
    public ResponseEntity<Response> listarPlanetasSalvos(){
        return ResponseEntity.ok(planetaService.listarTodos());
    }

    @Operation(summary = "Busque um planeta pelo nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso na requisição"),
            @ApiResponse(responseCode = "404", description = "Registros não encontrados"),
            @ApiResponse(responseCode = "500", description = "Erro durante a requisição")
    })
    @GetMapping("/nome")
    public ResponseEntity<Response> buscarPlanetaPorNome(@RequestParam("nome") String nome){
        return ResponseEntity.ok(planetaService.buscarPorNome(nome));
    }

    @Operation(summary = "Busque um planeta pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso na requisição"),
            @ApiResponse(responseCode = "404", description = "Registros não encontrados"),
            @ApiResponse(responseCode = "500", description = "Erro durante a requisição")
    })
    @GetMapping("/id")
    public ResponseEntity<Response> buscarPlanetaPorId(@RequestParam("id") String id){
        return ResponseEntity.ok(planetaService.buscarPorId(id));
    }

    @Operation(summary = "Remova um planeta pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso na requisição"),
            @ApiResponse(responseCode = "404", description = "Registros não encontrados"),
            @ApiResponse(responseCode = "500", description = "Erro durante a requisição")
    })
    @DeleteMapping("/id")
    public ResponseEntity<Response> removerPlaneta(@RequestParam("id") String id){
        return ResponseEntity.ok(planetaService.remover(id));
    }




}
