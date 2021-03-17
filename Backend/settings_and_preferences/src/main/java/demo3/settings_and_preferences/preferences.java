package demo3.settings_and_preferences;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class preferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column
    String user;
    @Column
    Boolean dark;
    @Column
    static final String[] timezone = {"CT", "PST", "EST"};
    String tz;
    String[] pref = new String[2];

    public preferences() {
        dark = false;
        tz = timezone[0];
    }

    public String getTime() {
        return tz;
    }

    public void darkMode() {
        dark = true;
        pref[0] = "Dark Mode On";
    }

    public String[] getPref() {
        return pref;
    }
}
