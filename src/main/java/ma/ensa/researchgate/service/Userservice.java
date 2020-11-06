package ma.ensa.researchgate.service;


import ma.ensa.researchgate.dto.RegisterRequest;
import ma.ensa.researchgate.entities.User;
import ma.ensa.researchgate.exceptions.UserAlreadyExistAuthenticationException;

public interface Userservice {
    public User adduser(RegisterRequest registerRequest) throws UserAlreadyExistAuthenticationException;
}
