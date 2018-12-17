package org.ln.autopartagedata;

import org.ln.autopartagedata.dal.*;
import org.ln.autopartagedata.domaine.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Calendar;


@SpringBootApplication
public class AutopartageDataApplication {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(AutopartageDataApplication.class, args);

        UserRepository userRepository = context.getBean("user_dao", UserRepository.class);
        RoadTripRepository roadTripRepository = context.getBean("roadtrip_dao", RoadTripRepository.class);
        StepRepository stepRepository = context.getBean("step_dao", StepRepository.class);
        PassengerRepository passengerRepository = context.getBean("passenger_dao", PassengerRepository.class);
        CommentRepository commentRepository = context.getBean("comment_dao", CommentRepository.class);

        User user = userRepository.save(new User(User.Genre.Monsieur,"Laurent","Lecomte",
                "dynaouest@gmail.com","06.06.06.06.06",1970));


        Step step = stepRepository.save(new Step());

        RoadTrip roadTrip = roadTripRepository.save(new RoadTrip(false,(byte)3,(byte)3, user));

        passengerRepository.save(new Passenger(user,step,true));

        commentRepository.save(new Comment(user,roadTrip,"C'est le premier commentaire au sujet de ce superbe projet" +
                " qu'est AutoPartage.","Un titre !" , new java.sql.Date(Calendar.getInstance().getTime().getTime())));
    }

}
