package com.assignment.userprofile.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class AddressDto implements Serializable {

    private static final long serialVersionUID = -2342962622794024393L;
    private Long id;
    @Pattern(regexp = "^(0|[1-9][0-9]*)$", message = "Pincode should consist of numbers")
    @Size(min=10, max=10, message = "Pincode should consist of 6 digits")
    @NotBlank(message = "Pincode cannot be blank")
    private String pincode;
    @NotBlank(message = "City cannot be blank")
    private String city;
    @NotBlank(message = "Address line cannot be blank")
    private String addressLine;
    @NotBlank(message = "State cannot be blank")
    private String state;
    @JsonBackReference
    private UserProfileDto user;

    public AddressDto() {
    }

    public AddressDto(Long id, String pincode, String city, String addressLine, String state, UserProfileDto user) {
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

    public UserProfileDto getUser() {
        return user;
    }

    public void setUser(UserProfileDto user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AddressDto{" +
                "id=" + id +
                ", pincode='" + pincode + '\'' +
                ", city='" + city + '\'' +
                ", addressLine='" + addressLine + '\'' +
                ", state='" + state + '\'' +
                '}';
    }


}
