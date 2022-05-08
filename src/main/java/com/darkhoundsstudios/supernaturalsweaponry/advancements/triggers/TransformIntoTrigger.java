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
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TransformIntoTrigger implements ICriterionTrigger<TransformIntoTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation("snweaponry", "transform_into");
    private final Map<PlayerAdvancements, TransformIntoTrigger.Listeners> listeners = Maps.<PlayerAdvancements, TransformIntoTrigger.Listeners>newHashMap();


    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(@NotNull PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.@NotNull Listener<TransformIntoTrigger.Instance> listener) {
        TransformIntoTrigger.Listeners consumeitemtrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (consumeitemtrigger$listeners == null) {
            consumeitemtrigger$listeners = new TransformIntoTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, consumeitemtrigger$listeners);
        }

        consumeitemtrigger$listeners.add(listener);
    }

    @Override
    public void removeListener(@NotNull PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.@NotNull Listener<TransformIntoTrigger.Instance> listener) {
        TransformIntoTrigger.Listeners consumeitemtrigger$listeners = this.listeners.get(playerAdvancementsIn);

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
    public TransformIntoTrigger.@NotNull Instance deserializeInstance(@NotNull JsonObject json, @NotNull JsonDeserializationContext context) {
        TransformIntoTrigger.Type t = TransformIntoTrigger.Type.UNKNOWN;
        try {
            t = TransformIntoTrigger.Type.valueOf(json.get("transformation").getAsString());
        } catch (IllegalArgumentException e) {
            throw new JsonSyntaxException("Unknown transformation '" + json.get("transformation").getAsString() + "'");
        }
        return new TransformIntoTrigger.Instance(t);
    }

    public static class Instance extends CriterionInstance {
        private final TransformIntoTrigger.Type type;

        public Instance(TransformIntoTrigger.Type type) {
            super(TransformIntoTrigger.ID);
            this.type = type;
        }

        public boolean test(TransformIntoTrigger.Type type) {
            return this.type == type;
        }
    }

    public void trigger(ServerPlayerEntity player, TransformIntoTrigger.Type type) {
        TransformIntoTrigger.Listeners enterblocktrigger$listeners = this.listeners.get(player.getAdvancements());

        if (enterblocktrigger$listeners != null) {
            enterblocktrigger$listeners.trigger(type);
        }
    }

    static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<TransformIntoTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<TransformIntoTrigger.Instance>>newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<TransformIntoTrigger.Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<TransformIntoTrigger.Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger(TransformIntoTrigger.Type type) {
            List<Listener<TransformIntoTrigger.Instance>> list = null;

            for (ICriterionTrigger.Listener<TransformIntoTrigger.Instance> listener : this.listeners) {
                if (((TransformIntoTrigger.Instance) listener.getCriterionInstance()).test(type)) {
                    if (list == null) {
                        list = Lists.<ICriterionTrigger.Listener<TransformIntoTrigger.Instance>>newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null) {
                for (ICriterionTrigger.Listener<TransformIntoTrigger.Instance> listener1 : list) {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }

    public enum Type {
        WEREWOLF,
        VAMPIRE,
        HUNTER,
        UNKNOWN
    }
}