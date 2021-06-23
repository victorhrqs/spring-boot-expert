package br.com.udemy.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private String error;
}
