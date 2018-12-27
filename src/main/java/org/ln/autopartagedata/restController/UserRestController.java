package org.ln.autopartagedata.restController;
import io.swagger.annotations.ApiOperation;
import org.json.JSONException;
import org.json.JSONObject;
import org.ln.autopartagedata.bCrypt.Hashing;
import org.ln.autopartagedata.domaine.User;
import org.ln.autopartagedata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;

@RestController
@RequestMapping("/api-rest")
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(final UserService userService){
        this.userService = userService;
    }

    @GetMapping(value="/{id}",produces="application/json")
    @ApiOperation(value = "Get user by ID")
    User getUserById(@PathVariable final Long id){
        return this.userService.getUserById(id);
    }

    /*@ResponseBody
    @RequestMapping(path="/test", method=RequestMethod.POST, consumes = "application/json")
    public String saveObj(Model model, @RequestBody String json) throws JSONException {

        JSONObject jsonObj=new JSONObject(json);

        return jsonObj.getString("email");
    }*/

    @GetMapping(path="/connection",produces="application/json")
    public String userConnection(@RequestBody String json) throws JSONException {
        System.out.println(json);
        User user = null;
        String retour = "";
        JSONObject jsonObjResponse = new JSONObject();
        //création d'un objet JSON à partir de la string globale du body (data)
        JSONObject jsonObj=new JSONObject(json);

        //Récuprération des valeurs de l'objet JSON dans des variables
        String email = jsonObj.getString("email");
        String password = jsonObj.getString("password");

        user = userService.findByEmail(email);

        if (user != null){
            String[] mutableHash = new String[1];
            Function<String, Boolean> update =hash -> { mutableHash[0] = hash; return true; };
            if(Hashing.verifyAndUpdateHash(password, user.getPassword(), update)){
                retour = "{\n" +
                        "\t\"id\":\"" + user.getId() + "\",\n" +
                        "\t\"userGenre\":\"" + user.getUserGenre() + "\",\n" +
                        "\t\"firstName\":\"" + user.getFirstName() + "\",\n" +
                        "\t\"lastName\":\"" + user.getLastName() + "\",\n" +
                        "\t\"email\":\"" + user.getEmail() + "\",\n" +
                        "\t\"phoneNumber\":\"" + user.getPhoneNumber() + "\",\n" +
                        "\t\"birthYear\":\"" + user.getBirthYear() + "\",\n" +
                        "}";
            }else{
                retour = "{\n" +
                        "\t\"retour\":\"Ce mot de passe ne correspond pas à l'adresse " + user.getEmail() + " !\",\n" +
                        "}";
            }
        }else{
            retour = "{\n" +
                    "\t\"retour\":\"Aucun user n'est enregistré avec cette adresse email.\"\n" +
                    "}";
        }

        return retour;
    }
}

