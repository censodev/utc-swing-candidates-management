package org.utc.k59.it3.repositories;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.utc.k59.it3.utils.HibernateUtil;

import java.util.List;

public abstract class CrudRepositoryImpl<E> implements CrudRepository<E> {
    private Class<E> clazz;

    CrudRepositoryImpl(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<E> findAll() {
        List<E> rs;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            rs = session.createQuery("FROM ".concat(clazz.getName()), clazz).list();
            session.getTransaction().commit();
            return rs;
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public E find(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(clazz, id);
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public Object save(E entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Object identifier = session.save(entity);
            session.getTransaction().commit();
            return identifier;
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public void save(List<E> entities) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            entities.forEach(session::save);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(E entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(List<E> entities) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            entities.forEach(session::update);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(E entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(List<E> entities) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            entities.forEach(session::delete);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
