package io.github.alathra.alathraskills.utility;

import java.util.List;
import java.util.Random;

public class RandomUtil {
    public static <T> T getRandomElementInList(List<T> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
