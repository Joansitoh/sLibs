package me.skillteam.utils.texts;

import lombok.Getter;

/**
 * Created by Joansiitoh (DragonsTeam && SkillTeam)
 * Date: 24/08/2021 - 17:41.
 */

@Getter
public enum Replaces {

    TARGET("<swcore_target>"),
    VALUE("<swcore_value>"),
    ;

    private final String object;

    Replaces(String object) {
        this.object = object;
    }

    public String replace(String input, String object) {
        return input.replace(this.object, object);
    }

}
