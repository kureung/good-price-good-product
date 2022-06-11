package kr.co.gpgp.domain.item.service.dto;

import kr.co.gpgp.domain.item.dto.ItemRequest;
import kr.co.gpgp.domain.item.dto.ItemResponse;
import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.item.ItemInfo;
import org.springframework.stereotype.Service;

@Service
public class ItemDtoService {

    public ItemResponse toDto(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .code(item.getCode())
                .imageUrl(item.getImageUrl())
                .name(item.getName())
                .price(item.getPrice())
                .releaseDate(item.getReleaseDate())
                .stockQuantity(item.getStockQuantity())
                .weight(item.getWeight())
                .build();
    }

    public Item toEntity(ItemRequest request) {
        ItemInfo info = ItemInfo.builder()
                .code(request.getCode())
                .imageUrl(request.getImageUrl())
                .name(request.getName())
                .releaseDate(request.getReleaseDate())
                .weight(request.getWeight())
                .build();

        return Item.builder()
                .price(request.getPrice())
                .info(info)
                .stockQuantity(request.getStockQuantity())
                .build();
    }

}
