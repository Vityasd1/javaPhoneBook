package sample;

import javafx.beans.property.SimpleStringProperty;

public class Person {
    private final SimpleStringProperty lastname;
    private final SimpleStringProperty firstname;
    private final SimpleStringProperty email;

    public Person(SimpleStringProperty lastname, SimpleStringProperty firstname, SimpleStringProperty email) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
    }

    public Person(String lastname, String firstname, String email) {
        this.lastname = new SimpleStringProperty(lastname);
        this.firstname = new SimpleStringProperty(firstname);
        this.email = new SimpleStringProperty(email);
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getLastname() {
        return lastname.get();
    }

    public SimpleStringProperty lastnameProperty() {
        return lastname;
    }

    public String getFirstname() {
        return firstname.get();
    }

    public SimpleStringProperty firstnameProperty() {
        return firstname;
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }
}
