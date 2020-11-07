package com.fortunator.api.schemas;

import javax.validation.constraints.NotNull;

public class UserSchema {

    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserSchema() {
    }

}
