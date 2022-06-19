package kr.co.gpgp.web.api.address;


import javax.validation.Valid;
import kr.co.gpgp.domain.address.AddressDto;
import kr.co.gpgp.domain.address.AddressService;
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

    @PostMapping("/{id}")
    public ResponseEntity<Void> create(
            @PathVariable Long id,
            @Valid @RequestBody AddressRequest addressRequest
    ) {
        AddressDto addressDto = AddressDto.of(id, addressRequest.getId(), addressRequest.getRoadName(), addressRequest.getZipCode(), addressRequest.getName(), addressRequest.getDetailed());

        addressService.create(addressDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        addressService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @Valid @RequestBody AddressRequest addressRequest
    ) {
        AddressDto addressDto = AddressDto.of(id, addressRequest.getId(), addressRequest.getRoadName(), addressRequest.getZipCode(), addressRequest.getName(), addressRequest.getDetailed());

        addressService.update(id, addressDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> select(
            @PathVariable Long id
    ) {

        addressService.select(id);

        return ResponseEntity.ok().build();
    }


}
