package org.ln.autopartagedata.service;

import org.ln.autopartagedata.dal.UserRepository;
import org.ln.autopartagedata.domaine.User;

public class UserService extends GenericService <UserRepository, User> {

    public User findByEmail(String email){
        return repository.findByEmail(email);
    }

    public int getUserBirthYear(User user){
        //TODO recupération de la date et extraction de l'année de naissance
        return 70;
    }
}
