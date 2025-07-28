package org.ordis.game;

import net.dv8tion.jda.api.EmbedBuilder;

public class Roles {
    public static final Role WEREWOLF = new Role(Faction.WEREWOLVES, Awakening.MIDNIGHT, Alignment.EVIL);

    public static final Role VILLAGER = new Role(Faction.VILLAGE, Awakening.NEVER, Alignment.GOOD);

    public static final Role MEDIC = new Role(Faction.VILLAGE, Awakening.MIDNIGHT, Alignment.GOOD) {
        @Override
        protected void onAction(Player player) {
            player.getUser().openPrivateChannel().queue(channel -> {
                channel.sendMessageEmbeds(new EmbedBuilder().setDescription("Which alignment do you want to see?").build()).queue();
            });
        }
    };
}