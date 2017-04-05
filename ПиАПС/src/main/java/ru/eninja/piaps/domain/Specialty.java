package ru.eninja.piaps.domain;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class Specialty {
    private String id;
    private String name;
    private Collection<Group> groupsById;
    private Faculty facultyByFacultyId;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Specialty specialty = (Specialty) o;

        if (id != null ? !id.equals(specialty.id) : specialty.id != null) return false;
        if (name != null ? !name.equals(specialty.name) : specialty.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

}
