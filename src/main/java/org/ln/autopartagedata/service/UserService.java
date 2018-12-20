package org.ln.autopartagedata.service;

import org.ln.autopartagedata.domaine.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findByEmail(String email);
    User getUserById(Long id);
}
