package org.ln.autopartagedata.restController;
import io.swagger.annotations.ApiOperation;
import org.json.JSONException;
import org.json.JSONObject;
import org.ln.autopartagedata.domaine.User;
import org.ln.autopartagedata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    User getUserById(@PathVariable final Long id){
        return this.userService.getUserById(id);
    }

    /*@ResponseBody
    @RequestMapping(path="/test", method=RequestMethod.POST, consumes = "application/json")
    public String saveObj(Model model, @RequestBody String json) throws JSONException {

        JSONObject jsonObj=new JSONObject(json);

        return jsonObj.getString("email");
    }*/
}

