package main.Hospital;

import main.Hospital.Persons.Patient;

import java.util.ArrayList;

public class Room {
    private int counter=1;
    private int id;
    private String genderRoom;
    private ArrayList<Patient> patients=new ArrayList<>();
    private boolean isFull;

    public boolean isFull() {
        return patients.size()>3;
    }

    public Room() {
        id=counter++;
    }
    public int getId(){
        return id;
    }
    public String getGender() {
        return genderRoom;
    }
    public void addPatient(Patient patient){
        if (patients.size()==0){
            genderRoom=patient.getGender();
        }
        patients.add(patient);
        patient.setRoom(this);
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }
}
