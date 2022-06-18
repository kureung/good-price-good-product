package kr.co.gpgp.web.api.order;

import kr.co.gpgp.domain.requirement.Requirement;
import org.springframework.stereotype.Component;

@Component
public class RequirementAdapter {

    public Requirement toEntity(String requirementMessage) {
        return new Requirement(requirementMessage);
    }

}
