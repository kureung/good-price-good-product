package kr.co.gpgp.domain.delivery.entity;

import static javax.persistence.EnumType.STRING;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import kr.co.gpgp.domain.delivery.entity.enums.StatusImpl;
import lombok.Getter;

@Entity
@Getter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(STRING)
    private StatusImpl status;

    protected Delivery() {
        status = StatusImpl.ACCEPT;
    }

    public void next() {
        status = status.next();
    }

    public String getStatus() {
        return status.get();
    }
}
