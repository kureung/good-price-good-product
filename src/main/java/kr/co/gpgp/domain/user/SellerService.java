package kr.co.gpgp.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellerService {

    private final SellerRepository sellerRepository;
    private final UserService userService;

    public Seller findOneByUser(Long userId) {
        User user = userService.findOne(userId);

        return sellerRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 판매자를 찾을 수 없습니다."));
    }

}
