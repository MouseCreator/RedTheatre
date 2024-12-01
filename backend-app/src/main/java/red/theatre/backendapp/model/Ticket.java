package red.theatre.backendapp.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OneToMany
    private List<Seat> seatList;
}
