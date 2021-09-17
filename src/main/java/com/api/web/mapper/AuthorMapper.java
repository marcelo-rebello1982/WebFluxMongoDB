package com.api.web.mapper;


import com.api.web.dto.AuthorRequestDTO;
import com.api.web.model.Author;
import org.mapstruct.Mapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author authorToAuthorRequestDTO(AuthorRequestDTO authorRequestDTO);

    default Mono<AuthorRequestDTO> authorRequestDTOToAuthor(Mono<Author> author) {
        return author.map(this::map);
    }

    default Flux<AuthorRequestDTO> authorRequestDTOToAuthor(Flux<Author> author) {
        return author.map(this::map);
    }

    AuthorRequestDTO map(Author author);

}
