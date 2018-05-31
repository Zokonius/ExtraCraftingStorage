package com.Zoko061602.ExtraCraftingStorage;

import appeng.block.AEBaseItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockExCraftingStorage extends AEBaseItemBlock {

	public ItemBlockExCraftingStorage(Block b) {
		super(b);
	}


	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName()+"_"+stack.getItemDamage();
	}

	@Override
    public int getMetadata(int meta){
        return meta;
    }

}
