/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Petra
 */
public class GenreTableModel extends AbstractTableModel{
    StringBuilder sb = new StringBuilder();
    private static final String[] COLUMN_NAME = {
        "Id",
        "Name"};

    private List<Genre> genres;

    public GenreTableModel(List<Genre> genres) {
        this.genres = genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
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
        return genres.size();
    }

    @Override
    public int getColumnCount() {
        return Genre.class.getDeclaredFields().length - 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return genres.get(rowIndex).getId();
            case 1:
                return genres.get(rowIndex).getName();
        }
        throw new RuntimeException("No such column.");
    }
}
