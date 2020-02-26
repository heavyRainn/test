package com.epam.interview.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotNull
    private Risk riskProfile;

    public Client() {
    }

    public Client(Long id, Risk riskProfile) {
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
        Client client = (Client) o;
        return Objects.equals(id, client.id) &&
                riskProfile == client.riskProfile;
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
