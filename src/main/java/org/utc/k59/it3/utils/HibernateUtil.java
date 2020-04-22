/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.utc.k59.it3.utils;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author JewCat
 */
public class HibernateUtil {
    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // load from different directory
            Properties properties = new Properties();
            properties.load(new FileReader("etc/application.properties"));

            SessionFactory sessionFactory = new Configuration()
                    .configure()
                    .setProperty("hibernate.connection.url", properties.getProperty("hibernate.connection.url"))
                    .setProperty("hibernate.connection.username", properties.getProperty("hibernate.connection.username"))
                    .setProperty("hibernate.connection.password", properties.getProperty("hibernate.connection.password"))
                    .buildSessionFactory();

            return sessionFactory;

        } catch (HibernateException | FileNotFoundException ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}
