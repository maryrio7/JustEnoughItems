package mezz.jei.gui.elements;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;

import mezz.jei.Internal;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.gui.textures.Textures;

/**
 * A small gui button that has an {@link IDrawable} instead of a string label.
 */
public class GuiIconButtonSmall extends Button {
	private final IDrawable icon;

	public GuiIconButtonSmall(int x, int y, int widthIn, int heightIn, IDrawable icon, Button.IPressable pressable) {
		super(x, y, widthIn, heightIn, "", pressable);
		this.icon = icon;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			Minecraft minecraft = Minecraft.getInstance();
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableAlphaTest();
			boolean hovered = isMouseOver(mouseX, mouseY);
			Textures textures = Internal.getTextures();
			DrawableNineSliceTexture texture = textures.getButtonForState(this.active, hovered);
			texture.draw(this.x, this.y, this.width, this.height);
			this.renderBg(minecraft, mouseX, mouseY);

			int color = 14737632;
			if (packedFGColor != 0) {
				color = packedFGColor;
			} else if (!this.active) {
				color = 10526880;
			} else if (hovered) {
				color = 16777120;
			}
			if ((color & -67108864) == 0) {
				color |= -16777216;
			}

			float red = (float) (color >> 16 & 255) / 255.0F;
			float blue = (float) (color >> 8 & 255) / 255.0F;
			float green = (float) (color & 255) / 255.0F;
			float alpha = (float) (color >> 24 & 255) / 255.0F;
			GlStateManager.color4f(red, blue, green, alpha);

			double xOffset = x + (height - this.icon.getWidth()) / 2.0;
			double yOffset = y + (width - this.icon.getHeight()) / 2.0;
			GlStateManager.pushMatrix();
			GlStateManager.translated(xOffset, yOffset, 0);
			this.icon.draw();
			GlStateManager.popMatrix();
		}
	}
}
