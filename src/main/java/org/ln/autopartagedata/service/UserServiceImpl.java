package org.ln.autopartagedata.service;

import org.ln.autopartagedata.dal.UserRepository;
import org.ln.autopartagedata.domaine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends GenericService <UserRepository, User> implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserById(Long id) {
        return this.userRepository.getUserById(id);
    }

}
