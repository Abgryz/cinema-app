package com.suitt.tables.client;

import com.suitt.security.user.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
//    void update(Client client);
    @Query(value = """
            INSERT INTO public.client(
                email, password)
                VALUES (:email, :password);
                """, nativeQuery = true)
    int register(@Param("email") String email, @Param("password") String password);
}
