public class UnitAbilities {
    public static void tankUnitPassive(){
        if(GameManager.isTankUnitAlive&&(!GameManager.isSpyUnitAlive||!GameManager.isSniperUnitAlive||!GameManager.isSaboteurUnitAlive)){
            GameManager.isSaboteurUnitAlive=true;
            GameManager.isSniperUnitAlive=true;
            GameManager.isSpyUnitAlive=true;
            GameManager.isTankUnitAlive=false;
            System.out.println("Трактористът ни героично се жертва да спаси своя брат по оръжие и падна в битка!");
        }
    }
    public static void sniperUnitPassive(int randomFireNumber){
        if(GameManager.isSniperUnitAlive==true){
            randomFireNumber=Utility.randomNumberForEnemyFire(20);
        }
    }
}
