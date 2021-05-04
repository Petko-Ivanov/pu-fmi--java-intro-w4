import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameManager {
    private static final int MAP_ROW_COUNT=15;
    private static final int MAP_COL_COUNT =15;

    private static final String UNIT_TERRAIN=". ";
    private static final String UNIT_SMALL_BUILDING="* ";
    private static final String UNIT_MEDIUM_BUILDING="% ";
    private static final String UNIT_LARGE_BUILDING="& ";

    //Тези листове ще пазят координатите на сградите, за да може после по-лесно да проверявам кога играчите минават през сгради
    private static  List<int[]> building1CoordinatesContainer=new ArrayList<>();
    private static  List<int[]> building2CoordinatesContainer=new ArrayList<>();
    private static  List<int[]> building3CoordinatesContainer=new ArrayList<>();
    //Този контейнер ще не помогне да присвойм координатите на всяка сграда
    private static List<int[]>coordinatesContainer=new ArrayList<>();

    private static final String UNIT_TANK="1 ";
    private static final String UNIT_SNIPER="2 ";
    private static final String UNIT_SPY="3 ";
    private static final String UNIT_SABOTEUR="4 ";
    private static final String UNIT_ENEMY="$ ";

    private static final String UNIT_COMMAND_FORWARD="W";
    private static final String UNIT_COMMAND_BACKWARDS="S";
    private static final String UNIT_COMMAND_RIGHT="D";
    private static final String UNIT_COMMAND_LEFT="A";
    private static final String UNIT_COMMAND_CHANGE_CAPTAIN="C";
    private static final String UNIT_COMMAND_PLANT_BOMB="F";

    private static String captainUnit=UNIT_TANK;
    private static String bodyUnit1=UNIT_SNIPER;
    private static String bodyUnit2=UNIT_SPY;
    private static String tailUnit=UNIT_SABOTEUR;
    private static int deadUnits=0;

   static int[] firstUnitLocation=new int[]{14,11};
   static int[] secondUnitLocation=new int[]{14,12};
   static int[] thirdUnitLocation=new int[]{14,13};
   static int[] fourthUnitLocation=new int[]{14,14};
   static int[] enemyUnitLocation=new int[2];
   static int[] bombLocation=new int[]{0,0};

   static boolean isTankUnitAlive=true;
   static boolean isSniperUnitAlive=true;
   static boolean isSpyUnitAlive=true;
   static boolean isSaboteurUnitAlive=true;
   static boolean isBombPlanted=false;

    public static String[][] gameMapInitialization(){
        String[][] gameMap=new String[15][15];
        for (int row=0;row<15;row++){
            for (int col=0;col<15;col++){
                gameMap[row][col]=UNIT_TERRAIN;
            }
        }
        gameMap=generateBuildings(gameMap);
        gameMap=generatePlayers(gameMap);
        gameMap=generateEnemies(gameMap);
        return gameMap;
    }
    public static void mapVisualisation(String[][] gameMap){
        for (int row=0;row<15;row++){
            for (int col=0;col<15;col++){
                System.out.print(gameMap[row][col]);
            }
            System.out.println();
        }
    }
    public static String[][] unitCommands(String[][] gameMap,String command){

        int[] thirdUnitHolder=new int[]{secondUnitLocation[0],secondUnitLocation[1]};
        int[] firstUnitHolder=new int[]{firstUnitLocation[0],firstUnitLocation[1]};
        int[] fourthUnitHolder=new int[]{thirdUnitLocation[0],thirdUnitLocation[1]};

        if(command.equals(UNIT_COMMAND_FORWARD)){
                gameMap[fourthUnitLocation[0]][fourthUnitLocation[1]] = UNIT_TERRAIN;
                firstUnitLocation[0] -= 1;
                secondUnitLocation = firstUnitHolder;
                thirdUnitLocation = thirdUnitHolder;
                fourthUnitLocation = fourthUnitHolder;
                enemyMovements(gameMap);
                buildingChecker(building1CoordinatesContainer, building2CoordinatesContainer, building3CoordinatesContainer, gameMap);
                return gameMap = generatePlayers(gameMap);
        }
        if(command.equals(UNIT_COMMAND_BACKWARDS)){
                gameMap[fourthUnitLocation[0]][fourthUnitLocation[1]] = UNIT_TERRAIN;
                firstUnitLocation[0] += 1;
                secondUnitLocation = firstUnitHolder;
                thirdUnitLocation = thirdUnitHolder;
                fourthUnitLocation = fourthUnitHolder;
                enemyMovements(gameMap);
                buildingChecker(building1CoordinatesContainer, building2CoordinatesContainer, building3CoordinatesContainer, gameMap);
                return gameMap = generatePlayers(gameMap);
        }
        if(command.equals(UNIT_COMMAND_RIGHT)){
                gameMap[fourthUnitLocation[0]][fourthUnitLocation[1]] = UNIT_TERRAIN;
                firstUnitLocation[1]++;
                secondUnitLocation = firstUnitHolder;
                thirdUnitLocation = thirdUnitHolder;
                fourthUnitLocation = fourthUnitHolder;
                enemyMovements(gameMap);
                buildingChecker(building1CoordinatesContainer, building2CoordinatesContainer, building3CoordinatesContainer, gameMap);
                return gameMap = generatePlayers(gameMap);
        }
        if(command.equals(UNIT_COMMAND_LEFT)){
                gameMap[fourthUnitLocation[0]][fourthUnitLocation[1]] = UNIT_TERRAIN;
                firstUnitLocation[1] -= 1;
                secondUnitLocation = firstUnitHolder;
                thirdUnitLocation = thirdUnitHolder;
                fourthUnitLocation = fourthUnitHolder;
                enemyMovements(gameMap);
                buildingChecker(building1CoordinatesContainer, building2CoordinatesContainer, building3CoordinatesContainer, gameMap);
                return gameMap = generatePlayers(gameMap);
        }

        if(command.equals(UNIT_COMMAND_CHANGE_CAPTAIN)){
            String[] unitString=new String[]{captainUnit,bodyUnit1,bodyUnit2,tailUnit};
            Scanner input=new Scanner(System.in);
            for (int i=1;i<5;i++){
                System.out.println(i+"."+unitString[i-1]);
            }
            //Тук въвеждам номерацията на позицията , не символа на юнита.
            // (т.е въведеното число трябва да отговаря на позицията , която искаме да направим капитан)
            int newCaptainNumber=input.nextInt();
            String tempUnit="";
            switch (newCaptainNumber){
                default:
                    break;
                case 2:
                    tempUnit=bodyUnit1;
                    bodyUnit1=captainUnit;
                    captainUnit=tempUnit;
                    break;
                case 3:
                    tempUnit=bodyUnit2;
                    bodyUnit2=captainUnit;
                    captainUnit=tempUnit;
                    break;
                case 4:
                    tempUnit=tailUnit;
                    tailUnit=captainUnit;
                    captainUnit=tempUnit;
                    break;
            }
            gameMap=generatePlayers(gameMap);
        }
        if(command.equals(UNIT_COMMAND_PLANT_BOMB)){
            if(captainUnit.equals(UNIT_SABOTEUR)){

            }
        }
        return gameMap;
    }
    private static String[][] generateBuildings(String[][] gameMap){
    //Разделям ги на зони , защото не мога да измисля друг начин за това условие
        // I-ва зона
        for (int row=1; row<5;row++){
            for (int col = 1; col< 7; col++){
                if(isPlacementPossible()){
                   gameMap= buildingType(gameMap,row,col,coordinatesContainer);
                    List<int[]>tempList=new ArrayList<>();
                    for (int i=0 ;i<coordinatesContainer.size();i++){
                        tempList.add(coordinatesContainer.get(i));
                    }
                   building1CoordinatesContainer=tempList;
                   coordinatesContainer.clear();
                   row=MAP_ROW_COUNT;
                   col=MAP_COL_COUNT;
                }
            }
        }
        // II-ра зона
        for (int row=1; row<MAP_ROW_COUNT-1;row++){
            for (int col = 10; col< MAP_COL_COUNT -1; col++){
                if(isPlacementPossible()){
                    gameMap=buildingType(gameMap,row,col,coordinatesContainer);
                    List<int[]>tempList=new ArrayList<>();
                    for (int i=0 ;i<coordinatesContainer.size();i++){
                        tempList.add(coordinatesContainer.get(i));
                    }
                    building2CoordinatesContainer=tempList;
                    coordinatesContainer.clear();
                    row= MAP_ROW_COUNT;
                    col= MAP_COL_COUNT;
                }
            }
        }
        //III-та зона
        for (int row=8; row<MAP_ROW_COUNT-1;row++){
            for (int col = 1; col< 6; col++){
                if(isPlacementPossible()){
                    gameMap=buildingType(gameMap,row,col,coordinatesContainer);
                    List<int[]>tempList=new ArrayList<>();
                    for (int i=0 ;i<coordinatesContainer.size();i++){
                        tempList.add(coordinatesContainer.get(i));
                    }
                    building3CoordinatesContainer=tempList;
                    coordinatesContainer.clear();
                    row=MAP_ROW_COUNT;
                    col=MAP_COL_COUNT;

                }
            }

        }
        return gameMap;
    }
    private static String[][] generateSmallBuilding(String[][] gameMap,int row,int col,List<int[]> coordinatesContainer){
        int[] tempContainer=new int[]{row,col};
        gameMap[row][col]=UNIT_SMALL_BUILDING;
        coordinatesContainer.add(tempContainer);
        col++;
        int[] tempContainer2=new int[]{row,col};
        gameMap[row][col]=UNIT_SMALL_BUILDING;
        coordinatesContainer.add(tempContainer2);
        row++;
        col--;
        int[] tempContainer3=new int[]{row,col};
        gameMap[row][col]=UNIT_SMALL_BUILDING;
        coordinatesContainer.add(tempContainer3);
        col++;
        int[] tempContainer4=new int[]{row,col};
        gameMap[row][col]=UNIT_SMALL_BUILDING;
        coordinatesContainer.add(tempContainer4);
        return gameMap;

    }
    private static String[][] generateMediumBuilding(String[][] gameMap,int row,int col,List<int[]> coordinatesContainer){
        int[] tempContainer=new int[]{row,col};
        gameMap[row][col]=UNIT_MEDIUM_BUILDING;
        coordinatesContainer.add(tempContainer);
        col++;
        int[] tempContainer2=new int[]{row,col};
        gameMap[row][col]=UNIT_MEDIUM_BUILDING;
        coordinatesContainer.add(tempContainer2);
        col++;
        int[] tempContainer3=new int[]{row,col};
        gameMap[row][col]=UNIT_MEDIUM_BUILDING;
        coordinatesContainer.add(tempContainer3);
        row++;
        col--;
        col--;
        int[] tempContainer4=new int[]{row,col};
        gameMap[row][col]=UNIT_MEDIUM_BUILDING;
        coordinatesContainer.add(tempContainer4);
        col++;
        int[] tempContainer5=new int[]{row,col};
        gameMap[row][col]=UNIT_MEDIUM_BUILDING;
        coordinatesContainer.add(tempContainer5);
        col++;
        int[] tempContainer6=new int[]{row,col};
        gameMap[row][col]=UNIT_MEDIUM_BUILDING;
        coordinatesContainer.add(tempContainer6);
        return gameMap;
    }
    private static String[][] generateLargeBuilding(String[][] gameMap,int row,int col,List<int[]> coordinatesContainer){
        int[] tempContainer=new int[]{row,col};
        gameMap[row][col]=UNIT_LARGE_BUILDING;
        coordinatesContainer.add(tempContainer);
        col++;
        int[] tempContainer2=new int[]{row,col};
        gameMap[row][col]=UNIT_LARGE_BUILDING;
        coordinatesContainer.add(tempContainer2);
        col++;
        int[] tempContainer3=new int[]{row,col};
        gameMap[row][col]=UNIT_LARGE_BUILDING;
        coordinatesContainer.add(tempContainer3);
        row++;
        col--;
        col--;
        int[] tempContainer4=new int[]{row,col};
        gameMap[row][col]=UNIT_LARGE_BUILDING;
        coordinatesContainer.add(tempContainer4);
        col++;
        int[] tempContainer5=new int[]{row,col};
        gameMap[row][col]=UNIT_LARGE_BUILDING;
        coordinatesContainer.add(tempContainer5);
        col++;
        int[] tempContainer6=new int[]{row,col};
        gameMap[row][col]=UNIT_LARGE_BUILDING;
        coordinatesContainer.add(tempContainer6);
        row++;
        col--;
        col--;
        int[] tempContainer7=new int[]{row,col};
        gameMap[row][col]=UNIT_LARGE_BUILDING;
        coordinatesContainer.add(tempContainer7);
        col++;
        int[] tempContainer8=new int[]{row,col};
        gameMap[row][col]=UNIT_LARGE_BUILDING;
        coordinatesContainer.add(tempContainer8);
        col++;
        int[] tempContainer9=new int[]{row,col};
        gameMap[row][col]=UNIT_LARGE_BUILDING;
        coordinatesContainer.add(tempContainer9);
        return gameMap;
    }
    //buildingType метода е опитът ми да се генерира рандом сграда във всяка от зоните , но не можах да измисля добър алгоритъм за изключване на възможност от рандом генератора
 // (пример : изключваме 2 след като се е генерирала 2-ра сграда) , затова има шанс да се генерират 2 еднакви сгради
    private static String[][] buildingType(String[][] gameMap, int row,int col,List<int[]> coordinatesContainer){
        int randomNumber = Utility.randomNumberForBuildingType();
        if (randomNumber == 1) {
           return gameMap = generateSmallBuilding(gameMap,row,col,coordinatesContainer);
        }
        if (randomNumber == 2) {
            return gameMap=generateMediumBuilding(gameMap,row,col,coordinatesContainer);
        }
        if (randomNumber == 3) {
          return   gameMap=generateLargeBuilding(gameMap,row,col,coordinatesContainer);
        }
        return gameMap;
    }
    private static void buildingChecker(List<int[]>building1,List<int[]>building2,List<int[]>building3,String[][] gameMap){
        String building1Symbol="";
        String building2Symbol="";
        String building3Symbol="";
        switch (building1.size()){
            case 4:
                building1Symbol=UNIT_SMALL_BUILDING;
                break;
            case 6:
                building1Symbol=UNIT_MEDIUM_BUILDING;
                break;
            case 9:
                building1Symbol=UNIT_LARGE_BUILDING;
                break;
            default:
                break;
        }
        switch (building2.size()){
            case 4:
                building2Symbol=UNIT_SMALL_BUILDING;
                break;
            case 6:
                building2Symbol=UNIT_MEDIUM_BUILDING;
                break;
            case 9:
                building2Symbol=UNIT_LARGE_BUILDING;
                break;
            default:
                break;
        }
        switch (building3.size()){
            case 4:
                building3Symbol=UNIT_SMALL_BUILDING;
                break;
            case 6:
                building3Symbol=UNIT_MEDIUM_BUILDING;
                break;
            case 9:
                building3Symbol=UNIT_LARGE_BUILDING;
                break;
            default:
                break;
        }
        for (int[] coordinate:building1) {
            if(!gameMap[coordinate[0]][coordinate[1]].equals(UNIT_TANK)||!gameMap[coordinate[0]][coordinate[1]].equals(UNIT_SNIPER)
                    ||!gameMap[coordinate[0]][coordinate[1]].equals(UNIT_SPY)||!gameMap[coordinate[0]][coordinate[1]].equals(UNIT_SABOTEUR)||!gameMap[coordinate[0]][coordinate[1]].equals(UNIT_ENEMY)){
                gameMap[coordinate[0]][coordinate[1]]=building1Symbol;
            }
        }
        for (int[] coordinate:building2) {
            if(!gameMap[coordinate[0]][coordinate[1]].equals(UNIT_TANK)||!gameMap[coordinate[0]][coordinate[1]].equals(UNIT_SNIPER)
                    ||!gameMap[coordinate[0]][coordinate[1]].equals(UNIT_SPY)||!gameMap[coordinate[0]][coordinate[1]].equals(UNIT_SABOTEUR)||!gameMap[coordinate[0]][coordinate[1]].equals(UNIT_ENEMY)){
                gameMap[coordinate[0]][coordinate[1]]=building2Symbol;
            }
        }
        for (int[] coordinate:building3) {
            if(!gameMap[coordinate[0]][coordinate[1]].equals(UNIT_TANK)||!gameMap[coordinate[0]][coordinate[1]].equals(UNIT_SNIPER)
                    ||!gameMap[coordinate[0]][coordinate[1]].equals(UNIT_SPY)||!gameMap[coordinate[0]][coordinate[1]].equals(UNIT_SABOTEUR)||!gameMap[coordinate[0]][coordinate[1]].equals(UNIT_ENEMY)){
                gameMap[coordinate[0]][coordinate[1]]=building3Symbol;
            }
        }
    }

    //Тук е опитът ми да няправя средния ред на средната сграда да е непреминаем , но както виждате напълно неуспешно,
    // опитах да пазя всеки отделен координат в лист от масив с 2 елемента и се получи ,
    // но мисля че нямам повече нерви да оправям всички грешки , които създава.
    // Затова моята брилянтна идея завинаги ще остане в света на коментарите

//    private static boolean canPlayersPass(String[][] gameMap,String command){
//        if(building1CoordinatesContainer.size()==6){
//            int[] coordinate1=new int[2];
//            int[] coordinate2=new int[2];
//            coordinate1=building1CoordinatesContainer.get(1);
//            coordinate2=building1CoordinatesContainer.get(4);
//            if(((firstUnitLocation[0]++==coordinate1[0]&&firstUnitLocation[1]==coordinate1[1])&&command.equals(UNIT_COMMAND_BACKWARDS))
//                    ||((firstUnitLocation[0]--==coordinate2[0]&&firstUnitLocation[1]==coordinate2[1])&&command.equals(UNIT_COMMAND_FORWARD))
//                    ||((firstUnitLocation[0]==coordinate1[0]&&firstUnitLocation[1]--==coordinate1[1])&&command.equals(UNIT_COMMAND_LEFT))
//                    ||((firstUnitLocation[0]==coordinate2[0]&&firstUnitLocation[1]--==coordinate2[1])&&command.equals(UNIT_COMMAND_LEFT))
//                    ||((firstUnitLocation[0]==coordinate1[0]&&firstUnitLocation[1]++==coordinate1[1])&&command.equals(UNIT_COMMAND_RIGHT))
//                    ||((firstUnitLocation[0]==coordinate2[0]&&firstUnitLocation[1]++==coordinate2[1])&&command.equals(UNIT_COMMAND_RIGHT))){
//                return false;
//            }
//        }
//        if(building2CoordinatesContainer.size()==6){
//            int[] coordinate1=new int[2];
//            int[] coordinate2=new int[2];
//            coordinate1=building2CoordinatesContainer.get(1);
//            coordinate2=building2CoordinatesContainer.get(4);
//            if((gameMap[firstUnitLocation[0]--][firstUnitLocation[1]].equals(gameMap[coordinate2[0]][coordinate2[1]])&&command.equals(UNIT_COMMAND_FORWARD))
//                    ||(gameMap[firstUnitLocation[0]++][firstUnitLocation[1]].equals(gameMap[coordinate1[0]][coordinate1[1]])&&command.equals(UNIT_COMMAND_BACKWARDS))
//                    ||(gameMap[firstUnitLocation[0]][firstUnitLocation[1]--].equals(gameMap[coordinate1[0]][coordinate1[1]])&&command.equals(UNIT_COMMAND_LEFT))
//                    ||(gameMap[firstUnitLocation[0]][firstUnitLocation[1]--].equals(gameMap[coordinate2[0]][coordinate2[1]])&&command.equals(UNIT_COMMAND_LEFT))
//                    ||(gameMap[firstUnitLocation[0]][firstUnitLocation[1]++].equals(gameMap[coordinate1[0]][coordinate1[1]])&&command.equals(UNIT_COMMAND_RIGHT))
//                    ||(gameMap[firstUnitLocation[0]][firstUnitLocation[1]++].equals(gameMap[coordinate2[0]][coordinate2[1]]))&&command.equals(UNIT_COMMAND_RIGHT)){
//                return false;
//            }
//        }
//        if(building3CoordinatesContainer.size()==6){
//            int[] coordinate1=new int[2];
//            int[] coordinate2=new int[2];
//            coordinate1=building3CoordinatesContainer.get(1);
//            coordinate2=building3CoordinatesContainer.get(4);
//            if((gameMap[firstUnitLocation[0]--][firstUnitLocation[1]].equals(gameMap[coordinate2[0]][coordinate2[1]])&&command.equals(UNIT_COMMAND_FORWARD))
//                  ||(gameMap[firstUnitLocation[0]++][firstUnitLocation[1]].equals(gameMap[coordinate1[0]][coordinate1[1]])&&command.equals(UNIT_COMMAND_BACKWARDS))
//                  ||(gameMap[firstUnitLocation[0]][firstUnitLocation[1]--].equals(gameMap[coordinate1[0]][coordinate1[1]])&&command.equals(UNIT_COMMAND_LEFT))
//                  ||(gameMap[firstUnitLocation[0]][firstUnitLocation[1]--].equals(gameMap[coordinate2[0]][coordinate2[1]])&&command.equals(UNIT_COMMAND_LEFT))
//                  ||(gameMap[firstUnitLocation[0]][firstUnitLocation[1]++].equals(gameMap[coordinate1[0]][coordinate1[1]])&&command.equals(UNIT_COMMAND_RIGHT))
//                  ||(gameMap[firstUnitLocation[0]][firstUnitLocation[1]++].equals(gameMap[coordinate2[0]][coordinate2[1]]))&&command.equals(UNIT_COMMAND_RIGHT)){
//                return false;
//            }
//        }
//        return true;
//
    private static boolean isPlacementPossible(){
        int rngCoefficient=Utility.randomNumberToGenerateBuildings();
        return rngCoefficient==1;
    }
    private static String[][] generatePlayers(String[][] gameMap){
        gameMap[firstUnitLocation[0]][firstUnitLocation[1]]=captainUnit;
        gameMap[secondUnitLocation[0]][secondUnitLocation[1]]=bodyUnit1;
        gameMap[thirdUnitLocation[0]][thirdUnitLocation[1]]=bodyUnit2;
        gameMap[fourthUnitLocation[0]][fourthUnitLocation[1]]=tailUnit;
        return gameMap;
    }
    private static String[][] generateEnemies(String[][] gameMap){
        int row=Utility.randomNumberForEnemiesSpawn();
        enemyUnitLocation[0]=row;
        int col=Utility.randomNumberForEnemiesSpawn();
        enemyUnitLocation[1]=col;
        gameMap[row][col]=UNIT_ENEMY;
        return gameMap;
    }
    private static String[][] enemyMovements(String[][] gameMap){
        int randomDirectionNumber=Utility.randomEnemyDirection();
        int lastEnemyPositionRow=enemyUnitLocation[0];
        int lastEnemyPositionCol=enemyUnitLocation[1];
        int[] tempArr=new int[]{lastEnemyPositionRow,lastEnemyPositionCol};
        if(randomDirectionNumber==1){
           if(tempArr[0]-->0) {
               lastEnemyPositionRow--;
           }
        }
        if(randomDirectionNumber==2){
            if(tempArr[0]++<15) {
                lastEnemyPositionRow++;
            }
        }
        if(randomDirectionNumber==3){
            if(tempArr[1]-->0){
            lastEnemyPositionCol--;
            }
        }
        if(randomDirectionNumber==4){
            if(tempArr[1]++<15) {
                lastEnemyPositionCol++;
            }
        }
        gameMap[enemyUnitLocation[0]][enemyUnitLocation[1]]=UNIT_TERRAIN;
        enemyUnitLocation[0]=lastEnemyPositionRow;
        enemyUnitLocation[1]=lastEnemyPositionCol;

        gameMap[lastEnemyPositionRow][lastEnemyPositionCol]=UNIT_ENEMY;
        return gameMap;
    }

    // Тук е се опитах да направя така ,че бай Петкан да стреля , но  по някаква причина инкрементацията на gameMap[tempContainer[0]] или другите подобни
    // действия винаги се запазват и просто вдигнах ръце. Оставям метода като доказателство , че поне съм се опитал.
    // Яд ме е също , че няма да мога да изпробвам метода за умиране на юнити.
    // Надявам се да получа feedback защо бай Петкан не стреля.
    private static void enemyShootRange(String[][] gameMap){
         int[] tempContainer=new int[]{enemyUnitLocation[0],enemyUnitLocation[1]};
         if(tempContainer[0]++<15&&(gameMap[tempContainer[0]++][tempContainer[1]].equals(UNIT_TANK)
            ||gameMap[tempContainer[0]++][tempContainer[1]].equals(UNIT_SNIPER)
            ||gameMap[tempContainer[0]++][tempContainer[1]].equals(UNIT_SABOTEUR)
            ||gameMap[tempContainer[0]++][tempContainer[1]].equals(UNIT_SPY))){
               int randomNumber=Utility.randomNumberForEnemyFire(24);
                if(randomNumber%11==0){
                    unitDies(gameMap);
                }
                if(randomNumber%11!=0){
                  int randomCorner=Utility.randomEnemyDirection();
                  if(randomCorner==1){
                      enemyUnitLocation[0]=0;
                      enemyUnitLocation[1]=0;
                  }
                  if(randomCorner==2){
                      enemyUnitLocation[0]=14;
                      enemyUnitLocation[1]=0;
                  }
                  if(randomCorner==3){
                      enemyUnitLocation[0]=0;
                      enemyUnitLocation[1]=14;
                  }
                  if(randomCorner==4){
                      enemyUnitLocation[0]=14;
                      enemyUnitLocation[1]=14;
                  }
                }
         }
        if(tempContainer[0]-->0&&(gameMap[tempContainer[0]--][tempContainer[1]].equals(UNIT_TANK)
                ||gameMap[tempContainer[0]--][tempContainer[1]].equals(UNIT_SNIPER)
                ||gameMap[tempContainer[0]--][tempContainer[1]].equals(UNIT_SABOTEUR)
                ||gameMap[tempContainer[0]--][tempContainer[1]].equals(UNIT_SPY))){
            int randomNumber=Utility.randomNumberForEnemyFire(24);
            if(randomNumber%11==0){
                unitDies(gameMap);
            }
            if(randomNumber%11!=0){
                int randomCorner=Utility.randomEnemyDirection();
                if(randomCorner==1){
                    enemyUnitLocation[0]=0;
                    enemyUnitLocation[1]=0;
                }
                if(randomCorner==2){
                    enemyUnitLocation[0]=14;
                    enemyUnitLocation[1]=0;
                }
                if(randomCorner==3){
                    enemyUnitLocation[0]=0;
                    enemyUnitLocation[1]=14;
                }
                if(randomCorner==4){
                    enemyUnitLocation[0]=14;
                    enemyUnitLocation[1]=14;
                }
            }

        }
        if(tempContainer[1]-->0&&(gameMap[tempContainer[0]][tempContainer[1]--].equals(UNIT_TANK)
                ||gameMap[tempContainer[0]][tempContainer[1]--].equals(UNIT_SNIPER)
                ||gameMap[tempContainer[0]][tempContainer[1]--].equals(UNIT_SABOTEUR)
                ||gameMap[tempContainer[0]][tempContainer[1]--].equals(UNIT_SPY))){
            int randomNumber=Utility.randomNumberForEnemyFire(24);
            if(randomNumber%11==0){
                unitDies(gameMap);
            }
            if(randomNumber%11!=0){
                int randomCorner=Utility.randomEnemyDirection();
                if(randomCorner==1){
                    enemyUnitLocation[0]=0;
                    enemyUnitLocation[1]=0;
                }
                if(randomCorner==2){
                    enemyUnitLocation[0]=14;
                    enemyUnitLocation[1]=0;
                }
                if(randomCorner==3){
                    enemyUnitLocation[0]=0;
                    enemyUnitLocation[1]=14;
                }
                if(randomCorner==4){
                    enemyUnitLocation[0]=14;
                    enemyUnitLocation[1]=14;
                }
            }
        }
        if(tempContainer[1]++<15&&(gameMap[tempContainer[0]][tempContainer[1]++].equals(UNIT_TANK)
                ||gameMap[tempContainer[0]][tempContainer[1]++].equals(UNIT_SNIPER)
                ||gameMap[tempContainer[0]][tempContainer[1]++].equals(UNIT_SABOTEUR)
                ||gameMap[tempContainer[0]][tempContainer[1]++].equals(UNIT_SPY))){
            int randomNumber=Utility.randomNumberForEnemyFire(24);
            if(randomNumber%11==0){
                unitDies(gameMap);
            }
            if(randomNumber%11!=0){
                int randomCorner=Utility.randomEnemyDirection();
                if(randomCorner==1){
                    enemyUnitLocation[0]=0;
                    enemyUnitLocation[1]=0;
                }
                if(randomCorner==2){
                    enemyUnitLocation[0]=14;
                    enemyUnitLocation[1]=0;
                }
                if(randomCorner==3){
                    enemyUnitLocation[0]=0;
                    enemyUnitLocation[1]=14;
                }
                if(randomCorner==4){
                    enemyUnitLocation[0]=14;
                    enemyUnitLocation[1]=14;
                }
            }
        }
        if((tempContainer[0]-->0&&tempContainer[1]-->0)&&(gameMap[tempContainer[0]--][tempContainer[1]--].equals(UNIT_TANK)
                ||gameMap[tempContainer[0]--][tempContainer[1]--].equals(UNIT_SNIPER)
                ||gameMap[tempContainer[0]--][tempContainer[1]--].equals(UNIT_SABOTEUR)
                ||gameMap[tempContainer[0]--][tempContainer[1]--].equals(UNIT_SPY))){
            int randomNumber=Utility.randomNumberForEnemyFire(24);
            if(randomNumber%11==0){
                unitDies(gameMap);
            }
            if(randomNumber%11!=0){
                int randomCorner=Utility.randomEnemyDirection();
                if(randomCorner==1){
                    enemyUnitLocation[0]=0;
                    enemyUnitLocation[1]=0;
                }
                if(randomCorner==2){
                    enemyUnitLocation[0]=14;
                    enemyUnitLocation[1]=0;
                }
                if(randomCorner==3){
                    enemyUnitLocation[0]=0;
                    enemyUnitLocation[1]=14;
                }
                if(randomCorner==4){
                    enemyUnitLocation[0]=14;
                    enemyUnitLocation[1]=14;
                }
            }
        }
        if((tempContainer[0]-->0&&tempContainer[1]++<15)&&(gameMap[tempContainer[0]--][tempContainer[1]++].equals(UNIT_TANK)
                ||gameMap[tempContainer[0]--][tempContainer[1]++].equals(UNIT_SNIPER)
                ||gameMap[tempContainer[0]--][tempContainer[1]++].equals(UNIT_SABOTEUR)
                ||gameMap[tempContainer[0]--][tempContainer[1]++].equals(UNIT_SPY))){
            int randomNumber=Utility.randomNumberForEnemyFire(24);
            if(randomNumber%11==0){
                unitDies(gameMap);
            }
            if(randomNumber%11!=0){
                int randomCorner=Utility.randomEnemyDirection();
                if(randomCorner==1){
                    enemyUnitLocation[0]=0;
                    enemyUnitLocation[1]=0;
                }
                if(randomCorner==2){
                    enemyUnitLocation[0]=14;
                    enemyUnitLocation[1]=0;
                }
                if(randomCorner==3){
                    enemyUnitLocation[0]=0;
                    enemyUnitLocation[1]=14;
                }
                if(randomCorner==4){
                    enemyUnitLocation[0]=14;
                    enemyUnitLocation[1]=14;
                }
            }
        }
        if((tempContainer[0]++<15&&tempContainer[1]-->0)&&(gameMap[tempContainer[0]++][tempContainer[1]--].equals(UNIT_TANK)
                ||gameMap[tempContainer[0]++][tempContainer[1]--].equals(UNIT_SNIPER)
                ||gameMap[tempContainer[0]++][tempContainer[1]--].equals(UNIT_SABOTEUR)
                ||gameMap[tempContainer[0]++][tempContainer[1]--].equals(UNIT_SPY))){
            int randomNumber=Utility.randomNumberForEnemyFire(24);
            if(randomNumber%11==0){
                if(captainUnit.equals(UNIT_TANK)){
                    isTankUnitAlive=false;
                }
                if(captainUnit.equals(UNIT_SPY)){
                    isSpyUnitAlive=false;
                }
                if(captainUnit.equals(UNIT_SNIPER)){
                    isSniperUnitAlive=false;
                }
                if(captainUnit.equals(UNIT_SABOTEUR)){
                    isSaboteurUnitAlive=false;
                }
            }
            if(randomNumber%11!=0){
                int randomCorner=Utility.randomEnemyDirection();
                if(randomCorner==1){
                    enemyUnitLocation[0]=0;
                    enemyUnitLocation[1]=0;
                }
                if(randomCorner==2){
                    enemyUnitLocation[0]=14;
                    enemyUnitLocation[1]=0;
                }
                if(randomCorner==3){
                    enemyUnitLocation[0]=0;
                    enemyUnitLocation[1]=14;
                }
                if(randomCorner==4){
                    enemyUnitLocation[0]=14;
                    enemyUnitLocation[1]=14;
                }
            }
        }
        if((tempContainer[0]++<15&&tempContainer[1]++<15)&&(gameMap[tempContainer[0]++][tempContainer[1]++].equals(UNIT_TANK)
                ||gameMap[tempContainer[0]++][tempContainer[1]++].equals(UNIT_SNIPER)
                ||gameMap[tempContainer[0]++][tempContainer[1]++].equals(UNIT_SABOTEUR)
                ||gameMap[tempContainer[0]++][tempContainer[1]++].equals(UNIT_SPY))){
            int randomNumber=Utility.randomNumberForEnemyFire(24);
            if(randomNumber%11==0){
                unitDies(gameMap);
            }
            if(randomNumber%11!=0){
                int randomCorner=Utility.randomEnemyDirection();
                if(randomCorner==1){
                    enemyUnitLocation[0]=0;
                    enemyUnitLocation[1]=0;
                }
                if(randomCorner==2){
                    enemyUnitLocation[0]=14;
                    enemyUnitLocation[1]=0;
                }
                if(randomCorner==3){
                    enemyUnitLocation[0]=0;
                    enemyUnitLocation[1]=14;
                }
                if(randomCorner==4){
                    enemyUnitLocation[0]=14;
                    enemyUnitLocation[1]=14;
                }
            }
        }
    }
    private static String[][] unitDies(String[][] gameMap){
        String temp=bodyUnit1;
        String temp2=bodyUnit2;
        String temp3=tailUnit;
        switch (deadUnits){
            case 0:
                captainUnit=temp;
                bodyUnit1=temp2;
                bodyUnit2=temp3;
                tailUnit=UNIT_TERRAIN;
                break;
            case 1:
                captainUnit=temp;
                bodyUnit1=temp2;
                bodyUnit2=UNIT_TERRAIN;
                tailUnit=UNIT_TERRAIN;
                break;
            case 2:
                captainUnit=temp;
                bodyUnit1=UNIT_TERRAIN;
                bodyUnit2=UNIT_TERRAIN;
                tailUnit=UNIT_TERRAIN;
                break;
            case 3:
                captainUnit=UNIT_TERRAIN;
                bodyUnit1=UNIT_TERRAIN;
                bodyUnit2=UNIT_TERRAIN;
                tailUnit=UNIT_TERRAIN;
                break;
        }
        if(captainUnit.equals(UNIT_TANK)){
            isTankUnitAlive=false;
        }
        if(captainUnit.equals(UNIT_SPY)){
            isSpyUnitAlive=false;
        }
        if(captainUnit.equals(UNIT_SNIPER)){
            isSniperUnitAlive=false;
        }
        if(captainUnit.equals(UNIT_SABOTEUR)){
            isSaboteurUnitAlive=false;
        }
        return gameMap;
    }
}