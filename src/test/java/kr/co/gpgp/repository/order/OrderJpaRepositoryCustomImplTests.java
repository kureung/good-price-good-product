package kr.co.gpgp.repository.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.address.AddressRepository;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.delivery.DeliveryRepository;
import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.item.ItemInfo;
import kr.co.gpgp.domain.order.Order;
import kr.co.gpgp.domain.order.OrderRepository;
import kr.co.gpgp.domain.order.OrderSearchCondition;
import kr.co.gpgp.domain.order.OrderSearchResponse;
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
class OrderJpaRepositoryCustomImplTests {

    @Autowired
    private OrderRepository sut;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private RequirementRepository requirementRepository;

    @Test
    void 주문_조회_테스트() {
        User user = User.of("name", "abc@naver.com", Role.USER);
        User savedUser = userRepository.save(user);

        for (int i = 0; i < 10; i++) {

            Requirement requirement = new Requirement("요청사항");
            requirementRepository.save(requirement);
            Address address = Address.of(savedUser, "123456789", "12345", "home", "123456789");
            addressRepository.save(address);

            Delivery delivery = Delivery.of(requirement, address);
            deliveryRepository.save(delivery);

            List<OrderLine> orderLines = new ArrayList<>();

            for (int k = 0; k < 5; k++) {

                ItemInfo info = ItemInfo.builder()
                        .code("123" + k)
                        .name("item" + k)
                        .weight(k)
                        .build();

                Item item = Item.builder()
                        .stockQuantity(100)
                        .price(1000)
                        .info(info)
                        .build();

                itemJpaRepository.save(item);

                OrderLine orderLine = OrderLine.of(item, k + 50);
                orderLines.add(orderLine);

            }

            Order order = Order.of(savedUser, delivery, orderLines);
            orderJpaRepository.save(order);

        }

        OrderSearchCondition condition = OrderSearchCondition.builder()
                .userId(savedUser.getId())
                .build();

        int PageSize = 3;
        PageRequest pageRequest = PageRequest.of(0, PageSize);

        Page<OrderSearchResponse> orderSearchResponse = sut.orderSearch(condition, pageRequest);

        List<OrderSearchResponse> content = orderSearchResponse.getContent();

        assertThat(content.size()).isEqualTo(PageSize);
    }

}
