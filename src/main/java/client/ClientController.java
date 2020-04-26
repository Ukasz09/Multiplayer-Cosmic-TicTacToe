package client;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.Gui;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.server.Messages;
import com.github.Ukasz09.ticTacToeTDD.server.ServerController;

import java.io.IOException;

public class ClientController {
    private Client client;
    private Gui gui;
    private ViewManager manager;

    private boolean gameIsEnd = false;

    //----------------------------------------------------------------------------------------------------------------//
    public ClientController(Gui gui) {
        this.client = new Client();
        this.gui = gui;
        manager = ViewManager.getInstance();
    }

    public void startGame() throws IOException {
        client.startConnection(ServerController.SERVER_PORT);
        client.sendMessage(Messages.START_BTN_CLICKED);
        while (!gameIsEnd)
            processResponse(client.readResponse());
    }

    private void processResponse(String response) {
        switch (response) {
            case Messages.CLEAR_ACTION_NODES:
                manager.clearActionNodes();
                break;

            case Messages.RESET_ACTUAL_PLAYER_ID:
                gui.resetActualPlayerID();
                break;

            case Messages.SCENE_TO_NICK_PAGE:
                gui.changeSceneToNewNickChoosePage();
                break;

            case Messages.WAITING_FOR_OTHER_PLAYER: {
                //todo: wyprintowac ze czekam na drugiego gracza
            }
            break;

            //todo: tu skonczylem
        }
    }


}
