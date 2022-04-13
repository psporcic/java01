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
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Petra
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Comparable<Person> {

    @XmlAttribute
    private int id;
    @XmlElement(name = "firstname")
    private String firstName;
    @XmlElement(name = "lastname")
    private String lastName;

    private static final String DEL = ",";

    public Person() {
    }

    public Person(int id, String firstName, String lastName) {
        this(firstName, lastName);
        this.id = id;
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static Set<Person> getPeopleFromData(String data) {

        Set people = new TreeSet<>();

        String[] items = data.split(DEL);

        for (String item : items) {
            Person currentPerson = new Person();

            String[] splitName = item.trim().split(" ");

            currentPerson.firstName = splitName[0].trim();

            StringBuilder lastNameBuilder = new StringBuilder();

            for (int i = 1; i < splitName.length; i++) {
                lastNameBuilder.append(splitName[i]);
                lastNameBuilder.append(" ");
            }

            currentPerson.lastName = lastNameBuilder.toString().trim();
            people.add(currentPerson);
        }
        return people;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public int compareTo(Person p) {
        return this.firstName.compareTo(p.firstName);
    }

}
