package org.ln.autopartagedata.restController;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roadtrips")
public class UserRoadTripController {

    @PostMapping(path="/test",consumes = "application/json")
    public String saveObj(@RequestBody String json) throws JSONException {

        JSONObject jsonObj=new JSONObject(json);

        return jsonObj.getString("endpoint");
    }

}
