package net.alkalus.mod.mekstrip;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import mekanism.common.MekanismBlocks;
import mekanism.common.MekanismItems;
import mekanism.common.recipe.RecipeHandler.Recipe;
import mekanism.common.util.RecipeUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CommonProxy {
	
	public static class ClientProxy extends CommonProxy {
		
	}

	public CommonProxy() {
	}

	public void preInit(final FMLPreInitializationEvent e) {

	}

	public void init(final FMLInitializationEvent e) {
	}

	public void postInit(final FMLPostInitializationEvent e) {
	}

	public void serverStarting(final FMLServerStartingEvent e) {
	}

	public void onLoadComplete(FMLLoadCompleteEvent event) {
		//Remove Recipes
		List<Field> aItems = getFields(MekanismItems.class);
		List<Field> aBlocks = getFields(MekanismBlocks.class);
		removeRecipeFromMap(aItems, 0);
		removeRecipeFromMap(aBlocks, 1);		
		for (Recipe r : Recipe.values()) {
			emptyMekRecipeMap(r);
		}		
	}

	public void removeRecipeFromMap(List<Field> aData, int type) {
		for (Field f : aData) {
			if (f != null) {
				f.setAccessible(true);
				Object y;
				try {
					y = f.get(null);	
					if (y != null) {
						if (y instanceof Item) {							
							for (short meta=0;meta<Short.MAX_VALUE;meta++) {								
								if (type == 0) {
									boolean b = RecipeUtils.removeRecipes(new ItemStack((Item) y, 1, meta));									
								}
								else {
									boolean b = RecipeUtils.removeRecipes(new ItemStack((Block) y, 1, meta));										
								}															
							}														
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}
			}
		}
	}
	
	public void emptyMekRecipeMap(Recipe aMap) {
		try {
			Field aRecipeField = Class.forName("mekanism.common.recipe.RecipeHandler.Recipe").getDeclaredField("recipes");
			aRecipeField.setAccessible(true);
			HashMap recipes;
			recipes = (HashMap) aRecipeField.get(aMap);
			if (recipes != null) {
				recipes.clear();
			}
		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | IllegalArgumentException
				| IllegalAccessException e) {
		}
	}
	
	public void registerNetworkStuff() {
	}

	private List<Field> getFields(Class t) {
        List<Field> fields = new ArrayList<>();
        Class clazz = t;
        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

}
