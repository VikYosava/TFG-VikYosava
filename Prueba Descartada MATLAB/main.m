Nmutaciones=5;
NEspecies=2^Nmutaciones;
% Cada individuo puede tener o no tener una mutación.
% Una especie es el número de individuos que comparten las mismas mutaciones.

Individuo=zeros(Nmutaciones,1);
IndBase=Individuo;
% Individuo inicial que no contiene ninguna mutación.

PoblacionEsp=zeros(1,NEspecies);
% Este array contendrá las cantidades de cada especie, empezando en 0.
TPoblacion=200;
PoblacionEsp(1)=TPoblacion;

PoblacionInd=zeros(Nmutaciones,NEspecies);
% Esta matriz contendrá a los distintos individuos en cada columna

ProbIndividuo=zeros(3,Nmutaciones);
% Matriz que contendrá las probabilidades de Mutar, Morir y Reproducirse
% que aporta cada mutación.

% El programa a continuación modela una generación de vida, es decir,
% calcula cuantos individuos mueren, nacen o mutan en una iteración
TPoblacion=PoblacionEsp(1);
for i=1:length(PoblacionEsp)
    TPoblacion=TPoblacion+PoblacionEsp(i);
end
% Calcula la población total


NGeneraciones=10;

[Generados,PoblacionInd]=Generacion(PoblacionEsp,PoblacionInd,ProbIndividuo,NGeneraciones,Individuo);

Generados
PoblacionInd 

figure;
hold on;
for i = 1:NEspecies
    plot(1:NGeneraciones, Generados(:, i));
end