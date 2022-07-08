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

    public List<Delivery> choiceStatus(String status, Long userId) {

        Long sellerId = userRepository.findById(userId)
                .stream()
                .filter(v -> v.getRole().equals(Role.SELLER))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("판매자인 유저 ID가 없습니다."))
                .getId();

        List<Order> orderList = orderRepository.findByUserId(sellerId);

        List<Delivery> deliveryList = Delivery.ofOrder(orderList);

        return deliveryList.stream().filter(list -> Objects.equals(list.getStatus().getStatus(), status))
                .collect(Collectors.toList());
    }

    public List<Delivery> allStatus(Long userId) {

        Long sellerId = userRepository.findById(userId)
                .stream()
                .filter(v -> v.getRole().equals(Role.SELLER))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("판매자인 유저 ID가 없습니다."))
                .getId();

        List<Order> orderList = orderRepository.findByUserId(sellerId);

        return Delivery.ofOrder(orderList);
    }

    public void targetUpdate(String status, DeliveryRequest deliveryRequest) {
        Delivery delivery = deliveryRepository.findById(deliveryRequest.getId())
                .orElseThrow(DeliveryNotFoundException::new);

        if (status.equals("ACCEPT")) {
            delivery.nextStepInstruct();
        } else if (status.equals("INSTRUCT")) {
            delivery.nextStepDeparture();
        } else {
            throw new IllegalArgumentException("선택한 배송 ID 가 없습니다.");
        }

    }

}
