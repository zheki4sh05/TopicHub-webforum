package web.forum.topichub.security.service;

import web.forum.topichub.dto.*;

public interface IAuthenticationService {
    void updatePassword(CredentialsDto credentialsDto);
}
