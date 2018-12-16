package org.ln.autopartagedata.dal;

import org.ln.autopartagedata.domaine.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

@RepositoryRestResource(path="comments-list")
@Component("comment_dao")
public interface CommentRepository extends CrudRepository<Comment, Long> {
}
