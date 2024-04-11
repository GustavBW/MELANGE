package gbw.melange.common.errors;

public class ServiceLoadingFailure extends RuntimeException{
    public ServiceLoadingFailure(String msg){
        super(msg);
    }
}
