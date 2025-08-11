package org.example;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


public class CalculoIMCTest {

    @Nested
    public class TestesDeEntradaDeDados {
        @Test
        public void calculoIMC_Valido() {
            double imc = CalculoIMC.calcularIMC(70,1.75);
            String classificacao = CalculoIMC.classificarIMC(imc);
            assertEquals("Saudável", classificacao);
        }
        @Test
        public void calculoIMC_Invalido_NumeroNegativo() {
            assertThrows(IllegalArgumentException.class, () -> CalculoIMC.calcularIMC(-10,1.75) );
        }
        @Test
        public void calculoIMC_Invalido_Letras() {
            String inputSimulado = "abc\n70\n1.75\n";
            ByteArrayInputStream in = new ByteArrayInputStream(inputSimulado.getBytes());

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;

            System.setIn(in);
            System.setOut(new PrintStream(outContent));

            double imc = CalculoIMC.getUsuarioIMC();

            System.setOut(originalOut);

            assertTrue(outContent.toString().contains("Erro ao converter do seu peso/altura"));
            assertEquals(22.86, imc, 0.01);
        }
        @Test
        public void calculoIMC_Invalido_Limites() {
            assertThrows(IllegalArgumentException.class, () -> CalculoIMC.calcularIMC(0,0.5));
        }
    }
    @Nested
    public class TesteDeLimites{
        @Test
        public void calculoIMC_LimiteInferior() {
            double imc = CalculoIMC.calcularIMC(30,1);
            assertEquals(30,imc);
        }
        @Test
        public void calculoIMC_LimiteSuperior() {
            double imc = CalculoIMC.calcularIMC(300,2.5);
            assertEquals(48,imc);
        }
        @Test
        public void calculoIMC_PesoCriticoInferior() {
            assertThrows(IllegalArgumentException.class, () -> CalculoIMC.calcularIMC(0,1.72));
        }
        @Test
        public void calculoIMC_PesoCriticoSuperior() {
            assertThrows(IllegalArgumentException.class, () -> CalculoIMC.calcularIMC(500,1.72));
        }
        @Test
        public void calculoIMC_AlturaCriticaSuperior() {
            assertThrows(IllegalArgumentException.class, () -> CalculoIMC.calcularIMC(65,3.5));
        }
        @Test
        public void calculoIMC_AlturaCriticaInferior() {
            assertThrows(IllegalArgumentException.class, () -> CalculoIMC.calcularIMC(65,0.5));
        }
    }



}
