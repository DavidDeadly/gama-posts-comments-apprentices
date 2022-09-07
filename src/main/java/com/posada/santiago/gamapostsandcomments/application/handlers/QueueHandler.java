package com.posada.santiago.gamapostsandcomments.application.handlers;

import com.google.gson.Gson;
import com.posada.santiago.gamapostsandcomments.application.bus.models.CommentModel;
import com.posada.santiago.gamapostsandcomments.application.bus.models.PostModel;
import org.springframework.stereotype.Service;

@Service
public class QueueHandler {

  private final Gson gson = new Gson();

  public <M> void process(String model, Class<M> mClass) {
    Object processedModel = gson.fromJson(model, mClass);

    System.out.println(processedModel);
  }
}
