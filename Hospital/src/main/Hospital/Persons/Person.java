package main.Hospital.Persons;

public abstract class Person {
    protected String name;
    protected String phoneNumber;

    public Person(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    public  String getName(){
        return name;
    }
}
