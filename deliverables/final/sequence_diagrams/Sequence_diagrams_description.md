###### The state of the game and the client-server communication is completely managed by the client controller and the server controller. Game controllers are to call actions on other packages via "access" classes, that collects all methods needed. More in depth, controllers always call methods and waits for returning information, also if this is to be waited from network connection. The idea is that a controller makes a request and wants data as answer (to be processed).
##### The structure of the whole section (network/controller communication/management) is in its early stages and will be subject to changes. The idea behind the communication is that controllers send and retrieve messages on a Socket queue, and network commands are called as methods on the RMI by network managers. UML-client and UML-server show the macrostructure of the project (early designs).
___
### Sequence diagrams description
##### Server Group:

The "Server" component represents the server entity that handles player connections and game sessions.
The "Model" component represents the game model, which contains information about the ongoing game.
The "GameServerController" component is responsible for managing the game flow and communication between the server and the model.

Client Group:

The "GameClientController" component represents the client controller, which handles user interactions and communicates with the server.
The "GameView" component represents the client view, which displays the user interface for interacting with the game.


# Player connects to server and starts match

The client starts by sending a message "update(new EVENT(EventID.START, null))" to "GameClientController" to initiate the client-side steps.
"GameClientController" responds by invoking the "ChoosePlayerName()" method to allow the player to choose a name.
After the player has chosen a name, "GameClientController" responds by invoking the "ChooseRMIorSocket()" method to allow the player to choose the connection mode.
"GameClientController" then invokes the "ChooseIPAddress()" method to allow the player to enter an IP address.
"GameClientController" sends a message to "RoomServices" to connect to the server, passing the IP address, the player's name, and itself as "GameClientController".
"RoomServices" is activated and returns the server to "GameClientController".
"GameView" is activated and displays a user interface for the player to choose whether to create a new game or join an existing one.
"GameClientController" sends a message to "RoomServices" to get the list of available rooms.
"GameView" sends an "update(new EVENT(Chosen Room))" message to "GameClientController" to communicate the room chosen by the user.
"RoomServices" creates an instance of "GameServerController" to manage the game.
"GameServerController" notifies "GameClientController" that the game has started by sending a "NotifyGameHasStarted(Board, CommonGoals, PersonalGoal, Shelf,...)" message containing the necessary information to initialize the game.

# Player select tiles and inserts in shelf 

The player's game turn begins, and the player selects tiles.
The selected tiles are sent to the server via the "GameClientController" component.
The server, represented by the "GameServerController" component, receives the tile selection request and handles it.
The server communicates to the "GameClientController" component that the selected tiles have been accepted.
The player is then presented with the option to choose the order of tile selection.
After choosing the order, the player proceeds to insert the selected tiles into a specific column.
The "GameClientController" component sends the tile insertion request to the server via the "RoomServices" component.
The server, upon receiving the tile insertion request, handles the operation through the "GameServerController" component.
The "GameServerController" interacts with the game model, represented by the "Model" component, to perform the insertion of tiles into the specified column.
The result of the tile insertion operation is communicated back to the "GameServerController" component from the game model.
Subsequently, the "GameServerController" performs end-of-turn checks.
Once the end-of-turn checks are completed, a new turn is initiated.

# Common goal checked and points assigned

"RoomServices" sends an "InsertTilesRequest" to "GameServerController" for a specific player.
"GameServerController" performs end-of-turn checks.
"GameServerController" interacts with "Model" to check the player's common goals.
"GameServerController" deactivates after completing common goal checks.
"GameServerController" initiates a new turn by sending a "newTurn" message to "GameClientController" with relevant player information, such as points, board status, shelf contents, etc.
"GameClientController" communicates with "GameView" to update the player's points display.

