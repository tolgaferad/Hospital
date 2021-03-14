package main.Hospital;

import java.util.Random;

public enum TypeSpecializatonDepartment {

    ORTOPEDIQ,VIRUSULOGIQ,KARDIOLOGIQ;

    public String getOrtopedicDiagnosis(){
        String[] ortoDiag={"Schupen krak","Izkulchen glezen","Schupen taz","izkrivena ruka"};
        return ortoDiag[new Random().nextInt(ortoDiag.length)];
    }
    public String getVirusDiagnosis(){
        String[] virDiag={"Grip","Pnevmoniq","Sharka","Losh virus"};
        return virDiag[new Random().nextInt(virDiag.length)];
    }
    public String getKardioDiagnosis(){
        String[] kardiDiag={"Visoko kruvno","Slabo surce","Surcebiene","surcebolest"};
        return kardiDiag[new Random().nextInt(kardiDiag.length)];
    }

    public String getOrtopedicMedicine(){
        String[] ortoMed={"OrtoMed-1","OrtoMed-2","OrtoMed-3","OrtoMed-4"};
        return ortoMed[new Random().nextInt(ortoMed.length)];
    }
    public String getVirusMedicine(){
        String[] virusMed={"VirusMed-1","VirusMed-2","VirusMed-3","VirusMed-4"};
        return virusMed[new Random().nextInt(virusMed.length)];
    }
    public String getKardioMedicine(){
        String[] kardiMed={"KardioMed-1","KardioMed-2","KardioMed-3","KardioMed-4"};
        return kardiMed[new Random().nextInt(kardiMed.length)];
    }
}
