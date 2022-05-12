package com.example;

import com.CentroDistribuicao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CentroDistribuicaoTest {
    // Teste Construtor
    @Test
    @DisplayName("Teste Construtor - Valor de entrada valido")
    void testCentroDistribuicao() {
        CentroDistribuicao centroDistribuico = new CentroDistribuicao(
                200,
                500,
                100,
                100
        );
    }

    @Test
    @DisplayName("Teste Construtor - Valor de entrada inválido para o aditivo")
    void testCentroDistribuicao2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CentroDistribuicao centroDistribuico = new CentroDistribuicao(
                    -200,
                    500,
                    100,
                    100
            );
        });
    }

    @Test
    @DisplayName("Teste Construtor - Valor de entrada inválido para a gasolina")
    void testCentroDistribuicao3() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CentroDistribuicao centroDistribuico = new CentroDistribuicao(
                    200,
                    -500,
                    100,
                    100
            );
        });
    }

    @Test
    @DisplayName("Teste Construtor - Valor de entrada inválido para o alcool1")
    void testCentroDistribuicao4() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CentroDistribuicao centroDistribuico = new CentroDistribuicao(
                    200,
                    500,
                    -100,
                    100
            );
        });
    }

    @Test
    @DisplayName("Teste Construtor - Valor de entrada inválido para o alcool2")
    void testCentroDistribuicao5() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CentroDistribuicao centroDistribuico = new CentroDistribuicao(
                    200,
                    500,
                    100,
                    -100
            );
        });
    }

    @Test
    @DisplayName("Teste Construtor - Valor de entrada inválido alcool1 diferente de alcool2")
    void testCentroDistribuicao6() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CentroDistribuicao centroDistribuico = new CentroDistribuicao(
                    200,
                    500,
                    50,
                    100
            );
        });
    }

    // Teste defineSituacao
    @Test
    @DisplayName("Teste de situação - EMERGENCIA")
    void testSituacao1() {
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                200,
                500,
                100,
                100
        );
        centroDistribuicao.defineSituacao();
        Assertions.assertEquals(CentroDistribuicao.SITUACAO.EMERGENCIA, centroDistribuicao.getSituacao());
    }

    @Test
    @DisplayName("Teste de situação - NORMAL")
    void testSituacao2() {
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                500,
                5000,
                1250,
                1250
        );
        centroDistribuicao.defineSituacao();
        Assertions.assertEquals(CentroDistribuicao.SITUACAO.NORMAL, centroDistribuicao.getSituacao());
    }

    @Test
    @DisplayName("Teste de situação - SOBRAVISO")
    void testSituacao3() {
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                500,
                4999,
                1250,
                1250
        );
        centroDistribuicao.defineSituacao();
        Assertions.assertEquals(CentroDistribuicao.SITUACAO.SOBRAVISO, centroDistribuicao.getSituacao());
    }

    // Teste Baseado  em especificação
    @Test
    @DisplayName("Teste baseado em especificação  - Encomenda Tipo de posto: Estrategico - NORMAL - Entrega normal")
    void testEncomenda() {
        // 500,10000,1250,1250
        int aditivo = 500;
        int alcool1 = 1250;
        int alcool2 = 1250;
        int gasolina = 10000;
        int combustivel = 200;
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                aditivo,
                gasolina,
                alcool1,
                alcool2
        );
        aditivo = aditivo - (combustivel * 5 / 100);
        gasolina = gasolina - (combustivel * 70 / 100);
        alcool1 = alcool1 - (combustivel * 25 / 100) / 2;
        alcool2 = alcool2 - (combustivel * 25 / 100) / 2;

        centroDistribuicao.defineSituacao();
        Assertions.assertArrayEquals( new int[] {aditivo, gasolina, alcool1, alcool2}, centroDistribuicao.encomendaCombustivel(combustivel, CentroDistribuicao.TIPOPOSTO.COMUM));
    }

    @Test
    @DisplayName("Teste baseado em especificação - Encomenda - Tipo de posto: Estrategico - NORMAL  -  Entrega normal")
    void testEncomenda2() {
        // 500,10000,1250,1250
        int aditivo = 500;
        int alcool1 = 1250;
        int alcool2 = 1250;
        int gasolina = 10000;
        int combustivel = 100;
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                aditivo,
                gasolina,
                alcool1,
                alcool2
        );
        aditivo = aditivo - (combustivel * 5 / 100);
        gasolina = gasolina - (combustivel * 70 / 100);
        alcool1 = alcool1 - (combustivel * 25 / 100) / 2;
        alcool2 = alcool2 - (combustivel * 25 / 100) / 2;

        centroDistribuicao.defineSituacao();
        Assertions.assertArrayEquals( new int[] {aditivo, gasolina, alcool1, alcool2}, centroDistribuicao.encomendaCombustivel(combustivel, CentroDistribuicao.TIPOPOSTO.ESTRATEGICO));
    }

    @Test
    @DisplayName("Teste baseado em especificação - Encomenda - Tipo de posto: Comum - SOBRAVISO -  Entrega Metade")
    void testEncomenda3() {
        // 200,7000,750,750
        int aditivo = 200;
        int alcool1 = 750;
        int alcool2 = 750;
        int gasolina = 7000;
        int combustivel = 300;
        int combustivel2 = 150;
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                aditivo,
                gasolina,
                alcool1,
                alcool2
        );


        aditivo = aditivo - (combustivel2 * 5 / 100);
        gasolina = gasolina - (combustivel2 * 70 / 100);
        alcool1 = alcool1 - (combustivel2 * 25 / 100) / 2;
        alcool2 = alcool2 - (combustivel2 * 25 / 100) / 2;

        centroDistribuicao.defineSituacao();
        Assertions.assertArrayEquals( new int[] {aditivo, gasolina, alcool1, alcool2}, centroDistribuicao.encomendaCombustivel(combustivel, CentroDistribuicao.TIPOPOSTO.COMUM));
    }

    @Test
    @DisplayName("Teste baseado em especificação - Encomenda - Tipo de posto: Estrategico - SOBRAVISO -  Entrega Normal")
    void testEncomenda4() {
        // 200,7000,750,750
        int aditivo = 200;
        int alcool1 = 750;
        int alcool2 = 750;
        int gasolina = 7000;
        int combustivel = 300;
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                aditivo,
                gasolina,
                alcool1,
                alcool2
        );
        aditivo = aditivo - (combustivel * 5 / 100);
        gasolina = gasolina - (combustivel * 70 / 100);
        alcool1 = alcool1 - (combustivel * 25 / 100) / 2;
        alcool2 = alcool2 - (combustivel * 25 / 100) / 2;

        centroDistribuicao.defineSituacao();
        Assertions.assertArrayEquals( new int[] {aditivo, gasolina, alcool1, alcool2}, centroDistribuicao.encomendaCombustivel(combustivel, CentroDistribuicao.TIPOPOSTO.ESTRATEGICO));
    }

    @Test
    @DisplayName("Teste baseado em especificação - Encomenda - Tipo de posto: COMUM - EMERGENCIAL -  Não Entrega")
    void testEncomenda5() {
        // 500,2000,750,750
        int aditivo = 500;
        int alcool1 = 750;
        int alcool2 = 750;
        int gasolina = 2000;
        int combustivel = 300;
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                aditivo,
                gasolina,
                alcool1,
                alcool2
        );

        centroDistribuicao.defineSituacao();
        Assertions.assertArrayEquals( new int[] {-14, 0, 0, 0}, centroDistribuicao.encomendaCombustivel(combustivel, CentroDistribuicao.TIPOPOSTO.COMUM));
    }

    @Test
    @DisplayName("Teste baseado em especificação - Encomenda - Tipo de posto: ESTRATEGICO - EMERGENCIAL -  ENTREGA METADE")
    void testEncomenda6() {
        // 500,2000,750,750
        int aditivo = 500;
        int alcool1 = 750;
        int alcool2 = 750;
        int gasolina = 2000;
        int combustivel = 200;
        int combustivel2 = 100;
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                aditivo,
                gasolina,
                alcool1,
                alcool2
        );
        aditivo = aditivo - (combustivel2 * 5 / 100);
        gasolina = gasolina - (combustivel2 * 70 / 100);
        alcool1 = alcool1 - (combustivel2 * 25 / 100) / 2;
        alcool2 = alcool2 - (combustivel2 * 25 / 100) / 2;

        centroDistribuicao.defineSituacao();
        Assertions.assertArrayEquals( new int[] {aditivo, gasolina, alcool1, alcool2}, centroDistribuicao.encomendaCombustivel(combustivel, CentroDistribuicao.TIPOPOSTO.ESTRATEGICO));
    }

    // Teste baseado em Valor Limite
    @Test
    @DisplayName("Teste baseado em valor limite 50% - Encomenda - Tipo de posto: COMUM - NORMAL -  ENTREGA")
    void testEncomenda7() {
        // 250,5000,625,625
        int aditivo = 250;
        int alcool1 = 625;
        int alcool2 = 625;
        int gasolina = 5000;
        int combustivel = 300;
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                aditivo,
                gasolina,
                alcool1,
                alcool2
        );
        aditivo = aditivo - (combustivel * 5 / 100);
        gasolina = gasolina - (combustivel * 70 / 100);
        alcool1 = alcool1 - (combustivel * 25 / 100) / 2;
        alcool2 = alcool2 - (combustivel * 25 / 100) / 2;

        centroDistribuicao.defineSituacao();
        Assertions.assertArrayEquals( new int[] {aditivo, gasolina, alcool1, alcool2}, centroDistribuicao.encomendaCombustivel(combustivel, CentroDistribuicao.TIPOPOSTO.COMUM));
    }

    @Test
    @DisplayName("Teste baseado em valor limite 50% - Encomenda - Tipo de posto: ESTRATEGICO - NORMAL -  ENTREGA")
    void testEncomenda8() {
        // 250,5000,625,625
        int aditivo = 250;
        int alcool1 = 625;
        int alcool2 = 625;
        int gasolina = 5000;
        int combustivel = 100;
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                aditivo,
                gasolina,
                alcool1,
                alcool2
        );
        aditivo = aditivo - (combustivel * 5 / 100);
        gasolina = gasolina - (combustivel * 70 / 100);
        alcool1 = alcool1 - (combustivel * 25 / 100) / 2;
        alcool2 = alcool2 - (combustivel * 25 / 100) / 2;

        centroDistribuicao.defineSituacao();
        Assertions.assertArrayEquals( new int[] {aditivo, gasolina, alcool1, alcool2}, centroDistribuicao.encomendaCombustivel(combustivel, CentroDistribuicao.TIPOPOSTO.ESTRATEGICO));
    }

    @Test
    @DisplayName("Teste baseado em valor limite <50% && >= 25%  - Encomenda - Tipo de posto: COMUM - SOBRAVISO -  ENTREGA METADE")
    void testEncomenda9() {
        // 250,5000,625,625
        int aditivo = 249;
        int alcool1 = 624;
        int alcool2 = 624;
        int gasolina = 4999;
        int combustivel = 250;
        int combustivel2 = 125;
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                aditivo,
                gasolina,
                alcool1,
                alcool2
        );

        aditivo = aditivo - (combustivel2 * 5 / 100);
        gasolina = gasolina - (combustivel2 * 70 / 100);
        alcool1 = alcool1 - (combustivel2 * 25 / 100) / 2;
        alcool2 = alcool2 - (combustivel2 * 25 / 100) / 2;

        centroDistribuicao.defineSituacao();
        Assertions.assertArrayEquals( new int[] {aditivo, gasolina, alcool1, alcool2}, centroDistribuicao.encomendaCombustivel(combustivel, CentroDistribuicao.TIPOPOSTO.COMUM));
    }

    @Test
    @DisplayName("Teste baseado em valor limite <50% && >= 25% - Encomenda - Tipo de posto: ESTRATEGICO - SOBRAVISO -  ENTREGA")
    void testEncomenda10() {
        // 125,2500,375,375
        int aditivo = 125;
        int alcool1 = 375;
        int alcool2 = 375;
        int gasolina = 2500;
        int combustivel = 200;
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                aditivo,
                gasolina,
                alcool1,
                alcool2
        );
        aditivo = aditivo - (combustivel * 5 / 100);
        gasolina = gasolina - (combustivel * 70 / 100);
        alcool1 = alcool1 - (combustivel * 25 / 100) / 2;
        alcool2 = alcool2 - (combustivel * 25 / 100) / 2;

        centroDistribuicao.defineSituacao();
        Assertions.assertArrayEquals( new int[] {aditivo, gasolina, alcool1, alcool2}, centroDistribuicao.encomendaCombustivel(combustivel, CentroDistribuicao.TIPOPOSTO.ESTRATEGICO));
    }


    @Test
    @DisplayName("Teste baseado em valor limite < 25%  - Encomenda - Tipo de posto: COMUM - SOBRAVISO -  ENTREGA METADE")
    void testEncomenda11() {
        // 125,2500,375,375
        int aditivo = 124;
        int alcool1 = 374;
        int alcool2 = 374;
        int gasolina = 2499;
        int combustivel = 100;
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                aditivo,
                gasolina,
                alcool1,
                alcool2
        );

        centroDistribuicao.defineSituacao();
        Assertions.assertArrayEquals( new int[] {-14, 0, 0, 0}, centroDistribuicao.encomendaCombustivel(combustivel, CentroDistribuicao.TIPOPOSTO.COMUM));
    }

    @Test
    @DisplayName("Teste baseado em valor limite <50 && >= 25 - Encomenda - Tipo de posto: ESTRATEGICO - SOBRAVISO -  ENTREGA")
    void testEncomenda12() {
        // 125,2500,375,375
        int aditivo = 125;
        int alcool1 = 375;
        int alcool2 = 375;
        int gasolina = 2500;
        int combustivel = 150;
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                aditivo,
                gasolina,
                alcool1,
                alcool2
        );
        aditivo = aditivo - (combustivel * 5 / 100);
        gasolina = gasolina - (combustivel * 70 / 100);
        alcool1 = alcool1 - (combustivel * 25 / 100) / 2;
        alcool2 = alcool2 - (combustivel * 25 / 100) / 2;

        centroDistribuicao.defineSituacao();
        Assertions.assertArrayEquals( new int[] {aditivo, gasolina, alcool1, alcool2}, centroDistribuicao.encomendaCombustivel(combustivel, CentroDistribuicao.TIPOPOSTO.ESTRATEGICO));
    }

    // Testes para recebimento de combustível
    @Test
    @DisplayName("Teste recebimento de gasolina, sem espaço no tanque")
    void recebeGasolinaTest1() {
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                125,
                10000,
                375,
                375
        );

        Assertions.assertEquals(0, centroDistribuicao.recebeGasolina(100));
    }

    @Test
    @DisplayName("Teste recebimento de aditivo, sem espaço no tanque")
    void recebeDieselTest1() {
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                500,
                10000,
                375,
                375
        );

        Assertions.assertEquals(0, centroDistribuicao.recebeAditivo(100));
    }

    @Test
    @DisplayName("Teste recebimento de alcool, sem espaço no tanque")
    void recebeAlcoolTest1() {
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                500,
                10000,
                1250,
                1250
        );

        Assertions.assertEquals(0, centroDistribuicao.recebeAlcool(100));
    }



    // Testes Parametrizados
    @ParameterizedTest
    @ValueSource(ints = { 100, 200, 300, -1 })
    @DisplayName("Teste Parametrizado gasolina")
    public void recebeGasolinaTest(int argument) {
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                100,
                100,
                100,
                100
        );

        int gasolina = centroDistribuicao.getGasolina();
        int gasolinaTotal = centroDistribuicao.getGasolina() + argument;
        int expected = -1;

        if (argument > 0) {
            expected = argument;
        }

        if (gasolinaTotal > CentroDistribuicao.MAX_ALCOOL) {
            expected = (CentroDistribuicao.MAX_ALCOOL + argument) - gasolina;
        }

        Assertions.assertEquals(centroDistribuicao.recebeGasolina(argument), expected);
    }

    @ParameterizedTest
    @ValueSource(ints = { 100, 200, 300, -1 })
    @DisplayName("Teste Parametrizado aditivo")
    public void recebeAditivoTest(int argument) {
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                100,
                100,
                100,
                100
        );

        int aditivo = centroDistribuicao.getAditivo();
        int aditivoTotal = centroDistribuicao.getAditivo() + argument;
        int expected = -1;

        if (argument > 0) {
            expected = argument;
        }

        if (aditivoTotal > CentroDistribuicao.MAX_ALCOOL) {
            expected = (CentroDistribuicao.MAX_ALCOOL + argument) - aditivo;
        }

        Assertions.assertEquals(centroDistribuicao.recebeAditivo(argument), expected);
    }

    @ParameterizedTest
    @ValueSource(ints = { 100, 200, 300, -1 })
    @DisplayName("Teste Parametrizado Alcool")
    public void recebeAlcoolTest(int argument) {
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(
                100,
                100,
                100,
                100
        );

        int alcool = centroDistribuicao.getAlcool1() + centroDistribuicao.getAlcool2();
        int alcoolTotal = centroDistribuicao.getAlcool1() + centroDistribuicao.getAlcool2() + argument;
        int expected = -1;

        if (argument > 0) {
            expected = argument;
        }

        if (alcoolTotal > CentroDistribuicao.MAX_ALCOOL) {
            expected = (CentroDistribuicao.MAX_ALCOOL + argument) - alcool;
        }

        Assertions.assertEquals(centroDistribuicao.recebeAlcool(argument), expected);
    }








}
