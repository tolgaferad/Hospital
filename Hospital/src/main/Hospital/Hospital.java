package main.Hospital;

import main.Hospital.Persons.Doctor;
import main.Hospital.Persons.Nurse;
import main.Hospital.Persons.Patient;
import util.Randomizator;

import java.util.ArrayList;
import java.util.HashMap;

public class Hospital {
    private final ArrayList<Doctor> doctors=new ArrayList<>();
    private final ArrayList<Nurse> nurses=new ArrayList<>();
    private final HashMap<TypeSpecializatonDepartment,ArrayList<Room>> rooms=new HashMap<>();
    private final Scheduler scheduler=new Scheduler(this);
    private int days;
    private static Hospital instance;
    
    private Hospital(){
        this.days=0;
        addDoctors();
        addNurses();
        rooms.put(TypeSpecializatonDepartment.ORTOPEDIQ,new ArrayList<>());
        rooms.put(TypeSpecializatonDepartment.KARDIOLOGIQ,new ArrayList<>());
        rooms.put(TypeSpecializatonDepartment.VIRUSULOGIQ,new ArrayList<>());
        for (int i = 0; i <10 ; i++) {
            rooms.get(TypeSpecializatonDepartment.ORTOPEDIQ).add(new Room());
        }
        for (int i = 0; i <10 ; i++) {
            rooms.get(TypeSpecializatonDepartment.VIRUSULOGIQ).add(new Room());

        }
        for (int i = 0; i <10 ; i++) {
            rooms.get(TypeSpecializatonDepartment.KARDIOLOGIQ).add(new Room());
        }
    }
    public static Hospital getInstance(){
        if (instance==null){
            instance=new Hospital();
        }
        return instance;
    }
    public void addDoctors(){
        doctors.add(new Doctor(Randomizator.getRandomName(),"088877564",
                TypeSpecializatonDepartment.VIRUSULOGIQ,this));
        doctors.add(new Doctor(Randomizator.getRandomName(),"54498",
                TypeSpecializatonDepartment.VIRUSULOGIQ,this));
        doctors.add(new Doctor(Randomizator.getRandomName(),"088877564",
                TypeSpecializatonDepartment.ORTOPEDIQ,this));
        doctors.add(new Doctor(Randomizator.getRandomName(),"54498",
                TypeSpecializatonDepartment.ORTOPEDIQ,this));
        doctors.add(new Doctor(Randomizator.getRandomName(),"088877564",
                TypeSpecializatonDepartment.KARDIOLOGIQ,this));
        doctors.add(new Doctor(Randomizator.getRandomName(),"54498",
                TypeSpecializatonDepartment.KARDIOLOGIQ,this));
    }
    public void addNurses(){
        nurses.add(new Nurse("Imren","087945615",22,TypeSpecializatonDepartment.VIRUSULOGIQ,this));
        nurses.add(new Nurse("Ebru","087945615",22,TypeSpecializatonDepartment.VIRUSULOGIQ,this));
        nurses.add(new Nurse("Moni","087945615",22,TypeSpecializatonDepartment.KARDIOLOGIQ,this));
        nurses.add(new Nurse("Pamela","087945615",22,TypeSpecializatonDepartment.KARDIOLOGIQ,this));
        nurses.add(new Nurse("Nikoleta","087945615",22,TypeSpecializatonDepartment.ORTOPEDIQ,this));
        nurses.add(new Nurse("Pisi","087945615",22,TypeSpecializatonDepartment.ORTOPEDIQ,this));
    }
    public synchronized void patientSigning(Patient patient){
        while(!hasAvailiableDoctors()){
            try {
                wait();
                System.out.println("Човекът "+patient.getName()+" чака, защото няма свободни лекари");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setDoctorToPatient(patient);
        while (!hasAvailiableBedsInDepartment(patient.getDoctor().getSpecialization(),patient)){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Room room=getAvailiableRoom(patient.getDoctor().getSpecialization(),patient);
        System.out.println("Пациент "+patient.getName()+" от пол: "+patient.getGender()+" е приет с диагноза "+patient.getDiagnose()+
                ".Лекуващ лекар: д-р "+patient.getDoctor().getName ());
        room.addPatient(patient);
        notifyAll();

    }
    public synchronized void nurseJob(Nurse nurse){
        while(!hasPatientInDepartment(nurse.getDepartment())){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i <rooms.get(nurse.getDepartment()).size(); i++) {
            Room room=rooms.get(nurse.getDepartment()).get(i);
            for (int j = 0; j <room.getPatients().size() ; j++) {
                if (room.getPatients().get(j).isTreated()){
                    continue;
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Сестра "+nurse.getName()+" даде на пациент "+room.getPatients().get(j).getName()+" в стая "+i+
                                " от отделение "+ nurse.getDepartment()+" лекарство");
                room.getPatients().get(j).setIsTreated(true);
            }
        }
    }
    public synchronized void doctorVizitation(Doctor doctor) {
        while(!hasPatients()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i <doctor.getPatients().size() ; i++) {
            Patient patient=doctor.getPatients().get(i);
            if (patient.isVisited()){
                continue;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Лекар "+doctor.getName()+" посети пациент "+patient.getName()+" в стая "
                            +patient.getRoom().getId()+
                    " от отделение "+ doctor.getSpecialization()
            );
            patient.setVisited(true);
        }
    }
    public synchronized void scheduling(Scheduler scheduler){
        while (!hasPatients()&&days<4){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (TypeSpecializatonDepartment key:rooms.keySet()) {
            for (int i = 0; i <rooms.get(key).size() ; i++) {
                Room room=rooms.get(key).get(i);
                for (int j = 0; j <room.getPatients().size() ; j++) {
                    Patient patient=room.getPatients().get(j);
                    if (patient.getTreatMentDuration()<=days){
                        patient.getDoctor().setAvailiable(true);
                        room.getPatients().remove(patient);
                        System.out.println("Пациент "+patient.getName()+" от пол "+ patient.getGender() +" с диагноза "+ patient.getDiagnose()
                                        +" беше изписан ");
                    }
                }
                notifyAll();
            }
        }

    }
    public void setDoctorToPatient(Patient patient){
        Doctor doctor=getAvailiableDoctor();
        doctor.setAvailiable(false);
        doctor.addPatient(patient);
        patient.setDoctor(doctor);
        patient.setDiagnose(doctor.giveDiagnose());
        patient.setTreatment(doctor.giveMedicine());
        patient.setTreatMentDuration(doctor.treatmentDuration());
    }
    public boolean hasAvailiableDoctors(){
        for (Doctor doctor : doctors) {
            if (doctor.isAvailiable()) {
                return true;
            }
        }
        return false;
    }
    public Doctor getAvailiableDoctor(){
        for (Doctor doctor : doctors) {
            if (doctor.isAvailiable()) {
                return doctor;
            }
        }
        return null;
    }
    public boolean hasAvailiableBedsInDepartment(TypeSpecializatonDepartment department,Patient patient){
        for (int i = 0; i <rooms.get(department).size() ; i++) {
            Room room=rooms.get(department).get(i);
            if (!room.isFull()&&(patient.getGender().equals(room.getGender())||room.getGender()==null)){
                return true;
            }
        }
        return false;
    }
    public Room getAvailiableRoom(TypeSpecializatonDepartment department,Patient patient){
        for (int i = 0; i <rooms.get(department).size() ; i++) {
            Room room=rooms.get(department).get(i);
            if (!room.isFull()&&(patient.getGender().equals(room.getGender())||room.getGender()==null)){
                return room;
            }
        }
        return null;
    }
    public boolean hasPatients(){
        for (TypeSpecializatonDepartment key:rooms.keySet()) {
            for (int i = 0; i <rooms.get(key).size() ; i++) {
                Room room=rooms.get(key).get(i);
                    if(room.getPatients().size()>0){
                        return true;
                    }
            }
        }
        return false;
    }
    public void incrementDays(){
        days++;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public ArrayList<Nurse> getNurses() {
        return nurses;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }
    public void resetingVisitations(){
        for (TypeSpecializatonDepartment key:rooms.keySet()) {
            for (int i = 0; i <rooms.get(key).size() ; i++) {
                Room room=rooms.get(key).get(i);
                for (int j = 0; j <room.getPatients().size() ; j++) {
                    room.getPatients().get(j).setVisited(false);
                }
            }
        }
    }
    public void resetingTreatments(){
        for (TypeSpecializatonDepartment key:rooms.keySet()) {
            for (int i = 0; i <rooms.get(key).size() ; i++) {
                Room room=rooms.get(key).get(i);
                for (int j = 0; j <room.getPatients().size() ; j++) {
                    room.getPatients().get(j).setIsTreated(false);
                }
            }
        }
    }
    public boolean hasPatientInDepartment(TypeSpecializatonDepartment department){
        for (int i = 0; i <rooms.get(department).size() ; i++) {
            Room room=rooms.get(department).get(i);
            if (room.getPatients().size()>0){
                return true;
            }
        }
        return false;
    }

}
