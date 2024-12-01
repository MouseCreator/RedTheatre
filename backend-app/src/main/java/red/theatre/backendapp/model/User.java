package red.theatre.backendapp.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
@Table(name="`user`")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nonnull
    private Long id;
    @Nonnull
    @Length(min = 1, max = 255)
    private String name;
    @Nonnull
    @Column(nullable = false, columnDefinition = "VARCHAR(31) DEFAULT 'client'")
    private String role;
}
