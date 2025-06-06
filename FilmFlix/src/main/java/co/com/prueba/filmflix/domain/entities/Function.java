package co.com.prueba.filmflix.domain.entities;

import co.com.prueba.filmflix.utils.enums.FunctionTime;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "functions")
public class Function {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private Film film;

    @Column(nullable = false, name = "time")
    private FunctionTime time;

    @Column(nullable = false, name = "price")
    private double functionPrice;

    @Column(nullable = false)
    private boolean enabled;

    @PrePersist
    public void onCreate(){
        this.enabled = true;
    }
}
