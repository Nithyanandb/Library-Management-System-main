// AuthorRepository.java
package com.StudentLibrary.Studentlibrary.Repositories;

import com.StudentLibrary.Studentlibrary.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public interface AuthorRepository extends JpaRepository<Author, Integer> {


    @Modifying
    @Query("update Author a set a.name = :#{#author.name}, a.email = :#{#author.email}, a.age = :#{#author.age}, a.country = :#{#author.country} where a.id = :#{#author.id}")
    int updateAuthorDetails(@Param("author") Author author);

    @Modifying
    @Query("delete Author a where a.id = :id")
    int deleteCustom(@Param("id") int id);

    List<Author> findByName(String name);

    @Query("SELECT a FROM Author a WHERE a.country = :country")
    List<Author> findByCountry(@Param("country") String country);

}
