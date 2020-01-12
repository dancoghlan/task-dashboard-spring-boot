package com.dancoghlan.taskwebapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Transactional(propagation = Propagation.REQUIRED)
public abstract class AbstractPersistenceService<T> implements PersitenceService<T> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractPersistenceService.class);
    private final JpaRepository<T, Long> jpaRepository;

    public AbstractPersistenceService(JpaRepository<T, Long> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public T add(T entity) {
        T addedTask = jpaRepository.save(entity);
        logger.info("Saved entity [{}]", entity);
        return addedTask;
    }

    @Override
    public T update(T entity) {
        T updatedEntity = jpaRepository.save(entity);
        logger.info("Updated entity [{}]", updatedEntity);
        return updatedEntity;
    }

    @Override
    public void remove(long id) {
        jpaRepository.deleteById(id);
        logger.info("Removed entity with id [{}]", id);
    }

    @Override
    public List<T> getAll() {
        List<T> entities = jpaRepository.findAll();
        if (!CollectionUtils.isEmpty(entities)) {
            logger.info("Retrieved [{}] entities", entities.size());
        }
        return entities;
    }

    @Override
    public Optional<T> getById(long id) {
        logger.info("Retrieving entity with id [{}]", id);
        return jpaRepository.findById(id);
    }

}
