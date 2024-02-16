package de.neuefische.spring_data_challenges.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDto {
    String name;
    int age;
    String job;
}
