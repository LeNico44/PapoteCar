package org.ln.autopartagedata.restController;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.ln.autopartagedata.bCrypt.Hashing;
import org.ln.autopartagedata.dal.UserRepository;
import org.ln.autopartagedata.domaine.User;
import org.ln.autopartagedata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/api-rest/users")
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(final UserService userService){
        this.userService = userService;
    }

    @GetMapping(value="/{id}",produces="application/json")
    @ApiOperation(value = "Get user by ID")
    Optional<User> getUserById(@PathVariable final Long id){
        return this.userService.getUserById(id);
    }

    @GetMapping(value="/email/{email}",produces="application/json")
    @ApiOperation(value = "Get user by email")
    User getUserByEmail(@PathVariable final String email){
        return this.userService.findByEmail(email);
    }

    /*@ResponseBody
    @RequestMapping(path="/test", method=RequestMethod.POST, consumes = "application/json")
    public String saveObj(Model model, @RequestBody String json) throws JSONException {

        JSONObject jsonObj=new JSONObject(json);

        return jsonObj.getString("email");
    }*/

    @PostMapping(path="/connection",produces="application/json")
    public @ResponseBody String userConnection(@RequestBody String json) throws JSONException {
        System.out.println(json);
        User user;
        JSONObject jsonRetour = new JSONObject();
        //création d'un objet JSON à partir de la string globale du body (data)
        JSONObject jsonObj=new JSONObject(json);

        //Récuprération des valeurs de l'objet JSON dans des variables
        String email = jsonObj.getString("email");
        String password = jsonObj.getString("password");

        user = userService.findByEmail(email);

        if (user != null){
            String[] mutableHash = new String[0];
            Function<String, Boolean> update =hash -> { mutableHash[0] = hash; return true; };
            if(Hashing.verifyAndUpdateHash(password, user.getPassword(), update)){
                
                jsonRetour.put("id", user.getId());
                jsonRetour.put("userGenre", user.getUserGenre());
                jsonRetour.put("firstName", user.getFirstName());
                jsonRetour.put("lastName", user.getLastName());
                jsonRetour.put("email", user.getEmail());
                jsonRetour.put("phoneNumber", user.getPhoneNumber());
                jsonRetour.put("birthYear", user.getBirthYear());
                
            }else{
                jsonRetour.put("retour","Ce mot de passe ne correspond pas à l'adresse " + user.getEmail() + " !");
            }
        }else{
            jsonRetour.put("retour", "Aucun user n'est enregistré avec cette adresse email.");
        }

        return jsonRetour.toString();
    }
}

