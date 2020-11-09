package com.fortunator.api.schemas;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fortunator.api.models.GoalTypeEnum;

public class CreateGoalSchema {

    @NotNull
    private Long amount;

    @NotNull
    private LocalDate date;

    @NotNull
    @Size(max = 255)
    private String description;

    @NotNull
    private GoalTypeEnum type;

    @NotNull
    @Valid
    private UserSchema user;

    public CreateGoalSchema() {
    }

    public Long getAmout() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GoalTypeEnum getType() {
        return type;
    }

    public void setType(GoalTypeEnum type) {
        this.type = type;
    }

    public UserSchema getUser() {
        return user;
    }

    public void setUser(UserSchema user) {
        this.user = user;
    }
}
