package dika.controller;

import dika.criteria.CriteriaAPI;
import dika.enums.BatteryCapacity;
import dika.enums.InternalStorage;
import dika.enums.PhoneBrands;
import dika.enums.Processor;
import dika.enums.ScreenDiagonal;
import dika.model.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/phones")
@RequiredArgsConstructor
public class ViewController {

    private final CriteriaAPI criteriaSearch;

    @GetMapping
    public String listPhones(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) List<String> brand,
            @RequestParam(required = false) List<String> processor,
            @RequestParam(required = false) List<String> internalStorage,
            @RequestParam(required = false) List<String> batteryCapacity,
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax,
            @RequestParam(name = "screenDiagonal", required = false) List<String> screenDiagonal,
            @RequestParam(required = false) String sort,
            Model ui
    ) {
        List<Phone> phones = criteriaSearch.searchAndSortPhones(
                brand,
                processor,
                screenDiagonal,
                internalStorage,
                batteryCapacity,
                priceMin,
                priceMax,
                model,
                sort
        );

        ui.addAttribute("phones", phones);
        ui.addAttribute("brands", PhoneBrands.values());
        ui.addAttribute("processors", Processor.values());
        ui.addAttribute("screenDiagonal", ScreenDiagonal.values());
        ui.addAttribute("internalStorage", InternalStorage.values());
        ui.addAttribute("batteryCapacity", BatteryCapacity.values());
        ui.addAttribute("param", ui.asMap());
        return "phone";
    }
}
