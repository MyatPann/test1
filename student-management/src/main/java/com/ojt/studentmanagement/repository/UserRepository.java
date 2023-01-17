package com.ojt.studentmanagement.repository;




import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import com.ojt.studentmanagement.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);
    
    //search
     @Query(value = "select * from users x where x.email like %:keyword% or x.name like %:keyword%", nativeQuery = true)
     List<User> findByKeyword(@Param("keyword") String keyword);
     
     User findByResetPasswordToken(String token);

}