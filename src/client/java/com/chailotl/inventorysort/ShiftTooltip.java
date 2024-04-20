package com.chailotl.inventorysort;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShiftTooltip extends Tooltip
{
	private final Text altContent;
	@Nullable
	private List<OrderedText> altLines;

	public ShiftTooltip(Text content, Text altContent)
	{
		super(content, content);

		this.altContent = altContent;
	}

	@Override
	public List<OrderedText> getLines(MinecraftClient client)
	{
		if (lines == null)
		{
			lines = wrapLines(client, content);
			altLines = wrapLines(client, altContent);
		}

		return Screen.hasShiftDown() ? altLines : lines;
	}
}