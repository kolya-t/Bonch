package ru.eninja.piaps.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;


/**
 * Copy of {@link org.springframework.data.jpa.domain.AbstractPersistable}
 *
 * @param <PK>
 */
@MappedSuperclass
public class GeneratedValueEntity<PK extends Serializable> implements Persistable<PK> {

    private PK id;

    @Id
    @GeneratedValue
    public PK getId() {
        return id;
    }

    public void setId(final PK id) {
        this.id = id;
    }

    @Transient
    @JsonIgnore
    public boolean isNew() {
        return null == getId();
    }

    @Override
    public String toString() {
        return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());
    }

    @Override
    public boolean equals(Object obj) {

        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(ClassUtils.getUserClass(obj))) {
            return false;
        }

        GeneratedValueEntity<?> that = (GeneratedValueEntity<?>) obj;

        return null != this.getId() && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {

        int hashCode = 17;

        hashCode += null == getId() ? 0 : getId().hashCode() * 31;

        return hashCode;
    }
}
