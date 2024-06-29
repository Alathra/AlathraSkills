package io.github.alathra.alathraskills.skills;

import com.github.milkdrinkers.colorparser.ColorParser;
import net.kyori.adventure.text.Component;
import org.bukkit.util.ChatPaginator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkillDescriptionUtil {

    public static List<Component> descriptionLineBreaker(String description, int lineLength, String miniMessageColor) {
        List<Component> componentList = new ArrayList<>();
        splitText(description, lineLength).forEach(str -> componentList.add(ColorParser.of(miniMessageColor + str).build()));
        return componentList;
    }

    public static List<String> splitText(String text, int lineLength){
        List<String> messages = new ArrayList<>();
        String[] data = text.split(" ");
        StringBuilder nextString = new StringBuilder();
        for(String s : data){
            if(s.length() > lineLength){
                continue;
            }

            if(nextString.chars().count() + s.chars().count() > lineLength){
                messages.add(nextString.toString());
                nextString = new StringBuilder(s + " ");
            } else{
                nextString.append(s).append(" ");
            }
        }
        messages.add(nextString.toString());
        return messages;
    }

}
