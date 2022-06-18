package kr.co.gpgp.domain.requirement;


import kr.co.gpgp.domain.requirement.Requirement.RequirementDto;

public class RequirementRequest {

    private Long id;
    private String message;

    private RequirementRequest(Long id, String message) {
        this.id=id;
        this.message = message;
    }

    public static RequirementRequest of(Long id, String message) {
        return new RequirementRequest(id,message);
    }

    public static RequirementDto toRequirementDto(RequirementRequest requirementRequest) {
        return RequirementDto.of(requirementRequest.getId(), requirementRequest.getMessage());
    }

    public String getMessage() {
        return message;
    }

    public Long getId() { return id; }
}
