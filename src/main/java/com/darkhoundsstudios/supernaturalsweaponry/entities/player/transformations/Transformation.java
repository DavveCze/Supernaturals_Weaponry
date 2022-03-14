package com.darkhoundsstudios.supernaturalsweaponry.entities.player.transformations;

import com.darkhoundsstudios.supernaturalsweaponry.effects.ModEffects;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Transformation{
    final String type;
    static boolean isWerewolf, isVampire, isHunter;
    int level;


    public Transformation(String type, int level) {
        switch (type)
        {
            case "Werewolf": isWerewolf = true; Werewolf.init(level); break;
            case "Hunter": isHunter = true; break;
            case "Vampire": isVampire = true; break;
        }
        this.level = level;
        this.type = type;
    }

    public String getTransType(){
        return this.type;
    }

    public int getLevel(){
        return this.level;
    }

    public static List<AttributeModifier> getModifiers(){
        if(isWerewolf)
            return Werewolf.getModifiers();
        return null;
    }

    public boolean increaseLevel(float playerXP) {
        if (isWerewolf) {
            if (this.level >= 1 && this.level < Werewolf.levelCap)
                return checkLevel(playerXP, Werewolf.levelCap, this.level);
        }
        return false;
    }

    private boolean checkLevel(float playerXP, int lvlCap, int currLvl) {
        boolean x = false;
        for (int i = lvlCap - 1; i >= currLvl; i--) {
            if (isWerewolf) {
                if (playerXP >= Werewolf.getXpNeeded(i) && currLvl < lvlCap) {
                    if (i - currLvl > 0)
                        this.level += i - currLvl;
                    else
                        this.level++;
                    x = true;
                    Werewolf.init(this.level);
                }
            }
        }
        return x;
    }


    public void ApplyEffects(PlayerEntity player) {
        if (isWerewolf) {
            if (level <= 4) {
                Werewolf.setEffects(0);
            }
            else if (level <= 6) {
                Werewolf.setEffects(1);
            }
            else if (level <= 10) {
                Werewolf.setEffects(2);
            }
            player.addPotionEffect(Werewolf.Wolf_Gift);
        }
    }


    static class Werewolf{
        private static List<AttributeModifier> modifiers;
        public static EffectInstance Wolf_Gift = new EffectInstance(ModEffects.WOLF_GIFT.get(),300, 0, true,false);

        private final static String name = "Werewolf";
        public final static int levelCap = 10;
        private final static int[] xpNeeded = new int[]{150,400,750,1150,1700,2400,3600,5700,8500};
        public static int healthBoost, strengthBoost, resistanceBoost;

        public static void init(int level){
            setBoosts(level);
            modifiers = setModifiers();
        }

        public static void setEffects(int amplifier){

            Wolf_Gift = new EffectInstance(ModEffects.WOLF_GIFT.get(),300, amplifier, true,false,true);
        }

        private static void setBoosts(int level)
        {
            switch (level){
                case 1:
                    healthBoost = 0;
                    strengthBoost = 2;
                    resistanceBoost = 1;
                    break;
                case 2:
                    healthBoost = 2;
                    strengthBoost = 2;
                    resistanceBoost = 2;
                    break;
                case 3:
                    healthBoost = 4;
                    strengthBoost = 3;
                    resistanceBoost = 2;
                    break;
                case 4:
                    healthBoost = 6;
                    strengthBoost = 3;
                    resistanceBoost = 2;
                    break;
                case 5:
                    healthBoost = 6;
                    strengthBoost = 3;
                    resistanceBoost = 3;
                    break;
                case 6:
                    healthBoost = 8;
                    strengthBoost = 3;
                    resistanceBoost = 4;
                    break;
                case 7:
                    healthBoost = 10;
                    strengthBoost = 3;
                    resistanceBoost = 4;
                    break;
                case 8:
                    healthBoost = 10;
                    strengthBoost = 3;
                    resistanceBoost = 5;
                    break;
                case 9:
                    healthBoost = 10;
                    strengthBoost = 4;
                    resistanceBoost = 5;
                    break;
                case 10:
                    healthBoost = 10;
                    strengthBoost = 4;
                    resistanceBoost = 8;
                    break;
                default:
                    healthBoost = 0;
                    strengthBoost = 0;
                    resistanceBoost = 0;
                    break;
            }
        }

        private static List<AttributeModifier> setModifiers() {
            ArrayList<AttributeModifier> _modifiers = new ArrayList<>();
            _modifiers.add(new AttributeModifier(UUID.randomUUID(), "Supernaturality-Health", healthBoost, AttributeModifier.Operation.ADDITION));
            _modifiers.add(new AttributeModifier(UUID.randomUUID(), "Supernaturality-Strength", strengthBoost, AttributeModifier.Operation.ADDITION));
            _modifiers.add(new AttributeModifier(UUID.randomUUID(), "Supernaturality-Resistance", resistanceBoost, AttributeModifier.Operation.ADDITION));
            return _modifiers;
        }

        public static List<AttributeModifier> getModifiers() {
            return modifiers;
        }

        public static int getXpNeeded(int level){
            return xpNeeded[level-1];
        }
    }
}