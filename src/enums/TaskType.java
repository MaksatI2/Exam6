package enums;

public enum TaskType {
    REGULAR("#FFFFFF"),
    URGENT("#FF5733"),
    WORK("#33FF57"),
    SHOPPING("#3357FF"),
    OTHER("#FF33A6");

    private final String color;


    TaskType(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
