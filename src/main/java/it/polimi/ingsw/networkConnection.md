# Client

When client is started, player is asked to choose a server IP
address and a name.
Server IP, player's name and viewAccess object are put into 
GameNetworkHandler.java.
After that, GameViewController.java calls a method based on
the type of connection requested:

> **connectionRMIToServer()** takes the attributes in the
GameNetworkHandler.java and builds a connection with the 
server. Server is to receive the viewAccess object. The
method should not end/return until the match finishes
(match finishes when boolean 
viewAccess.isGameEnded() == true).<br>
Server is able to make every action by itself after simply 
receiving the viewAccess RMI object; nothing else should be 
made.

> **connectionSocketToServer()** takes the attributes in the
GameNetworkHandler.java and builds a connection with the
server. Server is to receive a virtualViewAccess object. The
method should not end/return until the match finishes
(match finishes when server calls announceWinner()).<br>
Server will make every action on the virtualViewAccess
object: when a method is called on the virtualViewAccess
object it has to be packed in a message, which is then sent
via Socket and received by the client; 
GameNetworkHandler.java is then to call the respective
method on the viewAccess object.

___

# Server

When server is started, GameVirtualView.java start() 
method is started on a dedicated thread.
LobbyBuilder.java is then listening for filled game rooms
that are ready to start a match.
Game room are managed in the virtualView package.
After the game room is sent, every action needed to start
the match is made by LobbyBuilder.java and
GameModelController.java.
GameVirtualView.java should be able to respond to the
method call made by LobbyBuilder.java:

> **waitForClients()** is a method called by 
LobbyBuilder.java. When a game room is ready (player inside
number is equal to max players), it is passed as the return
data of this method.<br>
After that, this method is going to be immediately called 
again by LobbyBuilder.java. It has its own thread
already created by its caller.

> **start()** is a method that should be active for all
the server run time (it never returns), on its own thread
created in the constructor.<br>
The purpose is to have it listen for clients which wants
to connect, and to manage all the game rooms actions
that are to be made on the views (some virtualViewAccess
methods are to be used here to show game rooms actions).
When a room fills up it's passed to waitForClients()
which is notified and returns it.<br>
RMI clients sends the raw viewAccess object to be put
in the game room map alongside player's name as key.
Socket clients need a virtualViewAccess object to
be constructed and after put in the same game room map.
In sockets, when methods are called on virtualViewAccess,
they should generate a message that is sent to the client
(which calls respective method on his virtualView).

[VirtualViewAccess probably needs attributes to be added
in order to remember which client it is bound to.]