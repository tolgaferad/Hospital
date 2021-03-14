package main.Hospital.Persons;

import main.Hospital.Hospital;
import main.Hospital.Persons.Person;
import main.Hospital.TypeSpecializatonDepartment;

public class Nurse extends Person implements Runnable{

    private int age;
    private TypeSpecializatonDepartment department;
    private Hospital hospital;

    public Nurse(String name,String phoneNumber,int age,TypeSpecializatonDepartment department,Hospital hospital) {
        super(name,phoneNumber);
        this.age=age;
        this.department=department;
        this.hospital=hospital;
    }

    public TypeSpecializatonDepartment getDepartment() {
        return department;
    }

    @Override
    public void run() {
        while(true) {
            hospital.nurseJob(this);
        }
    }
}
