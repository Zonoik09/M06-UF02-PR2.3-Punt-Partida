package com.project.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.project.domain.*;

public class Manager {
    private static SessionFactory factory;

    /**
     * Crea la SessionFactory per defecte
     */
    public static void createSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            
            // Registrem totes les classes que tenen anotacions JPA
            configuration.addAnnotatedClass(Biblioteca.class);
            configuration.addAnnotatedClass(Llibre.class);
            configuration.addAnnotatedClass(Exemplar.class);
            configuration.addAnnotatedClass(Prestec.class);
            configuration.addAnnotatedClass(Persona.class);
            configuration.addAnnotatedClass(Autor.class);

            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
                
            factory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("No s'ha pogut crear la SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Crea la SessionFactory amb un fitxer de propietats específic
     */
    public static void createSessionFactory(String propertiesFileName) {
        try {
            Configuration configuration = new Configuration();
            
            configuration.addAnnotatedClass(Biblioteca.class);
            configuration.addAnnotatedClass(Llibre.class);
            configuration.addAnnotatedClass(Exemplar.class);
            configuration.addAnnotatedClass(Prestec.class);
            configuration.addAnnotatedClass(Persona.class);
            configuration.addAnnotatedClass(Autor.class);

            Properties properties = new Properties();
            try (InputStream input = Manager.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
                if (input == null) {
                    throw new IOException("No s'ha trobat " + propertiesFileName);
                }
                properties.load(input);
            }

            configuration.addProperties(properties);

            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
                
            factory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Error creant la SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Tanca la SessionFactory
     */
    public static void close() {
        if (factory != null) {
            factory.close();
        }
    }

    public static Autor addAutor(String gabrielGarcíaMárquez) {
        return null;
    }
}
