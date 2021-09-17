package com.api.web.util;

import com.api.web.dto.AuthorRequestDTO;
import com.api.web.model.Author;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class MappingUtils {

    public MappingUtils() {
    }

    public static Author mappingAuthorToEntity(@NotNull AuthorRequestDTO authorRequestDTO) {
        Objects.requireNonNull(authorRequestDTO);

        Author author = new Author();
        author.setId(authorRequestDTO.getId());
        author.setName(authorRequestDTO.getName());
        author.setNationality(authorRequestDTO.getNationality());
        author.setBiography(authorRequestDTO.getBiography());
        author.setCpf(authorRequestDTO.getCpf());
        author.setEmail(authorRequestDTO.getEmail());
        return author;
    }

}
