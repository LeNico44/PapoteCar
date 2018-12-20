package org.ln.autopartagedata;

import org.apache.logging.log4j.spi.AbstractLogger;
import org.ln.autopartagedata.bCrypt.Hashing;
import org.ln.autopartagedata.bCrypt.UpdatableBCrypt;
import org.ln.autopartagedata.dal.*;
import org.ln.autopartagedata.domaine.*;
import org.ln.autopartagedata.service.UserServiceImpl;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import java.util.Calendar;
import java.util.function.Function;


@SpringBootApplication
public class AutopartageDataApplication {

    @Configuration
    public class GlobalRepositoryRestConfigurer implements RepositoryRestConfigurer {

        @Override
        public void configureRepositoryRestConfiguration( RepositoryRestConfiguration config ) {
            config.getCorsRegistry().addMapping( "/api-rest/**" ).allowedOrigins( "*" ).allowedHeaders( "*" )
                    .allowedMethods( "*" );
        }
    }

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(AutopartageDataApplication.class, args);

        UserRepository userRepository = context.getBean("user_dao", UserRepository.class);
        RoadTripRepository roadTripRepository = context.getBean("roadtrip_dao", RoadTripRepository.class);
        StepRepository stepRepository = context.getBean("step_dao", StepRepository.class);
        PassengerRepository passengerRepository = context.getBean("passenger_dao", PassengerRepository.class);
        CommentRepository commentRepository = context.getBean("comment_dao", CommentRepository.class);

        User user = userRepository.save(new User(User.Genre.Monsieur,"Laurent","Lecomte",
                "dynaouest@gmail.com","06.06.06.06.06",1970, "password"));


        Step step = stepRepository.save(new Step());

        RoadTrip roadTrip = roadTripRepository.save(new RoadTrip(false,(byte)3,(byte)3, user));

        passengerRepository.save(new Passenger(user,step,true));

        UserServiceImpl userService = new UserServiceImpl(userRepository);

        commentRepository.save(new Comment(user,roadTrip,"C'est le premier commentaire au sujet de ce superbe projet" +
                " qu'est AutoPartage.","Un titre !" , new java.sql.Date(Calendar.getInstance().getTime().getTime())));

        User u2 = userService.getUserById(user.getId());

        System.out.println(u2.getId());

        /**************************************************************************/

        final Logger log = UpdatableBCrypt.getLog();

        String[] mutableHash = new String[1];
        Function<String, Boolean> update =hash -> { mutableHash[0] = hash; return true; };

        String hashPw1 = Hashing.hash("password");

        System.out.println("hash of pw1: {}" + hashPw1);
        log.debug("verifying pw1: {}", Hashing.verifyAndUpdateHash("password", hashPw1, update));
        log.debug("verifying pw1 fails: {}", Hashing.verifyAndUpdateHash("password1", hashPw1, update));
        String hashPw2 = Hashing.hash("password");
        log.debug("hash of pw2: {}", hashPw2);
        log.debug("verifying pw2: {}", Hashing.verifyAndUpdateHash("password", hashPw2, update));
        log.debug("verifying pw2 fails: {}", Hashing.verifyAndUpdateHash("password2", hashPw2, update));

        UpdatableBCrypt oldHasher = new UpdatableBCrypt(7);
        String oldHash = oldHasher.hash("password");
        log.debug("hash of oldHash: {}", oldHash);
        log.debug("verifying oldHash: {}, hash upgraded to: {}",
                Hashing.verifyAndUpdateHash("password", oldHash, update),
                mutableHash[0]);
    }

}
