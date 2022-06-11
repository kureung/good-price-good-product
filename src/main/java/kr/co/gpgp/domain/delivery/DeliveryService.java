package kr.co.gpgp.domain.delivery;

public interface DeliveryService {

    // TODO : 상태 취소 -> status 에서  실행
    void cancelStatus();

    // TODO : 다음 상태 로 값 변경 서비스
    void sequenceNextStatus();

}
