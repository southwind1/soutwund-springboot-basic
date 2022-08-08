package com.project.basic.service;

import com.project.basic.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User getUser(Long id);

    List<User> getAll();

    User getUserByName(String name);

    User getUser(Long id, String name);

    public User getByUsername(String username);

    public Set<String> getRoles(String username) ;

    public Set<String> getPermissions(String username);

}
