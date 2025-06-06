package co.com.prueba.filmflix.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name", length = 20)
    private String name;

    @Column(nullable = false, name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(nullable = false, name = "active")
    private boolean enabled;

    @PrePersist
    public void onCreate(){
        this.enabled = true;
    }
}
