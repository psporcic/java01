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
public class PersonTableModel extends AbstractTableModel {

    StringBuilder sb = new StringBuilder();
    private static final String[] COLUMN_NAME = {
        "Id",
        "First name",
        "Last name"};

    private List<Person> people;

    public PersonTableModel(List<Person> people) {
        this.people = people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
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
        return people.size();
    }

    @Override
    public int getColumnCount() {
        return Person.class.getDeclaredFields().length - 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return people.get(rowIndex).getId();
            case 1:
                return people.get(rowIndex).getFirstName();
            case 2:
                return people.get(rowIndex).getLastName();
        }
        throw new RuntimeException("No such column.");
    }
}
