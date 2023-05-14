package dev.ua.ikeepcalm.teamupnow.database.entities.source;

public enum GameENUM {

    APEX_LEGENDS, ARK_SURVIVAL,
    AMONG_US, BACKROOMS,
    B4B, BIGFOOT,

    BLACK_DESERT, BLITZ,
    BRAWL_STARS, CALL_OF_DUTY_WARZONE,
    CLASH_ROYALE, CSGO,

    DEAD_BY_DAYLIGH, DECEIT,
    DEEP_ROCK_GALACTIC, DEMONOLOGIST,
    DESTINY2, DOTA2,

    DONT_STARVE, DYINGLIGHT,
    DYINGLIGHT2, ELDEN_RING,
    EURO_TRACK_SIMULATOR, FOREST,

    FORTNITE, GHOST_WATCHERS,
    GENSHIN_IMPACT, GTFO,
    GTA_ONLINE, GREEN_HELL,

    GROUNDED, LEAGUE_OF_LEGENDS,
    MINECRAFT, OVERWATCH2,
    PHASMOPHOBIA, PROJECT_ZOMBOID,

    PUBG, RDR2,
    RAFT, READY_OR_NOT,
    ROBLOX, ROCKET_LEAGUE,

    RUST, SEA_OF_THIEVES,
    SHATTERLINE, SONS_OF_FOREST,
    STARDEW_VALLEY, TERRARIA,

    VALHEIM, VALORANT,
    VOIDTRAIN, WARFRAME,
    WILD_RIFT, WORLD_OF_TANKS,

    WORLD_OF_WARCRAFT, WORLD_OF_WARSHIPS;



    public String getButtonCallback(){
        return "profile-games-" + name();
    }

    public String getEditCallback(){
        return "edit-profile-games-" + name();
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
            case B4B -> "Back 4 Blood";
            case BIGFOOT -> "Bigfoot";
            case DECEIT -> "Deceit";
            case WORLD_OF_TANKS -> "World of Tanks";
            case WORLD_OF_WARSHIPS -> "World of Warships";
            case BLACK_DESERT -> "Black Desert";
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
            case EURO_TRACK_SIMULATOR -> "ETS 2";
            case GTFO -> "GTFO";
            case GREEN_HELL -> "Green Hell";
            case PROJECT_ZOMBOID -> "Project Zomboid";
            case RDR2 -> "Red Dead Redemption 2";
            case RAFT -> "Raft";
            case READY_OR_NOT -> "Ready or Not";
            case SHATTERLINE -> "Shatterline";
            case STARDEW_VALLEY -> "Stardew Valley";
            case VOIDTRAIN -> "Voidtrain";
        };
    }
}
