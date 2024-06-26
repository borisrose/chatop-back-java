package boris_rentals.api.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Entity // This tells Hibernate to make a table out of this class
public class Rental {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;
  
  private String name;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreatedDate
  private LocalDateTime created_at;

  @Column(name = "updated_at")
  @LastModifiedDate
  private LocalDateTime updated_at;

  private String picture;
  private String description;
  private Float surface;
  private Float price;
  private Integer owner_id;

  public Rental(String name, String picture, String description, Float surface, Float price, Integer owner_id){
    this.name = name;
    this.picture = picture;
    this.description = description;
    this.surface = surface;
    this.price = price; 
    this.owner_id = owner_id;
   
  }


  @PrePersist
  protected void onCreate() {
        if (created_at == null) {
            created_at = LocalDateTime.now();
        }
        updated_at = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updated_at = LocalDateTime.now();
  }
  

}