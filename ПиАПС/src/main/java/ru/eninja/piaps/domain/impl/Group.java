package ru.eninja.piaps.domain.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.eninja.piaps.domain.Newable;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Table(name = "`group`")
public class Group implements Newable {

    private String id;
    private Integer course;
    private Specialty specialtyBySpecialtyId;
    private Collection<Student> studentsById;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "course", nullable = false)
    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (getId() != null ? !getId().equals(group.getId()) : group.getId() != null) return false;
        if (course != null ? !course.equals(group.course) : group.course != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (course != null ? course.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "specialty_id", referencedColumnName = "id", nullable = false)
    public Specialty getSpecialtyBySpecialtyId() {
        return specialtyBySpecialtyId;
    }

    public void setSpecialtyBySpecialtyId(Specialty specialtyBySpecialtyId) {
        this.specialtyBySpecialtyId = specialtyBySpecialtyId;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "groupByGroupId")
    public Collection<Student> getStudentsById() {
        return studentsById;
    }

    public void setStudentsById(Collection<Student> studentsById) {
        this.studentsById = studentsById;
    }

    @Override
    @Transient
    public boolean isNew() {
        return id == null;
    }
}
