package org.utc.k59.it3.repositories;

import java.util.List;

public interface CrudRepository<E> {
    List<E> findAll();

    E find(Integer id);

    Object save(E entity);

    void save(List<E> entities);

    void update(E entity);

    void update(List<E> entities);

    void delete(E entity);

    void delete(List<E> entities);
}
