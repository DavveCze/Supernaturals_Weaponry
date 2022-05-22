package com.darkhoundsstudios.supernaturalsweaponry.entities.player.transformations;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.*;

public class Transformation{
    //drží typy transformací a jejich level
    final String  type;

    static boolean isWerewolf, isVampire, isHunter;
    int level;

    public boolean isFullmoon;



    public Transformation(LivingEntity entity,String type, int level) {
        switch (type)
        {
            case "Werewolf": isWerewolf = true; Werewolf.init(entity); break;
            case "Hunter": isHunter = true; break;
            case "Vampire": isVampire = true; break;
        }
        this.level = level;
        this.type = type;
    }


    //vrací transformaci
    public String getTransType(){
        return this.type;
    }

    public int getLevel(){
        return this.level;
    }

    //vrací list modifierů určité transformace při určitém levelu
  

    //zvyšuje level v závislosti na počet xp získaných hráčem
    public boolean increaseLevel(float playerXP) {
        if (isWerewolf) {
            if (this.level >= 1 && this.level < Werewolf.levelCap)
                return checkLevel(playerXP, this.level);
        }
        return false;
    }

    private boolean checkLevel(float playerXP, int currLvl) {
        boolean x = false;
        for (int i = Werewolf.levelCap - 1; i >= currLvl; i--) {
            if (isWerewolf) {
                if (playerXP >= Werewolf.getXpNeeded(i)) {
                    if (i - currLvl > 0)
                        this.level += i - currLvl;
                    else
                        this.level++;
                    x = true;
                }
            }
        }
        return x;
    }

    //Aplikuje speciální effekty transformací závislé na úrovni
    public void ApplyEffects(PlayerEntity player) {
        if (isWerewolf) {
            Werewolf.applyAll(player);
        }
    }

    public void addSkill(Skill skill, boolean constant, boolean passive){
        if (isWerewolf) {
            Werewolf.addSkill(skill,constant,passive);
        }
    }
    public boolean useSkill(LivingEntity entity, Skill skill){
        if (isWerewolf) {
           return Werewolf.useSkill(entity,skill);
        }
        return false;
    }
    public void disableSkill(LivingEntity entity, Skill skill){
        if (isWerewolf) {
            Werewolf.disableSkill(entity,skill);
        }
    }

    public TransformState getState() {
        if (isWerewolf)
            return Werewolf.state;
        return TransformState.Human;
    }
    public void setState(TransformState _state) {
        if (isWerewolf)
            Werewolf.state = _state;
    }
    public ArrayList<Integer> getSkillIds(){
        if (isWerewolf){
            return Werewolf.getSkillIds();
        }
        return null;
    }

    public void setSkills(int[] ids){
        if(isWerewolf){
            Werewolf.setSkillIds(ids);
        }
    }
    public void addBasicSkills(){
        if(isWerewolf){
            Werewolf.addBasicSkills();
        }
    }
    public boolean tryAddSkill(Skill _skill){
        if(isWerewolf) {
            for (Skill skill : Werewolf.all_skills) {
                if (skill.equals(_skill))
                    return false;
            }
        }
        return true;
    }
    public boolean removeSkill(Skill _skill){
        return Werewolf.removeSkill(_skill);
    }

    public enum TransformState{
        Human,
        Animal,
        Beast
    }

    //statická třída vlkodlaka, drží všechny důležité informace o něm
    static class Werewolf{
        //list modifierů
        //stálý effekt

        //Advancement things
        public static boolean checkBite = false, isFullMoon = false, unleashedBeast = false;
        public static int killedDuringNight, supernaturalKilled, killedTotal, huntersKilled,
                            masterHuntersKilled, wolfTamed, chieftainsKilled;
        public static List<Skill> passive_const_skillList = new ArrayList<>();
        public static List<Skill> passive_skillList = new ArrayList<>();
        public static List<Skill> active_skillList = new ArrayList<>();
        public static List<Skill> all_skills = new ArrayList<>();
        public static TransformState state;

        //drží vnitřní informace
        private final static String name = "Werewolf";
        public final static int levelCap = 10;
        private final static int[] xpNeeded = new int[]{150,400,750,1150,1700,2400,3600,5700,8500};

        //inicializuje nového vlkodlaka či při jeho level upu
        public static void init(LivingEntity entity){
            for (Skill skill: passive_skillList) {
                skill.onUse(entity);
            }
        }

        public static void addBasicSkills(){
            if(tryAddSkill(Skills.Werewolf_Skills.Wolf_form))
                addSkill(Skills.Werewolf_Skills.Wolf_form,Skills.Werewolf_Skills.Wolf_form.parameters().get(0),Skills.Werewolf_Skills.Wolf_form.parameters().get(1));
            if(tryAddSkill(Skills.Werewolf_Skills.Regeneration))
                addSkill(Skills.Werewolf_Skills.Regeneration,Skills.Werewolf_Skills.Regeneration.parameters().get(0),Skills.Werewolf_Skills.Regeneration.parameters().get(1));
            if(tryAddSkill(Skills.Werewolf_Skills.Poison_Reduction_I))
                addSkill(Skills.Werewolf_Skills.Poison_Reduction_I,Skills.Werewolf_Skills.Poison_Reduction_I.parameters().get(0),Skills.Werewolf_Skills.Poison_Reduction_I.parameters().get(1));
        }
        
        public static void applyAll(LivingEntity entity){
            for (Skill skill : passive_const_skillList) {
                skill.onUse(entity);
                skill.cooldown();
            }
            for (Skill skill : active_skillList) {
                if(state == TransformState.Animal || state == TransformState.Beast)
                    skill.applyEffects(entity);
                skill.cooldown();
            }
            for (Skill skill : passive_skillList) {
                if(skill == Skills.Werewolf_Skills.Poison_Reduction_I | skill == Skills.Werewolf_Skills.Poison_Reduction_II | skill == Skills.Werewolf_Skills.Wither_Reduction)
                    skill.onUse(entity);
                skill.cooldown();
            }
        }
        public static void applyPassive(LivingEntity entity){
            for (Skill skill: passive_skillList) {
                skill.onUse(entity);
            }
        }
        public static boolean tryAddSkill(Skill _skill) {
            for (Skill skill : Werewolf.all_skills) {
                if (skill.equals(_skill))
                    return false;
            }
            return true;
        }

        public static void addSkill(Skill skill, boolean constant, boolean passive){
            if(constant && passive)
                passive_const_skillList.add(skill);
            else if(passive)
                passive_skillList.add(skill);
            else if(!constant && !passive){
                active_skillList.add(skill);
            }
            all_skills.add(skill);
        }
        public static boolean useSkill(LivingEntity entity, Skill _skill){
            for (Skill skill:active_skillList) {
                if(skill == _skill){
                    skill.onUse(entity);
                    return true;
                }
            }
            return false;
        }
        public static void disableSkill(LivingEntity entity, Skill _skill){
            for (Skill skill : active_skillList) {
                if(skill == _skill){
                    skill.onDisable(entity);
                }
            }
        }
        public static boolean removeSkill(Skill _skill){
            if(all_skills.contains(_skill)) {
                if (_skill.parameters().get(0) && _skill.parameters().get(1))
                    passive_const_skillList.remove(_skill);
                else if (_skill.parameters().get(1))
                    passive_skillList.remove(_skill);
                else if (!_skill.parameters().get(0) && !_skill.parameters().get(1)) {
                    active_skillList.remove(_skill);
                }
                return true;
            }
            return false;
        }

        public static ArrayList<Integer> getSkillIds() {
            ArrayList<Integer> ids = new ArrayList<>();
            for (Skill skill : all_skills) {
                ids.add(skill.getID());
            }

            System.out.println(ids.size());
            return ids;
        }
        public static void setSkillIds(int[] ids) {
            active_skillList.clear();
            passive_skillList.clear();
            passive_const_skillList.clear();
            all_skills.clear();
            System.out.println(ids.length);
            addBasicSkills();
            System.out.println(active_skillList.size() + ", " + passive_skillList.size() + ", " + passive_const_skillList.size());
            for (int id : ids) {
                for (Skill skill : Skills.Werewolf_Skills.skill_list) {
                    if(id == skill.getID()){
                        if(tryAddSkill(skill)) {
                            addSkill(skill, skill.parameters().get(0), skill.parameters().get(1));
                        }
                        break;
                    }
                }
            }
        }

        //vrací zpět počet xp nutný pro další level up
        public static int getXpNeeded(int level){
            return xpNeeded[level-1];
        }
    }
}
