###### The state of the game and the client-server communication is completely managed by the client controller and the server controller. Game controllers are to call actions on other packages via "access" classes, that collects all methods needed. More in depth, controllers always call methods and waits for returning information, also if this is to be waited from network connection. The idea is that a controller makes a request and wants data as answer (to be processed).
##### The structure of the whole section (network/controller communication/management) is in its early stages and will be subject to changes. UML-client and UML-server show the macrostructure of the project (early designs).
___
### Sequence diagrams description
##### In the sequence diagrams, method calls are represented with full arrows and the returning data stands on the dotted ones.

# Player connects to server and starts match

**Server side**:
The server controller is waiting for new players, asking player's name to the virtual view.
The virtual view is listening for ping messages and eventually play requests. 
When these are received it asks for the player's name and gives it back to the controller.
Server controller is now waiting for commands from the clients (in this case "startMatch").

**Client side**:
The client controller, asked by the view, tries to connect to the server, via the network handler.
When the ping is answered with an ack, it simultaneously gives back the "connectedToServer" 
message to his controller and launches a play request to the server.
The view is so asked for a name, which should be ready to send to the server when a player's 
data request is going to be made.
After the following ack the game is declared ready and the view is waited for actions.
With the same principles, the "startMatch" message is forwarded to the server.

# Player select tiles and inserts in shelf 

**Server side**:
The server controller asks the virtual view for the player to pick
the tiles from the board.
Virtual view is to forward the request to the client.
Client returns a message with a list of coordinates of the board, a list of 
tiles that are to be inserted in the shelf (ordered as player's choice),
and the column selected.
The controller then calls player's pick method on the model, passing
the client's message.

**Client side**:
The client controller is waiting for a message from the server.
When the "pickTiles" command is retrieved, the board is 
updated and the player is called to select coordinates on it.
He's then asked for the insertion order of the tiles and for the 
column in where he adds them.
Checks of a legal selection are directly made in this section.
The information is the packed in a selection message and sent to
the server.