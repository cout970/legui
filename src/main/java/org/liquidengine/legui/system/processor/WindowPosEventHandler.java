package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.WindowPosEvent;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemWindowPosEvent;

import java.util.List;

/**
 * This class instance used to handle {@link WindowPosEvent}.
 */
public class WindowPosEventHandler extends AbstractSystemEventHandler<SystemWindowPosEvent> {

    /**
     * This method used to handle {@link WindowPosEvent}.
     *
     * @param event   event to be processed.
     * @param layer   target event layer.
     * @param context context.
     *
     * @return true if this event was handled and should not be handled more.
     */
    @Override
    protected boolean handle(SystemWindowPosEvent event, Layer layer, Context context, Frame frame) {
        pushEvent(layer.getContainer(), event, context, frame);
        return false;
    }

    /**
     * Used to push {@link WindowPosEvent} instance of {@link org.liquidengine.legui.event.Event} to {@link
     * org.liquidengine.legui.listener.EventProcessor}.
     *
     * @param component component for which should be created {@link WindowPosEvent}
     * @param event     event to push.
     * @param context   context.
     */
    private void pushEvent(Component component, SystemWindowPosEvent event, Context context, Frame frame) {
        if (!(component.isVisible())) return;
        context.getEventProcessor().pushEvent(new WindowPosEvent(component, context, frame, event.xpos, event.ypos));
        if (component instanceof Container) {
            List<Component> childs = ((Container) component).getChilds();
            for (Component child : childs) {
                pushEvent(child, event, context, frame);
            }
        }
    }
}
