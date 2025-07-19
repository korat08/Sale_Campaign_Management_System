package com.example.Sale_Campaign_Management_System.Service;

import com.example.Sale_Campaign_Management_System.Model.CustomUser;
import com.example.Sale_Campaign_Management_System.Model.CustomUserDetails;
import com.example.Sale_Campaign_Management_System.Repository.CustomUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    CustomUserRepo customUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CustomUser customUser = customUserRepo.findByUserName(username);

        if(customUser == null){
            throw new UsernameNotFoundException("User Not Found");
        }
        return new CustomUserDetails(customUser);
    }

}
