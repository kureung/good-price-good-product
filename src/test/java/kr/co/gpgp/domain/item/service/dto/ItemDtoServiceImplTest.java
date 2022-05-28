package kr.co.gpgp.domain.item.service.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.util.stream.Stream;
import kr.co.gpgp.domain.item.dto.ItemDtoRequest;
import kr.co.gpgp.domain.item.dto.ItemDtoResponse;
import kr.co.gpgp.domain.item.dto.ItemInfoDto;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.entity.ItemInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemDtoServiceImplTest {

    @Autowired
    private ItemDtoService itemDtoService;

    @ParameterizedTest
    @MethodSource("toConversionDtdProvideItem")
    void item을_itemDtoResponse로_변환_테스트(Item item) {
        ItemDtoResponse response = itemDtoService.itemConversionDto(item);

        assertAll(
            () -> assertThat(
                response.getPrice())
                .isEqualTo(item.getPrice()),

            () -> assertThat(
                response.getStockQuantity()).
                isEqualTo(item.getStockQuantity()),

            () -> assertThat(
                response.getItemInfoDto()
                    .getCode())
                .isEqualTo(item.getInfo()
                    .getCode()),

            () -> assertThat(
                response.getItemInfoDto()
                    .getImageUrl())
                .isEqualTo(item.getInfo()
                    .getImageUrl()),

            () -> assertThat(
                response.getItemInfoDto()
                    .getName())
                .isEqualTo(item.getInfo()
                    .getName()),

            () -> assertThat(
                response.getItemInfoDto()
                    .getWeight())
                .isEqualTo(item.getInfo()
                    .getWeight()),

            () -> assertThat(
                response.getItemInfoDto()
                    .getReleaseDate())
                .isEqualTo(item.getInfo()
                    .getReleaseDate()));
    }

    private static Stream<Arguments> toConversionDtdProvideItem() {
        return Stream.of(
            Arguments.of(
                Item.of(
                    1000,
                    100,
                    ItemInfo.of(
                        "item1",
                        500,
                        "123",
                        LocalDate.now(),
                        "www.naver.com"))),

            Arguments.of(
                Item.of(
                    2000,
                    300,
                    ItemInfo.of(
                        "item2",
                        600,
                        "456",
                        LocalDate.now().minusMonths(1),
                        "www.google.co.kr"))),

            Arguments.of(
                Item.of(
                    3000,
                    200,
                    ItemInfo.of(
                        "item3",
                        700,
                        "789",
                        LocalDate.now().minusDays(2),
                        "www.daum.net"))));
    }

    @ParameterizedTest
    @MethodSource("toConversionItemProvideItemDtoRequest")
    void itemDtoRequest를_item으로_변환_테스트(ItemDtoRequest request) {
        Item convertedItem = itemDtoService.dtoConversionItem(request);

        assertAll(
            () -> assertThat(
                convertedItem.getPrice())
                .isEqualTo(request.getPrice()),

            () -> assertThat(
                convertedItem.getStockQuantity()).
                isEqualTo(request.getStockQuantity()),

            () -> assertThat(
                convertedItem.getInfo()
                    .getCode())
                .isEqualTo(request.getItemInfoDto()
                    .getCode()),

            () -> assertThat(
                convertedItem.getInfo()
                    .getImageUrl())
                .isEqualTo(request.getItemInfoDto()
                    .getImageUrl()),

            () -> assertThat(
                convertedItem.getInfo()
                    .getName())
                .isEqualTo(request.getItemInfoDto()
                    .getName()),

            () -> assertThat(
                convertedItem.getInfo()
                    .getWeight())
                .isEqualTo(request.getItemInfoDto()
                    .getWeight()),

            () -> assertThat(
                convertedItem.getInfo()
                    .getReleaseDate())
                .isEqualTo(request.getItemInfoDto()
                    .getReleaseDate()));
    }

    private static Stream<Arguments> toConversionItemProvideItemDtoRequest() {
        return Stream.of(
            Arguments.of(
                ItemDtoRequest.of(
                    1000,
                    100,
                    ItemInfoDto.of(
                        "item1",
                        500,
                        "123",
                        "www.naver.com",
                        LocalDate.now()))),

            Arguments.of(
                ItemDtoRequest.of(
                    2000,
                    300,
                    ItemInfoDto.of(
                        "item2",
                        600,
                        "456",
                        "www.google.co.kr",
                        LocalDate.now().minusMonths(1)))),

            Arguments.of(
                ItemDtoRequest.of(
                    3000,
                    200,
                    ItemInfoDto.of(
                        "item3",
                        700,
                        "789",
                        "www.daum.net",
                        LocalDate.now().minusDays(2))))
        );
    }

    @ParameterizedTest
    @MethodSource("toConversionItemInfoProvideItemInfoDto")
    void ItemInfoDto를_ItemDto로_변환_테스트(ItemInfoDto infoDto) {
        ItemInfo info = itemDtoService.dtoConversionItemInfo(infoDto);
        assertAll(
            () -> assertThat(
                infoDto.getCode())
                .isEqualTo(info.getCode()),

            () -> assertThat(
                infoDto.getImageUrl())
                .isEqualTo(info.getImageUrl()),

            () -> assertThat(
                infoDto.getWeight())
                .isEqualTo(info.getWeight()),

            () -> assertThat(
                infoDto.getReleaseDate())
                .isEqualTo(info.getReleaseDate()),

            () -> assertThat(
                infoDto.getName())
                .isEqualTo(info.getName()));
    }

    private static Stream<Arguments> toConversionItemInfoProvideItemInfoDto() {
        return Stream.of(
            Arguments.of(
                ItemInfoDto.of(
                    "item1",
                    10,
                    "123",
                    "www.naver.com",
                    LocalDate.now())),

            Arguments.of(
                ItemInfoDto.of(
                    "item2",
                    20,
                    "456",
                    "www.google.co.kr",
                    LocalDate.now()
                        .minusMonths(1))));
    }

    @ParameterizedTest
    @MethodSource("toConversionDtoProvideItemInfo")
    void ItemInfo를_ItemInfoDto로_변환_테스트(ItemInfo info) {
        ItemInfoDto infoDto = itemDtoService.itemInfoConversionDto(info);
        assertAll(
            () -> assertThat(
                info.getCode())
                .isEqualTo(infoDto.getCode()),

            () -> assertThat(
                info.getImageUrl())
                .isEqualTo(infoDto.getImageUrl()),

            () -> assertThat(
                info.getWeight())
                .isEqualTo(infoDto.getWeight()),

            () -> assertThat(
                info.getReleaseDate())
                .isEqualTo(infoDto.getReleaseDate()),

            () -> assertThat(
                info.getName())
                .isEqualTo(infoDto.getName()));
    }

    private static Stream<Arguments> toConversionDtoProvideItemInfo() {
        return Stream.of(
            Arguments.of(
                ItemInfo.of(
                    "item1",
                    10,
                    "123",
                    LocalDate.now(),
                    "www.naver.com")),

            Arguments.of(
                ItemInfo.of(
                    "item2",
                    20,
                    "456",
                    LocalDate.now()
                        .minusMonths(1),
                    "www.google.co.kr")));
    }
}