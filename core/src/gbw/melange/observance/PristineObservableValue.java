package gbw.melange.observance;

public class PristineObservableValue<T> {

    private EqualityFunction<T> equalityFunction;
    private T value;
    private IPristineOnChangeConsumer<T> onChange;

    public PristineObservableValue(T initialValue, EqualityFunction<T> equalityFunction, IPristineOnChangeConsumer<T> onChange){
        this.value = initialValue;
        this.equalityFunction = equalityFunction;
        this.onChange = onChange;
    }
    public T get(){
        return value;
    }
    public void set(T newer){
        if(equalityFunction.test(value, newer)){
            onChange.accept(value, newer);
        }
        this.value = newer;
    }
    public void changeEqualityCondition(EqualityFunction<T> predicate){
        this.equalityFunction = predicate;
    }
    public void changeOnChange(IPristineOnChangeConsumer<T> onChange){
        this.onChange = onChange;
    }

}
