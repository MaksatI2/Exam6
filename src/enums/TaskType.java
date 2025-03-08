package enums;

public enum TaskType {
    REGULAR("#FFFFFF"),   // Обычная задача (белый)
    URGENT("#FF5733"),    // Срочное дело (красный)
    WORK("#33FF57"),      // Работа (зеленый)
    SHOPPING("#3357FF"),  // Покупки (синий)
    OTHER("#FF33A6");     // Прочее (розовый)

    private final String color;

    // Конструктор для цвета
    TaskType(String color) {
        this.color = color;
    }

    // Геттер для цвета
    public String getColor() {
        return color;
    }
}
