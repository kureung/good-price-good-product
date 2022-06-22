package kr.co.gpgp.domain.delivery;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.address.AddressRepository;
import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.item.ItemInfo;
import kr.co.gpgp.domain.order.Order;
import kr.co.gpgp.domain.order.OrderRepository;
import kr.co.gpgp.domain.order.OrderService;
import kr.co.gpgp.domain.requirement.Requirement;
import kr.co.gpgp.domain.user.Role;
import kr.co.gpgp.domain.user.User;
import kr.co.gpgp.domain.user.UserRepository;
import kr.co.gpgp.repository.item.ItemJpaRepository;
import kr.co.gpgp.web.api.delivery.DeliveryRequest;
import kr.co.gpgp.web.api.order.OrderRequest.OrderLineRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class DeliverySellerTest {

    private User user;
    private Address address;
    private Requirement requirement;
    private Delivery delivery;

    @SpyBean
    public DeliverySellerService deliverySellerService;

    @Autowired
    public AddressRepository addressRepository;

    @Autowired
    public DeliveryRepository deliveryRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    public ItemJpaRepository itemJpaRepository;

    @Autowired
    private OrderService sut;


    @BeforeEach
    void setup() {

        user = User.of("AAA", "AAA@gmail.com", Role.SELLER);
        address = Address.of(user, "AAA_AAA_AAA", "12345", "AAAName", "AAA1");
        requirement = new Requirement("AAA");
        delivery = Delivery.of(requirement, address);

        user = userRepository.save(user);
        address = addressRepository.save(address);
        delivery = deliveryRepository.save(delivery);

    }


    @Test
    void 판매한_목록중_선택된_배송_상태를_모두_조회할수_있다() {
        String status = "ACCEPT";
        Long userId = 1L;

        ItemInfo info = ItemInfo.builder()
                .code("123")
                .weight(10)
                .build();

        Item item = Item.builder()
                .stockQuantity(11)
                .info(info)
                .price(1000)
                .build();
        itemJpaRepository.save(item);

        List<OrderLineRequest> orderLineRequests = List.of(
                OrderLineRequest.builder()
                        .itemCode(item.getInfo().getCode())
                        .orderQuantity(2)
                        .build());

        List<OrderLineRequest> orderLineRequests2 = List.of(
                OrderLineRequest.builder()
                        .itemCode(item.getInfo().getCode())
                        .orderQuantity(1)
                        .build());


        List<Delivery> list = deliverySellerService.choiceStatus(status, user.getId());

        list.forEach(a -> System.out.println(a.getId() + ":" + a.getUserId() + "," + a.getStatus() + " ," + a.getAddressName()));

        assertAll(() -> assertThat(list).isNotNull(),
                () -> assertThat(list.get(1).getAddressName()).isEqualTo("AAAName"),
                () -> assertThat(list.get(0).getStatusMessage()).isEqualTo("결제완료"),
                () -> assertThat(list.get(0).getAddressRoadName()).isEqualTo("AAA_AAA_AAA"),
                () -> assertThat(list.get(0).getRequirementMessage()).isEqualTo("AAA")
        );

    }

    @Test
    void 판매한_목록_배송_상태를_모두_조회할수_있다() {
        ItemInfo info = ItemInfo.builder()
                .code("123")
                .weight(10)
                .build();

        Item item = Item.builder()
                .stockQuantity(11)
                .info(info)
                .price(1000)
                .build();
        itemJpaRepository.save(item);

        List<OrderLineRequest> orderLineRequests = List.of(
                OrderLineRequest.builder()
                        .itemCode(item.getInfo().getCode())
                        .orderQuantity(2)
                        .build());

        List<OrderLineRequest> orderLineRequests2 = List.of(
                OrderLineRequest.builder()
                        .itemCode(item.getInfo().getCode())
                        .orderQuantity(1)
                        .build());


        Delivery adelivery = Delivery.of(requirement, address);
        Delivery dd = deliveryRepository.save(adelivery);
        Delivery sdelivery = deliveryRepository.findById(dd.getId()).get();
        //q배송 pk1 1 보
        Order or = orderRepository.findById(1L).get();
        or.getDelivery().nextStepInstruct();    //안됨


        //판매자 id 조회
        // 그 주문 확인
        // 그 배송 확인
        // 이제 그 배송을 list 으로 가져오기
        List<Order> ors = orderRepository.findByUserId(user.getId());
        System.out.println(ors.get(1));
        List<Delivery> list = Delivery.ofOrder(ors);

        assertAll(() -> assertThat(list).isNotNull(),
                () -> assertThat(list.get(0).getAddressName()).isEqualTo("AAAName"),
                () -> assertThat(list.get(0).getStatusMessage()).isEqualTo("상품준비중"),
                () -> assertThat(list.get(0).getAddressRoadName()).isEqualTo("AAA_AAA_AAA"),
                () -> assertThat(list.get(0).getRequirementMessage()).isEqualTo("AAA")
        );
        assertAll(
                () -> assertThat(list.get(1).getAddressName()).isEqualTo("AAAName"),
                () -> assertThat(list.get(1).getStatusMessage()).isEqualTo("결제완료"),
                () -> assertThat(list.get(1).getAddressRoadName()).isEqualTo("AAA_AAA_AAA"),
                () -> assertThat(list.get(1).getRequirementMessage()).isEqualTo("AAA")
        );

    }

    @Test
    void 회원에게_판매한_상품의_배송_상태를_변경할수있다() {

        user = User.of("BBB", "BBB@gmail.com", Role.SELLER);
        address = Address.of(user, "BBB_BBB_BBB", "12345", "BBBName", "BBB1");
        requirement = new Requirement("BBBreq");
        delivery = Delivery.of(requirement, address);

        user = userRepository.save(user);
        address = addressRepository.save(address);
        delivery = deliveryRepository.save(delivery);

        DeliveryRequest deliveryRequest = DeliveryRequest.of(delivery.getId(), delivery.getRequirementMessage(), delivery.getAddressRoadName(), delivery.getAddressZipCode(), delivery.getAddressName(), delivery.getAddressDetailed());

        deliverySellerService.targetUpdate("ACCEPT", deliveryRequest);

        Delivery delivery = deliveryRepository.findById(2L)
                .get();

        assertThat(delivery.getStatus().getStatus())
                .isNotNull()
                .isEqualTo("INSTRUCT");
    }

}
