package gbw.melange.events;

import gbw.melange.common.events.OnClick;
import gbw.melange.common.events.interactions.Button;
import gbw.melange.common.events.interactions.Key;
import gbw.melange.common.hooks.OnRender;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ClickService implements OnClick, OnRender {

    private final Map<Key, List<Runnable>> keyDownListeners = new ConcurrentHashMap<>();
    private final Set<Key> activeKeys = ConcurrentHashMap.newKeySet();
    private final Map<Key, List<Runnable>> whileKeyActiveListeners = new ConcurrentHashMap<>();
    private final Map<Key, List<Runnable>> keyUpListeners = new ConcurrentHashMap<>();
    private final Map<Key, List<Runnable>> keyTypedListeners = new ConcurrentHashMap<>();
    private final Map<Button, List<MouseHandler>> mouseDownListeners = new ConcurrentHashMap<>();
    private final Set<Button> activeButtons = ConcurrentHashMap.newKeySet();
    private final Map<Button, List<Runnable>> whileButtonActiveListeners = new ConcurrentHashMap<>();
    private final Map<Button, List<MouseHandler>> mouseUpListeners = new ConcurrentHashMap<>();
    private final Map<Button, List<MouseHandler>> mouseClickListeners = new ConcurrentHashMap<>();
    private final List<MouseHandler> mouseMoveListeners = new ArrayList<>();

    @Override
    public void mouse(Button button, MouseHandler function) {
        mouseClickListeners.computeIfAbsent(button, k -> new ArrayList<>()).add(function);
    }
    @Override
    public void mouseDown(Button button, MouseHandler function) {
        mouseDownListeners.computeIfAbsent(button, k -> new ArrayList<>())
                .add(function);
    }
    @Override
    public void mouseUp(Button button, MouseHandler function) {
        mouseUpListeners.computeIfAbsent(button, k -> new ArrayList<>()).add(function);
    }
    @Override
    public void mouseMove(MouseHandler moveAction) {
        mouseMoveListeners.add(moveAction);

    }
    @Override
    public void key(Key key, Runnable runnable) {
        keyTypedListeners.computeIfAbsent(key, k -> new ArrayList<>())
                .add(runnable);
    }
    @Override
    public void keyDown(Key key, Runnable runnable) {
        keyDownListeners.computeIfAbsent(key, k -> new ArrayList<>())
                .add(runnable);
    }
    @Override
    public void keyUp(Key key, Runnable runnable) {
        keyUpListeners.computeIfAbsent(key, k -> new ArrayList<>())
                .add(runnable);
    }

    @Override
    public void whileKeyHeld(Key key, Runnable runnable) {
        whileKeyActiveListeners.computeIfAbsent(key, k -> new ArrayList<>()).add(runnable);
    }

    @Override
    public void whileButtonHeld(Button button, Runnable runnable) {
        whileButtonActiveListeners.computeIfAbsent(button, k -> new ArrayList<>()).add(runnable);
    }

    public void _keyDown(Key key) {
        keyDownListeners.computeIfAbsent(key, k -> new ArrayList<>());
        keyDownListeners.get(key).forEach(Runnable::run);
        //TODO: Check consumption
        activeKeys.add(key);
    }
    public void _keyUp(Key key) {
        if(activeKeys.contains(key)){
            //IF contains at this point, it is a full typed event
            keyTypedListeners.computeIfAbsent(key, k -> new ArrayList<>()).forEach(Runnable::run);
            //TODO: Check consumption x2
            activeKeys.remove(key);
        }
        keyUpListeners.computeIfAbsent(key, k -> new ArrayList<>()).forEach(Runnable::run);
    }
    public void _mouseDown(Button button, int x, int y, int index){
        mouseDownListeners.computeIfAbsent(button, k -> new ArrayList<>()).forEach(handler -> handler.handle(x, y));
        //TODO: check consumption
        activeButtons.add(button);
    }
    public void _mouseMoved(int x, int y){
        mouseMoveListeners.forEach(handler -> handler.handle(x, y));
    }
    public void _mouseUp(Button button, int x, int y, int index){
        if(activeButtons.contains(button)){
            activeButtons.remove(button);
            mouseUpListeners.computeIfAbsent(button, k -> new ArrayList<>()).forEach(handler -> handler.handle(x, y));
        }
        mouseUpListeners.computeIfAbsent(button, k -> new ArrayList<>()).forEach(handler -> handler.handle(x, y));
    }
    public void _scrollInputInstance(float lr, float ud){
        //LR = left right
        //UD = up down

    }

    @Override
    public void onRender(double deltaT) {
        for(Key key : activeKeys){
            if(whileKeyActiveListeners.containsKey(key)){
                whileKeyActiveListeners.get(key).forEach(Runnable::run);
            }
        }
        for(Button button : activeButtons){
            if(whileButtonActiveListeners.containsKey(button)){
                whileButtonActiveListeners.get(button).forEach(Runnable::run);
            }
        }
    }
}