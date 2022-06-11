package kr.co.gpgp.domain.delivery.service;

import kr.co.gpgp.domain.address.AddressRepository;
import kr.co.gpgp.domain.delivery.DeliveryRepository;
import kr.co.gpgp.domain.delivery.DeliveryUserService;
import kr.co.gpgp.domain.user.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class ServiceConfigTest {

    @SpyBean
    public DeliveryUserService deliveryUserService;

    @Autowired
    public AddressRepository addressRepository;

    @Autowired
    public DeliveryRepository deliveryRepository;

    @Autowired
    public UserRepository userRepository;

}
