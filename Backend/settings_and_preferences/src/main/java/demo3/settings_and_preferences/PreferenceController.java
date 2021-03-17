package demo3.settings_and_preferences;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PreferenceController {

    @Autowired
    PreferenceDatabase db;

    @GetMapping("/usersettings/{id}")
    preferences getPreferences(@PathVariable Integer id) {
        return db.getOne(id);
    }

    @PutMapping("/updatesettings/{id}{newSetting}")
    preferences updateTask(@RequestBody preferences p, @PathVariable Integer id) {
        preferences old_pref = db.getOne(id);
        db.save(old_pref);
        return old_pref;
    }


}
