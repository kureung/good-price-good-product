package kr.co.gpgp.web.api;

import java.net.URI;
import javax.validation.Valid;
import kr.co.gpgp.domain.item.dto.ItemDtoRequest;
import kr.co.gpgp.domain.item.dto.ItemDtoResponse;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.service.ItemCommandService;
import kr.co.gpgp.domain.item.service.ItemFindService;
import kr.co.gpgp.domain.item.service.dto.ItemDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemCommandService itemCommandService;
    private final ItemFindService itemFindService;
    private final ItemDtoService itemDtoService;

    @PostMapping
    public ResponseEntity<ItemDtoResponse> register(
        @Valid @RequestBody ItemDtoRequest request) {

        Item item = itemDtoService.dtoConversionItem(request);
        Long itemId = itemCommandService.save(item);
        Item findItem = itemFindService.findOne(itemId);
        ItemDtoResponse response = itemDtoService.itemConversionDto(findItem);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(findItem.getId())
            .toUri();
        
        return ResponseEntity.created(location)
            .body(response);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDtoResponse> findOneItem(
        @PathVariable Long itemId) {

        Item findItem = itemFindService.findOne(itemId);
        ItemDtoResponse response = itemDtoService.itemConversionDto(findItem);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{itemId}")
    public ResponseEntity<ItemDtoResponse> updateItem(
        @PathVariable Long itemId,
        @Valid @RequestBody ItemDtoRequest request) {

        Item item = itemDtoService.dtoConversionItem(request);
        itemCommandService.update(itemId, item);

        Item findItem = itemFindService.findOne(itemId);
        ItemDtoResponse response = itemDtoService.itemConversionDto(findItem);

        return ResponseEntity.ok(response);
    }
}
