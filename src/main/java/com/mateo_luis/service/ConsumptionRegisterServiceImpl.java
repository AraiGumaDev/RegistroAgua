package com.mateo_luis.service;
import com.mateo_luis.model.Register;
import com.mateo_luis.repository.RegisterInMemoryRepositoryImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConsumptionRegisterServiceImpl implements ConsumptionRegisterService {

    RegisterInMemoryRepositoryImpl registro = new RegisterInMemoryRepositoryImpl();
    ArrayList<Register> registrosDeConsumoDeAgua = registro.llenarRegistroDeConsumoDeAgua();

    public ConsumptionRegisterServiceImpl(RegisterInMemoryRepositoryImpl registerInMemoryRepositoryImpl) {
    }

    @Override
    public void ordenamientoBurbuja() {
        boolean ordenamientoBurbuja;
        for (int i = 0; i < registrosDeConsumoDeAgua.size() - 1; i++) {
            ordenamientoBurbuja = false;

            for (int j = 0; j < registrosDeConsumoDeAgua.size() - i - 1; j++) {
                if (registrosDeConsumoDeAgua.get(j).numeroHabitantes() > registrosDeConsumoDeAgua.get(j + 1).numeroHabitantes()) {
                    Register temp = registrosDeConsumoDeAgua.get(j);
                    registrosDeConsumoDeAgua.set(j, registrosDeConsumoDeAgua.get(j + 1));
                    registrosDeConsumoDeAgua.set(j + 1, temp);
                    ordenamientoBurbuja = true;
                }
            }
            if (!ordenamientoBurbuja) {
                break;
            }
        }
    }

    @Override
    public Double mediaDeConsumoPorVivienda() {
        System.out.println("----Media de consumo por vivienda----");
        Double aguaConsumidaTotal = totalAguaConsumida();
        mediaDeConsumoPorHabitante(aguaConsumidaTotal);
        Double promedioPorVivienda = aguaConsumidaTotal / registrosDeConsumoDeAgua.size();
        System.out.println("En total las " + registrosDeConsumoDeAgua.size() + " viviendas listadas consumieron: "
                + aguaConsumidaTotal + " Litros");
        System.out.println("Lo que da un promedio por vivienda de: " + promedioPorVivienda + " Litros");
        return promedioPorVivienda;
    }

    @Override
    public Double totalAguaConsumida(){
        Double aguaConsumidaTotal = 0D;
        for (Register registroConsumoAgua : registrosDeConsumoDeAgua) {
            aguaConsumidaTotal = aguaConsumidaTotal + registroConsumoAgua.consumoAguaMes();
        }
    return aguaConsumidaTotal;
    }

    @Override
    public Double mediaDeConsumoPorHabitante(Double aguaConsumidaTotal) {
        System.out.println("\n----Media de consumo por habitante----");
        Integer numeroHabitantes = totalHabitantes();
        mediaDeHabitantesPorVivienda(numeroHabitantes);
        Double promedioPorHabitante = Math.round((aguaConsumidaTotal / numeroHabitantes)*100.0)/100.0;
        System.out.println("Habiendo " + numeroHabitantes + " habitantes listados, el promedio de consumo por persona sería: "
                + promedioPorHabitante + " Litros");
        return promedioPorHabitante;
    }

    @Override
    public Integer totalHabitantes(){
        Integer habitantesTotal=0;
        for (Register registroConsumoAgua : registrosDeConsumoDeAgua) {
            habitantesTotal = habitantesTotal + registroConsumoAgua.numeroHabitantes();
        }
        return habitantesTotal;
    }

    @Override
    public void mediaDeHabitantesPorVivienda(Integer totalDeHabitantes) {
        System.out.println("\n----Media de habitantes por vivienda----");
        Double promediodeHabitantes = totalDeHabitantes / Double.valueOf(registrosDeConsumoDeAgua.size());
        System.out.println("En la comunidad hay un promedio de " + promediodeHabitantes + " habitantes por vivienda");
    }

    @Override
    public void medianaDeHabitantes() {
        System.out.println("\n----Mediana de habitantes----");
      /*Ya que no hemos visto cómo convertir entre clases, opté por manejar la variable
      medianaHabitantes como un String para facilitarme las cosas*/
        String medianaHabitantes = "";

        if (registrosDeConsumoDeAgua.size() % 2 == 0) {
            int posicion1 = registrosDeConsumoDeAgua.size() / 2 - 1;
            int posicion2 = registrosDeConsumoDeAgua.size() / 2;
            medianaHabitantes = "" + (((registrosDeConsumoDeAgua.get(posicion1).numeroHabitantes() * 1.0) + registrosDeConsumoDeAgua.get(posicion2).numeroHabitantes()) / 2);
        } else {
            int posicion = (registrosDeConsumoDeAgua.size() - 1) / 2;
            medianaHabitantes = "" + registrosDeConsumoDeAgua.get(posicion).numeroHabitantes();
        }
        System.out.println("La mediana de habitantes por hogar es: " + medianaHabitantes);
        
    }

    @Override
    public void modaDeHabitantesPorHogar() {
        System.out.println("\n----La moda de habitantes por hogar----");
        int maximoNumRepeticiones = 0;
        List<Integer> moda = new ArrayList<>();

        for (int i = 0; i < registrosDeConsumoDeAgua.size(); i++) {
            int contadorRepeticiones = 0;
            for (int j = 0; j < registrosDeConsumoDeAgua.size(); j++) {
                if (registrosDeConsumoDeAgua.get(i).numeroHabitantes() == registrosDeConsumoDeAgua.get(j).numeroHabitantes()) {
                    contadorRepeticiones++;
                }
            }
            if (contadorRepeticiones > maximoNumRepeticiones) {
                moda.clear();
                moda.add(registrosDeConsumoDeAgua.get(i).numeroHabitantes());
                maximoNumRepeticiones = contadorRepeticiones;
            } else if (contadorRepeticiones == maximoNumRepeticiones && !moda.contains(registrosDeConsumoDeAgua.get(i).numeroHabitantes())) {
                moda.add(registrosDeConsumoDeAgua.get(i).numeroHabitantes());
            }
        }

        System.out.print("La moda de habitantes por hogar es: ");
        for (int i = 0; i < moda.size(); i++) {
            System.out.print(moda.get(i));
            if (i < moda.size() - 2) {
                System.out.print(" ");
            } else if (i == moda.size() - 2) {
                System.out.print(" y ");
            }
        }
        System.out.println(" que se repitieron " + maximoNumRepeticiones + " veces.");

    }

    @Override
    public void registrosDesactualizados() {
        System.out.println("\n----Registros desactualizados----");
        System.out.println("Las siguientes viviendas tienen registros anteriores a el 2023-08-14:");
        for (Register registroConsumoAgua : registrosDeConsumoDeAgua){
        /*Aquí use el metodo isBefore ya que con el operador < no me dejaba comparar,
        me gustaria saber si hay otra forma de hacerlo*/
            if (registroConsumoAgua.fechaUltimaMedicion().isBefore(LocalDate.parse("14/08/2023", DateTimeFormatter.ofPattern("dd/MM/uuuu")))) {
                System.out.println(registroConsumoAgua.direccion()+"\túltimo registro: "+registroConsumoAgua.fechaUltimaMedicion());
            }
        }
    }


}

