package com.example.WDSRTC;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.example.WDSRTC.util.SplitJoinRoomMessage.splitJoinRoomMessage;

@Component
public class ServerCommandLineRunner implements CommandLineRunner {
    private final SocketIOServer server;

    @Autowired
    public ServerCommandLineRunner(SocketIOServer server) {
        this.server = server;

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("Socket " + socketIOClient.getSessionId() + " connect to server.");
            }
        });

        server.addEventListener("join-room", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                String[] splitStr = splitJoinRoomMessage(s);
                String roomId = splitStr[0], userId = splitStr[1];

                socketIOClient.joinRoom(roomId);
                System.out.println("User " + userId + " join room #" + roomId + ".");

                server.getRoomOperations(roomId).sendEvent("user-connected", userId);
            }
        });

        server.addDisconnectListener(new DisconnectListener() {
            // TODO socket.broadcast.to(roomId).emit('user-disconnected', userId)
            @Override
            public void onDisconnect(SocketIOClient socketIOClient) {
                System.out.println("Socket " + socketIOClient.getSessionId() + " disconnect to server.");
            }
        });
    }

    @Override
    public void run(String... args) throws Exception {
        server.start();
    }
}
