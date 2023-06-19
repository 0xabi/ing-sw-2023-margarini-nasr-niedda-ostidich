package it.polimi.ingsw.client.view.handler.match;

/**
 * @author Abdullah Nasr
 */
public class GuiObjectsHandler {

    private static final double mainShelfTilesSizeWidth=35;
    private static final double mainShelfTilesSizeHeight=35;

    private static final double boardTilesSizeWidth = 38;
    private static final double boardTilesSizeHeight = 39;

    private static final double playerShelfTilesSizeWidth=25;
    private static final double playerShelfTilesSizeHeight=25;

    /**
     * All the values are percentage
     */
    private static final double[]  mainShelfTilesPosY = {0.75, 0.75-0.135, 0.75-0.135*2.0, 0.75-0.135*3.0, 0.75-0.135*4.0, 0.75-0.135*5.0};

    private static final double[] mainShelfTilesPosX = {0.13, 0.13+0.155, 0.13+0.155*2.0, 0.13+0.155*3.0, 0.13+0.155*4.0};

    private static final double[] boardTilesPosX = {0.045, 0.045+0.1018, 0.045+0.1018*2, 0.045+0.1018*3,0.045+0.1018*4,0.045+0.1018*5, 0.045+0.1018*6,0.045+0.1018*7,0.045+0.1018*8};

    private static final double[] boardTilesPosY = {0.0477, 0.0477+0.1018, 0.0477+0.1018*2, 0.0477+0.1018*3,0.0477+0.1018*4,0.0477+0.1018*5, 0.0477+0.1018*6,0.0477+0.1018*7,0.0477+0.1018*8};

    /** Scoring Token **/

    private static final double scoringTokenWidth = 43;
    private static final double scoringTokenHeight = 43;
    private static final double percentageScoringTokenPosY = 0.259615;

    private static final double[] percentageScoringToken8PosX = {0.546012,0.558282,0.558282};

    private static final double[] percentageScoringToken6PosX = {0.576687,0.570552};

    private static final double[] percentageScoringToken4PosX = {0.582822,0.595092,0.582822};

    private static final double percentageScoringToken2PosX = 0.595092;

    private static final double placeScoreToken1PosY = 0.298913;

    private static final double placeScoreToken2PosY = 0.032608;

    private static final double placeScoreTokenPosX = -0.241758;

    private static final double placeScoreToken1MainPlayerPosY = 0.68125;
    private static final double placeScoreToken2MainPlayerPosY = 0.528125;

    private static final double placeScoreTokenMainPlayerPosX = -0.177215;

    private static final double rotationScoreToken = -9;



    /* Scoring Token **/

    /**
     * Get the rotation's angle of the scoring token
     * @return The rotation's angle of the scoring token
     * @author Abdullah Nasr
     */
    public static double getRotationScoreToken()
    {
        return rotationScoreToken;
    }

    /**
     * Gives the percentage value of the "scoring token 8" object's x position relative to the common goal object
     * @param numberOfPlayers the number of players in the match(2,3 or 4)
     * @return the percentage of position x relative to the common goal object given the number of players.
     * @author Abdullah Nasr
     */
    public static double getScoringToken8PosX(int numberOfPlayers)
    {
        return percentageScoringToken8PosX[numberOfPlayers-2];
    }

    /**
     * Gives the percentage value of the "scoring token 4" object's x position relative to the common goal object
     * @param numberOfPlayers the number of players in the match(2, 3 or 4)
     * @return the percentage of position x relative to the common goal object given the number of players.
     * @author Abdullah Nasr
     */
    public static double getScoringToken4PosX(int numberOfPlayers)
    {
        return percentageScoringToken4PosX[numberOfPlayers-2];
    }

    /**
     * Gives the percentage value of the "scoring token 4" object's x position relative to the common goal object
     * @param numberOfPlayers the number of players in the match(3 or 4)
     * @return the percentage of position x relative to the common goal object given the number of players.
     * @author Abdullah Nasr
     */
    public static double getScoringToken6PosX(int numberOfPlayers)
    {
        return percentageScoringToken6PosX[numberOfPlayers-3];
    }



    /**
     * Gives the percentage value of the "scoring token 2" object's x position relative to the common goal object
     * the scoring token 2 is present only during a match with 4 players
     * @return the percentage of position x relative to the common goal object during a match with 4 players.
     * @author Abdullah Nasr
     */
    public static double getScoringToken2PosX()
    {
        return percentageScoringToken2PosX;
    }


    /**
     * Gives the percentage value of the "scoring token" object's y position relative to the common goal object
     * @return the percentage of position y relative to the common goal object.
     * @author Abdullah Nasr
     */
    public static double getScoringTokenPosY()
    {
        return percentageScoringTokenPosY;
    }


    /**
     * Get the scoring token's width
     * @return the scoring token width
     * @author Abdullah Nasr
     */
    public static double getScoringTokenWidth()
    {
        return scoringTokenWidth;
    }

    /**
     * Get the scoring token's height
     * @return the scoring token height
     * @author Abdullah Nasr
     */
    public static double getScoringTokenHeight()
    {
        return scoringTokenHeight;
    }

    /**
     * Get the percentage of the first scoring token's y position when the opponent player wins it
     * @return The percentage of the first scoring token's y position when the opponent player wins it
     * @author Abdullah Nasr
     */
    public static double getPlaceScoreToken1PosY() {
        return placeScoreToken1PosY;
    }

    /**
     * Get the percentage of the second scoring token's y position when the opponent player wins it
     * @return The percentage of the second scoring token's y position when the opponent player wins it
     * @author Abdullah Nasr
     */
    public static double getPlaceScoreToken2PosY() {
        return placeScoreToken2PosY;
    }

    /**
     * Get the percentage of the scoring token's x position when the opponent player wins it
     * @return The percentage of the scoring token's x position when the opponent player wins it
     * @author Abdullah Nasr
     */
    public static double getPlaceScoreTokenPosX() {
        return placeScoreTokenPosX;
    }


    /**
     * Get the percentage of the first scoring token's y position when the player wins it
     * @return The percentage of the first scoring token's y position when the player wins it
     * @author Abdullah Nasr
     */
    public static double getPlaceScoreToken1MainPlayerPosY() {
        return placeScoreToken1MainPlayerPosY;
    }

    /**
     * Get the percentage of the second scoring token's y position when the player wins it
     * @return The percentage of the second scoring token's y position when the player wins it
     * @author Abdullah Nasr
     */
    public static double getPlaceScoreToken2MainPlayerPosY() {
        return placeScoreToken2MainPlayerPosY;
    }

    /**
     * Get the percentage of the scoring token's x position when the player wins it
     * @return The percentage of the scoring token's x position when the player wins it
     * @author Abdullah Nasr
     */
    public static double getPlaceScoreTokenMainPlayerPosX() {
        return placeScoreTokenMainPlayerPosX;
    }


    /** Shelf **/

    public static double getMainShelfTilesSizeWidth() {
        return mainShelfTilesSizeWidth;
    }


    public static double getMainShelfTilesSizeHeight() {
        return mainShelfTilesSizeHeight;
    }

    public static double getPlayerShelfTilesSizeWidth() {
        return playerShelfTilesSizeWidth;
    }

    public static double getPlayerShelfTilesSizeHeight() {
        return playerShelfTilesSizeHeight;
    }

    /**
     * The row with index 0 starts from the bottom of the shelf
     * @param row
     * @return
     */
    public static double getMainShelfTilesPosY(int row) {
        return mainShelfTilesPosY[row];
    }

    /**
     * The column with index 0 starts from the left of the shelf
     * @param col
     * @return
     */
    public static double getMainShelfTilesPosX(int col) { return mainShelfTilesPosX[col]; }


    /**
     * The column with index 0 starts from the left of the board
     * @param col
     * @return
     */
    public static double getBoardTilesPosX(int col)
    {
        return boardTilesPosX[col];
    }

    /**
     * The row with index 0 starts from the upper of the board
     * @param row
     * @return
     */
    public static double getBoardTilesPosY(int row)
    {
        return boardTilesPosY[row];
    }


    public static double getBoardTilesSizeWidth() {
        return boardTilesSizeWidth;
    }

    public static double getBoardTilesSizeHeight() {
        return boardTilesSizeHeight;
    }



}
