/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Petra
 */
public class MovieTableModel extends AbstractTableModel {

    StringBuilder sb = new StringBuilder();
    private static final String[] COLUMN_NAME = {
        "Id",
        "Title",
        "Published date",
        "Description",
        "Original title",
        "Directors",
        "Actors",
        "Lenght",
        "Genres",
        "Picture path",
        "Link",
        "Release date"};

    private List<Movie> movies;

    public MovieTableModel(List<Movie> movies) {
        this.movies = movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        fireTableDataChanged(); //refresha se tablica
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAME[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class; //kad se renderira prva kolona riječ je o integeru
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return Movie.class.getDeclaredFields().length - 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return movies.get(rowIndex).getId();
            case 1:
                return movies.get(rowIndex).getTitle();
            case 2:
                return movies.get(rowIndex).getPublishedDate().format(Movie.DATE_FORMAT);
            case 3:
                return movies.get(rowIndex).getDescription();
            case 4:
                return movies.get(rowIndex).getOriginalTitle();
            case 5:
                sb.setLength(0);
                Iterator<Person> iteratorDirector = movies.get(rowIndex).getDirectors().iterator();
                while (iteratorDirector.hasNext()) {
                    sb.append(iteratorDirector.next());
                    if (iteratorDirector.hasNext()) {
                        sb.append(", ");
                    }
                }
                return sb.toString();
            case 6:
                sb.setLength(0);
                Iterator<Person> iteratorActor = movies.get(rowIndex).getActors().iterator();
                while (iteratorActor.hasNext()) {
                    sb.append(iteratorActor.next());
                    if (iteratorActor.hasNext()) {
                        sb.append(", ");
                    }
                }
                return sb.toString();
            case 7:
                return movies.get(rowIndex).getLength();
            case 8:
                sb.setLength(0);
                Iterator<Genre> iteratorGenre = movies.get(rowIndex).getGenres().iterator();
                while (iteratorGenre.hasNext()) {
                    sb.append(iteratorGenre.next());

                    if (iteratorGenre.hasNext()) {
                        sb.append(", ");
                    }
                }
                return sb.toString();
            case 9:
                return movies.get(rowIndex).getPicturePath();
            case 10:
                return movies.get(rowIndex).getLink();
            case 11:
                return movies.get(rowIndex).getReleaseDate();
        }
        throw new RuntimeException("No such column.");
    }
}
