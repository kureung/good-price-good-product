package kr.co.gpgp.web.api.address;


import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.co.gpgp.auth.dto.SessionUser;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.address.AddressDto;
import kr.co.gpgp.domain.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        Long userId = getSessionUserId(request);

        AddressDto addressDto = AddressDto.of(addressRequest.getId(), addressRequest.getId(), addressRequest.getRoadName(), addressRequest.getZipCode(), addressRequest.getName(), addressRequest.getDetailed());

        Address address = addressService.create(userId, addressDto);

        return ResponseEntity.ok().body(address);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> delete(
            HttpServletRequest request,
            @Valid Long addressId
    ) {
        Long userId = getSessionUserId(request);

        addressService.delete(addressId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> update(
            HttpServletRequest request,
            AddressRequest addressRequest
    ) {

        Long userId = getSessionUserId(request);

        AddressDto addressDto = AddressDto.of(addressRequest.getId(), addressRequest.getId(), addressRequest.getRoadName(), addressRequest.getZipCode(), addressRequest.getName(), addressRequest.getDetailed());

        addressService.update(userId, addressDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ModelAndView home(
            HttpServletRequest request
    ) {
        ModelAndView mav = new ModelAndView("address");

        Long userId = getSessionUserId(request);
        List<Address> address = addressService.select(userId);
        List<AddressResponse> responses = AddressResponse.of(address);
        mav.addObject("addressList", responses);
        return mav;
    }


    private Long getSessionUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SessionUser user = (SessionUser) session.getAttribute("user");
        return user.getId();
    }
}
