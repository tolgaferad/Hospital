package main;

import main.Hospital.Hospital;
import main.Hospital.Persons.Doctor;
import main.Hospital.Persons.Patient;

public class Demo {
    public static void main(String[] args) {
        Hospital hospital=Hospital.getInstance();
        Thread patient1=new Thread(new Patient("Georgi","0874751946",33,"M",hospital));
        Thread patient2=new Thread(new Patient("Belliza","0889957880",48,"F",hospital));
        Thread patient3=new Thread(new Patient("Slavi","0888877766",55,"M",hospital));
        Thread patient4=new Thread(new Patient("Sisi","0899977766",55,"F",hospital));
        Thread patient5=new Thread(new Patient("Petko","0888577766",55,"M",hospital));
        patient1.start();
        patient2.start();
        patient3.start();
        patient4.start();
        patient5.start();
        hospital.getScheduler().start();
        for (int i = 0; i <hospital.getDoctors().size() ; i++) {
            Thread doctor=new Thread(hospital.getDoctors().get(i));
            doctor.start();
        }
        for (int i = 0; i <hospital.getNurses().size() ; i++) {
            Thread nurse=new Thread(hospital.getNurses().get(i));
            nurse.start();
        }
        for (int i = 0; i <10 ; i++) {
            System.out.println("Day "+i);
            hospital.incrementDays();
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hospital.resetingTreatments();
            hospital.resetingVisitations();
        }
    }

}
