package gbw.melange.welcomeapp.discovery;

import gbw.melange.common.builders.IElementBuilder;
import gbw.melange.common.elementary.IElementRenderer;
import gbw.melange.common.elementary.ISpace;
import org.springframework.beans.factory.annotation.Autowired;


public class CanItFindThis implements ISpace {

    //This should cause a problem
    public CanItFindThis(int hi){}

    public CanItFindThis(){}

    @Override
    public IElementBuilder createElement() {
        return null;
    }

    @Override
    public void render(IElementRenderer renderer) {

    }

    @Override
    public void dispose() {

    }
}















