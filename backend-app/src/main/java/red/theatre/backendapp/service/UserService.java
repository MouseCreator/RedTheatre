package red.theatre.backendapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import red.theatre.backendapp.dto.user.UserCreateDTO;
import red.theatre.backendapp.dto.user.UserResponseDTO;
import red.theatre.backendapp.exception.DataNotFoundException;
import red.theatre.backendapp.mapper.UserMapper;
import red.theatre.backendapp.model.User;
import red.theatre.backendapp.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private static DataNotFoundException notFound(String type, Object object) {
        return new DataNotFoundException("Неможливо знайти користувача за " + type + ":" + object);
    }
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->notFound("ідентифікатором", id));
        return userMapper.toResponse(user);
    }
    public UserResponseDTO getUserByLogin(String login) {
        User user = userRepository.findByLogin(login).orElseThrow(()->notFound("логіном", login));
        return userMapper.toResponse(user);
    }
    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        User user = userMapper.toUser(userCreateDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public User populate(UserCreateDTO userCreateDTO) {
        User user = userMapper.toUser(userCreateDTO);
        return userRepository.save(user);
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
