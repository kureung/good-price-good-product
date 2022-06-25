package kr.co.gpgp.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import java.util.UUID;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.address.AddressRepository;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.delivery.DeliveryRepository;
import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.item.ItemInfo;
import kr.co.gpgp.domain.orderline.OrderLine;
import kr.co.gpgp.domain.requirement.Requirement;
import kr.co.gpgp.domain.requirement.RequirementRepository;
import kr.co.gpgp.domain.user.Role;
import kr.co.gpgp.domain.user.User;
import kr.co.gpgp.domain.user.UserRepository;
import kr.co.gpgp.repository.item.ItemJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTests {

    @Autowired
    private OrderService sut;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private RequirementRepository requirementRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void 상품_주문_테스트() {
        // given
        User user = User.of("name", "abc@naver.com", Role.USER);
        User savedUser = userRepository.save(user);

        Requirement requirement = new Requirement("요청사항");
        Address address = Address.of(savedUser, "123456789", "12345", "주소", "주소주소");

        ItemInfo info = ItemInfo.builder()
                .name("name")
                .build();

        Item item = Item.builder()
                .stockQuantity(11)
                .info(info)
                .price(1000)
                .build();
        itemJpaRepository.save(item);

        List<OrderLine> orderLines = List.of(OrderLine.of(item, 10));

        // when, then
        assertDoesNotThrow(() ->
                sut.order(savedUser.getId(), address, requirement, orderLines)
        );

    }

    @Test
    void 결제완료_단계에서_주문취소시_예외가_발생하지_않는다() {
        // given
        User user = User.of("name", "abc@naver.com", Role.USER);
        User savedUser = userRepository.save(user);

        Requirement requirement = new Requirement("요청사항");
        Address address = Address.of(savedUser, "123456789", "12345", "주소", "주소주소");

        ItemInfo info = ItemInfo.builder()
                .name("name")
                .build();

        Item item = Item.builder()
                .stockQuantity(11)
                .info(info)
                .price(1000)
                .build();
        itemJpaRepository.save(item);

        List<OrderLine> orderLines = List.of(OrderLine.of(item, 10));

        Long orderId = sut.order(
                savedUser.getId(),
                address,
                requirement,
                orderLines);

        // when, then
        assertDoesNotThrow(() -> sut.cancel(orderId));
    }

    @Test
    void 상품준비중_단계에서_주문취소시_예외가_발생하지_않는다() {
        // given
        User user = User.of("name", "abc@naver.com", Role.USER);
        User savedUser = userRepository.save(user);

        Requirement requirement = new Requirement("요청사항");
        Address address = Address.of(savedUser, "123456789", "12345", "주소", "주소주소");

        ItemInfo info = ItemInfo.builder()
                .name("name")
                .build();

        Item item = Item.builder()
                .stockQuantity(11)
                .info(info)
                .price(1000)
                .build();
        itemJpaRepository.save(item);

        List<OrderLine> orderLines = List.of(OrderLine.of(item, 10));

        Long orderId = sut.order(
                savedUser.getId(),
                address,
                requirement,
                orderLines);

        Order findOrder = orderRepository.findById(orderId).orElseThrow();
        Delivery delivery = findOrder.getDelivery();
        delivery.nextStepInstruct();

        // when, then
        assertDoesNotThrow(() -> sut.cancel(orderId));
    }

    @Test
    void 주문_검색_테스트() {
        // given
        User user = User.of("name1", "abc@naver.com", Role.USER);
        User savedUser = userRepository.save(user);

        Requirement requirement = new Requirement("요청사항");
        requirementRepository.save(requirement);

        Address address = Address.of(user, "123456789", "12345", "name1", "detailed");
        addressRepository.save(address);

        Delivery delivery = Delivery.of(requirement, address);
        deliveryRepository.save(delivery);

        OrderSearchCondition condition = OrderSearchCondition.builder()
                .userId(savedUser.getId())
                .build();

        for (int i = 0; i < 100; i++) {
            ItemInfo info = ItemInfo.builder()
                    .name("name")
                    .build();

            Item item = Item.builder()
                    .info(info)
                    .price(1000)
                    .stockQuantity(100)
                    .build();
            itemJpaRepository.save(item);

            OrderLine orderLine = OrderLine.of(item, i);
            List<OrderLine> orderLines = List.of(orderLine);

            String orderCode = UUID.randomUUID().toString();
            Order order = Order.of(user, delivery, orderLines, orderCode);
            orderRepository.save(order);
        }

        // when
        int size = 5;
        PageRequest pageRequest = PageRequest.of(0, size);
        Page<OrderSearchResponse> searchResponses = sut.searchOrder(condition, pageRequest);
        List<OrderSearchResponse> content = searchResponses.getContent();

        // then
        assertThat(content.size()).isEqualTo(size);
    }

}