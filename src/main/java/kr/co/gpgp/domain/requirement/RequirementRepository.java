package kr.co.gpgp.domain.requirement;

import kr.co.gpgp.domain.requirement.entity.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirementRepository extends JpaRepository<Requirement, Long> {
}
