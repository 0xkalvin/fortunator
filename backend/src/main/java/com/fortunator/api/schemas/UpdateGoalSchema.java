package com.fortunator.api.schemas;

import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fortunator.api.models.GoalStatusEnum;

public class UpdateGoalSchema {

    @NotNull
    private GoalStatusEnum status;

    @NotNull
    @Valid
    private UserSchema user;

    public UpdateGoalSchema() {
    }

    public GoalStatusEnum getStatus() {
        return status;
    }

    public void setStatus(GoalStatusEnum status) {
        this.status = status;
    }

    public UserSchema getUser() {
        return user;
    }

    public void setUser(UserSchema user) {
        this.user = user;
    }
}
