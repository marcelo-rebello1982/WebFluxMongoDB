package com.api.web.repository;

import com.api.web.model.Author;
import com.api.web.model.EmailModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AuthorReactiveRepository extends ReactiveMongoRepository<Author, String> {


    Flux<Author> findByNameLike (String name, Sort sort);

    Flux<Author> findByNameContainingIgnoreCase (String name);

    Flux<Author> findByNameContainingIgnoreCase (String name, Pageable pageable);

    Mono<Author> findByIdAndDeleteIsFalse(String ig);

    Mono<Long> countByNameContainsIgnoreCase(String firstName);

    Flux<Author> findByEmail (EmailModel email);

    @Query("{ id: { $exists: true }}")
    Flux<Author> retrieveAllAuthors(final Pageable pageable);

}
