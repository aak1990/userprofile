package com.assignment.userprofile.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserProfileDto implements Serializable {

    private static final long serialVersionUID = 8574553355815791728L;
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String fullName;
    @NotBlank(message = "Nick name cannot be blank")
    private String nickName;
    @Pattern(regexp = "^(0|[1-9][0-9]*)$", message = "Phone number can have only digits")
    @Size(min=10, max=10, message = "Phone number should consist of 10 digits")
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;
    @JsonManagedReference
    private Set<AddressDto> addresses = new HashSet<>();

    public UserProfileDto() {
    }

    public UserProfileDto(Long id, String fullName, String nickName, String phoneNumber, Set<AddressDto> addresses) {
        this.id = id;
        this.fullName = fullName;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.addresses = addresses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<AddressDto> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<AddressDto> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "UserProfileDto{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", addresses=" + addresses +
                '}';
    }


}
