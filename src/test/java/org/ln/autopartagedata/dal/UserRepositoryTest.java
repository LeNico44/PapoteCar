package org.ln.autopartagedata.dal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ln.autopartagedata.domaine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void finByEmail(){
        User user = userRepository.save(new User(User.Genre.Monsieur,"Laurent","Lecomte",
                "dynaouest@gmail.com","06.06.06.06.06",(byte)1970));
        Assert.assertThat(user.getFirstName(), is(userRepository.findByEmail("dynaouest@gmail.com").getFirstName()));
    }

}
