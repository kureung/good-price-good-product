package kr.co.gpgp.common;

import kr.co.gpgp.domain.address.AddressRepository;
import kr.co.gpgp.domain.courier.CourierContainerRepository;
import kr.co.gpgp.domain.courier.CourierRepository;
import kr.co.gpgp.domain.delivery.DeliveryRepository;
import kr.co.gpgp.domain.item.ItemRepository;
import kr.co.gpgp.domain.order.OrderRepository;
import kr.co.gpgp.domain.user.UserRepository;
import kr.co.gpgp.repository.item.ItemJpaRepository;
import kr.co.gpgp.repository.order.OrderJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RepositoryTest {

    @Autowired
    public CourierContainerRepository courierContainerRepository;
    @Autowired
    public CourierRepository courierRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public AddressRepository addressRepository;
    @Autowired
    public DeliveryRepository deliveryRepository;
    @Autowired
    public OrderRepository orderRepository;
    @Autowired
    public ItemRepository itemRepository;
    @Autowired
    public ItemJpaRepository itemJpaRepository;
    @Autowired
    public OrderJpaRepository OrderJpaRepository;

}
