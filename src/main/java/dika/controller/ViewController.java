package dika;

import dika.model.Phone;
import dika.service.PhoneServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/phones")
@RequiredArgsConstructor
public class ViewController {

    private final PhoneServiceImpl phoneServiceImpl;

    @GetMapping
    public String getView(Model model,@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "30") int size) {
        model.addAttribute("phones", phoneServiceImpl.getAllPhones());
        return "phone";
    }
}
