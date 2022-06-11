package kr.co.gpgp.domain.user;

import java.util.Optional;

public interface UserRepository  {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long userId);

    User save(User member);

}
