package boris_rentals.api.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Entity // This tells Hibernate to make a table out of this class
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;


  @Column(name = "created_at", nullable = false, updatable = false)
  @CreatedDate
  private LocalDateTime created_at;

  @Column(name = "updated_at")
  @LastModifiedDate
  private LocalDateTime updated_at;
 
  private String name;
  private String email;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  public User(String name, String email, String password){
    this.name = name; 
    this.email = email;
    this.password = password;
    this.created_at = LocalDateTime.now();
    this.updated_at = LocalDateTime.now();

  
  }

}