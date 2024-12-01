package red.theatre.backendapp.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
@Embeddable
@Data
@NoArgsConstructor
public class PFKey implements Serializable {

    @Serial
    private static final long serialVersionUID = -8771030606491104907L;
    private Long performanceId;
    private Long figureId;

    public PFKey(Long performanceId, Long figureId) {
        this.performanceId = performanceId;
        this.figureId = figureId;
    }
}
