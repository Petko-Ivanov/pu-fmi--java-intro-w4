import java.util.Random;

public class Utility {
    public static int randomNumberToGenerateBuildings(){
        int randomNumber;
        Random random=new Random();
        return randomNumber=random.nextInt(3)+1;
    }
    public static int randomNumberForBuildingType(){
        Random random=new Random();
        return random.nextInt(3)+1;
    }
    public static int randomNumberForEnemiesSpawn(){
        Random random=new Random();
        int randomNumber=random.nextInt(13)+1;
        return randomNumber;
    }
    public static int randomEnemyDirection(){
        Random random=new Random();
        int randomDirection=random.nextInt(4)+1;
        return randomDirection;
    }
    public static int randomNumberForEnemyFire(int number){
        Random random=new Random();
        int randomNumber=random.nextInt(number)+1;
        return randomNumber;
    }
}
