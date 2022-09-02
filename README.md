##  프로젝트 소개 
```
좋은 가격과 좋은 상품을 제공하는 개발 도서 쇼핑몰 입니다. 
```

기술 스택
- 
- java 11
- Spring boot 2.6 gradle
- JPA
- QueryDsl
- Oauth2 
- JUnit
- mustache

설계 방식
-
-   액션 - 액터 정의
-   액션 - 액터 매칭
-   엔티티 정의
-   엔티티 매칭
-   관련있는 서비스끼리 묶기 (바운디드 컨텍스트)
-   서비스간의 관계 정의
-   서비스 분담 (파트 분배)
-   API 규격 정의

구현 
-
- 설계 -> 구현 -> 코드 리뷰(반복)
- 테스트 코드 작성하면서 개발 

요구사항명세서
-
-   회원
    -   kako login
    -   카카오 로그인시 db 에서 email 검색후 없으면 회원가입
    -   자쉬프트 추가하여 yml파일 kakao api 비밀키 값 암호화
    -   로그아웃 
-   상품
    -   상품 세부 조회
    -   상품 검색
    -   예외 처리 코드 정의
-   배송
    -   회원이 모든 배송 조회
    -   회원이 선택 배송 조회
    -   회원이 선택한배송을 배송완료 상태로 변경 가능
    -   판매자가 선택한 배송 단계 업그레이드
    -   판매가는 배송상태별로 조회할수있다.
-   주소
    -   주소 등록,수정,삭제,조회
    -   회원은 10개의 주소를 저장할수있다.
-   요청사항
    -   요청사항 등록,수정,삭제
    -   회원은 5개의 요청사항을 저정할수있다.
-   주문
    -   주문 등록
    -   주문 등록
    -   주문 삭제
    -   주문 검색
    -   주문 세부 조회
    -   예외 처리 코드 정의
-   예외 처리
-   공통 속성 처리
    -   수정자
    -   수정일
    -   등록자
    -   등록일
-   판매자
    -   상품 등록
    -   상품 수정
    -   상품 삭제
    
    
### ERD 
---
![gpgpErd](https://user-images.githubusercontent.com/105915960/186660945-848adaaf-3374-4466-a96b-23c95d513f3d.png)

#### 메인페이지
---
![메인페이지](https://user-images.githubusercontent.com/105915960/186663417-6b5d6788-39c8-46df-be5f-947a52ae2c28.png)

#### 로그인 페이지(jeonghun.kang.dev)
---
![로그인](https://user-images.githubusercontent.com/105915960/186663422-665a7d39-cd22-4f8e-90d4-394c4d7bbe57.png)


#### 배송지 목록 페이지 (jeonghun.kang.dev)
---
![배송지목록](https://user-images.githubusercontent.com/105915960/186663437-0e87b8d9-9ff6-477d-9720-40e78c0af5c6.png)


#### 판매원 배송관리 페이지 (jeonghun.kang.dev)
---
![관리자배송관리](https://user-images.githubusercontent.com/105915960/186663790-a21d3fae-dd66-40f7-a31f-76c7e3723f22.png)


#### 상세페이지 
---
![상세페이지](https://user-images.githubusercontent.com/105915960/186663485-2b3a1036-1256-4924-90ef-398825c81dd4.png)

#### 상품페이지
---
![상품페이지](https://user-images.githubusercontent.com/105915960/186663505-23f34b28-3a78-4c87-8f80-41d91bcbb34f.png)

#### 상품결제 페이지
---
![상품결제](https://user-images.githubusercontent.com/105915960/186663489-9eb03af1-1826-499b-800b-4233e4178cf5.png)

