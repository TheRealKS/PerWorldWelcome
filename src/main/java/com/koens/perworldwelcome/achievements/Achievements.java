package com.koens.perworldwelcome.achievements;

public enum Achievements {
    OPEN_INVENTORY("Taking inventory", "Press 'E' to open your inventory."),
    MINE_WOOD("Getting wood", "Attack a tree until a block of wood pops out"),
    BUILD_WORKBENCH("Benchmarking", "Craft a workbench with four blocks of planks"),
    BUILD_PICKAXE("Time to Mine!", "Use planks and sticks to make a pickaxe"),
    BUILD_FURNACE("Hot Topic", "Construct a furnace out of eight stone blocks"),
    ACQUIRE_IRON("Acquire Hardware", "Smelt an iron ingot"),
    BUILD_HOE("Time to Farm!", "Use planks and sticks to make a hoe"),
    MAKE_BREAD("Bake Bread!", "Turn wheat into bread"),
    BAKE_CAKE("The Lie", "Wheat, sugar, milk and eggs!"),
    BUILD_BETTER_PICKAXE("Getting an Upgrade", "Construct a better pickaxe"),
    COOK_FISH("Delicious Fish", "Catch and cook fish!"),
    ON_A_RAIL("On A Rail", "Travel by minecart at least 1 km from where you started"),
    BUILD_SWORD("Time to Strike!", "Use planks and sticks to make a sword"),
    KILL_ENEMY("Monster Hunter", "Attack and destroy a monster"),
    KILL_COW("Cow Tipper", "Harvest some leather"),
    FLY_PIG("When Pigs Fly", "Fly a pig off a cliff"),
    SNIPE_SKELETON("Sniper Duel", "Kill a skeleton with an arrow from more than 50 meters"),
    GET_DIAMONDS("DIAMONDS!", "Acquire diamonds with your iron tools"),
    NETHER_PORTAL("We Need to Go Deeper", "Build a portal to the nether"),
    GHAST_RETURN("Return to Sender", "Destroy a Ghast with a fireball"),
    GET_BLAZE_ROD("Into Fire", "Relieve a Blaze of its rod"),
    BREW_POTION("Local Brewery", "Brew a potion"),
    END_PORTAL("The End?", "Locate the End"),
    THE_END("The End.", "Defeat the Ender Dragon"),
    ENCHANTMENTS("Enchanter", "Use a book, obsidian and diamonds to construct an enchantment table"),
    OVERKILL("Overkill", "Deal nine hearts of damage in a single hit"),
    BOOKCASE("Librarian", "Build some bookshelves to improve your enchantment table"),
    EXPLORE_ALL_BIOMES("Adventuring Time", "Discover all biomes"),
    SPAWN_WITHER("The Beginning?", "Spawn the Wither"),
    KILL_WITHER("The Beginning.", "Kill the Wither"),
    FULL_BEACON("Beaconater", "Create a full beacon"),
    BREED_COW("Repopulation", "Breed two cows with wheat"),
    DIAMONDS_TO_YOU("Diamonds to you!", "Throw diamonds at another player."),
    OVERPOWERED("Overpowered", "Eat a Notch apple");

    private final String name;
    private final String clientequivalent;

    Achievements(final String name, final String clientequivalent) {
        this.name = name;
        this.clientequivalent = clientequivalent;
    }

    public String getName() {
        return name;
    }

    public String getClientequivalent() {
        return clientequivalent;
    }

}
