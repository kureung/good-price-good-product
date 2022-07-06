package kr.co.gpgp.common;

import kr.co.gpgp.domain.address.AddressService;
import kr.co.gpgp.domain.courier.CourierContainerService;
import kr.co.gpgp.domain.delivery.DeliveryCourierService;
import kr.co.gpgp.domain.delivery.DeliveryUserService;
import kr.co.gpgp.domain.requirement.RequirementRepository;
import kr.co.gpgp.domain.requirement.RequirementService;
import kr.co.gpgp.domain.user.UserService;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Disabled
@Transactional
@SpringBootTest
public class ServiceTest extends RepositoryTest {

    @Autowired
    public UserService userService;
    @Autowired
    public RequirementService requirementService;
    @Autowired
    public AddressService addressService;
    @Autowired
    public DeliveryUserService deliveryUserService;
    @Autowired
    public DeliveryCourierService deliveryCourierService;
    @Autowired
    public RequirementRepository requirementRepository;
    @Autowired
    public CourierContainerService courierContainerService;

}
