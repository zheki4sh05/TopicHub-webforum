package web.forum.topichub.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.*;
import java.time.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PasswordResetCode implements Serializable {
    private String email;
    private String code;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}
