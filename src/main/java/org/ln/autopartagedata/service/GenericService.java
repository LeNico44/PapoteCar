package org.ln.autopartagedata.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericService<T extends CrudRepository<E, Long>,E> {

    protected T repository;

    //TODO les méthodes pass-plat
    public List<E> findAll(){
        return (List<E>) repository.findAll();
    }

    public T getRepository() {
        return repository;
    }

    public void setRepository(T repository) {
        this.repository=repository;
    }
}
