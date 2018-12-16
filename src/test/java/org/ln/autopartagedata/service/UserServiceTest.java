package org.ln.autopartagedata.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ln.autopartagedata.dal.UserRepository;
import org.ln.autopartagedata.domaine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    UserService userService;

    @Test
    public void getUserBirthYear(){
        User user = userRepository.save(new User(User.Genre.Monsieur,"Laurent","Lecomte",
                "dynaouest@gmail.com","06.06.06.06.06",(byte)1970));
        Assert.assertThat(user.getBirthYear(), is(userService.getUserBirthYear(user)));
    }

}