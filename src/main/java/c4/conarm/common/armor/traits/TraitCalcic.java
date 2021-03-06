/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.common.armor.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.tinkering.TinkersArmor;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitCalcic extends AbstractArmorTrait {

    public TraitCalcic() {
        super("calcic", 0xffffff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onItemUse (LivingEntityUseItemEvent.Start evt) {
        if (evt.getItem().getItem() instanceof ItemBucketMilk && evt.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
            for (ItemStack armor : player.getArmorInventoryList()) {
                if (armor.getItem() instanceof TinkersArmor) {
                    if (!ToolHelper.isBroken(armor)) {
                        if (TinkerUtil.hasTrait(TagUtil.getTagSafe(armor), this.getModifierIdentifier())) {
                            ModifierTagHolder modtag = ModifierTagHolder.getModifier(armor, getModifierIdentifier());
                            Data data = modtag.getTagData(Data.class);
                            data.milk = true;
                            modtag.save();
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void finishMilk (LivingEntityUseItemEvent.Finish evt) {
        if (evt.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
            int level = 0;
            for (ItemStack armor : player.getArmorInventoryList()) {
                if (armor.getItem() instanceof TinkersArmor) {
                    if (!ToolHelper.isBroken(armor)) {
                        if (TinkerUtil.hasTrait(TagUtil.getTagSafe(armor), this.getModifierIdentifier())) {
                            ModifierTagHolder modtag = ModifierTagHolder.getModifier(armor, getModifierIdentifier());
                            Data data = modtag.getTagData(Data.class);
                            if (data.milk) {
                                ArmorHelper.healArmor(armor, 10, player, EntityLiving.getSlotForItemStack(armor).getIndex());
                                level++;
                                data.milk = false;
                                modtag.save();
                            }
                        }
                    }
                }
            }
            if (level > 0) {
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100 * level, level - 1));
            }
        }
    }

    public static class Data extends ModifierNBT {

        boolean milk;

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            milk = tag.getBoolean("milk");
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setBoolean("milk", milk);
        }
    }
}
