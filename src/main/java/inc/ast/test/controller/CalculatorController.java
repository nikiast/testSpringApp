package inc.ast.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/calc")
public class CalculatorController {
    @GetMapping
    public String calculator() {
        return "calculator/calc";
    }

    @PostMapping
    public String calculatorPost(@RequestParam(name = "value1") Double value1,
                                 @RequestParam(name = "value2") Double value2,
                                 @RequestParam(name = "operation") String operation,
                                 Model model) {
        if (!(value2 == 0 && operation.equals("/"))) {
            switch (operation) {
                case "+" -> model.addAttribute("result", value1 + value2);
                case "-" -> model.addAttribute("result", value1 - value2);
                case "*" -> model.addAttribute("result", value1 * value2);
                case "/" -> model.addAttribute("result", value1 / value2);
            }
        } else {
            model.addAttribute("result", "NaN");
        }
        return "calculator/calc";
    }

}