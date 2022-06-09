package kr.co.gpgp.domain.delivery.service;

import java.util.List;
import kr.co.gpgp.domain.address.repository.AddressRepository;
import kr.co.gpgp.domain.delivery.dto.DeliveryResponse;
import kr.co.gpgp.domain.delivery.entity.Delivery;
import kr.co.gpgp.domain.delivery.repository.DeliveryRepository;
import kr.co.gpgp.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryUserService {

    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;

    private final AddressRepository addressRepository;


    //유저는 자신의 모든 배송 을 조회를 할수 있다.
    public List<DeliveryResponse> selectAll(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없어 배송을 조회할수 없습니다."));

        return deliveryRepository.findByUserId(userId);
    }

    //유저는 자신의 배송 을 조회를 할수 있다.
    public DeliveryResponse select(Long userId, Long deliveryId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없어 배송을 조회할수 없습니다."));

        Delivery delivery = deliveryRepository.findById(deliveryId)
                .filter(s -> s.getUserId() == userId  )
                .orElseThrow(() -> new IllegalArgumentException("선택한 배송은 유저가 생성한게 아닙니다."));


        return DeliveryResponse.of(delivery.getId(),
                delivery.getRequirementMessage(),
                delivery.getAddressRoadName(),
                delivery.getAddressZipCode(),
                delivery.getAddressName(),
                delivery.getAddressDetailed());
    }


}
