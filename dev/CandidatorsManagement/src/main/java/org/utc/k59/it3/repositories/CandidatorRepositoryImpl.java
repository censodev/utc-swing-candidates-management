/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.utc.k59.it3.repositories;

import java.util.List;
import org.hibernate.Session;
import org.utc.k59.it3.models.Candidator;
import org.utc.k59.it3.utils.HibernateUtil;

/**
 *
 * @author JewCat
 */
public class CandidatorRepositoryImpl implements CandidatorRepository {

    @Override
    public List<Candidator> findAll() {
        List<Candidator> rs;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            rs = session.createQuery("FROM Candidator", Candidator.class).list();
            session.getTransaction().commit();
            return rs;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public Candidator find(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Candidator.class, id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public void save(Candidator candidator) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(candidator);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(Candidator candidator) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(candidator);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Candidator delete(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Candidator candidator = session.load(Candidator.class, id);
            session.delete(candidator);
            session.getTransaction().commit();
            return candidator;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public void save(List<Candidator> candidators) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            candidators.forEach(candidator -> session.save(candidator));
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(List<Candidator> candidators) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            candidators.forEach(candidator -> session.update(candidator));
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
