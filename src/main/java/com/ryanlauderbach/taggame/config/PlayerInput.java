package com.ryanlauderbach.taggame.config;

import com.ryanlauderbach.taggame.TagGame;
import org.bukkit.ChatColor;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerInput {
    
    public static void askForLength(Player player) {
        if (player.isConversing()) {
            return;
        }
        
        ConversationFactory factory = new ConversationFactory(TagGame.getPlugin());
        Conversation conversation = factory.withFirstPrompt(new LengthPrompt()).withLocalEcho(false).withEscapeSequence("exit").buildConversation(player);
        conversation.begin();
    }
    
    public static void askForLocation(Player player) {
        if (player.isConversing()) {
            return;
        }
        
        ConversationFactory factory = new ConversationFactory(TagGame.getPlugin());
        Conversation conversation = factory.withFirstPrompt(new XPrompt()).withLocalEcho(false).withEscapeSequence("exit").buildConversation(player);
        conversation.begin();
    }
    
    public static void askForBorderSize(Player player) {
        if (player.isConversing()) {
            return;
        }
        
        ConversationFactory factory = new ConversationFactory(TagGame.getPlugin());
        Conversation conversation = factory.withFirstPrompt(new BorderPrompt()).withLocalEcho(false).withEscapeSequence("exit").buildConversation(player);
        conversation.begin();
    }
}

class LengthPrompt extends NumericPrompt {
    
    @Override
    protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull Number input) {
        if (input.doubleValue() % 1 != 0) {
            context.getForWhom().sendRawMessage(ChatColor.DARK_GREEN + "Length set to " + ChatColor.YELLOW + input.doubleValue() + " minutes" + ChatColor.DARK_GREEN + ".");
            TagSettings.setTimerLength(input.doubleValue());
        } else if (input.intValue() == 1) {
            context.getForWhom().sendRawMessage(ChatColor.DARK_GREEN + "Length set to " + ChatColor.YELLOW + input.intValue() + " minute" + ChatColor.DARK_GREEN + ".");
            TagSettings.setTimerLength(input.intValue());
        } else {
            context.getForWhom().sendRawMessage(ChatColor.DARK_GREEN + "Length set to " + ChatColor.YELLOW + input.intValue() + " minutes" + ChatColor.DARK_GREEN + ".");
            TagSettings.setTimerLength(input.intValue());
        }
        
        SettingsGUI.showGUI((Player) context.getForWhom());
        
        return END_OF_CONVERSATION;
    }
    
    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return ChatColor.YELLOW + "Please enter the new length in minutes:";
    }
    
    @Override
    protected @Nullable String getInputNotNumericText(@NotNull ConversationContext context, @NotNull String invalidInput) {
        return ChatColor.RED + "Not a number!";
    }
    
    @Override
    protected @Nullable String getFailedValidationText(@NotNull ConversationContext context, @NotNull Number invalidInput) {
        return ChatColor.RED + "Invalid number!";
    }
    
    @Override
    protected boolean isNumberValid(@NotNull ConversationContext context, @NotNull Number input) {
        return input.doubleValue() > 0;
    }
}

class XPrompt extends NumericPrompt {
    
    @Override
    protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull Number input) {
        context.getForWhom().sendRawMessage(ChatColor.DARK_GREEN + "X-coordinate set to " + ChatColor.YELLOW + input.intValue() + ChatColor.DARK_GREEN + ".");
        TagSettings.setSpawnX(input.intValue());
        return new YPrompt();
    }
    
    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return ChatColor.YELLOW + "Please enter the x-coordinate:";
    }
}

class YPrompt extends NumericPrompt {
    
    @Override
    protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull Number input) {
        if (input.intValue() == -1) {
            context.getForWhom().sendRawMessage(ChatColor.DARK_GREEN + "Y-coordinate will be the highest block.");
        } else {
            context.getForWhom().sendRawMessage(ChatColor.DARK_GREEN + "Y-coordinate set to " + ChatColor.YELLOW + input.intValue() + ChatColor.DARK_GREEN + ".");
        }
        TagSettings.setSpawnY(input.intValue());
        return new ZPrompt();
    }
    
    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return ChatColor.YELLOW + "Please enter the y-coordinate (or -1 for highest block):";
    }
}

class ZPrompt extends NumericPrompt {
    
    @Override
    protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull Number input) {
        context.getForWhom().sendRawMessage(ChatColor.DARK_GREEN + "Z-coordinate set to " + ChatColor.YELLOW + input.intValue() + ChatColor.DARK_GREEN + ".");
        TagSettings.setSpawnZ(input.intValue());
        SettingsGUI.showGUI((Player) context.getForWhom());
        return null;
    }
    
    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return ChatColor.YELLOW + "Please enter the z-coordinate:";
    }
}

class BorderPrompt extends NumericPrompt {
    
    @Override
    protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull Number input) {
        if (input.intValue() == 1) {
            context.getForWhom().sendRawMessage(ChatColor.DARK_GREEN + "Size set to " + ChatColor.YELLOW + input.intValue() + " block" + ChatColor.DARK_GREEN + ".");
            TagSettings.setBorderSize(input.intValue());
        } else {
            context.getForWhom().sendRawMessage(ChatColor.DARK_GREEN + "Size set to " + ChatColor.YELLOW + input.intValue() + " blocks" + ChatColor.DARK_GREEN + ".");
            TagSettings.setBorderSize(input.intValue());
        }
        
        SettingsGUI.showGUI((Player) context.getForWhom());
        
        return END_OF_CONVERSATION;
    }
    
    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return ChatColor.YELLOW + "Please enter the new size in blocks:";
    }
    
    @Override
    protected @Nullable String getInputNotNumericText(@NotNull ConversationContext context, @NotNull String invalidInput) {
        return ChatColor.RED + "Not a number!";
    }
    
    @Override
    protected @Nullable String getFailedValidationText(@NotNull ConversationContext context, @NotNull Number invalidInput) {
        return ChatColor.RED + "Invalid number!";
    }
    
    @Override
    protected boolean isNumberValid(@NotNull ConversationContext context, @NotNull Number input) {
        return input.doubleValue() > 0;
    }
}