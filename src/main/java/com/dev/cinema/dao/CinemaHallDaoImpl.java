package com.dev.cinema.dao;

import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.util.HibernateUtil;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
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
            session = HibernateUtil.getSessionFactory().openSession();
            CriteriaQuery<CinemaHall> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(CinemaHall.class);
            criteriaQuery.from(CinemaHall.class);
            List<CinemaHall> resultList = session.createQuery(criteriaQuery).getResultList();
            return resultList;
        } catch (Exception e) {
            throw new DataProcessingException("Error retrieving all cinema halls", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
