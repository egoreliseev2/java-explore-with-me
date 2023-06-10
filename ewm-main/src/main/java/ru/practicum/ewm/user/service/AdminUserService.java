package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.exception.ObjectNotFoundException;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.mapper.UserMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.AdminUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminUserService {
    private final AdminUserRepository adminUserRepository;

    public List<UserDto> findAll(List<Long> ids, Pageable page) {
        log.info("User list sent");
        if (!ids.isEmpty()) {
            return adminUserRepository.findAllByIdIn(ids, page).stream()
                    .map(UserMapper.USER_MAPPER::toUserDto)
                    .collect(Collectors.toList());
        } else {
            return adminUserRepository.findAll(page).stream()
                    .map(UserMapper.USER_MAPPER::toUserDto)
                    .collect(Collectors.toList());
        }
    }

    @Transactional
    public UserDto create(UserDto userDto) {
        User user = adminUserRepository.save(UserMapper.USER_MAPPER.toUser(userDto));
        log.info("User created with id {}", user.getId());
        return UserMapper.USER_MAPPER.toUserDto(user);
    }

    @Transactional
    public void delete(long userId) {
        adminUserRepository.findById(userId).orElseThrow(() -> {
            throw new ObjectNotFoundException("User not found");
        });
        adminUserRepository.deleteById(userId);
        log.info("User with id {} deleted", userId);
    }
}
