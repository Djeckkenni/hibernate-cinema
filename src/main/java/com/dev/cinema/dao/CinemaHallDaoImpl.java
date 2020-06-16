package com.dev.cinema.dao;

import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.CinemaHall;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CinemaHallDaoImpl implements CinemaHallDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public CinemaHallDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Long cinemaHallId = (Long) session.save(cinemaHall);
            transaction.commit();
            cinemaHall.setId(cinemaHallId);
            return cinemaHall;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert cinema hall entity", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            CriteriaQuery<CinemaHall> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(CinemaHall.class);
            criteriaQuery.from(CinemaHall.class);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Error retrieving all cinema halls", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public CinemaHall getById(Long cinemaHallId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(CinemaHall.class, cinemaHallId);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get cinema hall with id = " + cinemaHallId, e);
        }
    }
}
