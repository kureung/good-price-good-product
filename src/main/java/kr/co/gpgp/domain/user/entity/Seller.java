package kr.co.gpgp.domain.user.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private int totalPrice;      //총 금액
    private LocalDateTime field; //정산 날짜

//    @OneToOne(fetch = FetchType.LAZY)
//    private User user;

    @Builder
    public Seller(int totalPrice, LocalDateTime field) {
        SellerValidator.verifyTotalPrice(totalPrice);
        SellerValidator.verifyField(field);

        this.totalPrice = totalPrice;
        this.field = field;
    }

    private static class  SellerValidator{
        private static void verifyTotalPrice(int totalPrice){
            if(totalPrice < 0){
                throw new IllegalArgumentException("총 금액은 음수가 나올수 없습니다.");
            }
        }
        private static void verifyField(LocalDateTime field){
            if(field == null ){
                throw new IllegalArgumentException("정산 금액은 null 을 받을수 없습니다.");

            }
        }
    }

    public void deleteSeller(){

    }
    public void renew(){

    }
}

