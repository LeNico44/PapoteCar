package org.ln.autopartagedata.dal;

import org.ln.autopartagedata.domaine.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

@RepositoryRestResource(path="users-list")
@Component("user_dao")
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(@Param("email") String email);

    User getUserById(@Param("id") Long id);
}
