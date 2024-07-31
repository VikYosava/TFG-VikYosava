% Cálculo de la probabilidad de que un individuo se reproduzca
% Parámetros explicados en ProbDeath
function [PR]=ProbReprod(Individuo,ProbIndividuo)
PR=Individuo*ProbIndividuo(:,3)+0.5;
for i=1:length(Individuo)
    PR=Individuo(i)*ProbIndividuo(i,3)+PR;
end
end