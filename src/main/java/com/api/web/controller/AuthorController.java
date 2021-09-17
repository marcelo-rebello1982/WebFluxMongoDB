package com.api.web.controller;

import com.api.web.dto.AuthorRequestDTO;
import com.api.web.enums.CrudOperation;
import com.api.web.mapper.AuthorMapper;
import com.api.web.model.Author;
import com.api.web.model.MapperUtility;
import com.api.web.response.AuthorResponse;
import com.api.web.service.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
//@RequestMapping("/com/api/web/controller")
@Api(value = "/web/controller", tags = {"API Author"})
//@CrossOrigin(methods = {POST,PUT,GET,DELETE}, maxAge = 3600, allowedHeaders
//        = {"x-request-with", "origin", "conten-type","accept"}, origins = "*")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorMapper authorMapper;

    @GetMapping(value = "/author/findAll", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retorna uma lista de author", response =  AuthorRequestDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Algum problema na requisição"),
            @ApiResponse(code = 200, message = "Retorna uma lista de AuthoresS"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção de sistema"),
    })
    public Flux<Author> findAll() {
        return authorService.findAll();
    }

    @GetMapping(value = "/author/findAll/pagination", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retorna uma lista de author com Paginação")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Algum problema na requisição"),
            @ApiResponse(code = 200, message = "Retorna uma lista Paginada de Autores"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção de sistema"),
    })
    public ResponseEntity<Mono<AuthorResponse>> findAll(
              @RequestParam(name = "limit", defaultValue = "30") Integer limit,
                    @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        return ok(authorService.findAll(offset, limit));
    }

    @GetMapping(value = "/author/{id}", produces =  APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar author por id", response =  AuthorRequestDTO.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Resposta com sucesso"),
            @ApiResponse(code = 400, message = "Algum problema na requisição"),
            @ApiResponse(code = 404, message = "Categoria Não existe"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção de sistema")})
    public Mono<ResponseEntity<Author>> findById(@PathVariable ("id") @Parameter (
    required = true, example = "1ff32db9drdc0d11cba376919dd7c5e",
    description = "Unique identifier of an Author") String id) {
        return authorService.findById(id);

    }
    @GetMapping("/author/name/{name}")
    @ApiOperation(value = "Buscar author por nome, parte do nome.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Algum problema na requisição"),
            @ApiResponse(code = 404, message = "Categoria Não existe"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção de sistema"),
    })
        public Flux<Author> findByNameLike(@PathVariable("name") String name) {
        return authorService.findByName(name);
    }

//    public Mono<ResponseEntity<List<Author>>> findByNameLike(@PathVariable("name") String name) {
//        return authorService.findByName(name);
//    }




    //    @GetMapping("/author/name/{name}")
//    @ApiOperation(value = "Buscar author por nome, parte do nome.")
//    @ApiResponses(value = {
//            @ApiResponse(code = 400, message = "Algum problema na requisição"),
//            @ApiResponse(code = 404, message = "Categoria Não existe"),
//            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
//            @ApiResponse(code = 500, message = "Foi gerada uma exceção de sistema"),
//    })
//    public Flux<Author> findByNameLike(@PathVariable("name") String name) {
//        return authorService.findByName(name);
//    }


    @PostMapping("/author/save")
    @ApiOperation(value = "Adiciona ou Altera um Author já cadastrado")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Author Cadastrado"),
            @ApiResponse(code = 400, message = "Algum problema na requisição"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção de sistema"),
    })
    public Mono<Author> save(@Valid @RequestBody AuthorRequestDTO authorRequestDTO) {
        return authorService.save(authorMapper.authorToAuthorRequestDTO(authorRequestDTO));
    }

    @DeleteMapping(path = "/author/delete/{id}")
    @ApiOperation(value = "Deleta um Author")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Solicitação bem sucedida"),
            @ApiResponse(code = 400, message = "Algum problema na requisição"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 404, message = "Author não existe"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção de sistema"),
    })
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") @Parameter(
            required = true, example = "1ffedse9gg44dd1ba3719466ee8x5g",
                description = "Unique identifier of a Author") String id){
                    return authorService.delete(id);
    }

    private boolean isNotAuthorized(CrudOperation operation, String id) {
        return isAuthorized(operation, id);

    }

    private boolean isAuthorized(CrudOperation operation, String id) {
        return false;
    }
}

//    public Flux<Owner> searchOwnersByName(@PathVariable("lastName") String searchString) {
//        return ownerServices.findOwnersByName(searchString);
//    }


