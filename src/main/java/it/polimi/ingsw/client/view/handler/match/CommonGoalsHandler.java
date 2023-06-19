package it.polimi.ingsw.client.view.handler.match;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.*;

public class CommonGoalsHandler {

    private static final Stack<ImageView> commonGoal1ScoringTokenStackImages = new Stack<>();
    private static final Stack<ImageView> commonGoal2ScoringTokenStackImages = new Stack<>();

    private static final HashMap<String, Boolean> place1occupied = new HashMap<>();
    private static int lenStackCG1;
    private static int lenStackCG2;

    private static ArrayList<String> opponentNames = new ArrayList<>();
    private static String nameMainPlayer;

    private static ImageView shelfPlayer;
    private static ImageView player1Shelf;
    private static ImageView player2Shelf;

    private static ImageView player3Shelf;

    private static Pane root;

    public static void setOpponentNames(ArrayList<String> opponentPlayerNames) {
        opponentNames = opponentPlayerNames;

        for(String currentName: opponentPlayerNames)
        {
            place1occupied.put(currentName, false);
        }
    }

    public static void setNamePlayer(String namePlayer) {
        nameMainPlayer = namePlayer;
        place1occupied.put(namePlayer,false);
    }

    public static void setShelves(ImageView mainPlayerShelf, ImageView shelfPlayer1, ImageView shelfPlayer2, ImageView shelfPlayer3)
    {
        shelfPlayer = mainPlayerShelf;
        player1Shelf = shelfPlayer1;
        player2Shelf = shelfPlayer2;
        player3Shelf = shelfPlayer3;
    }

    public static void setRoot(Pane rootPane)
    {
        root = rootPane;
    }


    public static ImageView createScoringToken4IV(ImageView commonGoal, int numPlayers)
    {
        ImageView scoringToken4IV = new ImageView();

        scoringToken4IV.setImage(new Image(Objects.requireNonNull(CommonGoalsHandler.class.getResource("/graphics/scoring tokens/scoring_4.jpg")).toString()));
        scoringToken4IV.setFitWidth(GuiObjectsHandler.getScoringTokenWidth());
        scoringToken4IV.setFitHeight(GuiObjectsHandler.getScoringTokenHeight());
        scoringToken4IV.setLayoutX(commonGoal.getLayoutX()+commonGoal.getFitWidth()*GuiObjectsHandler.getScoringToken4PosX(numPlayers));
        scoringToken4IV.setLayoutY(commonGoal.getLayoutY()+commonGoal.getFitHeight()*GuiObjectsHandler.getScoringTokenPosY());
        root.getChildren().add(scoringToken4IV);

        return scoringToken4IV;
    }

    public static ImageView createScoringToken8IV(ImageView commonGoal, int numPlayers)
    {
        ImageView scoringToken8IV = new ImageView();

        scoringToken8IV.setImage(new Image(Objects.requireNonNull(CommonGoalsHandler.class.getResource("/graphics/scoring tokens/scoring_8.jpg")).toString()));
        scoringToken8IV.setFitWidth(GuiObjectsHandler.getScoringTokenWidth());
        scoringToken8IV.setFitHeight(GuiObjectsHandler.getScoringTokenHeight());
        scoringToken8IV.setLayoutX(commonGoal.getLayoutX()+commonGoal.getFitWidth()*GuiObjectsHandler.getScoringToken8PosX(numPlayers));
        scoringToken8IV.setLayoutY(commonGoal.getLayoutY()+commonGoal.getFitHeight()*GuiObjectsHandler.getScoringTokenPosY());
        root.getChildren().add(scoringToken8IV);

        return scoringToken8IV;
    }

    public static ImageView createScoringToken6IV(ImageView commonGoal, int numPlayers)
    {
        ImageView scoringToken6IV = new ImageView();

        scoringToken6IV.setImage(new Image(Objects.requireNonNull(CommonGoalsHandler.class.getResource("/graphics/scoring tokens/scoring_6.jpg")).toString()));
        scoringToken6IV.setFitWidth(GuiObjectsHandler.getScoringTokenWidth());
        scoringToken6IV.setFitHeight(GuiObjectsHandler.getScoringTokenHeight());
        scoringToken6IV.setLayoutX(commonGoal.getLayoutX()+commonGoal.getFitWidth()*GuiObjectsHandler.getScoringToken6PosX(numPlayers));
        scoringToken6IV.setLayoutY(commonGoal.getLayoutY()+commonGoal.getFitHeight()*GuiObjectsHandler.getScoringTokenPosY());
        root.getChildren().add(scoringToken6IV);

        return scoringToken6IV;
    }

    public static ImageView createScoringToken2IV(ImageView commonGoal)
    {
        ImageView scoringToken2IV = new ImageView();

        scoringToken2IV.setImage(new Image(Objects.requireNonNull(CommonGoalsHandler.class.getResource("/graphics/scoring tokens/scoring_2.jpg")).toString()));
        scoringToken2IV.setFitWidth(GuiObjectsHandler.getScoringTokenWidth());
        scoringToken2IV.setFitHeight(GuiObjectsHandler.getScoringTokenHeight());
        scoringToken2IV.setLayoutX(commonGoal.getLayoutX()+commonGoal.getFitWidth()*GuiObjectsHandler.getScoringToken2PosX());
        scoringToken2IV.setLayoutY(commonGoal.getLayoutY()+commonGoal.getFitHeight()*GuiObjectsHandler.getScoringTokenPosY());
        root.getChildren().add(scoringToken2IV);


        return scoringToken2IV;
    }

    public static void initCommonGoals(int numTotPlayers, ImageView commonGoal1, ImageView commonGoal2)
    {
        if(numTotPlayers==2)
        {

            commonGoal1ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken4IV(commonGoal1,numTotPlayers));
            commonGoal1ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken8IV(commonGoal1,numTotPlayers));
            commonGoal2ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken4IV(commonGoal2,numTotPlayers));
            commonGoal2ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken8IV(commonGoal2,numTotPlayers));

        }
        else if(numTotPlayers==3)
        {
            commonGoal1ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken4IV(commonGoal1,numTotPlayers));
            commonGoal1ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken6IV(commonGoal1,numTotPlayers));
            commonGoal1ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken8IV(commonGoal1,numTotPlayers));
            commonGoal2ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken4IV(commonGoal2,numTotPlayers));
            commonGoal2ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken6IV(commonGoal2,numTotPlayers));
            commonGoal2ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken8IV(commonGoal2,numTotPlayers));

        }
        else if(numTotPlayers==4)
        {
            commonGoal1ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken2IV(commonGoal1));
            commonGoal1ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken4IV(commonGoal1,numTotPlayers));
            commonGoal1ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken6IV(commonGoal1,numTotPlayers));
            commonGoal1ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken8IV(commonGoal1,numTotPlayers));

            commonGoal2ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken2IV(commonGoal2));
            commonGoal2ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken4IV(commonGoal2,numTotPlayers));
            commonGoal2ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken6IV(commonGoal2,numTotPlayers));
            commonGoal2ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken8IV(commonGoal2,numTotPlayers));

        }

        lenStackCG1 = 0;
        lenStackCG2 = 0;
    }

    private static boolean firstPlaceIsOccupied(String namePlayer)
    {
        Boolean state = place1occupied.get(namePlayer);

        return state != null && state;

    }

    private static void moveScoringTokenToMainPlayer(ImageView scoringToken,ImageView shelfDestination, String namePlayer)
    {
        TranslateTransition translate;
        double destinationX;
        double destinationY;

        if(firstPlaceIsOccupied(namePlayer))
        {
            translate = new TranslateTransition();
            destinationX = shelfDestination.getLayoutX() + (shelfDestination.getFitWidth()*GuiObjectsHandler.getPlaceScoreTokenMainPlayerPosX());
            destinationY = shelfDestination.getLayoutY() + (shelfDestination.getFitHeight()*GuiObjectsHandler.getPlaceScoreToken1MainPlayerPosY());

            translate.setNode(scoringToken);

            translate.setDuration(Duration.millis(1000));

            translate.setToX(destinationX-scoringToken.getLayoutX());

            translate.setToY(destinationY-scoringToken.getLayoutY());

            translate.play();

            place1occupied.put(namePlayer,true);
        }
        else
        {
            translate = new TranslateTransition();
            destinationX = shelfDestination.getLayoutX() + (shelfDestination.getFitWidth()*GuiObjectsHandler.getPlaceScoreTokenMainPlayerPosX());
            destinationY = shelfDestination.getLayoutY() + (shelfDestination.getFitHeight()*GuiObjectsHandler.getPlaceScoreToken2MainPlayerPosY());

            translate.setNode(scoringToken);

            translate.setDuration(Duration.millis(1000));

            translate.setToX(destinationX-scoringToken.getLayoutX());

            translate.setToY(destinationY-scoringToken.getLayoutY());

            translate.play();
        }
    }

    private static void moveScoringTokenToOpponentPlayer(ImageView scoringToken,ImageView shelfDestination, String namePlayer)
    {
        TranslateTransition translate;
        double destinationX;
        double destinationY;

        if(firstPlaceIsOccupied(namePlayer))
        {
            translate = new TranslateTransition();
            destinationX = shelfDestination.getLayoutX() + (shelfDestination.getFitWidth()*GuiObjectsHandler.getPlaceScoreTokenPosX());
            destinationY = shelfDestination.getLayoutY() + (shelfDestination.getFitHeight()*GuiObjectsHandler.getPlaceScoreToken1PosY());

            translate.setNode(scoringToken);

            translate.setDuration(Duration.millis(1000));

            translate.setToX(destinationX-scoringToken.getLayoutX());

            translate.setToY(destinationY-scoringToken.getLayoutY());

            translate.play();

            place1occupied.put(namePlayer,true);
        }
        else
        {
            translate = new TranslateTransition();
            destinationX = shelfDestination.getLayoutX() + (shelfDestination.getFitWidth()*GuiObjectsHandler.getPlaceScoreTokenPosX());
            destinationY = shelfDestination.getLayoutY() + (shelfDestination.getFitHeight()*GuiObjectsHandler.getPlaceScoreToken2PosY());

            translate.setNode(scoringToken);

            translate.setDuration(Duration.millis(1000));

            translate.setToX(destinationX-scoringToken.getLayoutX());

            translate.setToY(destinationY-scoringToken.getLayoutY());

            translate.play();
        }
    }
    private static void moveScoringTokenCG1ToPlayer(String namePlayer)
    {
        int counter = 0;
        ImageView ivToMove = commonGoal1ScoringTokenStackImages.pop();

        if(namePlayer.equals(nameMainPlayer))
        {
            moveScoringTokenToMainPlayer(ivToMove,shelfPlayer,namePlayer);
        }
        else
        {
            for(String currentName : opponentNames)
            {
                if(namePlayer.equals(currentName))
                {
                    if(counter==0)
                    {
                        moveScoringTokenToOpponentPlayer(ivToMove,player1Shelf,namePlayer);
                    }
                    else if(counter==1)
                    {
                        moveScoringTokenToOpponentPlayer(ivToMove,player2Shelf,namePlayer);
                    }
                    else if(counter==3)
                    {
                        moveScoringTokenToOpponentPlayer(ivToMove,player3Shelf,namePlayer);
                    }
                }

                counter++;
            }
        }
    }
    public static void updateCommonGoal1(Map<Integer, String> givenCommonGoal1)
    {
        //there was a new achieved common goal 1
        if(lenStackCG1!=givenCommonGoal1.size())
        {
            Integer key=0;
            String namePlayer;

            //get the last pair value
            for(Map.Entry<Integer, String> currentEntry : givenCommonGoal1.entrySet())
            {
                key = currentEntry.getKey();
            }

            namePlayer = givenCommonGoal1.get(key);

            moveScoringTokenCG1ToPlayer(namePlayer);

            lenStackCG1++;
        }

    }

}
