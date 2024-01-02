package org.ordis.content;

import org.ordis.game.Alignment;
import org.ordis.game.Awakening;
import org.ordis.game.Faction;
import org.ordis.game.Role;

public class Roles {

    public static Role VILLAGER = new Role(Faction.VILLAGE, Awakening.NEVER, Alignment.GOOD);

    public static Role WEREWOLF = new Role(Faction.WEREWOLVES, Awakening.MIDNIGHT, Alignment.EVIL);
}
