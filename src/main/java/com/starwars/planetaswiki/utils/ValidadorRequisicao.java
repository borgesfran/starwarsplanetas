package com.starwars.planetaswiki.utils;

import com.starwars.planetaswiki.exception.ServiceException;
import com.starwars.planetaswiki.model.Planeta;

public interface ValidadorRequisicao {

    static void validarPlaneta(Planeta planeta){
        if(planeta.getNome().isEmpty() || planeta.getNome().length() < 2)
            throw new ServiceException("O nome do planeta não deve ser vazio ou ter menos de 2 caracteres.");

        if(planeta.getClima().isEmpty())
            throw new ServiceException("O clima do planeta não deve ser vazio.");

        if(planeta.getTerreno().isEmpty())
            throw new ServiceException("O terreno do planeta não deve ser vazio.");
    }

}
