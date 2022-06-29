package kr.co.gpgp.domain.requirement;


import java.util.List;
import java.util.Optional;
import kr.co.gpgp.domain.user.User;
import kr.co.gpgp.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RequirementService {

    private final RequirementRepository requirementRepository;
    private final UserRepository userRepository;
    final int REQUIREMENT_CREATE_COUNT_MAX = 5;

    public Requirement create(Long userId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user ID를 조회할수 없어 요청사항 를 생성할수 없습니다."));

        List<Requirement> requirementList = requirementRepository.findByUserId(userId);

        if (requirementList.size() > REQUIREMENT_CREATE_COUNT_MAX) {
            throw new IllegalArgumentException(user.getName() + " 회원의 요청사항 생성개수가 초과되어 더이상 요청사항 생성을 할수없습니다.");
        }
        Requirement requirement = Requirement.of(user, message);

        return requirementRepository.save(requirement);
    }

    public void delete(Long userId, Long requirementId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user ID를 조회할수 없어 요청사항을 삭제할수 없습니다."));

        requirementRepository.deleteById(requirementId);
    }

    public void update(Long userId, Long id, String message) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user ID를 조회할수 없어 요청사항을 수정할수 없습니다."));

        Requirement req = requirementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("requirement ID를 조회할수 없어 요청사항을 수정할수 없습니다."));

        req.updateMessage(message);
    }

    public List<Requirement> select(Long userId) {

        User user =userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user ID를 조회할수 없어 요청사항을 조회"));

        return requirementRepository.findByUserId(userId);
    }

}
