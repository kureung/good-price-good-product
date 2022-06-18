package kr.co.gpgp.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findOne(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
    }

    public void changeOfPermission(Long userId, String roleString) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("권한 변경하려는 회원 을 찾을수 없습니다."));

        Role role = Role.USER;

        if (roleString.equals(Role.SELLER.getKey()) || roleString.equals("SELLER")) {
            role = Role.SELLER;
        }
        if (roleString.equals(Role.COURIER.getKey()) || roleString.equals("COURIER")) {
            role = Role.COURIER;
        }
        if (roleString.equals(Role.ADMIN.getKey()) || roleString.equals("ADMIN")) {
            role = Role.ADMIN;
        }

        userRepository.save(user.updateRole(role));

    }

}
