package kr.co.gpgp.domain.courier;

import java.util.List;
import java.util.stream.Collectors;
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

        List<Courier> courierList = courierRepository.findByCourierArea(courierArea)
                .stream()
                .filter(ls -> ls.getCourierStatus()==CourierStatus.WAITING_FOR_DELIVERY)
                .collect(Collectors.toList());

        for (Courier courier : courierList) {
            int count = courierContainerRepository.findByCourierList(courier).size();
            if (count < DELIVERY_COUNT_MAX)
                return courier;
        }

        throw new CourierNotFoundException(courierArea + " 지역에 배송원이 없습니다.");
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
