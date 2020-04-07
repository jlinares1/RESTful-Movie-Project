package com.example.movierest;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class MovieDAO {
    @PersistenceContext
    private EntityManager entMan;

    public Movie create(Movie movie) {
        entMan.persist(movie);
        entMan.flush();
        entMan.clear();
        return movie;
    }

    public List<Movie> getAll() {
        List<Movie> movies = entMan.createQuery("select m from Movie m", Movie.class).getResultList();
        return movies;
    }

    public Movie getById(int id) {
        return entMan.find(Movie.class, id);
    }

    public void updateMovie(Movie movie) {
        entMan.merge(movie);
    }

    public void deleteById(int id) {
        Movie movie = getById(id);
        entMan.remove(movie);
    }
}
