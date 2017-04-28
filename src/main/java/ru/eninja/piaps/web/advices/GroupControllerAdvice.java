package ru.eninja.piaps.web.advices;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.eninja.piaps.web.FacultyController;
import ru.eninja.piaps.web.GroupController;


@ControllerAdvice(assignableTypes = GroupController.class)
public class GroupControllerAdvice {

    @ModelAttribute("classActiveGroups")
    public String cssActivePage() {
        return "active";
    }
}
