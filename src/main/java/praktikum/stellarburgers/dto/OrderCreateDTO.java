package praktikum.stellarburgers.dto;

import java.util.List;

public class OrderCreateDTO {
    private List<String> ingredients;
    public OrderCreateDTO(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public OrderCreateDTO() {
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
