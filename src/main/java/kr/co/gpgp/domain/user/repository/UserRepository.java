package kr.co.gpgp.domain.user.repository;

import kr.co.gpgp.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Long> {

    User findByEmailAndPw(String email,String pw);
    User findByEmail(String email);
}
