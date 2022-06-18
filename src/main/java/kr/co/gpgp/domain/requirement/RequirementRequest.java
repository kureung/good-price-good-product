package kr.co.gpgp.domain.requirement;


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

    public String getMessage() {
        return message;
    }

    public Long getId() { return id; }
}
