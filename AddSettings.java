import java.io.*;
import java.util.*;
/**
 * wait for updates :)
 * @author Samin Mehran
 */
public class AddSettings {
    static Scanner input = new Scanner(System.in);
    public static String fileName = "settings.txt" ;
    public static void main(String[] args) {
        changeSettingsMenu();
        readSettings();
    }
    /**
     * This function is to determine the status of win, loss and draw in the form of a message in play with computer
     * @param board => our game board
     * @param list => our list which we and computer can choose from it update each times
     */
    public static void playWithCom(String [][] board , ArrayList<String> list , int row , int column , int win , int block){

        int tieMoves = (row * column) - block ;
        int count = 0 ;

        while (true){

            int result = player1Move(board , list , row , column , win );
            if (result == 1){

                System.out.println("\n"+"\033[91mPlayer1 Wins !\033[0m");
                restartGame(row , column ,win ,block);
                break;
            }else{
                count++;
            }

            if(count == tieMoves){

                System.out.println("\n"+"It is a Tie !");
                restartGame(row , column ,win ,block);
                break;
            }
            result = computerMove(board , list , row , column , win );
            if (result == 1){

                System.out.println("\n"+"\u001b[38;5;32mComputer Wins !\u001b[0m");
                restartGame(row , column , win , block);
                break;
            }else{
                count++;
            }

            if(count == tieMoves){

                System.out.println("\n"+"It is a Tie !");
                restartGame(row , column ,win ,block);
                break;
            }
        }
    }
    /**
     * This function is to determine the status of win, loss and draw in the form of a message in play with two players
     * @param board => our game board
     * @param list => our list which we and computer can choose from it and update each times
     */
    public static void playWithPlayers(String [][] board , ArrayList<String> list , int row , int column , int win , int block){

        int tieMoves = (row * column) - block ;
        int count = 0 ;

        while (true){

            int result = player1Move(board , list , row , column , win);
            if (result == 1){

                System.out.println("\n"+"\033[91mPlayer1 Wins !\033[0m");
                restartGame(row , column ,win , block);
                break;
            }else{
                count++;
            }

            if(count == tieMoves){

                System.out.println("\n"+"It is a Tie !");
                restartGame(row , column ,win ,block);
                break;
            }

            result = player2Move(board , list , row , column , win );
            if (result == 1){

                System.out.println("\n"+"\u001b[38;5;32mPlayer2 Wins !\u001b[0m");
                restartGame(row , column ,win ,block);
                break;
            }else{
                count++;
            }
            if(count == tieMoves){

                System.out.println("\n"+"It is a Tie !");
                restartGame(row , column ,win ,block);
                break;
            }
        }
    }
    /**
     * This function is to print the board every time it is updated
     * @param board => our game board
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
     * @param board => our game board
     * @param list => our list which we and computer can choose from it and update each times
     * @return => check if player1 win ore not if it returns 0 => not win if it returns 1 => win
     */
    public static int player1Move(String [][] board , ArrayList<String> list , int row , int column , int win){

        String shape =  "\033[91mX \033[0m" ;
        System.out.println("\n"+"Player1 Make your move : ");

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
     * @param board => our game board
     * @param list =>  our list which we and computer can choose from it and update each times
     * @return => check if player2 win ore not if it returns 0 => not win if it returns 1 => win
     */
    public static int player2Move(String [][] board , ArrayList<String> list , int row , int column , int win){

        String shape = "\u001b[38;5;32mO \u001b[0m" ;

        System.out.println("\n"+"Player2 Make your move : ");

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
     * @return => => check if computer win ore not if it returns 0 => not win if it returns 1 => win
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
    public static void readSettings(){

        String line ;
        int intRow = 0 ;
        int intColumn = 0;
        int intBlocks = 0 ;
        int intWin = 0 ;
        int count = 0 ;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null){
                count++ ;
                if(count == 1){
                    intRow = Integer.parseInt(line) ;
                }
                if(count == 2){
                    intColumn = Integer.parseInt(line) ;
                }
                if(count == 3){
                    intBlocks = Integer.parseInt(line) ;
                }
                if(count == 4){
                    intWin = Integer.parseInt(line) ;
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        startGame(intRow , intColumn , intWin , intBlocks);
    }
    /**
     * the body of game runs in this function
     */
    public static void startGame(int row , int column , int win , int block){

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

        int startGame = 0 ;

        label:
        while (startGame == 0) {
            switch (choice) {
                case "2":
                    printBoard(gameBoard, row, column);
                    playWithCom(gameBoard, defaultList, row, column, win, block);
                    startGame = 1;

                    break;
                case "1":
                    printBoard(gameBoard, row, column);
                    playWithPlayers(gameBoard, defaultList, row, column, win, block);
                    startGame = 1;

                    break;
                case "3":
                    break label;
                default:

                    System.out.println("\033[3m" + "Wrong Command. Try again" + "\033[m");
                    choice = input.next();

                    if (choice.equals("1") || choice.equals("2")) {
                        System.out.print("\033[H\033[2J");
                    }
                    break;
            }
        }
    }
    /**
     * this function is for restart game
     * @param row => need for startGame function
     * @param column => need for startGame function
     * @param win => need for startGame function
     * @param block => need for startGame function
     */
    public static void restartGame(int row , int column , int win , int block){

        System.out.println("\033[3m" + "Restart game with enter (R)");
        System.out.println("Exit from game with enter (E)" + "\033[m");
        String userChoice = input.next();

        while (true){

            if(userChoice.equals("R")){
                System.out.print("\033[H\033[2J");
                changeSettingsMenu();
                startGame(row , column , win , block);
                break;

            }else if(userChoice.equals("E")){
                System.out.print("\033[H\033[2J");
                break;

            }else {
                System.out.println("\033[0m"+"Wrong command . try again"+"\033[0m");
                userChoice = input.next();
            }
        }
    }
}