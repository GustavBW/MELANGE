package gbw.melange.common.elementary.space;

import gbw.melange.common.annotations.View;

public record SpaceLayerEntry(ISpace spaceInstance, int layer, View.FocusPolicy focusPolicy) {
}
