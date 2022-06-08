package kr.co.gpgp.domain.item.service.dto;

import kr.co.gpgp.domain.item.dto.ItemDtoRequest;
import kr.co.gpgp.domain.item.dto.ItemDtoResponse;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.entity.ItemInfo;
import org.springframework.stereotype.Service;

@Service
public class ItemDtoService{

    public ItemDtoResponse toDto(Item item) {
        return ItemDtoResponse.builder()
                .code(item.getCode())
                .imageUrl(item.getImageUrl())
                .name(item.getName())
                .price(item.getPrice())
                .releaseDate(item.getReleaseDate())
                .stockQuantity(item.getStockQuantity())
                .weight(item.getWeight())
                .build();
    }

    public Item toEntity(ItemDtoRequest request) {
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
