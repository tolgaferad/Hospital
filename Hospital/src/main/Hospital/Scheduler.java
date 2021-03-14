package main.Hospital;

public class Scheduler extends Thread{
    private Hospital hospital;

    public Scheduler(Hospital hospital) {
        this.hospital = hospital;
    }
    @Override
    public void run() {
        while(true) {
            hospital.scheduling(this);
        }
    }
}
