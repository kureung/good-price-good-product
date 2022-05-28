package kr.co.gpgp.web.api;

import java.net.URI;
import javax.validation.Valid;
import kr.co.gpgp.domain.item.dto.ItemDtoRequest;
import kr.co.gpgp.domain.item.dto.ItemDtoResponse;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.service.ItemService;
import kr.co.gpgp.domain.item.service.dto.ItemDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemDtoService itemDtoService;

    @PostMapping
    public ResponseEntity<ItemDtoResponse> register(
        @Valid ItemDtoRequest request,
        BindingResult result) {

        Item item = itemDtoService.dtoConversionItem(request);
        Long itemId = itemService.save(item);
        Item findItem = itemService.findOne(itemId);
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
        @PathVariable Long id) {

        Item findItem = itemService.findOne(id);
        ItemDtoResponse response = itemDtoService.itemConversionDto(findItem);
        return ResponseEntity.ok(response);
    }

}
