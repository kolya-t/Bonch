package ru.eninja.piaps.web.advices;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.eninja.piaps.web.FacultyController;
import ru.eninja.piaps.web.StudentController;


@ControllerAdvice(assignableTypes = StudentController.class)
public class StudentControllerAdvice {

    @ModelAttribute("classActiveStudents")
    public String cssActivePage() {
        return "active";
    }
}
