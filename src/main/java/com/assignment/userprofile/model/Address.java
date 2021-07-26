package com.assignment.userprofile.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="user_address")
public class Address extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="pincode", nullable = false, length = 6)
    private String pincode;
    @Column(name="city", nullable = false)
    private String city;
    @Column(name="address_line", nullable = false)
    private String addressLine;
    @Column(name="state", nullable = false)
    private String state;
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "user_profile_id", nullable = false)
    @JsonBackReference
    private UserProfile user;

    public Address() {

    }

    public Address(Long id, String pincode, String city, String addressLine, String state, UserProfile user) {
        this.id = id;
        this.pincode = pincode;
        this.city = city;
        this.addressLine = addressLine;
        this.state = state;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", pincode='" + pincode + '\'' +
                ", city='" + city + '\'' +
                ", addressLine='" + addressLine + '\'' +
                ", state='" + state + '\'' +
                '}';
    }


}
