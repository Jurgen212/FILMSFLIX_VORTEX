package co.com.prueba.filmflix.domain.entities;

import co.com.prueba.filmflix.utils.enums.StaffRol;
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
public class FilmStaff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "staff_rol")
    private StaffRol staffRol;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "filmStaff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FilmCast> filmCastList;

    @PrePersist
    private void onCreate(){
        this.enabled = true;
    }
}
