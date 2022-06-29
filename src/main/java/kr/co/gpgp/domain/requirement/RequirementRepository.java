package kr.co.gpgp.domain.requirement;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirementRepository extends JpaRepository<Requirement, Long> {

    Optional<Requirement> findByMessage(String message);
    List<Requirement> findByUserId(Long userId);
}
