package it.polimi.ingsw.model;
import java.util.List;

/**
 * A player is constructed on his unique name. It has his own shelf and points (initialized as 0 and only addable).
 * Players are called to pick, choose order, and insert tiles from board to shelf. The process is completely checked to
 * avoid illegal moves.
 *
 * @author Pietro Andrea Niedda
 */
public class Player {

    private final String name;

    private int points;

    private final PersonalGoal personalGoal;

    private final Shelf shelf;

    /**
     * Class constructor.
     *
     * @param name is the player's name
     * @param personalGoalNumber is the sorted out number for player's personal goal
     * @author Pietro Andrea Niedda
     */
    public Player(String name, int personalGoalNumber) {
        this.name = name;
        points = 0;
        shelf = new Shelf();
        personalGoal = new PersonalGoal(personalGoalNumber);
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    public String getName(){
        return name;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    public int getPoints(){
        return points;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    public PersonalGoal getPersonalGoal(){
        return personalGoal;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    public Shelf getShelf(){
        return shelf;
    }

    /**
     * //TODO java doc is to be written
     *
     * @param points
     * @author Pietro Andrea Niedda
     */
    public void addPoints(int points) {
        this.points = this.points + points;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @param board
     * @author Pietro Andrea Niedda
     */
    public List<Coordinates> pickTiles(Board board) {
        
         int x = 0, y = 0, picks =checkAvailablePickNumber();
         ArrayList<Coordinates> list = new ArrayList<>();
         Scanner scanner = new Scanner(System.in);

         for(int i = 0; i < picks; i++){
         do{
         System.out.println("Choose x");
         x = scanner.nextInt();
         System.out.println("Choose y");
         y = scanner.nextInt();
         list.add(new Coordinates(x, y));
         if(!board.checkSelection(x, y))
         System.out.println("invalid pick, try again");
         }while(!board.checkSelection(x, y));

         System.out.println("done?");
         if(scanner.nextLine() == "y") i=4;
         }

        return null;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    private int checkAvailablePickNumber() {
        int picks=3;

        for(int i = 0; i < shelf.getColumn(); i++)
            if((shelf.getRow() - shelf.getTilesInColumn(i)) < picks) picks = shelf.getRow() - shelf.getTilesInColumn(i);

        return picks;
    }

    /**
     * //TODO java doc is to be written
     *
     * @param tiles
     * @author Pietro Andrea Niedda
     */
    public void insertTiles(List<Tile> tiles) {
        int column = getColumnChoice(tiles);

        chooseOrder(tiles);
        shelf.insertInColumn(tiles, column);
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    private int getColumnChoice() {
        int column;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose column");

        while(true){
            column = scanner.nextInt();

            if((column < shelf.getColumn() && column > -1) && shelf.getTilesInColumn(column) != shelf.getColumn() && tiles.size() > (shelf.getColumn() - shelf.getTilesInColumn(column)))
                return column;

            System.out.println("Invalid value, try again");
        }
        return 0;
    }

    /**
     * //TODO java doc is to be written
     *
     * @param tiles
     * @author Pietro Andrea Niedda
     */
    private void chooseOrder(List<Tile> tiles) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Tile> temp = new ArrayList<>();
        ArrayList<Integer> choosenIndex = new ArrayList<>();

        int index = 0;

        for(int i = 0; i < tiles.size(); i++){

            do {
                index = scanner.nextInt();

                if((index <tiles.size() && index > -1) || choosenIndex.contains(index))
                    System.out.println("Invalid value, try again");
                temp.add(tiles.get(index));
            }while(!(index < tiles.size() && index > -1) && !choosenIndex.contains(index));

            choosenIndex.add(index);
            temp.add(tiles.get(index));
        }

        tiles = temp;
    }

}
