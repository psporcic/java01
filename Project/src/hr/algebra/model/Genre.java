/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.util.Set;
import java.util.TreeSet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author Petra
 */
@XmlAccessorType(XmlAccessType.FIELD)

public class Genre implements Comparable<Genre>{

    @XmlAttribute
    private int id;
    private String name; 
    private static final String DEL = ",";

    public Genre() {
    }

    public Genre(int id, String name) {
        this(name);
        this.id = id;
    }

    
    public Genre(String name) {
        this.name = name;
    }
    

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public static Set<Genre> getGenresFromData(String data) {

        Set genres = new TreeSet<>();

        String[] items = data.split(DEL);

        for (String item : items) {
            Genre currentGenre = new Genre();

            currentGenre.name = item.trim();

            genres.add(currentGenre);
        }

        return genres;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Genre g) {
        return this.name.compareTo(g.name);
    }
    
}
