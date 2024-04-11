package gbw.melange.model.node;
//Technically an interface is just an abstract class with only abstract methods and static fields if any.
public abstract class ICenterNode<T extends IGLObject> implements IProvidingNode<T>, IDemandingNode<T> {

}
