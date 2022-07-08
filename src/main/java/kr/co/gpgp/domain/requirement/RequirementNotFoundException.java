package kr.co.gpgp.domain.requirement;

public class RequirementNotFoundException extends RuntimeException {
    
    public RequirementNotFoundException() {
        super("요청사항을 찾을수 없습니다.");
    }

}
