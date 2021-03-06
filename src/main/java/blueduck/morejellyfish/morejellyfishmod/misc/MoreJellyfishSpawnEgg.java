package blueduck.morejellyfish.morejellyfishmod.misc;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.function.Supplier;

public class MoreJellyfishSpawnEgg extends SpawnEggItem {

    public static final ArrayList<MoreJellyfishSpawnEgg> JELLYFISHING_SPAWN_EGGS = new ArrayList<>();

    public int primColor;
    public int secColor;

    public DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior() {
        public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
            entitytype.spawn(source.getWorld(), stack, null, source.getBlockPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
            stack.shrink(1);
            return stack;
        }
    };

    Supplier<? extends EntityType<?>> type;
    public MoreJellyfishSpawnEgg(Supplier<? extends EntityType<?>> typeIn, int primaryColorIn, int secondaryColorIn, Properties builder) {
        super(null, primaryColorIn, secondaryColorIn, builder);
        type = typeIn;
        primColor = primaryColorIn;
        secColor = secondaryColorIn;
        JELLYFISHING_SPAWN_EGGS.add(this);
    }

    @Override
    public EntityType<?> getType(@Nullable CompoundNBT p_208076_1_) {
        return type.get();
    }


    public static void doDispenserSetup() {
        for (final SpawnEggItem egg : JELLYFISHING_SPAWN_EGGS) {
            SpawnEggItem.EGGS.put(egg.getType(null), egg);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getColor(int tintIndex) {
        return tintIndex == 0 ? this.primColor : this.secColor;
    }


}
