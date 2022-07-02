package kr.co.gpgp.web.api.address;


import java.util.List;
import kr.co.gpgp.auth.dto.UserDetails;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.address.AddressDto;
import kr.co.gpgp.domain.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public String create(
            AddressRequest addressRequest,
            Authentication authentication
    ) {
        UserDetails user = UserDetails.of(authentication.getPrincipal());

        AddressDto addressDto = AddressDto.of(addressRequest.getId(), addressRequest.getId(), addressRequest.getRoadName(), addressRequest.getZipCode(), addressRequest.getName(), addressRequest.getDetailed());

        addressService.create(user.getId(), addressDto);

        return "redirect:/address";
    }

    @DeleteMapping
    public String delete(
            Long addressId,
            Authentication authentication
    ) {
        UserDetails user = UserDetails.of(authentication.getPrincipal());

        addressService.delete(user.getId(),addressId);

        return "redirect:/address";
    }

    @PutMapping
    public String update(
            AddressRequest addressRequest,
            Authentication authentication
    ) {
        UserDetails user = UserDetails.of(authentication.getPrincipal());

        AddressDto addressDto = AddressDto.of(addressRequest.getId(), addressRequest.getId(), addressRequest.getRoadName(), addressRequest.getZipCode(), addressRequest.getName(), addressRequest.getDetailed());

        addressService.update(user.getId(),  addressDto);

        return "redirect:/address";
    }

    @GetMapping
    public String home(
            Model model,
            Authentication authentication
    ) {
        UserDetails user = UserDetails.of(authentication.getPrincipal());

        List<Address> address = addressService.select(user.getId());
        List<AddressResponse> responses = AddressResponse.of(address);

        model.addAttribute("addressList", responses);

        return "/address";
    }


}
