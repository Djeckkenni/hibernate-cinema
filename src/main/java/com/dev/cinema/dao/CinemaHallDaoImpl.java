package com.dev.cinema.dao;

import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.CinemaHall;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CinemaHallDaoImpl extends GenericDaoImpl<CinemaHall> implements CinemaHallDao {
    private final SessionFactory factory;

    @Autowired
    public CinemaHallDaoImpl(SessionFactory factory) {
        super(factory);
        this.factory = factory;
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        cinemaHall = super.add(cinemaHall);
        return cinemaHall;
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = factory.openSession()) {
            CriteriaQuery<CinemaHall> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(CinemaHall.class);
            criteriaQuery.from(CinemaHall.class);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get list of all cinema halls ", e);
        }
    }

    @Override
    public CinemaHall getById(Long hallId) {
        return super.getById(hallId, CinemaHall.class);
    }
}
