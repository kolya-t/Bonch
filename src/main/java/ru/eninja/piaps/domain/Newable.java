package ru.eninja.piaps.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;


public interface Newable {

    @JsonIgnore
    boolean isNew();
}
