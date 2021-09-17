package com.api.web.repository;

import com.api.web.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorMongoRepository extends MongoRepository <Author, String> {

    Page<Author> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Modifying
    @Query("delete from Author a where a.name =:name or a.nationality =: nationality")
    int deleteAuthor(@Param("name")String name, @Param("nationality")String nationality);


}
