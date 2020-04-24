/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.utc.k59.it3.repositories;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.utc.k59.it3.models.Province;
import org.utc.k59.it3.utils.HibernateUtil;

/**
 *
 * @author JewCat
 */
public class ProvinceRepositoryImpl extends CrudRepositoryImpl<Province> implements ProvinceRepository {
    private static ProvinceRepositoryImpl instance;

    private ProvinceRepositoryImpl(Class<Province> clazz) {
        super(clazz);
    }

    public static ProvinceRepositoryImpl getInstance() {
        return instance == null ? new ProvinceRepositoryImpl(Province.class) : instance;
    }

    @Override
    public Province findByName(String provinceName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Province p WHERE p.name = :provinceName", Province.class).setParameter("provinceName", provinceName).list().get(0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
