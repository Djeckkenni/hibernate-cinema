package com.dev.cinema.dao;

import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.Movie;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MovieDaoImpl extends GenericDaoImpl<Movie> implements MovieDao {
    private final SessionFactory factory;

    @Autowired
    public MovieDaoImpl(SessionFactory factory) {
        super(factory);
        this.factory = factory;
    }

    @Override
    public Movie add(Movie movie) {
        movie = super.add(movie);
        return movie;
    }

    @Override
    public List<Movie> getAll() {
        try (Session session = factory.openSession()) {
            CriteriaQuery<Movie> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(Movie.class);
            criteriaQuery.from(Movie.class);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get list of all movies ", e);
        }
    }

    @Override
    public Movie getById(Long movieId) {
        return super.getById(movieId, Movie.class);
    }
}
