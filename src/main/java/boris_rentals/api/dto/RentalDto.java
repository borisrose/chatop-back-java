package boris_rentals.api.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RentalDto {

    private Integer id;
    private String name;
    private Float surface;
    private Float price;
    private String picture;
    private String description;
    private Integer owner_id;
    private String created_at;
    private String updated_at;

    public RentalDto(Integer id, String name, Float surface, Float price, String picture, String description,Integer owener_id, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.picture = picture;
        this.owner_id = owener_id;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // Formater la date selon le format spécifié
        this.created_at = created_at.format(formatter);
        this.updated_at = updated_at.format(formatter);
    }

}
