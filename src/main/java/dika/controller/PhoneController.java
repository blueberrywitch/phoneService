package dika;

import dika.model.Phone;
import dika.service.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/phones")
@RequiredArgsConstructor
public class PhoneController {

    private final PhoneService phoneService;

    @PostMapping
    public ResponseEntity<Phone> createPhone(@RequestBody Phone phone) {
        Phone createdPhone = phoneService.createPhone(phone);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPhone);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Phone> updatePhone(@PathVariable Long id, @RequestBody Phone phone) {
        Phone updatedPhone = phoneService.updatePhone(id, phone);
        return ResponseEntity.ok(updatedPhone);
    }

    @GetMapping("/{id}")
    public ModelAndView getPhoneById(@PathVariable Long id, ModelMap model) {
        model.addAttribute(phoneService.getPhoneById(id)) ;
        return new ModelAndView("phoneDetails", model);
    }

    @GetMapping("/search")
    public List<Phone> searchPhones(@RequestParam(defaultValue = "") String brand,
                                    @RequestParam(defaultValue = "") String model,
                                    @RequestParam(defaultValue = "") String processor,
                                    @RequestParam(defaultValue = "") String screenDiagonal,
                                    @RequestParam(defaultValue = "") String cameraResolution) {
        return phoneService.searchPhones(brand, model, processor, screenDiagonal, cameraResolution);
    }
}
