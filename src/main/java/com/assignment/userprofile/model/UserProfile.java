package com.assignment.userprofile.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user_profile")
public class UserProfile extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "nick_name", nullable = false)
    private String nickName;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> addresses = new HashSet<>();

    public UserProfile() {

    }

    public UserProfile(Long id, String fullName, String nickName, String phoneNumber, Set<Address> addresses) {
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

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddresses(Address address) {
        address.setUser(this);
        this.addresses.add(address);
    }

    public void clearAddresses() {
        this.addresses.clear();
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
