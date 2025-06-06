package co.com.prueba.filmflix.domain.entities;

import co.com.prueba.filmflix.utils.enums.FilmGenre;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 300)
    private String description;

    @Column(nullable = false, length = 20)
    private String duration;

    @Column(nullable = false, name = "film_genre")
    private FilmGenre filmGenre;

    @Column(nullable = false, name = "poster_url", columnDefinition = "TEXT")
    private String posterUrl;

    @Column(nullable = false, name = "trailer_url", columnDefinition = "TEXT")
    private String trailerUrl;

    @Column(nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FilmCast> filmCastList;

    @PrePersist
    public void onCreate(){
        this.enabled = true;
    }
}
