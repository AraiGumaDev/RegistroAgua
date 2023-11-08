package com.mateo_luis;

import com.mateo_luis.repository.RegisterUsingFileRepositoryImpl;
import com.mateo_luis.service.ConsumptionRegisterService;
import com.mateo_luis.service.ConsumptionRegisterServiceImpl;

public class ApplicationRunner {
    public static void main(String[] args) {
  
   ConsumptionRegisterService consumptionRegisterService = new ConsumptionRegisterServiceImpl(new RegisterUsingFileRepositoryImpl());
   consumptionRegisterService.ordenamientoBurbuja();
   
   consumptionRegisterService.mediaDeConsumoPorVivienda();
   consumptionRegisterService.mediaDeConsumoPorHabitante(consumptionRegisterService.totalAguaConsumida());
   consumptionRegisterService.mediaDeHabitantesPorVivienda(consumptionRegisterService.totalHabitantes());
   consumptionRegisterService.medianaDeHabitantes();
   consumptionRegisterService.modaDeHabitantesPorHogar();
   consumptionRegisterService.registrosDesactualizados();

  }
}
