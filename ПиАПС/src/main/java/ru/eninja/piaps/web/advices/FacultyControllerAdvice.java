package ru.eninja.piaps.web.advices;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.eninja.piaps.web.FacultyController;


@ControllerAdvice(assignableTypes = FacultyController.class)
public class FacultyControllerAdvice {

    @ModelAttribute("classActiveFaculties")
    public String cssActivePage() {
        return "active";
    }
}
