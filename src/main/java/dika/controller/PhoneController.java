package dika.controller;

import dika.model.Phone;
import dika.service.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
        phoneService.updatePhone(id, phone);
        return ResponseEntity.ok(phone);
    }

    @GetMapping("/{id}")
    public ModelAndView getPhoneById(@PathVariable Long id, ModelMap model) {
        model.addAttribute(phoneService.getPhoneById(id));
        return new ModelAndView("phoneDetails", model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhone(@PathVariable Long id) {
        phoneService.deletePhone(id);
        return ResponseEntity.noContent().build();
    }

}
