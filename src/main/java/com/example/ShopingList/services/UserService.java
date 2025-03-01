package com.example.ShopingList.services;


import com.example.ShopingList.entities.Role;
import com.example.ShopingList.entities.User;
import com.example.ShopingList.repos.RoleRepository;
import com.example.ShopingList.repos.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;

    public UserService(UserRepository userRepository,RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;

    }

    public User saveOneUser(User newUser) {
        return userRepository.save(newUser);
    }

    public User getOneUserbyId(Long userId) {
        return userRepository.findById(userId).orElse(null);

    }

    public User updateOneUser(long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            User foundUser = user.get();
            foundUser.setName(newUser.getName());
            foundUser.setPassword(newUser.getPassword());
            return userRepository.save(foundUser);
        }else{
            return null;
        }
    }
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
    public void assignRoleToUser(Long userId, String roleName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(role);
        userRepository.save(user);
    }
}
