package com.dancoghlan.taskwebapp.service;

import java.util.List;
import java.util.Optional;

public interface PersitenceService<T> {

    T add(T entity);

    T update(T entity);

    void remove(long id);

    List<T> getAll();

    Optional<T> getById(long id);

}
