package com.main.model;

import java.util.List;
import java.util.Optional;

public interface DataAccessObject<T> {
    Optional<T> get (int id);
    List<T> getAll ();

    void save(T t);
    void delete (T t);
    void update (T t);
    void update (T t, String[] params);
    void assignEntity (T entity, String[] params);
}
