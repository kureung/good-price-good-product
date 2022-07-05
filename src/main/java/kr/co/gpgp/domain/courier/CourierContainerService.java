package kr.co.gpgp.domain.courier;

import java.util.List;
import javax.persistence.Transient;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.address.AddressRepository;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.delivery.DeliveryRepository;
import kr.co.gpgp.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourierContainerService {

    private final CourierRepository courierRepository;
    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CourierContainerRepository courierContainerRepository;

    private static final int DELIVERY_COUNT_MAX = 10;

    @Transient
    public void deliveryToCourier(Delivery delivery) {
        Delivery findDelivery = deliveryRepository.save(delivery);

        Address address = addressRepository.findById(delivery.getAddress().getId())
                .orElseThrow(() -> new IllegalArgumentException("배송 주소 ID 가 없어 배송사로 전달하지 못합니다."));

        CourierArea courierArea = CourierContainerService.strToArea(address.getRoadName());

        Courier findCourier = deliveryDistribution(courierArea);

        CourierContainer courierContainer = CourierContainer.of(findCourier, findDelivery);

        courierContainerRepository.save(courierContainer);
    }

    private Courier deliveryDistribution(CourierArea courierArea) {

        //해당 지역 배송원들 조회
        List<Courier> courierList = courierRepository.findByCourierArea(courierArea);

        for (Courier couriers : courierList) {
            //해당 지역 배송원 컨테이너 검색
            List<CourierContainer> courierContainerList = courierContainerRepository.findByCourier(couriers);

            //타겟이된 지역 어느 한 배송원의 배송대기 검색
            int filterCourierContainer = (int) courierContainerList.stream()
                    .filter(ls -> ls.getCourierStatus().equals(CourierStatus.WAITING_FOR_DELIVERY)).count();

            //배송대기가 10개 이하 이라면
            if (filterCourierContainer < DELIVERY_COUNT_MAX) {
                return couriers;
            }
        }

        if (courierList.size() == 0) {
            throw new IllegalArgumentException("배송 지역 배송원이 존재 하지 않습니다.");
        }
        throw new IllegalArgumentException("인계 받을수 있는 배송원이 없습니다.");
    }

    private static CourierArea strToArea(String roadName) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < roadName.length(); i++) {
            if (roadName.charAt(i) == ' ') {
                break;
            }
            str.append(roadName.charAt(i));
        }

        return CourierArea.findArea(String.valueOf(str));
    }
}
