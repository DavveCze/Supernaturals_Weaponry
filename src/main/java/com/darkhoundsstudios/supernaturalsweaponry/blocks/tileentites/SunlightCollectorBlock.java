package com.darkhoundsstudios.supernaturalsweaponry.blocks.tileentites;

import com.darkhoundsstudios.supernaturalsweaponry.container.SunlightCollectorContainer;
import com.darkhoundsstudios.supernaturalsweaponry.tileentity.ModTileEntities;
import com.darkhoundsstudios.supernaturalsweaponry.tileentity.other.SunlightCollector;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;


public class SunlightCollectorBlock extends Block {

    public SunlightCollectorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.SUNLIGHT_COLLECTOR_TILE.get().create();
    }


    @Nullable
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);

            if(!player.isCrouching()) {
                if (tileEntity instanceof SunlightCollector) {
                    INamedContainerProvider containerProvider = createContainerProvider(worldIn, pos);

                    NetworkHooks.openGui(((ServerPlayerEntity) player), containerProvider, tileEntity.getPos());
                } else {
                    throw new IllegalStateException("Our Container provider is missing!");
                }
            }
        }

        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    private INamedContainerProvider createContainerProvider(World worldIn, BlockPos pos) {
        return new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.tutorialmod.lightning_channeler");
            }

            @Nullable
            @Override
            public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                return new SunlightCollectorContainer(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
    }
}
