package kr.co.gpgp.domain.delivery.entity;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.CascadeType;
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
import kr.co.gpgp.domain.delivery.entity.enums.DeliveryStatus;
import kr.co.gpgp.domain.requirement.entity.Requirement;
import kr.co.gpgp.domain.user.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "requirement_id")
    private Requirement requirement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @Enumerated(STRING)
    private DeliveryStatus status;

    private Delivery(Requirement requirement, Address address) {
        this.requirement = requirement;
        this.address = address;
        this.status = status.init(); // 배송 상태 초기화
    }

    public static Delivery of(Requirement requirement, Address address) {
        return new Delivery(requirement, address);
    }

    public void next() {
        Role role = Role.USER;// 임시 조치
        status = status.next(role);
    }

    public void next(Role role) {
        status = status.next(role);
    }

    public Long getUserId() {
        return getAddress().getUser().getId();
    }

    public String getStatusMessage() {
        return status.getMessage();
    }

    public String getRequirementMessage() {
        return getRequirement().getMessage();
    }

    public String getAddressRoadName() {
        return getAddress().getRoadName();
    }

    public String getAddressZipCode() {
        return getAddress().getZipCode();
    }

    public String getAddressName() {
        return getAddress().getName();
    }

    public String getAddressDetailed() {
        return getAddress().getDetailed();
    }

    public boolean isAccept() {
        return getStatus() == DeliveryStatus.ACCEPT;
    }

    public boolean isInstruct() {
        return getStatus() == DeliveryStatus.INSTRUCT;
    }

    public boolean isDeparture() {
        return getStatus() == DeliveryStatus.DEPARTURE;
    }

    public boolean isFinalDelivery() {
        return getStatus() == DeliveryStatus.FINAL_DELIVERY;
    }

    public boolean isNoneTracking() {
        return getStatus() == DeliveryStatus.NONE_TRACKING;
    }


}
