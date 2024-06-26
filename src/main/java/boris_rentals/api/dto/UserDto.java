package boris_rentals.api.dto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private String created_at;
    private String updated_at;

    public UserDto(Integer id, String name, String email, LocalDateTime created_at, LocalDateTime updated_at){
        this.id = id;
        this.name = name;
        this.email = email;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                // Formater la date selon le format spécifié
        this.created_at = created_at.format(formatter);
        this.updated_at = updated_at.format(formatter);
    }
}


