package Models;

import com.fasterxml.jackson.annotation.JsonInclude;

import Models.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Ingredient {
    private Integer id;
    private String name;
}
