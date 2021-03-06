package app.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = " teachers")
public class Teacher extends Person {
    private String email;
    private Double salaryPerHour;
    private Set<Course> course;

    public Teacher() { }

    @Column(name = "email")
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "salary_per_hour")
    public Double getSalaryPerHour() {
        return this.salaryPerHour;
    }

    public void setSalaryPerHour(Double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    @OneToMany(targetEntity = Course.class, mappedBy = "teacher")
    public Set<Course> getCourse() {
        return this.course;
    }

    public void setCourse(Set<Course> course) {
        this.course = course;
    }
}
