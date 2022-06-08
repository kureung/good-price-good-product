package kr.co.gpgp.domain.delivery.entity;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import kr.co.gpgp.domain.address.entity.Address;
import kr.co.gpgp.domain.delivery.entity.enums.DeliveryStatusImpl;
import kr.co.gpgp.domain.requirement.entity.Requirement;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requirement_id")
    private Requirement requirement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @Enumerated(STRING)
    private DeliveryStatusImpl status;

    private Delivery(Requirement requirement, Address address) {
        this.requirement = requirement;
        this.address = address;
        this.status = DeliveryStatusImpl.ACCEPT;
        status.statusMessageInit();
        status.sequenceInit();
    }

    public static Delivery of(Requirement requirement, Address address) {
        return new Delivery(requirement, address);
    }

    public void next() {
        status = status.next();
    }

    public String getStausMessage(){
        return getStatus().get();
    }
    public String getRequirementMessage(){
        return getRequirement().getMessage();
    }
    public String getAddressRoadName(){
        return getAddress().getRoadName();
    }
    public String getAddressZipCode(){
        return getAddress().getZipCode();
    }
    public String getAddressName(){
        return getAddress().getName();
    }
    public String getAddressDetailed(){
        return getAddress().getDetailed();
    }

}
