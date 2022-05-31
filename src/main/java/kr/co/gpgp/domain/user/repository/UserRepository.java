package kr.co.gpgp.domain.user.repository;

import java.util.Optional;
import kr.co.gpgp.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
}
