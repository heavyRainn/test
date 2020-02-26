package com.epam.interview.dto;

import javax.validation.constraints.Size;
import java.util.Set;

public class IdSetWrapper {

    @Size(min = 2)
    private Set<Long> ids;

    public Set<Long> getIds() {
        return ids;
    }

    public void setIds(Set<Long> ids) {
        this.ids = ids;
    }

}
