package edu.xinan.demo.dao;

import edu.xinan.demo.po.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by hoaven on 2019/2/14
 */
public interface UserMongoRepository extends ReactiveMongoRepository<User, String> {

}
