package com.example.movierest;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "movie", schema = "movie", catalog = "")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String genre;
    private double rate;
    private String description;
    private int rateNum;

    public Movie() {
        this.id = 0;
        this.title = "";
        this.genre = "";
        //maybe
        this.rate = 0.0;
        this.description = "";
        this.rateNum = 0;
    }

    public Movie(int id, String title, String genre, double rate, String description, int rateNum) {
        this.title = title;
        this.title = title;
        this.genre = genre;
        this.rate = rate;
        this.description = description;
        this.rateNum = rateNum;
    }

    @Basic
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "genre")
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Basic
    @Column(name = "rate")
    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "rateNum")
    public int getRateNum() {
        return rateNum;
    }

    public void setRateNum(int rateNum) {
        this.rateNum = rateNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
                Double.compare(movie.rate, rate) == 0 &&
                rateNum == movie.rateNum &&
                Objects.equals(title, movie.title) &&
                Objects.equals(genre, movie.genre) &&
                Objects.equals(description, movie.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, genre, rate, description, rateNum);
    }

    @Override
    public String toString() {
        return "Movie " + id + ": [ " + '\n' +
                "Title = " + title + '\n' +
                "Genre = " + genre + '\n' +
                "Rate = " + rate + '\n' +
                "Description = " + description + '\n' +
                "RateNum = " + rateNum + " ]" + '\n';
    }
}
