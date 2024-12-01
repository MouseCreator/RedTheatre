package red.theatre.backendapp.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name="`performance`")
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nonnull
    private Long id;
    @ManyToOne
    @JoinColumn(name = "theatre_id")
    @Nonnull
    private Theatre theatre;
    @ManyToOne
    @JoinColumn(name = "performance_details_id")
    @Nonnull
    private PerformanceDetails details;
    @Column(name="date_time")
    @Nonnull
    private LocalDateTime timeDate;
}
