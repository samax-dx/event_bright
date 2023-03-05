package com.technext.event_bright.svc_auth.repository;

import com.technext.event_bright.svc_auth.entity.LoginBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoginBaseRepository extends JpaRepository<LoginBase, String> {
}
