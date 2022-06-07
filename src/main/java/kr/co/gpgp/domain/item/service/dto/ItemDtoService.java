package kr.co.gpgp.domain.item.service.dto;

import kr.co.gpgp.domain.item.dto.ItemDtoRequest;
import kr.co.gpgp.domain.item.dto.ItemDtoResponse;
import kr.co.gpgp.domain.item.dto.ItemInfoDto;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.entity.ItemInfo;
import org.springframework.stereotype.Service;

@Service
public class ItemDtoService{

    public ItemDtoResponse itemConversionDto(Item item) {
        ItemInfoDto infoDto = itemInfoConversionDto(item.getInfo());
        return ItemDtoResponse.of(
            item.getPrice(),
            item.getStockQuantity(),
            infoDto
        );
    }

    public Item dtoConversionItem(ItemDtoRequest request) {
        ItemInfo info = dtoConversionItemInfo(request.getItemInfoDto());
        return Item.of(
            request.getPrice(),
            request.getStockQuantity(),
            info
        );
    }

    public ItemInfo dtoConversionItemInfo(ItemInfoDto infoDto) {
        return ItemInfo.of(
            infoDto.getName(),
            infoDto.getWeight(),
            infoDto.getCode(),
            infoDto.getReleaseDate(),
            infoDto.getImageUrl()
        );
    }

    public ItemInfoDto itemInfoConversionDto(ItemInfo info) {
        return ItemInfoDto.of(
            info.getName(),
            info.getWeight(),
            info.getCode(),
            info.getImageUrl(),
            info.getReleaseDate()
        );
    }
}
