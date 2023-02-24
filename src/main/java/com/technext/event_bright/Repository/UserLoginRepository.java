package com.technext.event_bright.Repository;

import com.technext.event_bright.Entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
    @Query("select ul from UserLogin ul where ul.loginId=?1")
    UserLogin findByLoginId(String loginId);
}
