package red.theatre.backendapp.mapper;

import org.mapstruct.Mapper;
import red.theatre.backendapp.config.MapStructConfig;
import red.theatre.backendapp.dto.user.UserCreateDTO;
import red.theatre.backendapp.dto.user.UserResponseDTO;
import red.theatre.backendapp.model.User;
@Mapper(config = MapStructConfig.class)
public interface UserMapper {
    UserResponseDTO toResponse(User user);
    User toUser(UserCreateDTO userCreateDTO);
}
