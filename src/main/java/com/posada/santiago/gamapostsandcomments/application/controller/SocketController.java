package com.posada.santiago.gamapostsandcomments.application.controller;


import com.google.gson.Gson;
import com.posada.santiago.gamapostsandcomments.application.bus.models.CommentModel;
import com.posada.santiago.gamapostsandcomments.application.bus.models.PostModel;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.logging.Logger;

@Component
@ServerEndpoint("/retrieve/{correlationId}")
public class SocketController {
    private static final Logger logger = Logger.getLogger(SocketController.class.getName());
    private static Map<String, Map<String, Session>> sessions;
    private final Gson gson = new Gson();


    public SocketController() {
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("correlationId") String correlationId) {
    }

    @OnClose
    public void onClose(Session session, @PathParam("correlationId") String correlationId) {

    }

    @OnError
    public void onError(Session session, @PathParam("correlationId") String correlationId, Throwable throwable) {

    }

    public void sendPostCreated(String correlationId, PostModel model) {


    }

    public void sendCommentAdded(String correlationId, CommentModel model) {




    }
}
