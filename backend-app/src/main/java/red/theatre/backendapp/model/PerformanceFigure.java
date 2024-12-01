package red.theatre.backendapp.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="`performance_figure`")
public class PerformanceFigure {
    @EmbeddedId
    private PFKey id;
    @ManyToOne
    @MapsId("performanceId")
    @JoinColumn(name = "performance_id")
    private Performance performance;
    @ManyToOne
    @MapsId("figureId")
    @JoinColumn(name = "figure_id")
    private Figure figure;
}
