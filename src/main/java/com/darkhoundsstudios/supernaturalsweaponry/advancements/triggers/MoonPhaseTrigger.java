package com.darkhoundsstudios.supernaturalsweaponry.advancements.triggers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.JsonUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MoonPhaseTrigger implements ICriterionTrigger<MoonPhaseTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation("snweaponry", "moon_phase_change");
    private final Map<PlayerAdvancements, MoonPhaseTrigger.Listeners> listeners = Maps.<PlayerAdvancements, MoonPhaseTrigger.Listeners>newHashMap();


    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(@NotNull PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.@NotNull Listener<MoonPhaseTrigger.Instance> listener) {
        MoonPhaseTrigger.Listeners consumeitemtrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (consumeitemtrigger$listeners == null) {
            consumeitemtrigger$listeners = new MoonPhaseTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, consumeitemtrigger$listeners);
        }

        consumeitemtrigger$listeners.add(listener);
    }

    @Override
    public void removeListener(@NotNull PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.@NotNull Listener<MoonPhaseTrigger.Instance> listener) {
        MoonPhaseTrigger.Listeners consumeitemtrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (consumeitemtrigger$listeners != null) {
            consumeitemtrigger$listeners.remove(listener);

            if (consumeitemtrigger$listeners.isEmpty()) {
                this.listeners.remove(playerAdvancementsIn);
            }
        }
    }

    @Override
    public void removeAllListeners(@NotNull PlayerAdvancements playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    @Override
    public MoonPhaseTrigger.@NotNull Instance deserializeInstance(@NotNull JsonObject json, @NotNull JsonDeserializationContext context) {
        MoonPhase t = MoonPhase.UNKNOWN;
        try {
            t = MoonPhase.valueOf(json.get("moon_phase").getAsString());
        } catch (IllegalArgumentException e) {
            throw new JsonSyntaxException("Unknown moon phase '" + json.get("moon_phase").getAsString() + "'");
        }
        return new MoonPhaseTrigger.Instance(t);
    }

    public static class Instance extends CriterionInstance {
        private final MoonPhase type;

        public Instance(MoonPhase type) {
            super(MoonPhaseTrigger.ID);
            this.type = type;
        }

        public boolean test(MoonPhase type) {
            return this.type == type;
        }
    }

    public void trigger(ServerPlayerEntity player, MoonPhase type) {
        MoonPhaseTrigger.Listeners enterblocktrigger$listeners = this.listeners.get(player.getAdvancements());

        if (enterblocktrigger$listeners != null) {
            enterblocktrigger$listeners.trigger(type);
        }
    }

    static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener<MoonPhaseTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<MoonPhaseTrigger.Instance>>newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<MoonPhaseTrigger.Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<MoonPhaseTrigger.Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger(MoonPhase type) {
            List<ICriterionTrigger.Listener<MoonPhaseTrigger.Instance>> list = null;

            for (ICriterionTrigger.Listener<MoonPhaseTrigger.Instance> listener : this.listeners) {
                if (((MoonPhaseTrigger.Instance) listener.getCriterionInstance()).test(type)) {
                    if (list == null) {
                        list = Lists.<ICriterionTrigger.Listener<MoonPhaseTrigger.Instance>>newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null) {
                for (ICriterionTrigger.Listener<MoonPhaseTrigger.Instance> listener1 : list) {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }

    public enum MoonPhase {
        FULL_MOON,
        WANING_GIBBOUS,
        THIRD_QUARTER,
        WANING_CRESCENT,
        NEW_MOON,
        WAXING_CRESCENT,
        FIRST_QUARTER,
        WAXING_GIBBOUS,
        UNKNOWN
    }
}
