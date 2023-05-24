package it.polimi.ingsw.client.view.handler;

import it.polimi.ingsw.server.model.Shelf;

public class GuiObjectsHandler {

    public static double mainShelfPosY = 320;
    private static double mainShelfTilesSizeWidth=35;
    private static double mainShelfTilesSizeHeight=35;

    private static double playerShelfTilesSizeWidth=25;
    private static double playerShelfTilesSizeHeight=25;

    //private static final double[]  mainShelfTilesPosY = {347.0, 306.0, 263.0, 221.0, 178.0, 134.0};
    private static final double[]  mainShelfTilesPosY = {380.0, 337.0, 293.0, 251.0, 208.0, 165.0};

    private static final double[] mainShelfTilesPosX = {248.0, 298.0, 348.0, 397.0, 445.0};
    //private static double[] mainShelfTilesPosX = {52.0/390.0, 112.0/390.0, 170.0/390.0, 230.0/390.0, 290.0/390.0};
/*
390px

52px
112px
170px
230px
290px
 */
    private static final double[]
            shelfPlayer1TilesPosY = {610, 586, 562, 537, 513, 488},
            shelfPlayer2TilesPosY= {610, 586, 562, 537, 513, 488},
            shelfPlayer3TilesPosY = {610, 586, 562, 537, 513, 488};

    private static final double[] shelfPLayer1TilesPosX = {571, 600, 629, 657, 686};
    private static final double[] shelfPlayer2TilesPosX = {786, 815, 844, 872, 901};
    private static final double[] shelfPlayer3TilesPosX = {1002, 1031, 1060, 1088, 1117};




    /**
     *
     * @param ratio_width
     * @param ratio_height
     */
    public static void resizeObjects(double ratio_width, double ratio_height)
    {
        mainShelfTilesSizeWidth = mainShelfTilesSizeWidth*ratio_width;
        //mainShelfTilesSizeHeight = mainShelfTilesSizeHeight*ratio_height;

        playerShelfTilesSizeWidth = playerShelfTilesSizeWidth*ratio_width;
        playerShelfTilesSizeHeight = playerShelfTilesSizeHeight*ratio_height;

        for(int i=0;i< Shelf.getColumnLength();i++)
        {
            //mainShelfTilesPosY[i] = mainShelfTilesPosY[i]*ratio_height;
            shelfPlayer1TilesPosY[i] = shelfPlayer1TilesPosY[i]*ratio_height;
            shelfPlayer2TilesPosY[i] = shelfPlayer2TilesPosY[i]*ratio_height;
            shelfPlayer3TilesPosY[i] = shelfPlayer3TilesPosY[i]*ratio_height;
        }

        for(int i=0;i<Shelf.getRowLength();i++)
        {
            mainShelfTilesPosX[i] = mainShelfTilesPosX[i]*ratio_width;
            shelfPLayer1TilesPosX[i] = shelfPLayer1TilesPosX[i]*ratio_width;
            shelfPlayer2TilesPosX[i] = shelfPlayer2TilesPosX[i]*ratio_width;
            shelfPlayer3TilesPosX[i] = shelfPlayer3TilesPosX[i]*ratio_width;
        }


    }

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
     *
     * @param row
     * @return
     */
    public static double getShelfPlayer1TilesPosY(int row)
    {
        return shelfPlayer1TilesPosY[row];
    }

    /**
     *
     * @param row
     * @return
     */
    public static double getShelfPlayer2TilesPosY(int row)
    {
        return shelfPlayer2TilesPosY[row];
    }

    /**
     *
     * @param row
     * @return
     */
    public static double getShelfPlayer3TilesPosY(int row)
    {
        return shelfPlayer3TilesPosY[row];
    }

    /**
     * The column with index 0 starts from the left of the shelf
     * @param col
     * @return
     */
    public static double getShelfPLayer1TilesPosX(int col)
    {
        return shelfPLayer1TilesPosX[col];
    }

    /**
     *
     * @param col
     * @return
     */
    public static double getShelfPLayer2TilesPosX(int col)
    {
        return shelfPlayer2TilesPosX[col];
    }

    /**
     *
     * @param col
     * @return
     */
    public static double getShelfPlayer3TilesPosX(int col)
    {
        return shelfPlayer3TilesPosY[col];
    }



}
