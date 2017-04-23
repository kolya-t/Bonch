package ru.eninja.piaps.domain.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.eninja.piaps.domain.NonGeneratedValueEntity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "`specialty`")
public class Specialty extends NonGeneratedValueEntity<String> {
    private String name;
    private Collection<Group> groupsById;
    private Faculty facultyByFacultyId;

    @Basic
    @Column(name = "name", nullable = false)
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

        Specialty specialty = (Specialty) o;

        if (getId() != null ? !getId().equals(specialty.getId()) : specialty.getId() != null) return false;
        if (name != null ? !name.equals(specialty.name) : specialty.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "specialtyBySpecialtyId")
    public Collection<Group> getGroupsById() {
        return groupsById;
    }

    public void setGroupsById(Collection<Group> groupsById) {
        this.groupsById = groupsById;
    }

    @ManyToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "id", nullable = false)
    public Faculty getFacultyByFacultyId() {
        return facultyByFacultyId;
    }

    public void setFacultyByFacultyId(Faculty facultyByFacultyId) {
        this.facultyByFacultyId = facultyByFacultyId;
    }
}
