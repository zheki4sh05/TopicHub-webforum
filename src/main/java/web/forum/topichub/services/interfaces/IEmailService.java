package web.forum.topichub.services.interfaces;

import web.forum.topichub.model.*;

public interface IEmailService {
    void sendCommentNotification(Comment comment, User author);
}
