package kr.co.gpgp.repository.user;

import java.util.Optional;
import kr.co.gpgp.domain.user.User;
import kr.co.gpgp.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return jpaRepository.findById(userId);
    }

    @Override
    public User save(User user) {
        return jpaRepository.save(user);
    }

}
