
% Cálculo de la probabilidad de morir de cada individuo
% ProbIndividuo es una matriz de probabilidades donde 1 es Mutar, 2 es
% Morir y 3 es Reproducirse
% Individuo es un array de mutaciones que representa el genoma de una
% especie
% Población es el total de individuos que hay en una especie
function [PD]=ProbDeath(Individuo,ProbIndividuo,EspPoblacion)
if(EspPoblacion>=200)
    PD=1;
else
    PD=0.5;
    for i=1:length(Individuo)
        PD=Individuo(i)*ProbIndividuo(i,2)+PD;
        % Individuo es un array de 1s y 0s, por lo que al multiplicarlo por
        % ProbIndividuo(i,X) se obtiene la probabilidad que aporta cada
        % mutación de mutar, morir o reproducirse. Al sumar estas
        % probabilidades se calcula la de cada individuo
    end
    PD=PD*(EspPoblacion/200); % Cálculo de muerte según la capacidad de la
% población, a mayor población menos "recursos" y más muerte natural
% Este cálculo fija el maximo de individuos de cada especie, sujeto a
% cambios
end
end