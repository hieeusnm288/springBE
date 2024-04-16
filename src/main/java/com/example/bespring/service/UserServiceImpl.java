package com.example.bespring.service;

import com.example.bespring.domain.Account;
import com.example.bespring.domain.Role;
import com.example.bespring.repository.AcountRepository;
import com.example.bespring.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private AcountRepository acountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(AcountRepository acountRepository , RoleRepository roleRepository){
        this.acountRepository = acountRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Account findByUsername(String username) {
        return acountRepository.findByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = findByUsername(username);
        if (account == null){
            throw new UsernameNotFoundException("Username không tồn tại");
        }
        String username1 = account.getUsername();
        String password = account.getPassword();

        if (username == null || password == null) {
            throw new IllegalStateException("Tài khoản không có thông tin đăng nhập hợp lệ");
        }

        Role role = account.getRole();
        if (role == null) {
            throw new IllegalStateException("Tài khoản không có vai trò nào được gán");
        }

        String roleName = role.getRoleName();
        if (roleName == null) {
            throw new IllegalStateException("Tên vai trò không thể là null");
        }

        return new User(username, password, Collections.singletonList(new SimpleGrantedAuthority(roleName)));
    }

//    private Collection<? extends GrantedAuthority> roleToAuthorities(Collection<Role> roles){
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
//    }
private Collection<? extends GrantedAuthority> roleToAuthorities(Collection<Role> roles) {
    if (roles == null || roles.isEmpty()) {
        throw new IllegalStateException("Tài khoản không có vai trò nào được gán");
    }
    Role role = roles.iterator().next(); // Lấy vai trò đầu tiên
    String roleName = role.getRoleName();
    Objects.requireNonNull(roleName, "Tên vai trò không thể là null");
    return Collections.singletonList(new SimpleGrantedAuthority(roleName));
}
}
