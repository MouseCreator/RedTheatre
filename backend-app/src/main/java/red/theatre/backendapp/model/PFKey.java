package red.theatre.backendapp.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
@Embeddable
@Data
public class PFKey implements Serializable {

    @Serial
    private static final long serialVersionUID = -8771030606491104907L;
    private Long performanceId;
    private Long figureId;
}
