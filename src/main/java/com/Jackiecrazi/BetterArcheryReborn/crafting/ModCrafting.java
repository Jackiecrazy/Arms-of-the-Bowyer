package com.Jackiecrazi.BetterArcheryReborn.crafting;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

import com.Jackiecrazi.BetterArcheryReborn.Items.*;
import com.Jackiecrazi.BetterArcheryReborn.Items.arrows.DrillArrow;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModCrafting {
	public static void initCrafting(){
		List recipes=CraftingManager.getInstance().getRecipeList();
		GameRegistry.addRecipe(new ItemStack(ModItems.Bowstring), "n", "n", "n", 'n', Items.string);
		GameRegistry.addRecipe(new ItemStack(ModItems.Longbow,1,OreDictionary.WILDCARD_VALUE), "a  ", "bc ", "a  ", 'b', Items.string, 'a', Items.stick, 'c', new ItemStack(Items.bow,1,OreDictionary.WILDCARD_VALUE));
		GameRegistry.addRecipe(new ItemStack(ModItems.Comp), new Object[]{
			"ab ",
			"acd",
			"aef",
			'a',Items.string,
			'b',Items.bone,
			'c',Items.leather,
			'd',Items.stick,
			'e',new ItemStack(ModItems.onemisc,1,1),
			'f',new ItemStack(ModItems.misc,1,0)
		});
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.Recurve,1,OreDictionary.WILDCARD_VALUE),new Object[]{new ItemStack(Items.bow,1,OreDictionary.WILDCARD_VALUE)});
		GameRegistry.addRecipe(new ItemStack(ModItems.Yumi,1,OreDictionary.WILDCARD_VALUE), "abb", "acb", "ab ", 'b', Items.reeds, 'a', Items.string, 'c', Items.leather);
		GameRegistry.addRecipe(new ItemStack(ModItems.FireArrow), "h","i",'h',new ItemStack(Items.coal,1,0),'i',new ItemStack(Items.arrow));
		GameRegistry.addRecipe(new ItemStack(ModItems.FireArrow), "h","i",'h',new ItemStack(Items.coal,1,1),'i',new ItemStack(Items.arrow));
		GameRegistry.addRecipe(new ItemStack(ModItems.FireArrow,2), "h  ","ii ",'h',new ItemStack(Items.blaze_powder),'i',new ItemStack(Items.arrow));
		GameRegistry.addRecipe(new ItemStack(ModItems.ImpactArrow), "ha ","i",'h',new ItemStack(Items.gunpowder),'a',new ItemStack(Blocks.redstone_torch),'i',new ItemStack(Items.arrow));
		GameRegistry.addRecipe(new ItemStack(ModItems.TimedArrow), "hh ","ia",'h',new ItemStack(Items.gunpowder),'a',new ItemStack(Items.string),'i',new ItemStack(Items.arrow));
		GameRegistry.addRecipe(new ItemStack(ModItems.EnderArrow), "h","i",'h',new ItemStack(Items.ender_pearl),'i',new ItemStack(Items.arrow));
		GameRegistry.addRecipe(new ItemStack(ModItems.TorchArrow), "h","i",'h',new ItemStack(Blocks.torch),'i',new ItemStack(Items.arrow));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.DerpArrow,1), new Object[]{new ItemStack(Items.arrow,1)});
		GameRegistry.addRecipe(new ItemStack(ModItems.DrillArrow,3), 
				"fff",
				" pt",
				"aaa",
				'p', Blocks.sticky_piston,
				'f', Items.flint,
				't', Blocks.redstone_torch,
				'a', Items.arrow);
		GameRegistry.addRecipe(new ItemStack(ModItems.DrillArrow,1),"h","i",'h',new ItemStack(Items.flint),'i',new ItemStack(ModItems.DrillArrow,1,((DrillArrow)ModItems.DrillArrow).setBroken(0,true)));
		recipes.add(new RecipesSplittingArrow());
		recipes.add(new PotArrowRecipe());
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.onemisc,1,0),
				"f",
				"b",
				'f', Items.rotten_flesh,
				'b', new ItemStack(Items.potionitem));
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(ModItems.onemisc,1,0), new ItemStack(ModItems.onemisc,1,1), 0.2F);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.misc,1,0), new Object[]{new ItemStack(Items.rotten_flesh)});
	}
}
