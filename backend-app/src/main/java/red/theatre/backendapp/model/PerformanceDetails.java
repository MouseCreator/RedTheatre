package red.theatre.backendapp.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@Entity
@Table(name = "`performance_details`")
public class PerformanceDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nonnull
    private Long id;
    @Nonnull
    @Length(min = 1, max = 255)
    private String name;
    @Length(max = 4095)
    private String description;
    @Column(name = "picture_url")
    private String pictureUrl;
}
