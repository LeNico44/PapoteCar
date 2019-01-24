package org.ln.autopartagedata.service;

import org.ln.autopartagedata.domaine.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    User findByEmail(String email);
    Optional<User> getUserById(Long id);
}
