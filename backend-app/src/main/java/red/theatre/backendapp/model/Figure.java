package red.theatre.backendapp.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
@Table(name="`figure`")
@NoArgsConstructor
public class Figure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nonnull
    private Long id;
    @Nonnull
    @Length(min = 2, max = 255)
    private String name;
    @Nonnull
    @Length(min = 2, max = 255)
    private String role;
}
