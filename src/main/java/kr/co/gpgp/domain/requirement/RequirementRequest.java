package kr.co.gpgp.domain.requirement;


public class RequirementRequest {

    private String message;

    private RequirementRequest(String message) {
        this.message = message;
    }

    public static RequirementRequest of(String message) {
        return new RequirementRequest(message);
    }

    public String getMessage() {
        return message;
    }
}
