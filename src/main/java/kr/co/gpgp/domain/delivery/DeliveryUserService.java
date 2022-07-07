package kr.co.gpgp.domain.delivery;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.address.AddressNotFoundException;
import kr.co.gpgp.domain.address.AddressRepository;
import kr.co.gpgp.domain.requirement.Requirement;
import kr.co.gpgp.domain.requirement.RequirementNotFoundException;
import kr.co.gpgp.domain.requirement.RequirementRepository;
import kr.co.gpgp.domain.user.UserNotFoundException;
import kr.co.gpgp.domain.user.UserRepository;
import kr.co.gpgp.repository.delivery.DeliveryRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryUserService {

    private final DeliveryRepositoryImpl deliveryRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final RequirementRepository requirementRepository;

    public List<Delivery> selectAll(Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return deliveryRepository.findByUserId(userId);
    }

    public Delivery select(Long userId, Long deliveryId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return deliveryRepository.findById(deliveryId)
                .filter(s -> s.getUserId()==userId)
                .orElseThrow(DeliveryNotFoundException::new);
    }

    public void nextStepPurchaseConfirmation(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(DeliveryNotFoundException::new);
        delivery.nextStepPurchaseConfirmation();
    }

    @Transactional
    public Delivery save(Long id, Long addressId, Long requirementId) {
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        Requirement requirement = requirementRepository.findById(requirementId).orElseThrow(RequirementNotFoundException::new);
        Address address = addressRepository.findById(addressId).orElseThrow(AddressNotFoundException::new);

        Delivery delivery = Delivery.of(requirement, address);

        return deliveryRepository.save(delivery);
    }

    public void delete(Long userId, Long deliveryId) {

        Delivery findDelivery = deliveryRepository.findById(deliveryId).orElseThrow(DeliveryNotFoundException::new);

        userRepository.findById(userId)
                .stream()
                .filter(u -> u.getId()==findDelivery.getUserId())
                .findFirst()
                .orElseThrow(DeliveryNotFoundException::new);

        deliveryRepository.delete(findDelivery);
    }

    public void update(Long id, Long deliveryId) {
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        Delivery findDelivery = deliveryRepository.findById(deliveryId).orElseThrow(DeliveryNotFoundException::new);

        findDelivery.nextStepPurchaseConfirmation();
    }

    public List<Long> toAddressIdList(List<Delivery> delivery) {
        return delivery.stream()
                .map(ls -> ls.getAddress().getId())
                .collect(Collectors.toList());
    }

    public List<Long> toRequirementIdList(List<Delivery> delivery) {
        return delivery.stream()
                .map(ls -> ls.getRequirement().getId())
                .collect(Collectors.toList());
    }

}
