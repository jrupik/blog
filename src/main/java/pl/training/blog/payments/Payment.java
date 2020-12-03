package pl.training.blog.payments;

import lombok.*;
import pl.training.blog.jpa.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Identifiable<Long> {

    @GeneratedValue
    @Id
    private Long id;
    @Column(name = "time_stamp", nullable = false)
    private LocalDateTime timestamp;
    private Long value;

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) otherObject;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

}