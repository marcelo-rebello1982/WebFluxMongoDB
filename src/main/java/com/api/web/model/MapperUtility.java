package com.api.web.model;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class MapperUtility {

    private MapperUtility() {}

    static Author toAuthor(Author author) {
        return new Author(

                author.getId(),
                author.getName(),
                author.getCpf(),
                author.getNationality(),
                author.getBiography(),
                author.getEmail(),
                author.getBirthdate());
    }

    public Author mappingAuthorToEntity(Author old, Author updated) {
        updated.setName(old.getName());
        updated.setBirthdate(old.getBirthdate());
        updated.setBiography(old.getBiography());
        updated.setNationality(old.getNationality());
        updated.setEmail(old.getEmail());
        return updated;
    }

    public static Author mappingAuthorToEntity(@NotNull Author author) {
        Objects.requireNonNull(author);
        Author authorObj = new Author();
        authorObj.setId(author.getId());
        authorObj.setName(author.getName());
        authorObj.setCpf(author.getCpf());
        authorObj.setNationality(author.getNationality());
        authorObj.setBiography(author.getBiography());
        authorObj.setEmail(author.getEmail());
        authorObj.setBirthdate(author.getBirthdate());
        return authorObj;
    }
}
