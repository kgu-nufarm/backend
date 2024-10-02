package api.kgu.nufarm.application.user.service;

import api.kgu.nufarm.application.user.dao.UserRepository;
import api.kgu.nufarm.application.user.dto.request.UserRequestDto;
import api.kgu.nufarm.application.user.entity.Role;
import api.kgu.nufarm.application.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long save(UserRequestDto userRequestDto) {
        validateAlreadyExistEmail(userRequestDto.getEmail());
        User user = User.toEntity(userRequestDto);
        user.updatePassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.updateRole(Role.USER);
        userRepository.save(user);
        return user.getId();
    }

    public void validateAlreadyExistEmail(String email) {
        if(userRepository.existsByEmail(email)) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
