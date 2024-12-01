package red.theatre.backendapp.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
@Table(name="`theatre`")
@NoArgsConstructor
public class Theatre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nonnull
    private Long id;
    @Nonnull
    @Length(min = 1, max = 255)
    private String name;
    @Nonnull
    @Length(min = 1, max = 4095)
    private String description;
    @Nonnull
    @Length(min = 1, max = 255)
    private String address;
    @Nonnull
    @Min(value = 10)
    @Max(value = 1000)
    private Integer numSeats;
}
