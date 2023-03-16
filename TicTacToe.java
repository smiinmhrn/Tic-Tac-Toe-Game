import java.util.*;
/**
 * wait for updates :)
 * @author Samin Mehran
 */
public class TicTacToe {
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        startGame();
    }
    /**
     * this function is for restart game if the players wants
     */
    public static void restartGame(){

        System.out.println("\033[3m" + "Restart game with enter (R)");
        System.out.println("Exit from game with enter (E)" + "\033[m");
        String userChoice = input.next();

        while (true){

            if(userChoice.equals("R")){
                System.out.print("\033[H\033[2J");
                startGame();
                break;

            }else if(userChoice.equals("E")){
                System.out.print("\033[H\033[2J");
                break;

            }else {
                System.out.println("\033[91m" +"\033[3m" + "Wrong command . try again" + "\033[m" + "\033[0m" );
                userChoice = input.next();
            }
        }
    }
    /**
     * this function is our game body and first Menu. Why "first Menu" ? wait for updates :)
     */
    public static void startGame(){

        String [][] gameBoard = {
                {"1"  , "2"  , "3"  , "4" },
                {"5"  , "6"  , "7"  , "8" },
                {"9"  , "10"  , "11"  , "12" },
                {"13"  , "14"  , "15"  , "16" },
        };

        ArrayList<String> defaultList = new ArrayList<>();

        for (int n = 1; n < 17; n++) {
            String convertN = String.valueOf(n);
            defaultList.add(convertN);
        }
        Collections.shuffle(defaultList);

        for (int n = 0 ; n < 3 ; n++) {

            String block = defaultList.get(n);

            for (int k = 0; k < 4 ; k++) {
                for (int j = 0; j < 4; j++) {

                    if (gameBoard[k][j].equals(block)){
                        gameBoard[k][j] = "\u001b[33m# \u001b[0m" ;
                    }
                }
            }
        }

        defaultList.remove(0);
        defaultList.remove(0);
        defaultList.remove(0);

        System.out.println("\033[3m"+"Welcome! Do you want to play with two players or play with computer ?");
        System.out.println("""
                1. Play with two players
                2. Play with computer
                3. Exit
                Make your choice :\s""" + "\033[m");

        String choice = input.next() ;
        if(choice.equals("1") || choice.equals("2")){
            System.out.print("\033[H\033[2J");
        }

        int startGame = 0;

        while (startGame == 0){

            if(choice.equals("3")){
                break;
            }

            switch (choice) {
                case "1" -> {
                    printBoard(gameBoard);
                    playWithPlayers(gameBoard , defaultList);
                    startGame = 1;
                }
                case "2" -> {
                    printBoard(gameBoard);
                    playWithCom(gameBoard, defaultList);
                    startGame = 1;
                }
                default -> {
                    System.out.println("\033[91m" + "\033[3m" + "Wrong Command. Try again" + "\033[m" + "\033[0m");
                    choice = input.next();
                    if(choice.equals("1") || choice.equals("2")){
                        System.out.print("\033[H\033[2J");
                    }
                }
            }
        }
    }
    /**
     * This function is to determine the status of win, loss and draw in the form of a message in play with computer
     * @param board => our game board
     * @param list => our list which we and computer can choose from it update each times
     */
    public static void playWithCom(String [][] board , ArrayList<String> list){

        int count = 0;

        while (true){

            int result = player1Move(board , list);

            if (result == 1){

                System.out.println("\n" + "\033[91mPlayer1 Wins !\033[0m");
                restartGame();

                break;
            }else{
                count++;
            }

            if(count == 13){

                System.out.println("\n" + "It is a Tie !");
                restartGame();
                break;
            }

            result = computerMove(board , list);

            if (result == 1){

                System.out.println("\n" + "\u001b[38;5;32mComputer Wins !\u001b[0m");
                restartGame();
                break;

            }else{
                count++;
            }
        }
    }
    /**
     * This function is to determine the status of win, loss and draw in the form of a message in play with two players
     * @param board => our game board
     * @param list => our list which we and computer can choose from it and update each times
     */
    public static void playWithPlayers(String [][] board , ArrayList<String> list){

        int count = 0;

        while (true){

            int result = player1Move(board , list);

            if (result == 1){

                System.out.println("\n" + "\033[91mPlayer1 Wins !\033[0m");
                restartGame();
                break;

            }else{
                count++;
            }

            if(count == 13){

                System.out.println("\n" + "It is a Tie !");
                restartGame();
                break;
            }

            result = player2Move(board , list);

            if (result == 1){

                System.out.println("\n" + "\u001b[38;5;32mPlayer2 Wins !\u001b[0m");
                restartGame();
                break;

            }else{
                count++;
            }
        }
    }
    /**
     * This function is to print the board every time it is updated
     * @param board => our game board
     */
    public static void printBoard(String[][] board){

        System.out.println("\u001b[33m" + "   [GAME STARTED]  " + "\u001b[0m");

        for (int i = 0; i < 4 ; i++) {
            for (int j = 0; j < 4; j++) {

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
    public static int player1Move(String [][] board , ArrayList<String> list){

        String shape =  "\033[91mX \033[0m";
        System.out.println("\n" + "Player1 Make your move : ");

        String move = input.next();
        int index = list.indexOf(move);

        if (index != -1){
            System.out.print("\033[H\033[2J");
            list.remove(index);
        }

        while(index == -1){
            System.out.println("\033[91m" + "Invalid Move. Try again" + "\033[0m");
            move = input.next();
            index = list.indexOf(move);

            if(index!=-1){
                System.out.print("\033[H\033[2J");
                list.remove(move);
            }
        }

        int saveI = 0 ;
        int saveJ = 0 ;

        for (int i = 0; i < 4 ; i++) {
            for (int j = 0; j < 4; j++) {

                if(board[i][j].equals(move)){
                    board[i][j] = "\033[91mX \033[0m";

                    saveI = i;
                    saveJ = j;
                }
            }
        }

        printBoard(board);

        int winingResult ;

        winingResult = rowCheck(board , saveI , shape );

        if (winingResult != 1){
            winingResult = columnCheck(board , saveJ , shape);
        }

        if (winingResult != 1){
            winingResult = minorDiameterCheck(saveI , saveJ , board , shape);
        }

        if (winingResult != 1){
            winingResult = mainDiameterCheck(saveI , saveJ , board , shape);
        }

        return winingResult;
    }
    /**
     * This function is to check the presence of each player's move and transfer the move to the game board if it exists
     * @param board => our game board
     * @param list =>  our list which we and computer can choose from it and update each times
     * @return => check if player2 win ore not if it returns 0 => not win if it returns 1 => win
     */
    public static int player2Move(String [][] board , ArrayList<String> list){

        String shape = "\u001b[38;5;32mO \u001b[0m" ;
        System.out.println("\n" + "Player2 Make your move : ");

        String move = input.next();
        int index = list.indexOf(move);

        if (index != -1){
            System.out.print("\033[H\033[2J");
            list.remove(index);
        }

        while(index == -1){

            System.out.println("\033[91m" + "Invalid Move. Try again" + "\033[0m");
            move = input.next();
            index = list.indexOf(move);

            if(index!=-1){
                System.out.print("\033[H\033[2J");
                list.remove(index);
            }
        }

        int saveI = 0;
        int saveJ = 0;

        for (int i = 0; i < 4 ; i++) {
            for (int j = 0; j < 4; j++) {

                if(board[i][j].equals(move)){
                    board[i][j] = "\u001b[38;5;32mO \u001b[0m";
                    saveI = i ;
                    saveJ = j ;
                }
            }
        }

        printBoard(board);

        int winingResult ;

        winingResult = rowCheck(board , saveI , shape );

        if (winingResult != 1){
            winingResult = columnCheck(board , saveJ , shape);
        }

        if (winingResult != 1){
            winingResult = mainDiameterCheck(saveI , saveJ , board , shape);
        }

        if (winingResult != 1){
            winingResult = minorDiameterCheck(saveI , saveJ , board , shape);
        }

        return winingResult ;
    }
    /**
     * This function is to check the presence of each computer's move and transfer the move to the game board if it exists
     * @param board => our game board
     * @param list => our list which we and computer can choose from it and update each times
     * @return => => check if computer win ore not if it returns 0 => not win if it returns 1 => win
     */
    public static int computerMove(String [][] board , ArrayList<String> list){

        String shape = "\u001b[38;5;32mO \u001b[0m";

        int size = list.size();
        size = size - 1 ;

        if (size == 0){
            size = 1 ;
        }

        Random rand = new Random();
        int index = rand.nextInt(size);

        String move = list.get(index) ;
        System.out.print("\033[H\033[2J");

        int saveI = 0;
        int saveJ = 0;

        for (int i = 0; i <4 ; i++) {
            for (int j = 0; j <4 ; j++) {

                if(board[i][j].equals(move)){
                    board[i][j] = "\u001b[38;5;32mO \u001b[0m" ;
                    saveI = i;
                    saveJ = j;
                }
            }
        }

        printBoard(board);

        int winingResult ;

        winingResult = rowCheck(board , saveI , shape );

        if (winingResult != 1){
            winingResult = columnCheck(board , saveJ , shape);
        }

        if (winingResult != 1){
            winingResult = mainDiameterCheck (saveI , saveJ , board , shape);
        }

        if (winingResult != 1){
            winingResult = minorDiameterCheck(saveI , saveJ , board , shape);
        }
        list.remove(index);
        return winingResult ;
    }
    /**
     * This function is to determine the winning probability of each player in the row
     * @param board => our game bored
     * @param row => gives use the position of the player move in row of game board
     * @param xo => give use X ore O then we can check which player (X ore O) will win
     * @return => check if player win ore not if it returns 0 => not win if it returns 1 => win
     */
    public static int rowCheck( String [][] board , int row , String xo){

        int count = 0 ;
        int winingResult = 0 ;

        for (int j = 0; j < 4 ; j++) {

            if (board[row][j].equals(xo)){
                count++;
                if (count == 3){
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
     * @param column => gives use the position of the player move in column of game board
     * @param xo => give use X ore O then we can check which player (X ore O) will win
     * @return => check if player win ore not if it returns 0 => not win if it returns 1 => win
     */
    public static int columnCheck( String [][] board , int column , String xo ){

        int count = 0 ;
        int winingResult = 0 ;

        for (int i = 0; i < 4 ; i++) {

            if (board[i][column].equals(xo)){
                count++;
                if (count == 3){
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
     * This function is to determine the winning probability of each player in the main diameter of game board
     * @param row => gives use the position of the player move in row of game board
     * @param column => gives use the position of the player move in column of game board
     * @param board => our game board
     * @param xo => give use X ore O then we can check which player (X ore O) will win
     * @return => check if player win ore not if it returns 0 => not win if it returns 1 => win
     */
    public static int mainDiameterCheck(int row , int column  ,String [][] board , String xo){

        if ( row < column){
            column = column - row ;
            row = 0 ;
        }
        if( column < row){
            row = row - column ;
            column = 0 ;
        }
        if( row == column){
            row = 0 ;
            column = 0 ;
        }

        int winingResult = 0 ;
        int count = 0 ;
        while (true){

            if(board[row][column].equals(xo)){
                count++ ;
                if(count == 3){
                    winingResult = 1 ;
                    break;
                }
            }else{
                count = 0 ;
            }
            if( row == 3 || column == 3 ){
                break;
            }
            row++;
            column++;

        }
        return winingResult ;
    }
    /**
     * This function is to determine the winning probability of each player in the minor diameter of game board
     * @param row => gives use the position of the player move in row of game board
     * @param column => gives use the position of the player move in column of game board
     * @param board => our game board
     * @param xo => give use X ore O then we can check which player (X ore O) will win
     * @return => check if player win ore not if it returns 0 => not win if it returns 1 => win
     */
    public static int minorDiameterCheck(int row , int column ,  String [][] board , String xo){

        if( (row + column) <= 3){
            column = column + row ;
            row = 0 ;
        }else{
            int distance = 3 - column ;
            column = 3 ;
            row = row - distance ;
        }

        int winingResult = 0 ;
        int count = 0 ;

        while (true){

            if(board[row][column].equals(xo)){
                count++ ;
                if(count == 3){
                    winingResult = 1 ;
                    break;
                }
            }else{
                count = 0 ;
            }

            if( row == 3 || column == 0 ){
                break;
            }
            row++;
            column--;
        }
        return winingResult ;
    }

}
