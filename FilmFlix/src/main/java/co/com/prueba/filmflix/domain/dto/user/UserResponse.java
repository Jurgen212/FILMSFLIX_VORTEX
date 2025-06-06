package co.com.prueba.filmflix.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UserResponse {
    private Long id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String role;
    private boolean enabled;
}
