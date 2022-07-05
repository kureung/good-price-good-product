package kr.co.gpgp.domain.courier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CourierArea {

    //도
    GYEONGGI_DO("경기도"),
    GANGWON_DO("강원도"),
    CHUNGCHEONGNAM_DO("충청남도"),
    CHUNGCHEONGBUKDO_DO("충청북도"),
    GYEONGSANGBUK_DO("경상북도"),
    JEOLLABUK_DO("전라북도"),
    JEOLLANAM_DO("전라남도"),
    GYEONGSANGNAM_DO("경상남도"),

    //시
    SEOUL("서울특별시"),
    INCHEON("인천광역시"),
    DAEJEON("대천광역시"),
    DAEGU("대구광역시"),
    GWANGJU("광주광역시"),
    ULSAN("울산광역시"),
    BUSAN("부산광역시");

    private String area;
    CourierArea(String area) {
        this.area=area;
    }
    public String getArea(){
        return area;
    }

    public static CourierArea findArea(String str) {
        return areaMap.get(str);
    }
    static final Map<String,CourierArea> areaMap = areaInit();

    private static Map<String,CourierArea> areaInit(){
        Map<String,CourierArea> areaMap = new HashMap<>();

        List<CourierArea> list = List.of(CourierArea.values());

        for (CourierArea courierArea : list) {
            areaMap.put(courierArea.getArea(),courierArea );
        }

        return areaMap;
    }


}
