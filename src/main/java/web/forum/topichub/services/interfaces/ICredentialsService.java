package web.forum.topichub.services.interfaces;

import web.forum.topichub.dto.*;

import java.util.*;

public interface ICredentialsService {
    void generateCode(CodeDto request, Locale locale);

    String submitCode(CodeDto request);

    void update(CredentialsDto credentialsDto);
}
