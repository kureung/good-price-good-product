package kr.co.gpgp.web.api.requirement;

import kr.co.gpgp.domain.requirement.Requirement;
import org.springframework.stereotype.Component;

@Component
public class RequirementAdapter {

    public Requirement toEntity(String requirementMessage) {
        return new Requirement(requirementMessage);
    }

}
