package dev.ua.ikeepcalm.teamupnow.database.entities.source;

public enum GameENUM {

    AMONG_US,
    APEX_LEGENDS,
    ARK_SURVIVAL,
    BACKROOMS,
    B4B,
    BATTLEFIELD_2042,
    BATTLEFIELD_V,
    BIGFOOT,
    BLACK_DESERT,
    BLITZ,
    BRAWL_STARS,
    CALL_OF_DUTY_WARZONE,
    CLASH_ROYALE,
    CSGO,
    DAYZ,
    DEAD_BY_DAYLIGH,
    DECEIT,
    DEEP_ROCK_GALACTIC,
    DEMONOLOGIST,
    DESTINY2,
    DOTA2,
    DONT_STARVE,
    DYINGLIGHT,
    DYINGLIGHT2,
    ELDEN_RING,
    EURO_TRACK_SIMULATOR,
    FACTORIO,
    FALLOUT_4,
    FARCRY_5,
    FARCRY_6,
    FOREST,
    FORTNITE,
    GHOST_WATCHERS,
    GENSHIN_IMPACT,
    GTFO,
    GTA_ONLINE,
    GREEN_HELL,
    GROUNDED,
    HEARTHSTONE,
    HOI4,
    IT_TAKES_TWO,
    LEAGUE_OF_LEGENDS,
    MINECRAFT,
    MEET_YOUR_MAKER,
    NO_MANS_SKY,
    OVERWATCH2,
    PAYDAY2,
    PHASMOPHOBIA,
    PROJECT_ZOMBOID,
    PUBG,
    R6_SIEGE,
    RAFT,
    READY_OR_NOT,
    RDR2,
    ROBLOX,
    ROCKET_LEAGUE,
    RUST,
    SEA_OF_THIEVES,
    SHATTERLINE,
    SMITE,
    SONS_OF_FOREST,
    SONS_OF_THE_FOREST,
    STARDEW_VALLEY,
    SUBNAUTICA_NITROX,
    TERRARIA,
    TES_ONLINE,
    THE_FOREST,
    THE_OUTLAST_TRIALS,
    VALHEIM,
    VALORANT,
    VOIDTRAIN,
    WARFRAME,
    WILD_RIFT,
    WORLD_OF_TANKS,
    WORLD_OF_WARCRAFT,
    WORLD_OF_WARSHIPS;



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
            case BATTLEFIELD_2042 -> "Battlefield 2042";
            case BATTLEFIELD_V -> "Battlefield V";
            case BIGFOOT -> "Bigfoot";
            case DECEIT -> "Deceit";
            case WORLD_OF_TANKS -> "World of Tanks";
            case WORLD_OF_WARSHIPS -> "World of Warships";
            case BLACK_DESERT -> "Black Desert";
            case BLITZ -> "WoT Blitz";
            case ROCKET_LEAGUE -> "Rocket League";
            case DAYZ -> "DayZ";
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
            case FACTORIO -> "Factorio";
            case FALLOUT_4 -> "Fallout 4";
            case FARCRY_5 -> "Far Cry 5";
            case FARCRY_6 -> "Far Cry 6";
            case HEARTHSTONE -> "Hearthstone";
            case HOI4 -> "Hearts of Iron 4";
            case IT_TAKES_TWO -> "It Takes Two";
            case MEET_YOUR_MAKER -> "Meet Your Maker";
            case NO_MANS_SKY -> "No Man's Sky";
            case PAYDAY2 -> "Payday 2";
            case R6_SIEGE -> "Rainbow 6 Siege";
            case SMITE -> "Smite";
            case SONS_OF_THE_FOREST -> "Sons of the Forest";
            case SUBNAUTICA_NITROX -> "Subnautica (Nitox)";
            case TES_ONLINE -> "Skyrim Online";
            case THE_FOREST -> "The Forest";
            case THE_OUTLAST_TRIALS -> "The Outlast Trials";
        };
    }
}
