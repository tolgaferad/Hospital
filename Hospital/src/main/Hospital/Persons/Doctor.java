package main.Hospital.Persons;

import main.Hospital.Hospital;
import main.Hospital.TypeSpecializatonDepartment;
import util.Randomizator;

import java.util.ArrayList;
import java.util.Collection;

public class Doctor extends Person implements Runnable{
    private TypeSpecializatonDepartment specialization;
    private boolean isAvailiable=true;
    private ArrayList<Patient> patients=new ArrayList<>();
    private Hospital hospital;

    public Doctor(String name, String phoneNumber, TypeSpecializatonDepartment specialization,Hospital hospital) {
        super(name, phoneNumber);
        this.specialization=specialization;
        this.hospital=hospital;

    }
    public void addPatient(Patient patient){
        patients.add(patient);
    }
    public void setAvailiable(boolean availiable) {
        this.isAvailiable = availiable;
    }
    public boolean isAvailiable() {
        return isAvailiable;
    }
    public TypeSpecializatonDepartment getSpecialization() {
        return specialization;
    }
    public String giveDiagnose(){
        if (specialization==specialization.ORTOPEDIQ){
            return specialization.getOrtopedicDiagnosis();
        }
        else if(specialization==specialization.VIRUSULOGIQ){
            return specialization.getVirusDiagnosis();
        }
        else{
            return specialization.getKardioDiagnosis();
        }
    }
    public String giveMedicine(){
        if (specialization==specialization.ORTOPEDIQ){
            return specialization.getOrtopedicMedicine();
        }
        else if(specialization==specialization.VIRUSULOGIQ)
        {
            return specialization.getVirusMedicine();
        }
        else{
            return specialization.getKardioMedicine();
        }
    }
    public int treatmentDuration(){
        return Randomizator.randomFromTo(3,5);
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    @Override
    public void run() {
        while(true){
            hospital.doctorVizitation(this);
        }
    }
}
