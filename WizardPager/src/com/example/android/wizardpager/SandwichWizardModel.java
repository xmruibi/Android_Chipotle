package com.example.android.wizardpager;

import android.content.Context;
import co.juliansuarez.libwizardpager.wizard.model.AbstractWizardModel;
import co.juliansuarez.libwizardpager.wizard.model.BranchPage;
import co.juliansuarez.libwizardpager.wizard.model.CustomerInfoPage;
import co.juliansuarez.libwizardpager.wizard.model.MultipleFixedChoicePage;
import co.juliansuarez.libwizardpager.wizard.model.NumberPage;
import co.juliansuarez.libwizardpager.wizard.model.PageList;
import co.juliansuarez.libwizardpager.wizard.model.SingleFixedChoicePage;
import co.juliansuarez.libwizardpager.wizard.model.TextPage;

public class SandwichWizardModel extends AbstractWizardModel {
	public SandwichWizardModel(Context context) {
		super(context);
	}

	@Override
	protected PageList onNewRootPageList() {
        return new PageList(
                new BranchPage(this, "Order type")
                        .addBranch("Burrito",
                						
                				new SingleFixedChoicePage(this, "Filling").setChoices("Chicken",
                						"Steak", "Barbacoa", "Carnitas", "Veggie")
                						.setRequired(true),

                				new SingleFixedChoicePage(this, "Rice").setChoices(
                						"No Rice", "White Rice", "Brown Rice")
                						.setRequired(true),

                				new SingleFixedChoicePage(this, "Beans").setChoices(
                						"No Beans", "Black Beans", "Pinto Beans")
                						.setRequired(true),

                				new MultipleFixedChoicePage(this, "Toppings").setChoices(
                						"Fajita Veggies", "Sour Cream", "Cheese", 
                						"Guacamole", "Lettuce"),
                						
                                new SingleFixedChoicePage(this, "Chips type")
                                        .setChoices("No Chips", "Chips and Guacamole", "Chips")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Salsa")
                                        .setChoices("No Salsa", "Roasted Chili-Corn Salsa", 
                                        		"Fresh Tomato", "Tomatillo-Green Chili Salsa")
                                        .setValue("No Salsa"),

                				new MultipleFixedChoicePage(this, "Drinks").setChoices(
                						"No Drink", "Fountain Soda Large", "Fountain Soda Small", 
                						"Bottled Water", "Nantucket Nectar-Apple",
                						"Nantucket Nectar-Peach Orange", "Nantucket Nectar-Pomegranate Cherry")
                        )
                        .addBranch("Burrito Bowl",
        						
                				new SingleFixedChoicePage(this, "Filling").setChoices("Chicken",
                						"Steak", "Barbacoa", "Carnitas", "Veggie")
                						.setRequired(true),

                				new SingleFixedChoicePage(this, "Rice").setChoices(
                						"No Rice", "White Rice", "Brown Rice")
                						.setRequired(true),

                				new SingleFixedChoicePage(this, "Beans").setChoices(
                						"No Beans", "Black Beans", "Pinto Beans")
                						.setRequired(true),

                				new MultipleFixedChoicePage(this, "Toppings").setChoices(
                						"Fajita Veggies", "Sour Cream", "Cheese", 
                						"Guacamole", "Lettuce"),
                						
                                        new SingleFixedChoicePage(this, "Chips type")
                                        .setChoices("No Chips", "Chips and Guacamole", "Chips")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Salsa")
                                        .setChoices("No Salsa", "Roasted Chili-Corn Salsa", 
                                        		"Fresh Tomato", "Tomatillo-Green Chili Salsa")
                                        .setValue("No Salsa"),

                        				new MultipleFixedChoicePage(this, "Drinks").setChoices(
                        						"No Drink", "Fountain Soda Large", "Fountain Soda Small", 
                        						"Bottled Water", "Nantucket Nectar-Apple",
                        						"Nantucket Nectar-Peach Orange", "Nantucket Nectar-Pomegranate Cherry")
                                )
                        .addBranch("Tacos",
        						
                				new SingleFixedChoicePage(this, "Filling").setChoices("Chicken",
                						"Steak", "Barbacoa", "Carnitas", "Veggie")
                						.setRequired(true),

                				new SingleFixedChoicePage(this, "Rice").setChoices(
                						"No Rice", "White Rice", "Brown Rice")
                						.setRequired(true),

                				new SingleFixedChoicePage(this, "Beans").setChoices(
                						"No Beans", "Black Beans", "Pinto Beans")
                						.setRequired(true),

                						new MultipleFixedChoicePage(this, "Toppings").setChoices(
                        						"Fajita Veggies", "Sour Cream", "Cheese", 
                        						"Guacamole", "Lettuce"),
                						
                                        new SingleFixedChoicePage(this, "Chips type")
                                        .setChoices("No Chips", "Chips and Guacamole", "Chips")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Salsa")
                                        .setChoices("No Salsa", "Roasted Chili-Corn Salsa", 
                                        		"Fresh Tomato", "Tomatillo-Green Chili Salsa")
                                        .setValue("No Salsa"),

                        				new MultipleFixedChoicePage(this, "Drinks").setChoices(
                        						"No Drink", "Fountain Soda Large", "Fountain Soda Small", 
                        						"Bottled Water", "Nantucket Nectar-Apple",
                        						"Nantucket Nectar-Peach Orange", "Nantucket Nectar-Pomegranate Cherry")
                                )
                        .addBranch("Salad",
        						
                				new SingleFixedChoicePage(this, "Filling").setChoices("Chicken",
                						"Steak", "Barbacoa", "Carnitas", "Veggie")
                						.setRequired(true),

                				new SingleFixedChoicePage(this, "Rice").setChoices(
                						"No Rice", "White Rice", "Brown Rice")
                						.setRequired(true),

                				new SingleFixedChoicePage(this, "Beans").setChoices(
                						"No Beans", "Black Beans", "Pinto Beans")
                						.setRequired(true),

                						new MultipleFixedChoicePage(this, "Toppings").setChoices(
                        						"Fajita Veggies", "Sour Cream", "Cheese", 
                        						"Guacamole", "Lettuce"),
                						
                                        new SingleFixedChoicePage(this, "Chips type")
                                        .setChoices("No Chips", "Chips and Guacamole", "Chips")
                                        .setRequired(true),

                                new SingleFixedChoicePage(this, "Salsa")
                                        .setChoices("No Salsa", "Roasted Chili-Corn Salsa", 
                                        		"Fresh Tomato", "Tomatillo-Green Chili Salsa")
                                        .setValue("No Salsa"),

                        				new MultipleFixedChoicePage(this, "Drinks").setChoices(
                        						"No Drink", "Fountain Soda Large", "Fountain Soda Small", 
                        						"Bottled Water", "Nantucket Nectar-Apple",
                        						"Nantucket Nectar-Peach Orange", "Nantucket Nectar-Pomegranate Cherry")
                                )
                        
                        .setRequired(true),
                        new TextPage(this, "Comments").setRequired(true).setRequired(true),

                new CustomerInfoPage(this, "Your info")
                        .setRequired(true)
        );
    }
}
