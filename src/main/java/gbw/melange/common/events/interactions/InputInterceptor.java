package gbw.melange.common.events.interactions;

import gbw.melange.common.events.OnClick;

public interface InputInterceptor {
    
    void apply(OnClick onClick);

}
