package com.ineat.sample.quickadapter;

/**
 * Created by mslimani on 08/10/2016.
 */
public final class Ineatien {

    private final String firstName;
    private final String lastName;
    private final boolean isNew;

    public Ineatien(String firstName, String lastName) {
        this(firstName, lastName, false);
    }

    public Ineatien(String firstName, String lastName, boolean isNew) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isNew = isNew;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isNew() {
        return isNew;
    }

    @Override
    public String toString() {
        return "Ineatien{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isNew=" + isNew +
                '}';
    }
}
