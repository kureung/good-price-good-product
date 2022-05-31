package kr.co.gpgp.domain.user.repository;

import kr.co.gpgp.domain.user.entity.User;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void login(){
        User user = User.builder()
            .name("kang")
            //.pw("1q2w3e4r!")
            .email("kgh2252@naver.com")
            .build();

        userRepository.save(user);

//        System.out.println(userRepository.findByEmailAndPw("kgh2252@naver.com","1q2w3e4r!").getName());
    }
    @Test
    void 로그인실패시예외(){
        User user = User.builder()
            .name("kang")
            //.pw("1q2w3e4r!")
            .email("kgh2252@naver.com")
            .build();

        userRepository.save(user);

//        assertThatThrownBy(() -> userRepository.findByEmailAndPw("kgh2252@naver.com","1q2we4r!").getId())
//            .isInstanceOf(NullPointerException.class);

    }

    @Test
    void 이메일_중복체크(){
        User user = User.builder()
            .name("kang")
            //.pw("1q2w3e4r!")
            .email("kgh2252@naver.com")
            .build();

        userRepository.save(user);

        assertThat(userRepository.findByEmail("kgh2252@naver.com"))
            .isNotInstanceOf(IllegalArgumentException.class);
    }


}