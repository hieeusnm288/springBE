package com.example.bespring.repository;

import com.example.bespring.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
//    public Role findbyId(Long id);
    public Role findByRoleName(String roleName);
}
