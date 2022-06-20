package kr.co.gpgp.domain.requirement;


import kr.co.gpgp.domain.requirement.Requirement.RequirementDto;
import kr.co.gpgp.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequirementService {

    private final RequirementRepository requirementRepository;
    private final UserRepository userRepository;


    public void create(RequirementDto requirementDto){
        Requirement requirement = Requirement.of(requirementDto.getMessage());

        requirementRepository.save(requirement);
    }


}
