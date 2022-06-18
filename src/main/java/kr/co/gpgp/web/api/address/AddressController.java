package kr.co.gpgp.web.api.address;


import javax.validation.Valid;
import kr.co.gpgp.domain.address.Address.AddressDto;
import kr.co.gpgp.domain.address.AddressService;
import kr.co.gpgp.domain.address.dto.AddressRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address/")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/{userId}")
    public ResponseEntity<Void> create(
            @PathVariable Long userId,
            @Valid @RequestBody AddressRequest addressRequest
    ) {
        addressService.create(userId,
                AddressRequest.toAddressRequestDto(addressRequest)
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long userId
    ) {
        addressService.delete(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> update(
            @PathVariable Long userId,
            @Valid @RequestBody AddressRequest addressRequest
    ) {
        AddressDto addressDto = AddressDto.of(addressRequest);

        addressService.update(userId, addressDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Void> select(
            @PathVariable Long userId
    ) {
        addressService.select(userId);
        return ResponseEntity.ok().build();
    }


}
