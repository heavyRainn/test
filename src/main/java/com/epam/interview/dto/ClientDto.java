package com.epam.interview.dto;

import com.epam.interview.model.Risk;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ClientDto {

    @NotNull
    private Long id;
    @NotNull
    private Risk riskProfile;

    public ClientDto() {
    }

    public ClientDto(Long id, Risk riskProfile) {
        this.id = id;
        this.riskProfile = riskProfile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Risk getRiskProfile() {
        return riskProfile;
    }

    public void setRiskProfile(Risk riskProfile) {
        this.riskProfile = riskProfile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDto clientDto = (ClientDto) o;
        return Objects.equals(id, clientDto.id) &&
                riskProfile == clientDto.riskProfile;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, riskProfile);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", riskProfile=" + riskProfile +
                '}';
    }

}
