package kr.co.gpgp.web.api.requirement;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.gpgp.domain.requirement.Requirement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequirementResponse {

    private Long id;
    private String message;

    private RequirementResponse(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public static RequirementResponse of(Requirement requirement) {
        return new RequirementResponse(requirement.getId(), requirement.getMessage());
    }

    public static List<RequirementResponse> of(List<Requirement> requirement) {
        return requirement.stream()
                .map(RequirementResponse::of)
                .collect(Collectors.toList());
    }

}
