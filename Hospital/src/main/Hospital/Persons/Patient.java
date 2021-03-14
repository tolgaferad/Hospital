package main.Hospital.Persons;

import main.Hospital.Hospital;
import main.Hospital.Persons.Doctor;
import main.Hospital.Persons.Person;
import main.Hospital.Room;

public class Patient extends Person implements Runnable {

  private int age;
  private Doctor doctor;
  private String diagnose;
  private String treatment;
  private int treatMentDuration;
  private String gender;
  private Room room;
  private Hospital hospital;
  private boolean isTreated=false;
  private boolean isVisited=false;

  public Patient(String name, String phoneNumber, int age,String gender,Hospital hospital) {
  super(name, phoneNumber);
  this.age=age;
  this.gender=gender;
  this.hospital=hospital;
 }

    public boolean isTreated() {
        return isTreated;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public int getTreatMentDuration() {
        return treatMentDuration;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
    public void setTreatMentDuration(int duration){
      this.treatMentDuration=duration;
    }

    public String getGender() {
      return gender;
    }

    @Override
    public void run() {
          hospital.patientSigning(this);

    }

    public void setVisited(boolean isVisited) {
      this.isVisited=isVisited;
    }

    public void setIsTreated(boolean isTreated) {
      this.isTreated=isTreated;
    }
}
