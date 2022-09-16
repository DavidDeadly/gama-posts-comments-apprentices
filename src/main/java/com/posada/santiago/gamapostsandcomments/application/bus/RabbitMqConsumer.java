package com.posada.santiago.gamapostsandcomments.application.bus;



import com.google.gson.Gson;
import com.posada.santiago.gamapostsandcomments.application.bus.models.CommentModel;
import com.posada.santiago.gamapostsandcomments.application.bus.models.PostModel;
import com.posada.santiago.gamapostsandcomments.application.controller.SocketController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;



@Component
@Slf4j
public class RabbitMqConsumer {

  private final Gson gson = new Gson();
  private final SocketController controller;

  public static final String PROXY_QUEUE_POST_CREATED = "events.proxy.post.created";
  public static final String PROXY_QUEUE_COMMENT_ADDED = "events.proxy.comment.added";

  public RabbitMqConsumer(SocketController controller) {
    this.controller = controller;
  }

  @RabbitListener(queues = PROXY_QUEUE_POST_CREATED)
  public void listenToPostCreatedQueue(String postReceived) {

    PostModel post = gson.fromJson(postReceived, PostModel.class);

    log.info("[Post received]: " + post);

    controller.sendModel("mainspace", post);
  }

  @RabbitListener(queues = PROXY_QUEUE_COMMENT_ADDED)
  public void listenToCommentAddedQueue(String commentReceived){

    CommentModel comment = gson.fromJson(commentReceived, CommentModel.class);

    log.info("[Comment received]: " + comment);

    controller.sendModel(comment.getPostId(), comment);
  }
}
