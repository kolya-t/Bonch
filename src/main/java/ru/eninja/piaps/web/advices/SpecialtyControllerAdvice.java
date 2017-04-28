package ru.eninja.piaps.web.advices;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.eninja.piaps.web.FacultyController;
import ru.eninja.piaps.web.SpecialtyController;


@ControllerAdvice(assignableTypes = SpecialtyController.class)
public class SpecialtyControllerAdvice {

    @ModelAttribute("classActiveSpecialties")
    public String cssActivePage() {
        return "active";
    }
}
