package com.ordis.base;

import java.util.LinkedList;

import javax.annotation.Nonnull;

import com.google.errorprone.annotations.ForOverride;
import com.ordis.Bot;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

public class Issue implements Recurring {
	@Nonnull
	private final Bot bot;
	@Nonnull
	private final MessageChannel channel;
	@Nonnull
	private final LinkedList<Message> history = new LinkedList<>();
	@Nonnull
	private MessageBuilder builder;
	private @Nonnull Property<IssueState> state = new Property<>(IssueState.class, IssueState.OPEN);
	private @Nonnull Property<Integer> interactionCount = new Property<>(Integer.class, 0);
	private @Nonnull Property<Boolean> open = new Property<>(Boolean.class, true);
	private long created = System.currentTimeMillis();
	private long expires = Long.MAX_VALUE;

	public Issue(@Nonnull Bot bot, @Nonnull MessageChannel channel, @Nonnull MessageBuilder builder,
			@Nonnull IssueState state) {
		this.bot = bot;
		this.channel = channel;
		this.builder = builder;
	}

	public Issue(@Nonnull Bot bot, @Nonnull MessageChannel channel) {
		this(bot, channel, new MessageBuilder(), IssueState.OPEN);
	}

	public void send(@Nonnull Message msg) {
		channel.sendMessage(msg).queue(m -> {
			bot.getIssues().put(m.getId(), this);
			history.add(m);
		});
	}

	public void edit(@Nonnull Message msg) {
		Message message = history.getLast();
		if (message != null)
			message.editMessage(msg).queue((@Nonnull Message m) -> {
				String last = message.getId();
				String current = m.getId();
				history.remove(message);
				history.add(m);
				if (last != current) {
					getBot().getIssues().remove(last);
					getBot().getIssues().put(current, this);
				}
			});
	}

	@Override
	public boolean finished() {
		return !isValid();
	}

	@ForOverride
	@Override
	public void start() {
		send(builder.build());
	}

	@ForOverride
	@Override
	public void end() {
		getHistory().forEach(m -> getBot().getIssues().remove(m.getId()));
		getHistory().clear();
	}

	public boolean isExpired() {
		return created + expires > System.currentTimeMillis();
	}

	public boolean isValid() {
		return state.getValue().isOpen() && !isExpired();
	}

	public void onMessageContextInteraction(@Nonnull MessageContextInteractionEvent event) {
		if (!isValid())
			return;
		System.out.println("Logged Context Interaction");
	}

	public void onMessageDelete(@Nonnull MessageDeleteEvent event) {
		if (!isValid())
			return;
	}

	public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
		if (!isValid())
			return;
	}

	public void onMessageReactionRemove(@Nonnull MessageReactionRemoveEvent event) {
		if (!isValid())
			return;
	}

	@Nonnull
	public Bot getBot() {
		return bot;
	}

	@Nonnull
	public MessageChannel getChannel() {
		return channel;
	}

	public LinkedList<Message> getHistory() {
		return history;
	}

	public void setBuilder(@Nonnull MessageBuilder builder) {
		this.builder = builder;
	}

	@Nonnull
	public MessageBuilder getBuilder() {
		return builder;
	}

	public Property<Integer> interactionCountProperty() {
		return interactionCount;
	}

	public Property<IssueState> stateProperty() {
		return state;
	}

	public Property<Boolean> openProperty() {
		return open;

	}

	public void setState(@Nonnull IssueState state) {
		this.state.setValue(state);
	};

	@Nonnull
	public IssueState getState() {
		return state.getValue();
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}
}
