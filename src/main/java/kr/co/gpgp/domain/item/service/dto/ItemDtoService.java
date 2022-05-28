package kr.co.gpgp.domain.item.service.dto;

import kr.co.gpgp.domain.item.dto.ItemDtoRequest;
import kr.co.gpgp.domain.item.dto.ItemDtoResponse;
import kr.co.gpgp.domain.item.dto.ItemInfoDto;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.entity.ItemInfo;

public interface ItemDtoService {

    ItemDtoResponse itemConversionDto(Item item);

    Item dtoConversionItem(ItemDtoRequest request);

    ItemInfo dtoConversionItemInfo(ItemInfoDto infoDto);

    ItemInfoDto itemInfoConversionDto(ItemInfo info);

}
