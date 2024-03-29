package it.polimi.ingsw.client.view.handler.match;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.InputStreamReader;
import java.util.*;

/**
 * The class is responsible for creating and animating everything related to common goals
 *
 * @author Abdullah Nasr
 */
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

    /**
     * Store the opponents' name
     *
     * @param opponentPlayerNames The list of opponents' name
     * @author Abdullah Nasr
     */
    public static void setOpponentNames(ArrayList<String> opponentPlayerNames) {
        opponentNames = opponentPlayerNames;

        for (String currentName : opponentPlayerNames) {
            place1occupied.put(currentName, false);
        }
    }

    /**
     * Store the player's name
     *
     * @param namePlayer The player's name
     * @author Abdullah Nasr
     */
    public static void setNamePlayer(String namePlayer) {
        nameMainPlayer = namePlayer;
        place1occupied.put(namePlayer, false);
    }

    /**
     * Store all shelves images
     *
     * @param mainPlayerShelf The main player shelf's image
     * @param shelfPlayer1    The first opponent player shelf's image
     * @param shelfPlayer2    The second opponent player shelf's image
     * @param shelfPlayer3    The third opponent player shelf's image
     * @author Abdullah Nasr
     */
    public static void setShelves(ImageView mainPlayerShelf, ImageView shelfPlayer1, ImageView shelfPlayer2, ImageView shelfPlayer3) {
        shelfPlayer = mainPlayerShelf;
        player1Shelf = shelfPlayer1;
        player2Shelf = shelfPlayer2;
        player3Shelf = shelfPlayer3;
    }

    /**
     * Store the pane
     *
     * @param rootPane The pane
     * @author Abdullah Nasr
     */
    public static void setRoot(Pane rootPane) {
        root = rootPane;
    }


    /**
     * Create the scoring token 4 with the corresponding size and position based on the number of players and the common goal's image
     *
     * @param commonGoal The common goal's image
     * @param numPlayers The number of players during the match
     * @return The scoring token 4's image
     */
    public static ImageView createScoringToken4IV(ImageView commonGoal, int numPlayers) {
        ImageView scoringToken4IV = new ImageView();

        scoringToken4IV.setImage(new Image(Objects.requireNonNull(CommonGoalsHandler.class.getResource("/graphics/scoring tokens/scoring_4.jpg")).toString()));
        scoringToken4IV.setFitWidth(GuiObjectsHandler.getScoringTokenWidth());
        scoringToken4IV.setFitHeight(GuiObjectsHandler.getScoringTokenHeight());
        scoringToken4IV.setLayoutX(commonGoal.getLayoutX() + commonGoal.getFitWidth() * GuiObjectsHandler.getScoringToken4PosX(numPlayers));
        scoringToken4IV.setLayoutY(commonGoal.getLayoutY() + commonGoal.getFitHeight() * GuiObjectsHandler.getScoringTokenPosY());
        root.getChildren().add(scoringToken4IV);

        return scoringToken4IV;
    }


    /**
     * Create the scoring token 8 with the corresponding size and position based on the number of players and the common goal's image
     *
     * @param commonGoal The common goal's image
     * @param numPlayers The number of players during the match
     * @return The scoring token 8's image
     */
    public static ImageView createScoringToken8IV(ImageView commonGoal, int numPlayers) {
        ImageView scoringToken8IV = new ImageView();

        scoringToken8IV.setImage(new Image(Objects.requireNonNull(CommonGoalsHandler.class.getResource("/graphics/scoring tokens/scoring_8.jpg")).toString()));
        scoringToken8IV.setFitWidth(GuiObjectsHandler.getScoringTokenWidth());
        scoringToken8IV.setFitHeight(GuiObjectsHandler.getScoringTokenHeight());
        scoringToken8IV.setLayoutX(commonGoal.getLayoutX() + commonGoal.getFitWidth() * GuiObjectsHandler.getScoringToken8PosX(numPlayers));
        scoringToken8IV.setLayoutY(commonGoal.getLayoutY() + commonGoal.getFitHeight() * GuiObjectsHandler.getScoringTokenPosY());
        root.getChildren().add(scoringToken8IV);

        return scoringToken8IV;
    }

    /**
     * Create the scoring token 6 with the corresponding size and position based on the number of players and the common goal's image
     *
     * @param commonGoal The common goal's image
     * @param numPlayers The number of players during the match
     * @return The scoring token 6's image
     */
    public static ImageView createScoringToken6IV(ImageView commonGoal, int numPlayers) {
        ImageView scoringToken6IV = new ImageView();

        scoringToken6IV.setImage(new Image(Objects.requireNonNull(CommonGoalsHandler.class.getResource("/graphics/scoring tokens/scoring_6.jpg")).toString()));
        scoringToken6IV.setFitWidth(GuiObjectsHandler.getScoringTokenWidth());
        scoringToken6IV.setFitHeight(GuiObjectsHandler.getScoringTokenHeight());
        scoringToken6IV.setLayoutX(commonGoal.getLayoutX() + commonGoal.getFitWidth() * GuiObjectsHandler.getScoringToken6PosX(numPlayers));
        scoringToken6IV.setLayoutY(commonGoal.getLayoutY() + commonGoal.getFitHeight() * GuiObjectsHandler.getScoringTokenPosY());
        root.getChildren().add(scoringToken6IV);

        return scoringToken6IV;
    }


    /**
     * Create the scoring token 2 with the corresponding size and position based on the common goal's image
     *
     * @param commonGoal The common goal's image
     * @return The scoring token 2's image
     */
    public static ImageView createScoringToken2IV(ImageView commonGoal) {
        ImageView scoringToken2IV = new ImageView();

        scoringToken2IV.setImage(new Image(Objects.requireNonNull(CommonGoalsHandler.class.getResource("/graphics/scoring tokens/scoring_2.jpg")).toString()));
        scoringToken2IV.setFitWidth(GuiObjectsHandler.getScoringTokenWidth());
        scoringToken2IV.setFitHeight(GuiObjectsHandler.getScoringTokenHeight());
        scoringToken2IV.setLayoutX(commonGoal.getLayoutX() + commonGoal.getFitWidth() * GuiObjectsHandler.getScoringToken2PosX());
        scoringToken2IV.setLayoutY(commonGoal.getLayoutY() + commonGoal.getFitHeight() * GuiObjectsHandler.getScoringTokenPosY());
        root.getChildren().add(scoringToken2IV);


        return scoringToken2IV;
    }


    /**
     * Set the images and tips of the two common goals
     *
     * @param commonGoal1     The image of the first common goal
     * @param commonGoal1Name The name of the first common goal
     * @param commonGoal2     The image of the second common goal
     * @param commonGoal2Name The name of the second common goal
     * @author Abdullah Nasr
     */
    public static void setImageCommonGoals(ImageView commonGoal1, String commonGoal1Name, ImageView commonGoal2, String commonGoal2Name) {
        Tooltip TipCg2 = new Tooltip();
        Tooltip TipCg1 = new Tooltip();

        TipCg1.setText(getCommonGoalDescription(commonGoal1Name));
        TipCg1.setShowDuration(Duration.millis(6000));
        TipCg2.setText(getCommonGoalDescription(commonGoal2Name));
        TipCg2.setShowDuration(Duration.millis(6000));


        Tooltip.install(commonGoal1, TipCg1);
        Tooltip.install(commonGoal2, TipCg2);

        commonGoal1.setImage(getCommonGoalImage(commonGoal1Name));
        commonGoal2.setImage(getCommonGoalImage(commonGoal2Name));
    }

    /**
     * Import common goal's image based on the name of the common goal
     *
     * @author Abdullah Nasr
     */
    public static Image getCommonGoalImage(String commonGoalName) {

        return new Image(Objects.requireNonNull(CommonGoalsHandler.class.getResource("/graphics/commonGoalCards/" + commonGoalName + ".jpg")).toString());

    }

    /**
     * Read the json with the description of the specified common goal
     *
     * @param commonGoalName The common gaol's name
     * @return The description of the specified common goal
     */
    public static String getCommonGoalDescription(String commonGoalName) {
        JsonObject jObject = JsonParser.parseReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("configFiles/commonGoalDescription.json")))).getAsJsonObject();
        if (jObject.has(commonGoalName)) {
            return jObject.get(commonGoalName).getAsString();
        }

        return "N/A";

    }


    /**
     * Create all the scoring token given the number of players and the common goals' images
     *
     * @param numTotPlayers The number of players during the match
     * @param commonGoal1   The first common goal's image
     * @param commonGoal2   The second common goal's image
     * @author Abdullah Nasr
     */
    public static void initScoringTokens(int numTotPlayers, ImageView commonGoal1, ImageView commonGoal2) {
        if (numTotPlayers == 2) {

            commonGoal1ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken4IV(commonGoal1, numTotPlayers));
            commonGoal1ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken8IV(commonGoal1, numTotPlayers));
            commonGoal2ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken4IV(commonGoal2, numTotPlayers));
            commonGoal2ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken8IV(commonGoal2, numTotPlayers));

        } else if (numTotPlayers == 3) {
            commonGoal1ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken4IV(commonGoal1, numTotPlayers));
            commonGoal1ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken6IV(commonGoal1, numTotPlayers));
            commonGoal1ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken8IV(commonGoal1, numTotPlayers));
            commonGoal2ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken4IV(commonGoal2, numTotPlayers));
            commonGoal2ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken6IV(commonGoal2, numTotPlayers));
            commonGoal2ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken8IV(commonGoal2, numTotPlayers));

        } else if (numTotPlayers == 4) {
            commonGoal1ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken2IV(commonGoal1));
            commonGoal1ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken4IV(commonGoal1, numTotPlayers));
            commonGoal1ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken6IV(commonGoal1, numTotPlayers));
            commonGoal1ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken8IV(commonGoal1, numTotPlayers));

            commonGoal2ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken2IV(commonGoal2));
            commonGoal2ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken4IV(commonGoal2, numTotPlayers));
            commonGoal2ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken6IV(commonGoal2, numTotPlayers));
            commonGoal2ScoringTokenStackImages.add(CommonGoalsHandler.createScoringToken8IV(commonGoal2, numTotPlayers));

        }

        lenStackCG1 = 0;
        lenStackCG2 = 0;
    }

    /**
     * It checks if graphically the place where is stored the first taken scoring token is occupied.
     * If it returns true it means that the player has just taken a common goal.
     *
     * @param namePlayer The player's name
     * @return True if graphically the place where is stored the first taken scoring token is occupied, false otherwise.
     * @author Abdullah Nasr
     */
    private static boolean firstPlaceIsNotOccupied(String namePlayer) {
        Boolean state = place1occupied.get(namePlayer);

        return state == null || !state;

    }

    /**
     * It moves the scoring token image to the main player's shelf given the scoring token's image, the player's shelf and the player's name.
     *
     * @param scoringToken     The scoring token to be moved
     * @param shelfDestination The shelf's image of the player that obtained the common goal
     * @param namePlayer       The player's name that obtained the common goal
     * @author Abdullah Nasr
     */
    private static void moveScoringTokenToMainPlayer(ImageView scoringToken, ImageView shelfDestination, String namePlayer) {
        TranslateTransition translate;
        double destinationX;
        double destinationY;

        if (firstPlaceIsNotOccupied(namePlayer)) {
            translate = new TranslateTransition();
            destinationX = shelfDestination.getLayoutX() + (shelfDestination.getFitWidth() * GuiObjectsHandler.getPlaceScoreTokenMainPlayerPosX());
            destinationY = shelfDestination.getLayoutY() + (shelfDestination.getFitHeight() * GuiObjectsHandler.getPlaceScoreToken1MainPlayerPosY());

            translate.setNode(scoringToken);

            translate.setDuration(Duration.millis(600));

            translate.setToX(destinationX - scoringToken.getLayoutX());

            translate.setToY(destinationY - scoringToken.getLayoutY());

            translate.play();

            place1occupied.put(namePlayer, true);
        } else {
            translate = new TranslateTransition();
            destinationX = shelfDestination.getLayoutX() + (shelfDestination.getFitWidth() * GuiObjectsHandler.getPlaceScoreTokenMainPlayerPosX());
            destinationY = shelfDestination.getLayoutY() + (shelfDestination.getFitHeight() * GuiObjectsHandler.getPlaceScoreToken2MainPlayerPosY());

            translate.setNode(scoringToken);

            translate.setDuration(Duration.millis(600));

            translate.setToX(destinationX - scoringToken.getLayoutX());

            translate.setToY(destinationY - scoringToken.getLayoutY());

            translate.play();
        }
    }

    /**
     * It moves the scoring token image to the opponent player's shelf given the scoring token's image, the player's shelf and the player's name.
     *
     * @param scoringToken     The scoring token to be moved
     * @param shelfDestination The shelf's image of the player that obtained the common goal
     * @param namePlayer       The player's name that obtained the common goal
     * @author Abdullah Nasr
     */
    private static void moveScoringTokenToOpponentPlayer(ImageView scoringToken, ImageView shelfDestination, String namePlayer) {
        TranslateTransition translate;
        double destinationX;
        double destinationY;

        if (firstPlaceIsNotOccupied(namePlayer)) {
            translate = new TranslateTransition();
            destinationX = shelfDestination.getLayoutX() + (shelfDestination.getFitWidth() * GuiObjectsHandler.getPlaceScoreTokenPosX());
            destinationY = shelfDestination.getLayoutY() + (shelfDestination.getFitHeight() * GuiObjectsHandler.getPlaceScoreToken1PosY());

            translate.setNode(scoringToken);

            translate.setDuration(Duration.millis(600));

            translate.setToX(destinationX - scoringToken.getLayoutX());

            translate.setToY(destinationY - scoringToken.getLayoutY());

            translate.play();

            place1occupied.put(namePlayer, true);
        } else {
            translate = new TranslateTransition();
            destinationX = shelfDestination.getLayoutX() + (shelfDestination.getFitWidth() * GuiObjectsHandler.getPlaceScoreTokenPosX());
            destinationY = shelfDestination.getLayoutY() + (shelfDestination.getFitHeight() * GuiObjectsHandler.getPlaceScoreToken2PosY());

            translate.setNode(scoringToken);

            translate.setDuration(Duration.millis(600));

            translate.setToX(destinationX - scoringToken.getLayoutX());

            translate.setToY(destinationY - scoringToken.getLayoutY());

            translate.play();
        }
    }

    /**
     * It moves to the player that obtained the common goal the scoring token near the
     * player's shelf
     *
     * @param scoringTokenIV The scoring token's image
     * @param namePlayer     The player's name that obtained the common goal
     * @author Abdullah Nasr
     */
    private static void moveScoringTokenCGToPlayer(ImageView scoringTokenIV, String namePlayer) {
        int counter = 0;


        if (namePlayer.equals(nameMainPlayer)) {
            moveScoringTokenToMainPlayer(scoringTokenIV, shelfPlayer, namePlayer);
        } else {
            for (String currentName : opponentNames) {
                if (namePlayer.equals(currentName)) {
                    if (counter == 0) {
                        moveScoringTokenToOpponentPlayer(scoringTokenIV, player1Shelf, namePlayer);
                    } else if (counter == 1) {
                        moveScoringTokenToOpponentPlayer(scoringTokenIV, player2Shelf, namePlayer);
                    } else if (counter == 2) {
                        moveScoringTokenToOpponentPlayer(scoringTokenIV, player3Shelf, namePlayer);
                    }
                }

                counter++;
            }
        }
    }

    /**
     * It stores the taken common goal 1's scoring tokens with the corresponding player's name
     *
     * @param givenCommonGoal1 The taken common goal 1's scoring tokens with the corresponding player's name
     * @author Abdullah Nasr
     */
    public static void updateCommonGoal1(Map<Integer, String> givenCommonGoal1) {
        //there was a new achieved common goal 1
        if (lenStackCG1 != givenCommonGoal1.size()) {
            Integer key = -1;
            String namePlayer;

            //get the min token
            //to find the last inserted token
            //and then also find the name of the player who took the last common goal
            for (Map.Entry<Integer, String> currentEntry : givenCommonGoal1.entrySet()) {
                if (key == -1 || currentEntry.getKey() < key) {
                    key = currentEntry.getKey();
                }
            }

            namePlayer = givenCommonGoal1.get(key);

            ImageView scoringTokenIV = commonGoal1ScoringTokenStackImages.pop();
            moveScoringTokenCGToPlayer(scoringTokenIV, namePlayer);

            lenStackCG1++;
        } else {
            System.out.println("No common goal 1 achieved");
        }

    }

    /**
     * It stores the taken common goal 2's scoring tokens with the corresponding player's name
     *
     * @param givenCommonGoal2 The taken common goal 2's scoring tokens with the corresponding player's name
     * @author Abdullah Nasr
     */
    public static void updateCommonGoal2(Map<Integer, String> givenCommonGoal2) {
        //there was a new achieved common goal 1
        if (lenStackCG2 != givenCommonGoal2.size()) {
            Integer key = -1;
            String namePlayer;

            //get the min token
            //to find the last inserted token
            //and then also find the name of the player who took the last common goal
            for (Map.Entry<Integer, String> currentEntry : givenCommonGoal2.entrySet()) {
                if (key == -1 || currentEntry.getKey() < key) {
                    key = currentEntry.getKey();
                }
            }

            namePlayer = givenCommonGoal2.get(key);

            ImageView scoringTokenIV = commonGoal2ScoringTokenStackImages.pop();
            moveScoringTokenCGToPlayer(scoringTokenIV, namePlayer);

            lenStackCG2++;
        } else {
            System.out.println("No common goal 2 achieved");
        }
    }

    /**
     * Clear data of the match
     * Is needed in case you make another match in the same GUI instance
     *
     * @author Abdullah Nasr
     */
    public static void clear() {
        commonGoal1ScoringTokenStackImages.clear();
        commonGoal2ScoringTokenStackImages.clear();
        place1occupied.clear();
        lenStackCG1 = lenStackCG2 = 0;
    }
}
