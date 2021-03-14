package util;

import java.util.Random;

public class Randomizator {
    public static int randomFromTo(int min,int max){
        return new Random().nextInt((max-min)+1)+min;
    }
    public static String getRandomName(){
        String[] names={"Petur Petrov","Gosho Georgiev","Ivan Ivanov","Valyo Asenov","Tolga Ferad","Krisi Georgieva",
                "Moni Chausheva","Sinem Bahtiyar"};
        return names[new Random().nextInt(names.length)];
    }
}
