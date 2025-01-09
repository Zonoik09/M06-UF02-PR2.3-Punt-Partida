package com.project.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.hibernate.*;
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

    public static Autor addAutor(String nom) {
        Session session = factory.openSession();
        Transaction tx = null;
        Autor autor = null;

        try {
            tx = session.beginTransaction();
            autor = new Autor();
            autor.setNom(nom);
            session.save(autor);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            System.err.println("Error afegint autor: " + e);
        } finally {
            session.close();
        }

        return autor;
    }

    public static Llibre addLlibre(String isbn, String titol, String editorial, int any) {
        Session session = factory.openSession();
        Transaction tx = null;
        Llibre llibre = null;

        try {
            tx = session.beginTransaction();
            llibre = new Llibre();
            llibre.setIsbn(isbn);
            llibre.setTitol(titol);
            llibre.setEditorial(editorial);
            llibre.setAnyPublicacio(any);
            session.save(llibre);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            System.err.println("Error afegint llibre: " + e);
        } finally {
            session.close();
        }

        return llibre;
    }

    public static void updateAutor(int autorId, String nom, Set<Llibre> llibres) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Autor autor = session.get(Autor.class, autorId);
            if (autor != null) {
                autor.setNom(nom);
                autor.setLlibres(llibres);
                session.update(autor);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            System.err.println("Error actualitzant autor: " + e);
        } finally {
            session.close();
        }
    }

    public static Collection<?> listCollection(Class<?> entityClass) {
        Session session = factory.openSession();
        List<?> result = null;

        try {
            Query<?> query = session.createQuery("from " + entityClass.getSimpleName());
            result = query.list();
        } catch (HibernateException e) {
            System.err.println("Error llistant entitats: " + e);
        } finally {
            session.close();
        }

        return result;
    }

    public static Biblioteca addBiblioteca(String nom, String ciutat, String adreça, String telefon, String email) {
        Session session = factory.openSession();
        Transaction tx = null;
        Biblioteca biblioteca = null;

        try {
            tx = session.beginTransaction();
            biblioteca = new Biblioteca();
            biblioteca.setNom(nom);
            biblioteca.setCiutat(ciutat);
            biblioteca.setAdreca(adreça);
            biblioteca.setTelefon(telefon);
            biblioteca.setEmail(email);
            session.save(biblioteca);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            System.err.println("Error afegint biblioteca: " + e);
        } finally {
            session.close();
        }

        return biblioteca;
    }

    public static Exemplar addExemplar(String codi, Llibre llibre, Biblioteca biblioteca) {
        Session session = factory.openSession();
        Transaction tx = null;
        Exemplar exemplar = null;

        try {
            tx = session.beginTransaction();
            exemplar = new Exemplar();
            exemplar.setCodiBarres(codi);
            exemplar.setLlibre(llibre);
            exemplar.setBiblioteca(biblioteca);
            session.save(exemplar);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            System.err.println("Error afegint exemplar: " + e);
        } finally {
            session.close();
        }

        return exemplar;
    }

    public static Persona addPersona(String dni, String nom, String telefon, String email) {
        Session session = factory.openSession();
        Transaction tx = null;
        Persona persona = null;

        try {
            tx = session.beginTransaction();
            persona = new Persona();
            persona.setDni(dni);
            persona.setNom(nom);
            persona.setTelefon(telefon);
            persona.setEmail(email);
            session.save(persona);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            System.err.println("Error afegint persona: " + e);
        } finally {
            session.close();
        }

        return persona;
    }

    public static Prestec addPrestec(Exemplar exemplar, Persona persona, LocalDate dataInici, LocalDate dataFi) {
        Session session = factory.openSession();
        Transaction tx = null;
        Prestec prestec = null;

        try {
            tx = session.beginTransaction();
            prestec = new Prestec();
            prestec.setExemplar(exemplar);
            prestec.setPersona(persona);
            prestec.setDataPrestec(dataInici);
            prestec.setDataRetornPrevista(dataFi);
            session.save(prestec);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            System.err.println("Error afegint préstec: " + e);
        } finally {
            session.close();
        }

        return prestec;
    }

    public static void registrarRetornPrestec(int prestecId, LocalDate dataRetorn) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Prestec prestec = session.get(Prestec.class, prestecId);
            if (prestec != null) {
                prestec.setDataRetornReal(dataRetorn);
                session.update(prestec);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            System.err.println("Error registrant el retorn del préstec: " + e);
        } finally {
            session.close();
        }
    }
    public static boolean collectionToString(Class<?> entityClass, Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            System.out.println("No hi ha elements a la col·lecció de: " + entityClass.getSimpleName());
            return false;
        }

        System.out.println(entityClass.getSimpleName() + " elements:");

        try {
            // Intentar inicializar la colección si es Lazy
            collection.forEach(item -> {
                // Aquí puedes asegurar que la colección está completamente inicializada
                Hibernate.initialize(item);
                System.out.println(item);
            });
            return true;
        } catch (LazyInitializationException e) {
            System.out.println("No s'han pogut inicialitzar les col·leccions: " + e.getMessage());
            return false;
        }
    }


    public static boolean formatMultipleResult(List<Object[]> results) {
        if (results == null || results.isEmpty()) {
            System.out.println("No hi ha resultats.");
            return false;
        }

        results.forEach(row -> {
            for (Object field : row) {
                System.out.print(field + " ");
            }
            System.out.println();
        });

        return true;
    }
    public static List<Llibre> findLlibresAmbAutors() {
        Session session = factory.openSession();
        List<Llibre> result = null;

        try {
            // Aquí usamos 'autors' ya que es el nombre correcto de la propiedad en Llibre
            Query<Llibre> query = session.createQuery(
                    "select distinct l from Llibre l left join fetch l.autors", // 'autors' es el nombre de la propiedad en Llibre
                    Llibre.class
            );
            result = query.list();
        } catch (HibernateException e) {
            System.err.println("Error executant consulta llibres amb autors: " + e);
        } finally {
            session.close();
        }

        return result;
    }


    public static List<Object[]> findLlibresEnPrestec() {
        Session session = factory.openSession();
        List<Object[]> result = null;

        try {
            Query<Object[]> query = session.createQuery(
                    "select l.titol, p.nom from Prestec pr " +
                            "join pr.exemplar e " +
                            "join e.llibre l " +
                            "join pr.persona p where pr.dataRetornReal is null", // Asegúrate de usar 'dataRetornReal'
                    Object[].class
            );
            result = query.list();
        } catch (HibernateException e) {
            System.err.println("Error executant consulta llibres en préstec: " + e);
        } finally {
            session.close();
        }

        return result;
    }


    public static List<Object[]> findLlibresAmbBiblioteques() {
        Session session = factory.openSession();
        List<Object[]> result = null;

        try {
            Query<Object[]> query = session.createQuery(
                    "select l.titol, b.nom from Exemplar e " +
                            "join e.llibre l " +
                            "join e.biblioteca b",
                    Object[].class
            );
            result = query.list();
        } catch (HibernateException e) {
            System.err.println("Error executant consulta llibres amb biblioteques: " + e);
        } finally {
            session.close();
        }

        return result;
    }


}
