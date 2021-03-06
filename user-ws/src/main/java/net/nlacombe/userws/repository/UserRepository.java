package net.nlacombe.userws.repository;

import net.nlacombe.userws.domain.User;
import net.nlacombe.userws.domain.UserStatus;
import net.nlacombe.userws.entity.UserEntity;
import net.nlacombe.userws.entity.UserStatusEntity;
import net.nlacombe.userws.jparepository.UserJpaRepository;
import net.nlacombe.userws.mapper.UserEntityMapper;
import net.nlacombe.userws.mapper.UserStatusEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRepository
{
	private final UserJpaRepository userJpaRepository;
	private final UserEntityMapper userEntityMapper;
	private final UserStatusEntityMapper userStatusEntityMapper;

    public UserRepository(UserJpaRepository userJpaRepository, UserEntityMapper userEntityMapper, UserStatusEntityMapper userStatusEntityMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userEntityMapper = userEntityMapper;
        this.userStatusEntityMapper = userStatusEntityMapper;
    }

    public User getUserById(int userId)
	{
		UserEntity userTable = userJpaRepository.getOne(userId);
		User user = userEntityMapper.mapToDomainType(userTable);

		return user;
	}

	public User saveUser(User user)
	{
		UserEntity userEntity = userEntityMapper.mapToEntityType(user);
		userEntity =  userJpaRepository.save(userEntity);
		return userEntityMapper.mapToDomainType(userEntity);
	}

	public void deleteUser(int userId)
	{
		userJpaRepository.deleteById(userId);
	}

	public boolean userWithUsernameAndStatusExist(String username, UserStatus status)
	{
		return getUserByUsernameAndStatus(username, status)!=null;
	}

	public User getUserByUsernameAndStatus(String username, UserStatus status)
	{
		UserStatusEntity statusEntity = userStatusEntityMapper.mapToEntityType(status);

		UserEntity userEntity = userJpaRepository.findByUsernameAndStatus(username, statusEntity);

		return userEntityMapper.mapToDomainType(userEntity);
	}
}
