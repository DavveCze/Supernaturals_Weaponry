package com.darkhoundsstudios.supernaturalsweaponry.advancements.triggers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ParentDoneTrigger implements ICriterionTrigger<ParentDoneTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation("snweaponry", "parent_done");
    private final Map<PlayerAdvancements, ParentDoneTrigger.Listeners> listeners = Maps.<PlayerAdvancements, ParentDoneTrigger.Listeners>newHashMap();


    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(@NotNull PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.@NotNull Listener<ParentDoneTrigger.Instance> listener) {
        ParentDoneTrigger.Listeners consumeitemtrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (consumeitemtrigger$listeners == null) {
            consumeitemtrigger$listeners = new ParentDoneTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, consumeitemtrigger$listeners);
        }

        consumeitemtrigger$listeners.add(listener);
    }

    @Override
    public void removeListener(@NotNull PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.@NotNull Listener<ParentDoneTrigger.Instance> listener) {
        ParentDoneTrigger.Listeners consumeitemtrigger$listeners = this.listeners.get(playerAdvancementsIn);

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
    public ParentDoneTrigger.@NotNull Instance deserializeInstance(@NotNull JsonObject json, @NotNull JsonDeserializationContext context) {
        String t = "";
        try {
            t = json.get("parent_id").getAsString();
        } catch (IllegalArgumentException e) {
            throw new JsonSyntaxException("Unknown parent '" + json.get("parent").getAsString() + "'");
        }
        return new ParentDoneTrigger.Instance(t);
    }

    public static class Instance extends CriterionInstance {
        private final String type;

        public Instance(String type) {
            super(ParentDoneTrigger.ID);
            this.type = type;
        }

        public boolean test(String type) {
            return Objects.equals(this.type, type);
        }
    }

    public void trigger(ServerPlayerEntity player, String type) {
        ParentDoneTrigger.Listeners enterblocktrigger$listeners = this.listeners.get(player.getAdvancements());

        if (enterblocktrigger$listeners != null) {
            enterblocktrigger$listeners.trigger(type);
        }
    }

    static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<ParentDoneTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<ParentDoneTrigger.Instance>>newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<ParentDoneTrigger.Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<ParentDoneTrigger.Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger(String type) {
            List<Listener<ParentDoneTrigger.Instance>> list = null;

            for (ICriterionTrigger.Listener<ParentDoneTrigger.Instance> listener : this.listeners) {
                if (((ParentDoneTrigger.Instance) listener.getCriterionInstance()).test(type)) {
                    if (list == null) {
                        list = Lists.<ICriterionTrigger.Listener<ParentDoneTrigger.Instance>>newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null) {
                for (ICriterionTrigger.Listener<ParentDoneTrigger.Instance> listener1 : list) {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}