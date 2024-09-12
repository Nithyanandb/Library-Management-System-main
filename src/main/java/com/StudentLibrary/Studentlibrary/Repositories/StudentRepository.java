package com.StudentLibrary.Studentlibrary.Repositories;

import com.StudentLibrary.Studentlibrary.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import java.util.List;

    @Transactional
    public interface StudentRepository extends JpaRepository<Student, Integer> {

        @Modifying
        @Query("update Student s set s.emailId = :newEmail where s.emailId = :oldEmail")
        int updateStudentEmail(@Param("oldEmail") String oldEmail, @Param("newEmail") String newEmail);

        @Modifying
        @Query("delete from Student s where s.id = :id")
        int deleteCustom(@Param("id") int id);

        @Modifying
        @Query("update Student s set s.emailId = :emailId, s.name = :name, s.age = :age, s.country = :country where s.id = :id")
        int updateStudentDetails(@Param("id") int id, @Param("emailId") String emailId, @Param("name") String name, @Param("age") int age, @Param("country") String country);

        @Query("select s from Student s where s.emailId = :mail")
        List<Student> find_by_mail(@Param("mail") String mail);

        @Query(value = "select * from student s where s.email_id = :mail", nativeQuery = true)
        List<Student> findbymail(@Param("mail") String mail);


    }



//JPQL-->Java persistence Query language-->(Objects and Attributes)
//Native sql query-->(columns and tables)



//find student by given name
//Terminal---> Select * from student where email =email
//1. JPQL--Dealing with java objects
//Student(exact class name) class has the variable name as emailId so b.emailId
// :mail has to passed in the argument of the function exact variable name as in the args


//Native sql query -- dealing with sql tables
//SQL table formed with name student not Student
//if the variable name is emailId then parameter used in query is email_id
//Hibernate converts camel case to _ separated names
//Think of it as a sql table


