package pl.wylegala.library;

import java.util.Objects;

public enum Action {
    DEL("del"), ADD("add"), LIST("list");

    private final String name;

    Action(String name) {
        this.name = name;
    }

    public static Action of(String name) {
        for (Action action : values()) {
            if (Objects.equals(action.name, name)) {
                return action;
            }
        }
        throw new IllegalArgumentException("Unknow action: " + name);
    }
}
