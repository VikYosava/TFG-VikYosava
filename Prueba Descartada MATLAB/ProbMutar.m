
% Cálculo de la probabilidad de que el hijo de un individuo mute
% Parámetros explicados en ProbDeath
function [PMut]=ProbMutar(Individuo,ProbIndividuo)
PMut=0.5;
for i=1:length(Individuo)
    PMut=Individuo(i)*ProbIndividuo(i,1)+PMut;
end
end