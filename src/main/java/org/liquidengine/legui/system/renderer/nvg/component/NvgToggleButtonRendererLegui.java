package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.color.ColorUtil;
import org.liquidengine.legui.component.ToggleButton;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.renderer.nvg.NvgLeguiComponentRenderer;

import static org.liquidengine.legui.system.renderer.nvg.NvgLeguiRenderer.renderBorder;
import static org.liquidengine.legui.system.renderer.nvg.NvgLeguiRenderer.renderIcon;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.nvgRestore;
import static org.lwjgl.nanovg.NanoVG.nvgSave;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgToggleButtonRendererLegui extends NvgLeguiComponentRenderer<ToggleButton> {

    @Override
    protected void renderComponent(ToggleButton toggleButton, LeguiContext context, long nanovg) {
        createScissor(nanovg, toggleButton);
        {
            Vector2f pos  = toggleButton.getScreenPosition();
            Vector2f size = toggleButton.getSize();

            nvgSave(nanovg);
            // render background
            renderBackground(nanovg, toggleButton, pos, size, context);
            renderBorder(toggleButton, context);
        }
        resetScissor(nanovg);

        nvgRestore(nanovg);
    }

    private void renderBackground(long nvg, ToggleButton agui, Vector2f pos, Vector2f size, LeguiContext context) {
        boolean  focused         = agui.isFocused();
        boolean  hovered         = agui.isHovered();
        boolean  pressed         = agui.isPressed();
        boolean  toggled         = agui.isToggled();
        Vector4f backgroundColor = new Vector4f(toggled ? agui.getToggledBackgroundColor() : agui.getBackgroundColor());

        Icon bgIcon = toggled ? agui.getTogglededBackgroundIcon() : agui.getBackgroundIcon();
        Icon image;
        if (!focused && !hovered && !pressed) {
            image = bgIcon;
        } else if (hovered && !pressed) {
            image = (image = agui.getHoveredBackgroundIcon()) == null ? bgIcon : image;
        } else if (pressed) {
            image = (image = agui.getPressedBackgroundIcon()) == null ? bgIcon : image;
        } else {
            image = (image = agui.getFocusedBackgroundIcon()) == null ? bgIcon : image;
        }

        drawRectangle(nvg, backgroundColor, pos, size);
        if (hovered) {
            if (!pressed) {
                Vector4f opp = ColorUtil.oppositeBlackOrWhite(backgroundColor);
                opp.w = 0.3f;
                drawRectangle(nvg, opp, pos, size);
            } else {
                Vector4f opp = ColorUtil.oppositeBlackOrWhite(backgroundColor);
                opp.w = 0.6f;
                drawRectangle(nvg, opp, pos, size);
            }
        }

        if (image != null) {
            renderIcon(image, agui, context);
        }
    }
}