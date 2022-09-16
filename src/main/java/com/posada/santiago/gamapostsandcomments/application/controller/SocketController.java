package com.posada.santiago.gamapostsandcomments.application.controller;


import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@ServerEndpoint("/retrieve/{correlationId}")
public class SocketController {
    private static final Logger logger = Logger.getLogger(SocketController.class.getName());
    private static Map<String, Map<String, Session>> sessions = new ConcurrentHashMap<>();

    private final Gson gson = new Gson();


    public SocketController() {
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("correlationId") String correlationId) {
        Map<String, Session> sessionSpot = sessions.getOrDefault(correlationId, new HashMap<>());
        sessionSpot.put(session.getId(), session);
        sessions.put(correlationId, sessionSpot);
        logger.info(String.format("%d Sessions Connected to { %s }", sessionSpot.size(), correlationId));
    }

    @OnClose
    public void onClose(Session session, @PathParam("correlationId") String correlationId) {
        Map<String, Session> sessionSpot = sessions.get(correlationId);
        sessionSpot.remove(session.getId());
        logger.info(String.format("Disconnected from { %s }, %d remaining", correlationId, sessionSpot.size()));
    }

    @OnError
    public void onError(Session session, @PathParam("correlationId") String correlationId, Throwable throwable) {
        sessions.get(correlationId).remove(session.getId());
        logger.log(Level.SEVERE, throwable.getMessage());
    }

    public <M> void sendModel(String correlationId, M model) {
        String msgModel = gson.toJson(model);

        if(Objects.isNull(correlationId)) return;
        if(!sessions.containsKey(correlationId)) return;

        Collection<Session> sessionsOn = sessions.get(correlationId).values();

        sessionsOn
            .forEach(session -> session.getAsyncRemote().sendText(msgModel));

        logger.info(String.format("Sent from { %s } to %d Sessions", correlationId, sessionsOn.size()));
    }
}
