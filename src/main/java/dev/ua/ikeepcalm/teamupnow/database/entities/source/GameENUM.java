package dev.ua.ikeepcalm.teamupnow.database.entities.source;

public enum GameENUM {
    CSGO, VALORANT,
    DESTINY2, FORTNITE,
    APEX_LEGENDS, OVERWATCH2,


    MINECRAFT, TERRARIA,
    VALHEIM, ARK_SURVIVAL,
    RUST, WARFRAME,

    DONT_STARVE, GROUNDED,
    SEA_OF_THIEVES, DEEP_ROCK_GALACTIC,
    FOREST, SONS_OF_FOREST,

    ROBLOX, GENSHIN_IMPACT,
    LEAGUE_OF_LEGENDS, DOTA2,
    WILD_RIFT, WORLD_OF_WARCRAFT,

    PHASMOPHOBIA, BACKROOMS,
    GHOST_WATCHERS, DEMONOLOGIST,
    BIGFOOT, DECEIT,

    DYINGLIGHT, DYINGLIGHT2,
    WORLD_OF_TANKS, WORLD_OF_WARSHIPS,
    BLITZ, ROCKET_LEAGUE,

    DEAD_BY_DAYLIGH, AMONG_US,
    GTA_ONLINE, CALL_OF_DUTY_WARZONE,
    ELDEN_RING, PUBG,

    CLASH_ROYALE, BRAWL_STARS;



    public String getButtonCallback(){
        return "profile-games-" + name();
    }

    public String getButtonText() {
        return switch (this) {
            case MINECRAFT -> "Minecraft";
            case CSGO -> "CS:GO";
            case VALORANT -> "Valorant";
            case DESTINY2 -> "Destiny 2";
            case GENSHIN_IMPACT -> "Genshin Impact";
            case LEAGUE_OF_LEGENDS -> "League of Legends";
            case DOTA2 -> "Dota 2";
            case WILD_RIFT -> "LoL: Wild RIft";
            case WORLD_OF_WARCRAFT -> "World of Warcraft";
            case PHASMOPHOBIA -> "Phasmophobia";
            case DYINGLIGHT -> "Dying Light";
            case DYINGLIGHT2 -> "Dying Light 2";
            case FORTNITE -> "Fortnite";
            case APEX_LEGENDS -> "Apex Legends";
            case OVERWATCH2 -> "Overwatch 2";
            case TERRARIA -> "Terraria";
            case VALHEIM -> "Valheim";
            case ARK_SURVIVAL -> "Ark Survival";
            case RUST -> "Rust";
            case WARFRAME -> "Warframe";
            case DONT_STARVE -> "Don't starve";
            case GROUNDED -> "Grounded";
            case SEA_OF_THIEVES -> "Sea of Thieves";
            case DEEP_ROCK_GALACTIC -> "Deep Rock Galactic";
            case FOREST -> "Forest";
            case SONS_OF_FOREST -> "Sons of Forest";
            case ROBLOX -> "Roblox";
            case BACKROOMS -> "Backrooms";
            case GHOST_WATCHERS -> "Ghost Watchers";
            case DEMONOLOGIST -> "Demonologist";
            case BIGFOOT -> "Bigfoot";
            case DECEIT -> "Deceit";
            case WORLD_OF_TANKS -> "World of Tanks";
            case WORLD_OF_WARSHIPS -> "World of Warships";
            case BLITZ -> "WoT Blitz";
            case ROCKET_LEAGUE -> "Rocket League";
            case DEAD_BY_DAYLIGH -> "Dead by Daylight";
            case AMONG_US -> "Among Us";
            case GTA_ONLINE -> "GTA Online";
            case CALL_OF_DUTY_WARZONE -> "Call of Duty: Warzone";
            case ELDEN_RING -> "Elden Ring";
            case PUBG -> "PUBG";
            case CLASH_ROYALE -> "Clash Royale";
            case BRAWL_STARS -> "Brawl Stars";
        };
    }
}
