package ru.eninja.piaps.domain.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.eninja.piaps.domain.Newable;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "`faculty`")
public class Faculty implements Newable {

    private Integer id;
    private String name;
    private Collection<Specialty> specialtiesById;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Faculty faculty = (Faculty) o;

        if (getId() != null ? !getId().equals(faculty.getId()) : faculty.getId() != null) return false;
        if (name != null ? !name.equals(faculty.name) : faculty.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "facultyByFacultyId")
    public Collection<Specialty> getSpecialtiesById() {
        return specialtiesById;
    }

    public void setSpecialtiesById(Collection<Specialty> specialtiesById) {
        this.specialtiesById = specialtiesById;
    }

    @Override
    @Transient
    public boolean isNew() {
        return id == null;
    }
}
