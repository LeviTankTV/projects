package GameV2;

public abstract class Effect {
    protected int duration;
    protected String description; // Добавлен для описания эффекта

    public Effect(int duration, String description) { // Добавлен параметр description
        this.duration = duration;
        this.description = description;
    }

    public abstract void apply(Entity target);

    public abstract void remove(Entity target);

    public abstract void update(Entity target);

    // Getter для описания эффекта
    public String getDescription() {
        return description;
    }
}