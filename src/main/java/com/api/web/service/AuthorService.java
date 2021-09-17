package com.api.web.service;

import com.api.web.exceptions.EmailDuplicatedException;
import com.api.web.model.Author;
import com.api.web.model.EmailModel;
import com.api.web.model.MapperUtility;
import com.api.web.repository.AuthorReactiveRepository;
import com.api.web.response.AuthorResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Service
public class AuthorService {

    @Autowired
    private AuthorReactiveRepository authorReactiveRepository;

    public Flux<Author> findAll() {
        return authorReactiveRepository.findAll();
    }

    public Mono<ResponseEntity<Author>> findById(String id) {
        return authorReactiveRepository.findById(id)
                .map(author ->
                        new ResponseEntity<Author>(author, HttpStatus.OK))
                            .defaultIfEmpty(new ResponseEntity<>
                                (HttpStatus.NOT_FOUND));
    }
        public Flux<Author> findByName(@NotNull String name) {
        return Flux.from(authorReactiveRepository.findByNameContainingIgnoreCase(name))
                .map(MapperUtility::mappingAuthorToEntity);
    }



//    public Mono<ResponseEntity<List<Author>>> findByName(String name) {
//        return authorReactiveRepository.findByNameContainingIgnoreCase(name)
//                .collectList().flatMap(users -> {
//                    if (users.isEmpty()) {
//                        return Mono.just(notFound().build());
//                    } else {
//                        return Mono.just(ok().body(users));
//                    }});
//    }


    @Transactional
    public Mono<Author> save(Author author) {
        if (StringUtils.isNotBlank(author.getId())) {
            return authorReactiveRepository.findByIdAndDeleteIsFalse(author.getId())
                     .switchIfEmpty(Mono.error
                         (new Exception("Author not found in id : " + author.getId())))
                             .doOnSuccess(newAuthor -> {
                                 newAuthor = author.mappingAuthorToEntity(newAuthor);
                                 if (isExistsEmail(newAuthor.getEmail()))
                                     throw new EmailDuplicatedException(newAuthor.getEmail());
                                 authorReactiveRepository.save(newAuthor).subscribe();
                             });
        }
        return authorReactiveRepository.save(author);
    }

    public Mono<AuthorResponse> findAll(Integer offset, Integer limit) {
        AuthorResponse result = new AuthorResponse();
        return authorReactiveRepository.count()
                .map(totalElements -> {
                    result.setTotalElements(totalElements);
                       return totalElements;
                           })
                                .flatMapMany(author -> authorReactiveRepository.retrieveAllAuthors(
                                    PageRequest.of(offset, limit)))
                                        .collectList()
                                            .map(data -> {
                                                result.setAuthors(data);
                                                    return result;
                           });
    }

    public Mono<AuthorResponse> findAllByName(Integer offset,Integer limit, String name) {
        AuthorResponse result = new AuthorResponse();
        return authorReactiveRepository.countByNameContainsIgnoreCase(name)
              .map(totalElements -> {
                  result.setTotalElements(totalElements);
                  return result;
                      }).flatMapMany(author ->
                         authorReactiveRepository
                            .findByNameContainingIgnoreCase(name, PageRequest.of(offset, limit)))
                                .collectList().map(data -> {
                                    result.setAuthors(data);
                                        return  result;
                      });
    }

    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") @Parameter(required = true,
            example = "1ff2gt9df544cc4gr87469aa7d56e",
            description = "Unique identifier of a Author") String id) {
        return authorReactiveRepository.findById(id)
                .flatMap(existingAuthor -> authorReactiveRepository
                        .delete(existingAuthor)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public boolean isExistsEmail(EmailModel email) {
        return authorReactiveRepository.findByEmail(email) != null;
    }

    public <T> Mono<T> monoResponseNotFoundException() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Author Not Found"));
    }

    private List<Author> listAllAuthors() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author());
        return authors;
    }

//
//    public AuthorResponse loadAuthorsResponse(Integer offset, Integer limit) {
//        Page<Author> authorPage = authorMongoRepository.findAll(PageRequest.of(offset, limit));
//        AuthorResponse response = new AuthorResponse();
//        if (authorPage != null) {
//            response.setAuthors(authorPage.getContent());
//            response.setTotalElements(authorPage.getTotalElements());
//        }
//        return response;
//    }

//    public ResponseEntity<Mono<String>> insertList() {
//        List<Author> authorList = new ArrayList<>();
//
//        for (Integer index = 0; index < 50; index++) {
//
//            Author author = new Author();
//
//            long minDay = LocalDate.of(1968, 1, 1).toEpochDay();
//            long maxDay = LocalDate.of(2010, 12, 31).toEpochDay();
//            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
//
//            author.setName(RandomStringUtils.randomAlphabetic(20));
//            author.setNationality(RandomStringUtils.randomAlphabetic(20));
//            author.setBiography(RandomStringUtils.randomAlphabetic(20));
//            author.setNationality(RandomStringUtils.randomAlphabetic(20));
//            author.setBirthdate(LocalDate.ofEpochDay(randomDay));
//            authorList.add(author);
//        }
//        authorReactiveRepository.saveAll(authorList);
//        return ResponseEntity.ok(Mono.justOrEmpty(HttpStatus.OK.getReasonPhrase()));
//    }

//    public Mono<AuthorResponse> findAuthors(Integer offset, Integer limit, String firstName) {
//        AuthorResponse result = new AuthorResponse();
//        return authorReactiveRepository.countByFirstNameContainsIgnoreCase(firstName)
//                .map(totalElements -> {
//                    result.setTotalElements(totalElements);
//                    return result;
//                })
//                .flatMapMany(el -> authorRepository.findByFirstNameContainsIgnoreCase(firstName, PageRequest.of(offset, limit)))
//                .collectList()
//                .map(data -> {
//                    result.setAuthors(data);
//                    return result;
//                });
//    }


//
//        public AuthorResponse findAllByName(Integer offset, Integer limit, String query) {
//        Page<Author> authorPage = authorMongoRepository.findByNameContainsIgnoreCase(query, PageRequest.of(offset, limit));
//        AuthorResponse response = new AuthorResponse();
//        if (authorPage != null) {
//            response.setAuthors(authorPage.getContent());
//            response.setTotalElements(authorPage.getTotalElements());
//        }
//        return response;
//    }





}
