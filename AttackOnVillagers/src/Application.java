import java.util.Scanner;

public class Application {
    public static void  gameLoop(String[][] gameMap){
            Scanner input=new Scanner(System.in);
            String command= input.next();
            gameMap=GameManager.unitCommands(gameMap,command);
            GameManager.mapVisualisation(gameMap);
            gameLoop(gameMap);

    }
    public static  void main(String[] args){
        String[][] gameMap=GameManager.gameMapInitialization();
        GameManager.mapVisualisation(gameMap);
        gameLoop(gameMap);
    }
}
