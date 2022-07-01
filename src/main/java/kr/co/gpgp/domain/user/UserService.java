package kr.co.gpgp.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;

    public User findOne(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
    }


    @Transactional
    public void changeOfPermission(Long userId, Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("권한 변경하려는 회원 을 찾을수 없습니다."));

        if (user.getRole() == Role.USER) {
            if(role == Role.SELLER){
                Seller seller = Seller.of(user);
                sellerRepository.save(seller);
            }
        }

        user.updateRole(role);
    }

}
