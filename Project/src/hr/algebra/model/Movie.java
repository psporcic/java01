/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Petra
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "title", "publishedDate", "description", "originalTitle",
    "directors", "actors", "length", "genres", "picturePath", "link", "releaseDate"})
public class Movie {

    public static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @XmlAttribute
    private int id;
    
    private String title;
    
    @XmlElement(name = "publisheddate")
    @XmlJavaTypeAdapter(PublishedDateAdapter.class)
    private LocalDateTime publishedDate;
    
    private String description;
    
    @XmlElement(name = "originaltitle")
    private String originalTitle;
    
    @XmlElementWrapper
    @XmlElement(name = "director")
    private Set<Person> directors;
    
    @XmlElementWrapper
    @XmlElement(name = "actor")
    private Set<Person> actors;
    
    private int length;
    
    @XmlElementWrapper
    @XmlElement(name = "genre")
    private Set<Genre> genres;
    
    @XmlElement(name = "picturepath")
    private String picturePath;
    
    private String link;
    
    @XmlElement(name = "releasedate")
    private String releaseDate;

    public Movie() {
    }

    public Movie(int id, String title, LocalDateTime publishedDate, String description, String originalTitle, Set<Person> directors, Set<Person> actors, int length, Set<Genre> genres, String picturePath, String link, String releaseDate) {
        this(title, publishedDate, description, originalTitle, directors, actors, length, genres, picturePath, link, releaseDate);
        this.id = id;
    }

    public Movie(String title, LocalDateTime publishedDate, String description, String originalTitle, Set<Person> directors, Set<Person> actors, int length, Set<Genre> genres, String picturePath, String link, String releaseDate) {
        this.title = title;
        this.publishedDate = publishedDate;
        this.description = description;
        this.originalTitle = originalTitle;
        this.directors = directors;
        this.actors = actors;
        this.length = length;
        this.genres = genres;
        this.picturePath = picturePath;
        this.link = link;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Set<Person> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<Person> directors) {
        this.directors = directors;
    }

    public Set<Person> getActors() {
        return actors;
    }

    public void setActors(Set<Person> actors) {
        this.actors = actors;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return id + " - " + title;
    }

}
