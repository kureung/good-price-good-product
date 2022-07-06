package kr.co.gpgp.domain.courier;

import java.util.List;
import kr.co.gpgp.domain.delivery.Delivery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourierContainerService {

    private static final int DELIVERY_COUNT_MAX = 10;

    private final CourierRepository courierRepository;
    private final CourierContainerRepository courierContainerRepository;

    @Transactional
    public void sellerToCourier(Delivery delivery) {

        CourierArea courierArea = areaEnumValueOf(delivery.getAddressRoadName());
        Courier findCourier = productToCourier(courierArea);
        CourierContainer courierContainer = CourierContainer.of(findCourier, delivery);

        courierContainerRepository.save(courierContainer);
    }

    private Courier productToCourier(CourierArea courierArea) {

        List<Courier> courierList = courierRepository.findByCourierArea(courierArea);

        for (Courier couriers : courierList) {
            List<CourierContainer> courierContainerList = courierContainerRepository.findByCourierList(couriers);

            int filterCourierContainer = (int) courierContainerList.stream()
                    .filter(ls -> ls.getCourierStatus().equals(CourierStatus.WAITING_FOR_DELIVERY))
                    .count();

            if (filterCourierContainer < DELIVERY_COUNT_MAX) {
                return couriers;
            }

        }

        throw new IllegalArgumentException("인계 받을수 있는 배송원이 없습니다.");
    }

    private static CourierArea areaEnumValueOf(String roadName) {
        StringBuilder str = new StringBuilder();

        str.append(roadName.charAt(0));

        for (int i = 1; i < roadName.length(); i++) {
            if (roadName.charAt(i - 1)=='시' || roadName.charAt(i - 1)=='도') break;
            str.append(roadName.charAt(i));
        }

        return CourierArea.findArea(String.valueOf(str));
    }

}
