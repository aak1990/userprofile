package com.assignment.userprofile.service;

import com.assignment.userprofile.dto.UserProfileDto;
import com.assignment.userprofile.model.Address;
import com.assignment.userprofile.model.UserProfile;
import com.assignment.userprofile.repository.AddressRepository;
import com.assignment.userprofile.repository.UserProfileRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
public class UserProfileServiceImpl implements UserProfileService{

    private UserProfileRepository userProfileRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, ModelMapper modelMapper) {
        this.userProfileRepository = userProfileRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserProfileDto save(UserProfileDto userProfileDto) {
        modelMapper.typeMap(UserProfileDto.class, UserProfile.class).addMappings(mapper -> {
            mapper.skip(UserProfile::setAddresses);
        });
        UserProfile userProfile = modelMapper.map(userProfileDto, UserProfile.class);
        if(userProfileDto!=null && !CollectionUtils.isEmpty(userProfileDto.getAddresses())) {
            userProfileDto.getAddresses().stream().map(addressDto -> modelMapper.map(addressDto, Address.class))
                    .forEach(address -> { userProfile.addAddresses(address); });
        }
        return modelMapper.map(userProfileRepository.save(userProfile), UserProfileDto.class);
    }

    @Override
    public UserProfileDto update(Long id, UserProfileDto userProfileDto) {
        Optional existingUserProfileData = userProfileRepository.findById(id);
        if(existingUserProfileData.isPresent()) {
            UserProfile existingUserProfile = (UserProfile) existingUserProfileData.get();
            existingUserProfile.setPhoneNumber(userProfileDto.getPhoneNumber());
            existingUserProfile.setFullName(userProfileDto.getFullName());
            existingUserProfile.setNickName(userProfileDto.getNickName());
            existingUserProfile.clearAddresses();
            userProfileDto.getAddresses().stream().map(addressDto -> modelMapper.map(addressDto, Address.class))
                    .forEach(address -> { existingUserProfile.addAddresses(address); });
            return modelMapper.map(userProfileRepository.save(existingUserProfile), UserProfileDto.class);
        } else {
            return null;
        }
    }

    @Override
    public UserProfileDto get(Long id) {
        Optional userProfileData = userProfileRepository.findById(id);
        return userProfileData.isPresent() ? modelMapper.map((UserProfile) userProfileData.get(), UserProfileDto.class) : null;
    }

    @Override
    public List<UserProfileDto> getAll() {
        List<UserProfile> userProfileEntityList = userProfileRepository.findAll();
        Type listType = new TypeToken<List<UserProfileDto>>() {}.getType();
        return modelMapper.map(userProfileEntityList, listType);
    }

    @Override
    public void delete(Long id) {
        userProfileRepository.deleteById(id);
    }
}
