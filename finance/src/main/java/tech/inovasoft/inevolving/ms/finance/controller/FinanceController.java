package tech.inovasoft.inevolving.ms.finance.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Finance | Finanças",
        description = "Personal Finance Endpoint Manager | Gerenciador dos endpoints de Finanças Pessoais"
)
@RestController
@RequestMapping("/ms/finance")
public class FinanceController {

}
