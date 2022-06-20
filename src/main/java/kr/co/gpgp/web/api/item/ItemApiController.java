package kr.co.gpgp.web.api.item;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.item.ItemCommandService;
import kr.co.gpgp.domain.item.ItemFindService;
import kr.co.gpgp.domain.item.ItemSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemApiController {

    private final ItemCommandService itemCommandService;
    private final ItemFindService itemFindService;

    @PostMapping
    public ResponseEntity<ItemResponse> register(
            @Valid @RequestBody ItemRequest request
    ) {
        Item item = request.toEntity();
        Item savedItem = itemCommandService.save(item);
        ItemResponse response = ItemResponse.toDto(savedItem);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(response);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> findOneItem(
            @PathVariable Long itemId) {

        Item findItem = itemFindService.findOne(itemId);
        ItemResponse response = ItemResponse.toDto(findItem);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{itemId}")
    public ResponseEntity<ItemResponse> updateItem(
            @PathVariable Long itemId,
            @Valid @RequestBody ItemRequest request) {

        Item item = request.toEntity();
        itemCommandService.update(itemId, item);

        Item findItem = itemFindService.findOne(itemId);
        ItemResponse response = ItemResponse.toDto(findItem);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<List<ItemResponse>> itemSearch(
            @Valid @RequestBody ItemSearchCondition condition,
            Pageable pageable
    ) {
        Page<ItemResponse> searchItemResponses = itemFindService.search(condition, pageable)
                .map(ItemResponse::toDto);

        List<ItemResponse> content = searchItemResponses.getContent();

        return ResponseEntity.ok(content);
    }

}
