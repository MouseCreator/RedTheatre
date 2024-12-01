package red.theatre.backendapp.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="`ticket`")
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nonnull
    private Long id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Nonnull
    @JoinColumn(name = "`owner`", referencedColumnName = "id")
    private User owner;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Nonnull
    @JoinColumn(name = "performance_id", referencedColumnName = "id")
    private Performance performance;
    @Nonnull
    @Column(nullable = false, columnDefinition = "VARCHAR(31) DEFAULT 'unpaid'")
    private String status;
    @Nonnull
    @Column(name = "`created_at`")
    private LocalDateTime createdAt;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Seat> seatList;
}
