import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
/**
 * @author Samin Mehran
 */
public class AddAccount {
    static Scanner input = new Scanner(System.in);
    public static String fileName = "settings.txt" ;
    public static String accountFileName = "account.txt" ;
    public static void main(String[] args) throws IOException {
        creatAccountFile();
        changeSettingsMenu();
        readSettings();
    }
    /**
     * This function is to determine the status of win, loss and draw in the form of a message in play with computer
     * @param userName => account name
     * @param board => our game board
     * @param list => our list which we and computer can choose from it update each times
     * @param row => related with settings
     * @param column => related with settings
     * @param win => related with settings
     * @param block => related with settings
     */
    public static void playWithCom(String userName , String [][] board , ArrayList<String> list , int row , int column , int win , int block) throws IOException {

        int tieMoves = (row * column) - block ;
        int count = 0 ;

        while (true){

            int result = player1Move(userName , board , list , row , column , win );
            if (result == 1){

                System.out.println("\n"+"\033[91m" + userName + " Wins !\033[0m");
                updateScores(userName , 1 , 0 , 0 , 1);
                restartGame(userName, "" , row , column ,win ,block);
                break;
            }else{
                count++;
            }

            if(count == tieMoves){

                System.out.println("\n"+"It is a Tie !");
                updateScores(userName , 0 , 0 , 1 , 1);
                restartGame(userName, "" , row , column ,win ,block);
                break;
            }
            result = computerMove(board , list , row , column , win );
            if (result == 1){

                System.out.println("\n"+"\u001b[38;5;32mComputer Wins !\u001b[0m");
                updateScores(userName , 0 , 1 ,0 , 1);
                restartGame(userName, "" , row , column ,win ,block);
                break;
            }else{
                count++;
            }

            if(count == tieMoves){

                System.out.println("\n"+"It is a Tie !");
                updateScores(userName , 0 , 0 , 1 , 1);
                restartGame(userName , "" ,row , column ,win ,block);
                break;
            }
        }
    }
    /**
     * This function is to determine the status of win, loss and draw in the form of a message in play with two players
     * @param userNameOne => username for player one
     * @param userNameTwo => username for player two
     * @param board => our game board
     * @param list => our list which we and computer can choose from it and update each times
     * @param row => related with settings
     * @param column => related with settings
     * @param win => related with settings
     * @param block => related with settings
     */
    public static void playWithPlayers(String userNameOne , String userNameTwo ,String [][] board , ArrayList<String> list , int row , int column , int win , int block)throws IOException{

        int tieMoves = (row * column) - block ;
        int count = 0 ;

        while (true){

            int result = player1Move(userNameOne , board , list , row , column , win);
            if (result == 1){

                System.out.println("\n"+"\033[91m" + userNameOne + " Wins !\033[0m");
                updateScores(userNameOne , 1 , 0 , 0 , 1);
                updateScores(userNameTwo , 0 , 1 , 0 , 1);
                restartGame(userNameOne , userNameTwo ,row , column ,win , block);
                break;
            }else{
                count++;
            }

            if(count == tieMoves){

                System.out.println("\n"+"It is a Tie !");
                updateScores(userNameOne , 0 , 0 , 1 , 1);
                updateScores(userNameTwo , 0 , 0 , 1 , 1);
                restartGame(userNameOne , userNameTwo , row , column ,win ,block);
                break;
            }

            result = player2Move(userNameTwo , board , list , row , column , win );
            if (result == 1){

                System.out.println("\n"+"\u001b[38;5;32m" + userNameTwo + " Wins !\u001b[0m");
                updateScores(userNameTwo , 1 , 0 , 0 , 1);
                updateScores(userNameOne , 0 , 1 , 0 , 1);
                restartGame(userNameOne , userNameTwo , row , column ,win ,block);
                break;
            }else{
                count++;
            }
            if(count == tieMoves){

                System.out.println("\n"+"It is a Tie !");
                updateScores(userNameOne , 0 , 0 , 1 , 1);
                updateScores(userNameTwo , 0 , 0 , 1 , 1);
                restartGame(userNameOne , userNameTwo , row , column ,win ,block);
                break;
            }
        }
    }
    /**
     * This function is to print the board every time it is updated
     * @param board => our game board
     * @param row => related with settings
     * @param column => related with settings
     */
    public static void printBoard(String[][] board , int row , int column){

        System.out.println("\u001b[33m"+"[GAME STARTED]"+"\u001b[0m");

        for (int i = 0; i < row ; i++) {
            for (int j = 0; j < column ; j++) {

                System.out.printf("| %-2s " , board[i][j] );
            }
            System.out.println("|");
        }
    }
    /**
     * This function is to check the presence of each player's move and transfer the move to the game board if it exists
     * @param username => username of player
     * @param board => our game board
     * @param list => our list which we and computer can choose from it and update each times
     * @param row => related with settings
     * @param column => related with settings
     * @param win => related with settings
     * @return => check if player1 win ore not if it returns 0 => not win if it returns 1 => win
     */
    public static int player1Move(String username , String [][] board , ArrayList<String> list , int row , int column , int win){

        String shape =  "\033[91mX \033[0m" ;
        System.out.println("\n"+ username + " Make your move : ");

        String move = input.next();
        int index = list.indexOf(move);

        if (index != -1){
            System.out.print("\033[H\033[2J");
            list.remove(index);
        }

        while(index == -1){
            System.out.println("\033[91m"+"Invalid Move. Try again"+"\033[0m");
            move = input.next();
            index = list.indexOf(move);

            if(index != -1){
                System.out.print("\033[H\033[2J");
                list.remove(move);
            }
        }

        int saveI = 0 ;
        int saveJ = 0 ;

        for (int i = 0; i < row ; i++) {
            for (int j = 0; j < column ; j++) {

                if(board[i][j].equals(move)){
                    board[i][j] = "\033[91mX \033[0m";

                    saveI = i ;
                    saveJ = j ;
                }
            }
        }

        printBoard(board , row ,column );

        int winingResult ;

        winingResult = rowCheck(board , saveI , shape , win , column);

        if (winingResult != 1){
            winingResult = columnCheck(board , saveJ , shape , win , row );
        }

        if (winingResult != 1){
            winingResult =  minorDiameterCheck(saveI , saveJ , row , column , win , board , shape) ;
        }

        if (winingResult != 1){
            winingResult = mainDiameterCheck(saveI , saveJ , row , column , win , board , shape) ;
        }
        return winingResult ;
    }
    /**
     * This function is to check the presence of each player's move and transfer the move to the game board if it exists
     * @param username => username of player
     * @param board => our game board
     * @param list =>  our list which we and computer can choose from it and update each times
     * @param row => related with settings
     * @param column => related with settings
     * @param win => related with settings
     * @return => check if player2 win ore not if it returns 0 => not win if it returns 1 => win
     */
    public static int player2Move(String username , String [][] board , ArrayList<String> list , int row , int column , int win){

        String shape = "\u001b[38;5;32mO \u001b[0m" ;

        System.out.println("\n"+ username + " Make your move : ");

        String move = input.next();
        int index = list.indexOf(move);

        if (index != -1){
            System.out.print("\033[H\033[2J");
            list.remove(index);
        }

        while(index == -1){
            System.out.println("\033[91m"+"Invalid Move. Try again"+"\033[0m");
            move = input.next();
            index = list.indexOf(move);

            if(index!=-1){
                System.out.print("\033[H\033[2J");
                list.remove(index);
            }
        }

        int saveI = 0;
        int saveJ = 0 ;

        for (int i = 0; i < row ; i++) {
            for (int j = 0; j < column ; j++) {

                if(board[i][j].equals(move)){
                    board[i][j] = "\u001b[38;5;32mO \u001b[0m";
                    saveI = i ;
                    saveJ = j ;
                }
            }
        }

        printBoard(board , row ,column );

        int winingResult ;

        winingResult = rowCheck(board , saveI , shape , win , column );

        if (winingResult != 1){
            winingResult = columnCheck(board , saveJ , shape , win , row );
        }

        if (winingResult != 1){
            winingResult= mainDiameterCheck(saveI , saveJ , row , column , win , board , shape) ;
        }

        if (winingResult != 1){
            winingResult =  minorDiameterCheck(saveI , saveJ , row , column , win , board , shape) ;
        }

        return winingResult ;
    }
    /**
     * This function is to check the presence of each computer's move and transfer the move to the game board if it exists
     * @param board => our game board
     * @param list => our list which we and computer can choose from it and update each times
     * @param row => related with settings
     * @param column => related with settings
     * @param win => related with settings
     * @return => check if computer win ore not if it returns 0 => not win if it returns 1 => win
     */
    public static int computerMove(String [][] board , ArrayList<String> list , int row , int column , int win ){

        String shape = "\u001b[38;5;32mO \u001b[0m";

        int size = list.size();
        size = size - 1 ;

        if (size == 0 ){
            size = 1 ;
        }

        Random rand = new Random();
        int index = rand.nextInt(size);

        String move = list.get(index) ;
        System.out.print("\033[H\033[2J");

        int saveI = 0 ;
        int saveJ = 0 ;

        for (int i = 0; i < row  ; i++) {
            for (int j = 0; j < column  ; j++) {

                if(board[i][j].equals(move)){
                    board[i][j] = "\u001b[38;5;32mO \u001b[0m" ;
                    saveI = i ;
                    saveJ = j ;
                }
            }
        }
        printBoard(board , row ,column );

        int winingResult ;

        winingResult = rowCheck(board , saveI , shape , win , column );

        if (winingResult != 1){
            winingResult = columnCheck(board , saveJ , shape , win , row);
        }

        if (winingResult != 1){
            winingResult = mainDiameterCheck(saveI , saveJ , row , column , win , board , shape) ;
        }

        if (winingResult != 1){
            winingResult =  minorDiameterCheck(saveI , saveJ , row , column , win , board , shape) ;
        }

        list.remove(index);
        return winingResult ;
    }
    /**
     * This function is to determine the winning probability of each player in the row
     * @param board => our game bored
     * @param x => gives use the position of the player move in row of game board
     * @param xo => give use X ore O then we can check which player (X ore O) will win
     * @param win => related with settings
     * @param column => related with settings
     * @return => check if player win ore not if it returns 0 => not win if it returns 1 => win
     */
    public static int rowCheck( String [][] board , int x , String xo , int win , int column){

        int count = 0 ;
        int winingResult = 0 ;

        for (int j = 0; j < column ; j++) {
            if (board[x][j].equals(xo)){
                count++;
                if (count == win ){
                    winingResult = 1 ;
                    break;
                }
            }else{
                count = 0 ;
            }
        }
        return winingResult ;
    }
    /**
     * This function is to determine the winning probability of each player in the column
     * @param board => our game bored
     * @param y => gives use the position of the player move in column of game board
     * @param xo => give use X ore O then we can check which player (X ore O) will win
     * @param win => related with settings
     * @param row => related with settings
     * @return => check if player win ore not if it returns 0 => not win if it returns 1 => win
     */
    public static int columnCheck( String [][] board , int y , String xo , int win , int row){

        int count = 0 ;
        int winingResult = 0 ;

        for (int i = 0; i < row ; i++) {
            if (board[i][y].equals(xo)){
                count++;
                if (count == win ){
                    winingResult = 1 ;
                    break;
                }
            }else{
                count = 0 ;
            }
        }
        return winingResult ;
    }
    /**
     * change settings and write it into file. parameters can change if player wants
     */
    public static void changeSettings(String row , String column , String block , String win){

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(row  + "\n");
            writer.write(column  + "\n");
            writer.write(block + "\n");
            writer.write(win  + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * apply default settings of game
     */
    public static void applyDefaultSetting(){

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            bufferedWriter.write( "4" + "\n"); // Row
            bufferedWriter.write( "4" + "\n"); // Column
            bufferedWriter.write( "3" + "\n"); // Blocks
            bufferedWriter.write( "3" + "\n"); // Win
            bufferedWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * This function is to determine the winning probability of each player in the main diameter of game board
     * @param row => gives use the position of the player move in row of game board
     * @param column => gives use the position of the player move in column of game board
     * @param win => related with settings
     * @param board => our game board
     * @param xo => give use X ore O then we can check which player (X ore O) will win
     * @param columnMove => the location of players move in game board => j
     * @param rowMove => the location of players move in game board => i
     * @return => check if player win ore not if it returns 0 => not win if it returns 1 => win
     */
    public static int mainDiameterCheck(int rowMove , int columnMove , int row , int column , int win ,  String [][] board , String xo){

        if ( rowMove < columnMove ){
            columnMove = columnMove - rowMove ;
            rowMove = 0 ;
        }
        if( columnMove < rowMove ){
            rowMove = rowMove - columnMove ;
            columnMove = 0 ;
        }
        if( rowMove == columnMove ){
            rowMove = 0 ;
            columnMove = 0 ;
        }

        int winingResult = 0 ;
        int count = 0 ;
        while (true){

            if( board[rowMove][columnMove].equals(xo) ){
                count++ ;
                if(count == win){
                    winingResult = 1 ;
                    break;
                }
            }else{
                count = 0 ;
            }

            if( rowMove == (row -1) || columnMove == (column -1) ){
                break;
            }
            rowMove++;
            columnMove++;

        }
        return winingResult ;
    }
    /**
     * This function is to determine the winning probability of each player in the minor diameter of game board
     * @param row => gives use the position of the player move in row of game board
     * @param column => gives use the position of the player move in column of game board
     * @param win => related with settings
     * @param board => our game board
     * @param xo => give use X ore O then we can check which player (X ore O) will win
     * @param rowMove => the location of players move in game board => i
     * @param columnMove => the location of players move in game board => j
     * @return => check if player win ore not if it returns 0 => not win if it returns 1 => win
     */
    public static int minorDiameterCheck(int rowMove , int columnMove , int row , int column , int win ,  String [][] board , String xo){

        if( (rowMove + columnMove) <= (column -1) ){

            columnMove = columnMove + rowMove ;
            rowMove = 0 ;
        }else{
            int distance = (column -1) - columnMove ;
            columnMove = column - 1 ;
            rowMove = rowMove - distance ;
        }

        int winingResult = 0 ;
        int count = 0 ;

        while (true){

            if( board[rowMove][columnMove].equals(xo) ){
                count++ ;
                if( count == win ){
                    winingResult = 1 ;
                    break;
                }
            }else{
                count = 0 ;
            }

            if( rowMove == (row -1) || columnMove == 0 ){
                break;
            }
            rowMove++;
            columnMove--;

        }
        return winingResult ;
    }
    /**
     * this function is for changing settings
     */
    public static void changeSettingsMenu() {

        File exist = new File(fileName);
        if (!exist.exists()) {
            applyDefaultSetting();
        }

        System.out.println("\033[3m" +"Welcome! in this game you can change the way you want to play");
        System.out.println("1. Change setting");
        System.out.println("2. Apply default settings");
        System.out.println("3. Skip and continue ");
        System.out.println("Make your choice :" + "\033[m");

        String choice = input.next();

        if (choice.equals("1") || choice.equals("2") || choice.equals("3")) {
            System.out.print("\033[H\033[2J");
        }

        label:
        while (true) {

            switch (choice) {
                case "1":
                    System.out.println("\u001b[33m" + "[ CHANG SETTING ]" + "\u001b[0m");
                    System.out.println("\033[3m" + "How many rows do you want the game board have? ");
                    String row = input.next();
                    while (true) {
                        while (true) {
                            try {
                                Integer.parseInt(row);
                                break ;
                            } catch (Exception e) {
                                System.out.println("\033[91m" + "Not acceptable. Enter a \"number\" try again" + "\033[0m");
                                row = input.next();
                            }
                        }

                        if (Integer.parseInt(row) <= 0) {
                            System.out.println("\033[91m" + "The given number for row is not acceptable. try again" + "\033[0m");
                            row = input.next();
                        } else {
                            break;
                        }
                    }
                    System.out.println("How many columns do you want the game board have?");
                    String column = input.next();
                    while (true) {
                        while (true) {
                            try {
                                Integer.parseInt(column);
                                break ;
                            } catch (Exception e) {
                                System.out.println("\033[91m" + "Not acceptable. Enter a \"number\" try again" + "\033[0m");
                                column = input.next();
                            }
                        }

                        if (Integer.parseInt(column) <= 0) {
                            System.out.println("\033[91m" + "The given number for column is not acceptable. try again" + "\033[0m");
                            column = input.next();
                        } else {
                            break;
                        }
                    }
                    System.out.println("How many blocks do you want the game board have?");
                    String blocks = input.next();

                    int result = Integer.parseInt(row) * Integer.parseInt(column);
                    while (true) {
                        while (true) {
                            try {
                                Integer.parseInt(blocks);
                                break ;
                            } catch (Exception e) {
                                System.out.println("\033[91m" + "Not acceptable. Enter a \"number\" try again" + "\033[0m");
                                blocks = input.next();
                            }
                        }

                        if (Integer.parseInt(blocks) >= result) {
                            System.out.println("\033[91m" + "The given number for blocks is not acceptable. try again" + "\033[0m");
                            blocks = input.next();
                        } else {
                            break;
                        }
                    }
                    System.out.println("How many squares you want to determine the winner of the game");
                    String win = input.next();
                    while (true) {
                        while (true) {
                            try {
                                Integer.parseInt(win);
                                break ;
                            } catch (Exception e) {
                                System.out.println("\033[91m" + "Not acceptable. Enter a \"number\" try again" + "\033[0m");
                                win = input.next();
                            }
                        }

                        if (Integer.parseInt(win) <= 0 || Integer.parseInt(win) == 1) {
                            System.out.println("\033[91m" + "The given number for wining is not acceptable. try again" + "\033[0m");
                            win = input.next();
                        } else {
                            break;
                        }
                    }
                    changeSettings(row, column, blocks, win);
                    break label;

                case "2":
                    applyDefaultSetting();
                    break label;
                case "3":
                    break label;
                default:

                    System.out.println("\033[91m" + "Wrong Command. Try again" + "\033[0m" + "\033[m");
                    choice = input.next();

                    if (choice.equals("1") || choice.equals("2") || choice.equals("3")) {
                        System.out.print("\033[H\033[2J");
                    }
                    break;
            }
        }
    }
    /**
     * this function is for apply settings
     */
    public static void readSettings() throws IOException {

        String line ;
        int row = 0 ;
        int column = 0;
        int blocks = 0 ;
        int win = 0 ;
        int count = 0 ;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null){
                count++ ;
                if(count == 1){
                    row = Integer.parseInt(line) ;
                }
                if(count == 2){
                    column = Integer.parseInt(line) ;
                }
                if(count == 3){
                    blocks = Integer.parseInt(line) ;
                }
                if(count == 4){
                    win = Integer.parseInt(line) ;
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        startGame(row , column , win , blocks);
    }
    /**
     * the body of game runs in this function
     * parameters related with settings
     */
    public static void startGame(int row , int column , int win , int block) throws IOException{

        int bordCount = 0 ;
        String [][] gameBoard = new String[row][column] ;

        for (int i = 0; i < row ; i++) {
            for (int j = 0; j < column ; j++) {
                bordCount++;
                String StringBordCount = String.valueOf(bordCount) ;
                gameBoard[i][j] = StringBordCount ;
            }
        }

        int counter = row * column ;

        ArrayList<String> defaultList = new ArrayList<>();
        for (int n=1; n <= counter; n++) {
            String convertN = String.valueOf(n) ;
            defaultList.add(convertN);
        }
        Collections.shuffle(defaultList);

        for (int n = 0 ; n < block ; n++) {

            String blocks = defaultList.get(n) ;

            for (int k = 0; k < row ; k++) {
                for (int j = 0; j < column; j++) {

                    if (gameBoard[k][j].equals(blocks)){
                        gameBoard[k][j] = "\u001b[33m# \u001b[0m" ;
                    }
                }
            }
        }

        if (block > 0) {
            defaultList.subList(0, block).clear();
        }

        System.out.print("\033[H\033[2J");
        System.out.println("\033[m" +"\u001b[33m"+"[ CHANGES APPLIED ]"+"\u001b[0m");
        System.out.println("\033[3m" + "Welcome again! Do you want to play with two players or play with computer ?");
        System.out.println("""
                1. Play with two players
                2. Play with computer
                3. Exit
                Make your choice :\s""" + "\033[m");

        String choice = input.next() ;
        if(choice.equals("1") || choice.equals("2") || choice.equals("3")){
            System.out.print("\033[H\033[2J");
        }

        String accountName1 ;
        String accountName2 ;

        label:
        while (true) {
            switch (choice) {
                case "2":
                    System.out.println("\u001b[33m" + "[ PLAYER 1 ]" + "\u001b[0m");
                    accountName1 = makeAccount();

                    printBoard(gameBoard, row, column);
                    playWithCom(accountName1, gameBoard, defaultList, row, column, win, block);

                    break label;
                case "1":

                    System.out.println("\u001b[33m" + "[ PLAYER 1 ]" + "\u001b[0m");
                    accountName1 = makeAccount();

                    System.out.println("\u001b[33m" + "[ PLAYER 2 ]" + "\u001b[0m");
                    accountName2 = makeAccount();

                    printBoard(gameBoard, row, column);
                    playWithPlayers(accountName1, accountName2, gameBoard, defaultList, row, column, win, block);

                    break label;
                case "3":
                    break label;
                default:

                    System.out.println("\033[91m" + "\033[3m" + "Wrong Command. Try again" + "\033[m" + "\033[0m");
                    choice = input.next();

                    if (choice.equals("1") || choice.equals("2")) {
                        System.out.print("\033[H\033[2J");
                    }
                    break;
            }
        }
    }
    /**
     * This function is for restart game
     * @param userNameOne => username of player 1
     * @param userNameTwo => username of player 2
     * @param row => related with settings
     * @param column => related with settings
     * @param win => related with settings
     * @param block => related with settings
     */
    public static void restartGame(String userNameOne , String userNameTwo ,int row , int column , int win , int block) throws IOException {

        System.out.println("\033[3m" + "Restart game with enter (R)");
        System.out.println("Exit from game with enter (E)");
        System.out.println("Show info with enter (I)"  + "\033[m" );
        String userChoice = input.next();

        label:
        while (true){

            switch (userChoice) {
                case "R":
                    System.out.print("\033[H\033[2J");
                    changeSettingsMenu();
                    startGame(row, column, win, block);
                    break label;

                case "E":
                    System.out.print("\033[H\033[2J");
                    break label;

                case "I":
                    printInfo(userNameOne);
                    if(userNameTwo != null){
                        printInfo(userNameTwo);
                    }
                    break label;
                default:
                    System.out.println("\033[91m" + "Wrong command . try again" + "\033[0m");
                    userChoice = input.next();
                    break;
            }
        }
    }
    /**
     * This function is for creating account file for save data of players
     */
    public static void creatAccountFile(){

        File exist = new File(accountFileName);
        if (!exist.exists()) {

            try {
                BufferedWriter BufferedWriter = new BufferedWriter(new FileWriter(accountFileName));
                BufferedWriter.write( "DATA" + "\n");
                BufferedWriter.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    /**
     * This function is for show creating account menu and creat account
     */
    public static String makeAccount() throws IOException {

        String accountName = null;
        int stop = 0 ;
        System.out.println("\u001b[33m"+"[ SIGN IN / SIGN UP ]"+"\u001b[0m");
        System.out.println("\033[3m" + "You must be registered before to play the game");
        System.out.println("1. Creat account");
        System.out.println("2. Already have an account ");
        System.out.println("Make your choice :" + "\033[m");
        String choice = input.next() ;

        while (stop == 0){
            switch (choice) {
                case "1" -> {
                    stop = 1;
                    accountName = newRegistered();
                }
                case "2" -> {
                    stop = 1;
                    accountName = alreadyRegistered();
                }
                default -> {
                    System.out.println("\033[91m" + "\033[3m" + "Wrong command . try again" + "\033[0m" + "\033[0m");
                    choice = input.next();
                }
            }
        }
        return accountName ;
    }
    /**
     * This function is for creating account for new player
     */
    public static String newRegistered(){

        System.out.println("\033[3m" + "Enter your username : " + "\033[0m" );
        String username =  input.next();
        String line ;

        int stopReading = 0 ;
        while (stopReading == 0) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(accountFileName));
                while ((line = bufferedReader.readLine()) != null) {

                    if (username.equals(line)) {
                        System.out.println("\033[91m" +"\033[3m" + "Already taken . Choose another on :" + "\033[0m" +"\033[0m");
                        stopReading = 0;
                        break;
                    }else{
                        stopReading = 1 ;
                    }
                }

                if (stopReading == 0 ){
                    username = input.next() ;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        String password ;
        while (true) {

            System.out.println("\033[3m" + "Enter your password : " + "\033[0m");
            password = input.next();
            System.out.println("\033[3m" + "Enter your password again : " + "\033[0m");
            String  repeatPassword = input.next();
            if((password.equals(repeatPassword))){
                break;
            }else{
                System.out.println("\033[91m" + "\033[3m" + "Wrong password .Try again : " + "\033[0m" + "\033[0m");
            }
        }

        writeWithoutOverWrite(username , password , 0 , 0 , 0 , 0);
        return username ;
    }
    /**
     * This function is for loge in into an exists account
     */
    public static String alreadyRegistered() throws IOException {

        System.out.println("\033[3m" + "Enter your username : " + "\033[0m") ;
        String username = input.next() ;

        int stopReading = 0 ;
        int count = 0 ;
        String line ;

        while (stopReading == 0) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(accountFileName));
                while ((line = bufferedReader.readLine()) != null) {
                    count++ ;
                    if (username.equals(line)) {
                        System.out.println("\033[3m" + "Enter password : " + "\033[0m");
                        stopReading = 1 ;
                        break;
                    }
                }

                if (stopReading == 0 ){
                    System.out.println("\033[91m" + "\033[3m" + "Wrong username. Try again : " + "\033[0m" + "\033[0m");
                    count = 0 ;
                    username = input.next() ;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        int wrongPass = 0 ;
        String passWord = input.next();

        while (wrongPass == 0){
            if(passWord.equals(Files.readAllLines(Paths.get(accountFileName)).get(count))){
                wrongPass = 1 ;
            }else{
                System.out.println("\033[91m" + "\033[3m" + "Wrong password. Try again : " + "\033[0m" + "\033[0m");
                passWord = input.next();
            }
        }
        return username ;
    }
    /**
     * This function is use for write update scores and put default scores in file
     */
    public static void writeWithoutOverWrite(String accountName , String pass , int defaultWin , int defaultLost , int defaultTie , int defaultGame){

        try{
            FileWriter firstStream = new FileWriter(accountFileName,true);
            BufferedWriter out = new BufferedWriter(firstStream);
            out.write(accountName + "\n");
            out.write(pass + "\n");
            out.write(defaultWin+ "\n"); // win
            out.write(defaultLost + "\n"); //lost
            out.write(defaultTie + "\n"); // tie
            out.write(defaultGame + "\n"); // games

            out.close();
        }catch (Exception e){
            System.err.println("Error while writing to file: " +
                    e.getMessage());
        }
    }

    /**
     * This function is for printing scores if players want
     */
    public static void printInfo (String accountName) throws IOException {

        String line ;
        int count = 0 ;
        int stopReading = 0 ;

        while (stopReading == 0) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(accountFileName));
                while ((line = bufferedReader.readLine()) != null) {
                    count++;
                    if (accountName.equals(line)) {
                        stopReading = 1;
                        break;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("\n" + "\u001b[33m"+"[ SCORES ]"+"\u001b[0m");
        System.out.println("USERNAME : " + accountName);
        System.out.println("PASSWORD : " + Files.readAllLines(Paths.get(accountFileName)).get(count));
        System.out.println("WIN : " + Files.readAllLines(Paths.get(accountFileName)).get(count+1));
        System.out.println("LOST : " + Files.readAllLines(Paths.get(accountFileName)).get(count+2));
        System.out.println("TIE : " + Files.readAllLines(Paths.get(accountFileName)).get(count+3));
        System.out.println("GAME : " + Files.readAllLines(Paths.get(accountFileName)).get(count+4));

    }
    /**
     * This function is for update scores and write it
     */
    public static void updateScores(String accountName , int win , int lost , int tie , int game) throws IOException {

        String line ;
        int count = 0 ;
        int stopReading = 0 ;

        while (stopReading == 0) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(accountFileName));
                while ((line = bufferedReader.readLine()) != null) {
                    count++;
                    if (accountName.equals(line)) {
                        stopReading = 1;
                        break;
                    }
                }
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String pass = Files.readAllLines(Paths.get(accountFileName)).get(count);
        int wins = Integer.parseInt(Files.readAllLines(Paths.get(accountFileName)).get(count+1));
        int losts = Integer.parseInt(Files.readAllLines(Paths.get(accountFileName)).get(count+2));
        int ties = Integer.parseInt(Files.readAllLines(Paths.get(accountFileName)).get(count+3));
        int games = Integer.parseInt(Files.readAllLines(Paths.get(accountFileName)).get(count+4));

        wins += win ;
        losts += lost ;
        ties += tie ;
        games += game ;

        ArrayList<String> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(accountFileName));
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }

            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int index = list.indexOf(accountName);
        list.remove(accountName);
        for (int i = 1; i <= 5 ; i++) {
            list.remove(index);
        }

        String stringLine ;
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(accountFileName));
            for (String saveList : list) {
                stringLine = saveList;
                bufferedWriter.write(stringLine + "\n");
            }

            bufferedWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        writeWithoutOverWrite(accountName , pass  , wins , losts , ties , games);
    }
}


