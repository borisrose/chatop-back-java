package boris_rentals.api.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Entity // This tells Hibernate to make a table out of this class
public class Message {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;
  
  @Column(name = "created_at", nullable = false, updatable = false)
  @CreatedDate
  private LocalDateTime created_at;

  @Column(name = "updated_at")
  @LastModifiedDate
  private LocalDateTime updated_at;

  // @ManyToOne(fetch = FetchType.LAZY, optional = false)
  // @JoinColumn(name = "user_id", nullable = false)
  // @OnDelete(action = OnDeleteAction.CASCADE)
  // @JsonIgnore
  // private User user;

  // @ManyToOne(fetch = FetchType.LAZY, optional = false)
  // @JoinColumn(name = "rental_id", nullable = false)
  // @OnDelete(action = OnDeleteAction.CASCADE)
  // @JsonIgnore
  // private Rental rental;

  private Integer user_id;
  private Integer rental_id;
  private String message;

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
  

  
  Message(Integer rental_id, Integer user_id, String message){
    this.rental_id = rental_id; 
    this.user_id = user_id;
    this.message = message;
  }

}