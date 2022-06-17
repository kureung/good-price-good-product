package kr.co.gpgp.domain.delivery;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import kr.co.gpgp.domain.order.Order;
import kr.co.gpgp.domain.order.OrderRepository;
import kr.co.gpgp.domain.user.Role;
import kr.co.gpgp.domain.user.UserRepository;
import kr.co.gpgp.repository.delivery.DeliveryRepositoryImpl;
import kr.co.gpgp.web.api.delivery.DeliveryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliverySellerService {

    private final DeliveryRepositoryImpl deliveryRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    //판매한 목록중 선택된 배송 상태를 모두 조회
    public List<Delivery> choiceStatus(String status, Long userId) {
        //1.유저에서 판매자 판별
        //2.판매자 id 조회 가반으로 주문 찾기
        //3.그 주분 기반으로 배송 찾기

        //1 단계
        Long sellerId = userRepository.findById(userId)
                .stream()
                .filter(v -> v.getRole().equals(Role.SELLER))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("판매자인 유저 ID가 없습니다."))
                .getId();

        //2단계
        List<Order> orderList = orderRepository.findByDeliveryId(sellerId);

        //3 단계
        List<Delivery> deliveryList = Delivery.ofOrder(orderList);

        return deliveryList.stream().filter(list -> Objects.equals(list.getStatus().getStatus(), status))
                .collect(Collectors.toList());
    }

    //판매한 목록중 상태구분없이 모두 조회
    public List<Delivery> allStatus(Long userId) {
        //1 단계
        Long sellerId = userRepository.findById(userId)
                .stream()
                .filter(v -> v.getRole().equals(Role.SELLER))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("판매자인 유저 ID가 없습니다."))
                .getId();

        //2단계
        List<Order> orderList = orderRepository.findByDeliveryId(sellerId);

        //3 단계
        List<Delivery> deliveryList = Delivery.ofOrder(orderList);
        return deliveryRepository.findByUserId(userId);
    }


    //선택한 판매를 배송상태변경을 할수있다.
    public void targetUpdate(String status, DeliveryRequest deliveryRequest) {
        Delivery delivery = deliveryRepository.findById(deliveryRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("선택한 배송 ID 가 없습니다."));

        if (status.equals("ACCEPT")) {
            delivery.nextStepInstruct();
        } else if (status.equals("INSTRUCT")) {
            delivery.nextStepDeparture();
        } else {
            throw new IllegalArgumentException("선택한 배송 ID 가 없습니다.");
        }

    }
}
