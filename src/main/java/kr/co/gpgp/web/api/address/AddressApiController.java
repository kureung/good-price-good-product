package kr.co.gpgp.web.api.address;


import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import kr.co.gpgp.auth.dto.UserDetails;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.address.AddressDto;
import kr.co.gpgp.domain.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressApiController {

    private final AddressService addressService;

    @PostMapping("")
    public ResponseEntity<Address> create(
            AddressRequest addressRequest,
            HttpServletRequest request
    ) {

        UserDetails user = UserDetails.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        AddressDto addressDto = AddressDto.of(addressRequest.getId(), addressRequest.getId(), addressRequest.getRoadName(), addressRequest.getZipCode(), addressRequest.getName(), addressRequest.getDetailed());

        Address address = addressService.create(user.getId(), addressDto);

        return ResponseEntity.ok().body(address);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> delete(
            @Valid Long addressId
    ) {
        addressService.delete(addressId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> update(
            AddressRequest addressRequest
    ) {
        UserDetails user = UserDetails.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        AddressDto addressDto = AddressDto.of(addressRequest.getId(), addressRequest.getId(), addressRequest.getRoadName(), addressRequest.getZipCode(), addressRequest.getName(), addressRequest.getDetailed());

        addressService.update(user.getId(), addressDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("address");

        UserDetails user = UserDetails.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        List<Address> address = addressService.select(user.getId());
        List<AddressResponse> responses = AddressResponse.of(address);

        mav.addObject("addressList", responses);
        return mav;
    }


}
