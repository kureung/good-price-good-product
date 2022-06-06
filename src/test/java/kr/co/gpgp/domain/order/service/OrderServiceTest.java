package kr.co.gpgp.domain.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import kr.co.gpgp.domain.address.entity.Address;
import kr.co.gpgp.domain.delivery.entity.Delivery;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.entity.ItemInfo;
import kr.co.gpgp.domain.item.repository.ItemRepository;
import kr.co.gpgp.domain.order.entity.Order;
import kr.co.gpgp.domain.order.enums.OrderStatus;
import kr.co.gpgp.domain.order.repository.OrderRepository;
import kr.co.gpgp.domain.orderline.dto.OrderLineRequest;
import kr.co.gpgp.domain.orderline.entity.OrderLine;
import kr.co.gpgp.domain.orderline.service.OrderLineDtoService;
import kr.co.gpgp.domain.requirement.entity.Requirement;
import kr.co.gpgp.domain.user.entity.Role;
import kr.co.gpgp.domain.user.entity.User;
import kr.co.gpgp.domain.user.repository.UserRepository;
import kr.co.gpgp.web.exception.ErrorCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderLineDtoService orderLineDtoService;

    @ParameterizedTest
    @MethodSource("provideOrderParam")
    void 상품_주문_테스트(User user, Delivery delivery, Item item) {

        // given
        User savedUser = userRepository.save(user);

        itemRepository.save(item);

        List<OrderLineRequest> orderLineRequests = List.of(
                OrderLineRequest.builder()
                        .itemCode(item.getInfo().getCode())
                        .itemQuantity(10)
                        .build());

        // when
        Long orderId = orderService.order(
                savedUser.getId(),
                delivery,
                orderLineRequests);

        // then
        Order findOrder = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        assertAll(
                () -> assertThat(findOrder.getOrderStatus())
                        .isEqualTo(OrderStatus.ORDER),

                () -> assertThat(findOrder.getDelivery())
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(delivery),

                () -> assertThat(findOrder.getUser())
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(user)
        );
    }

    private static Stream<Arguments> provideOrderParam() {

        User user = User.of("name", "abc@naver.com", Role.USER);

        Requirement requirement = new Requirement("요청사항");
        Address address = Address.of(user, "123456789", "12345", "주소", "주소주소");
        Delivery delivery = Delivery.of(requirement, address);

        Item item = Item.of(
                1000,
                30,
                ItemInfo.of(
                        "item1",
                        10,
                        "123",
                        LocalDate.now(),
                        "www.naver.com"));

        return Stream.of(
                Arguments.of(user, delivery, item));
    }

    @ParameterizedTest
    @MethodSource("provideOrderExceptionTestParam")
    void 상품_주문시_재고수량을_초과하면_예외가_발생한다(User user, Delivery delivery, Item item) {

        // given
        User savedUser = userRepository.save(user);

        itemRepository.save(item);

        List<OrderLineRequest> lineRequests = List.of(OrderLineRequest.builder()
                .itemCode("123")
                .itemQuantity(40)
                .build());

        // then
        assertThatThrownBy(() -> orderService.order(
                savedUser.getId(),
                delivery,
                lineRequests))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(ErrorCode.STOCK_OUT_OF_RANGE.getMessage());
    }

    private static Stream<Arguments> provideOrderExceptionTestParam() {

        User user = User.of("name", "abc@naver.com", Role.USER);

        Requirement requirement = new Requirement("요청사항");
        Address address = Address.of(user, "123456789", "12345", "주소", "주소주소");
        Delivery delivery = Delivery.of(requirement, address);

        Item item = Item.of(
                1000,
                30,
                ItemInfo.of(
                        "item1",
                        10,
                        "123",
                        LocalDate.now(),
                        "www.naver.com"));

        return Stream.of(
                Arguments.of(user, delivery, item));
    }

    @ParameterizedTest
    @MethodSource("provideOrderCancelParam")
    void 결제완료_단계에서_주문취소시_상품_재고가_채워진다(User user, Delivery delivery, Item item) {
        // given
        int beforeOrderItemQuantity = item.getStockQuantity();
        User savedUser = userRepository.save(user);
        itemRepository.save(item);

        int orderQuantity = 10;
        List<OrderLineRequest> orderLineRequests = List.of(
                OrderLineRequest.builder()
                        .itemCode(item.getInfo().getCode())
                        .itemQuantity(orderQuantity)
                        .build());

        Long orderId = orderService.order(
                savedUser.getId(),
                delivery,
                orderLineRequests);

        // when
        orderService.cancelOrder(orderId);
        int afterCancelOrderItemQuantity = item.getStockQuantity();

        // then
        Order findOrder = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        assertAll(
                () -> assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL),
                () -> assertThat(afterCancelOrderItemQuantity).isEqualTo(beforeOrderItemQuantity)
        );
    }

    private static Stream<Arguments> provideOrderCancelParam() {
        User user = User.of("name", "abc@naver.com", Role.USER);

        Requirement requirement = new Requirement("요청사항");
        Address address = Address.of(user, "123456789", "12345", "주소", "주소주소");
        Delivery delivery = Delivery.of(requirement, address);


        int itemQuantity = 30;
        Item item = Item.of(
                1000,
                itemQuantity,
                ItemInfo.of(
                        "item1",
                        10,
                        "123",
                        LocalDate.now(),
                        "www.naver.com"));

        return Stream.of(Arguments.of(user, delivery, item));

    }

    @ParameterizedTest
    @MethodSource("provideOrderCancelParam")
        void 상품준비중_단계에서_주문취소시_상품_재고가_채워진다(User user, Delivery delivery, Item item) {

        // given
        int beforeOrderItemQuantity = item.getStockQuantity();

        User savedUser = userRepository.save(user);
        itemRepository.save(item);

        int orderQuantity = 10;
        List<OrderLineRequest> orderLineRequests = List.of(
                OrderLineRequest.builder()
                        .itemCode(item.getInfo().getCode())
                        .itemQuantity(orderQuantity)
                        .build());

        Long orderId = orderService.order(
                savedUser.getId(),
                delivery,
                orderLineRequests);

        // when
        orderService.cancelOrder(orderId);
        int afterCancelOrderItemQuantity = item.getStockQuantity();

        // then
        Order findOrder = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        assertAll(
                () -> assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL),
                () -> assertThat(afterCancelOrderItemQuantity).isEqualTo(beforeOrderItemQuantity)
        );
    }

    @ParameterizedTest
    @MethodSource("provideOrderFindOneParam")
    void 주문_단건_조회_테스트(User user, Delivery delivery, Item item) {

        // given
        userRepository.save(user);

        itemRepository.save(item);

        List<OrderLine> orderLines = List.of(OrderLine.of(item, 10));

        Order order = Order.of(user, delivery, orderLines);
        Order savedOrder = orderRepository.save(order);

        // when
        Order findOrder = orderService.findOne(savedOrder.getId());

        // then
        assertThat(order)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(findOrder);
    }

    private static Stream<Arguments> provideOrderFindOneParam() {

        User user = User.of("name", "abc@naver.com", Role.USER);

        Requirement requirement = new Requirement("요청사항");
        Address address = Address.of(user, "123456789", "12345", "주소", "주소주소");
        Delivery delivery = Delivery.of(requirement, address);

        Item item = Item.of(
                1000,
                30,
                ItemInfo.of(
                        "item1",
                        10,
                        "123",
                        LocalDate.now(),
                        "www.naver.com"));

        return Stream.of(Arguments.of(user, delivery, item));
    }
}
