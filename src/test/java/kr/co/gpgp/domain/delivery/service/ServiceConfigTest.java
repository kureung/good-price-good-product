package kr.co.gpgp.domain.delivery.service;

import kr.co.gpgp.domain.address.repository.AddressRepository;
import kr.co.gpgp.domain.delivery.repository.DeliveryRepository;
import kr.co.gpgp.domain.user.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class ServiceConfigTest {


    @SpyBean
    DeliveryUserService deliveryUserService;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    UserRepository userRepository;

}
