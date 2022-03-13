package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;



@Entity
@Table(name = "meals", uniqueConstraints = @UniqueConstraint(columnNames = {"date_time", "user_id"}))

@NamedQueries(
        {@NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m  WHERE m.user.id=:user_id AND m.id=:id"),
        @NamedQuery(name = Meal.GET, query = "SELECT m FROM Meal m LEFT JOIN FETCH  m.user" +
                "  WHERE m.user.id=:user_id AND m.id=:id"),
        @NamedQuery(name = Meal.ALL_SORTED, query = "SELECT m FROM Meal m LEFT JOIN FETCH  m.user" +
                " WHERE m.user.id=:user_id ORDER BY m.dateTime" ),
        @NamedQuery(name = Meal.ALL_FILTERED_BY_TIME, query = "SELECT m FROM Meal m LEFT JOIN FETCH  m.user" +
                " WHERE m.dateTime > ?1 AND m.dateTime < ?2 AND m.user.id = ?3"),
        @NamedQuery(name = Meal.UPDATE, query = "UPDATE Meal m SET m.dateTime = ?1, m.calories = ?2, m.description = ?3" +
                "  WHERE m.user.id = ?4 AND m.id = ?5")})


public class Meal extends AbstractBaseEntity {

    public static final String DELETE = "meal.delete";
    public static final String UPDATE = "meal.update";
    public static final String GET = "meal.getMeal";
    public static final String ALL_SORTED = "meal.getAllSorted";
    public static final String ALL_FILTERED_BY_TIME = "meal.getAllFilteredByTime";

    @Column(name = "date_time", nullable = false)

    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    private String description;

    @Column(name = "calories", nullable = false)
    @Range(max = 10000)
    private int calories;

    @CollectionTable(name = "users", joinColumns = @JoinColumn(name = "id"))
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
