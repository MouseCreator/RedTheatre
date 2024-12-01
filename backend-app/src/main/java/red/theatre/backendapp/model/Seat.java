package red.theatre.backendapp.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
@Entity
@Table(name="`seat`")
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nonnull
    private Long id;
    @ManyToOne
    @JoinColumn(name = "performance_id")
    @Nonnull
    private Performance performance;
    @Length(min = 1, max = 31)
    @Nonnull
    private String status;
    @DecimalMin(value = "0.00")
    private BigDecimal price;
}
