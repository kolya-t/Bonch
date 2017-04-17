package ru.eninja.piaps.util.specifications;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.sql.Date;
import java.util.List;


public class Filter implements Specification {

    private List<String> filterFields;
    private String filterWord;

    /**
     * Фильтрация
     *
     * @param filterFields имена полей класса, по которым фильтровать
     * @param filterWord   строка запроса фильтрации
     */
    public Filter(List<String> filterFields, String filterWord) {
        this.filterFields = filterFields;
        this.filterWord = filterWord;

    }

    @SuppressWarnings("unchecked")
    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
        Predicate p = cb.disjunction();

        for (String fullField : filterFields) {
            Path path = root;
            /* Получение конкретного поля класса по его строковому пути */
            for (String shortField : fullField.split("\\.")) {
                path = path.get(shortField);
            }

            /* Применение определенного фильтра в зависимости от типа поля */
            Expression<Boolean> expr = null;
            switch (path.getJavaType().getName()) {
                case "java.lang.String":
                    expr = cb.like(path, "%" + filterWord + "%");
                    break;
                case "java.sql.Date":
                    try {
                        expr = cb.equal(path, Date.valueOf(filterWord));
                    } catch (IllegalArgumentException ignore) {
                    }
                    break;
                case "java.lang.Integer":
                    try {
                        expr = cb.equal(path, Integer.valueOf(filterWord));
                    } catch (NumberFormatException ignore) {
                    }
                    break;
            }

            /* Если выражение было обработано - добавить его в список выражений для дизъюнкции */
            if (expr != null) {
                p.getExpressions().add(expr);
            }
        }

        return p;
    }
}
