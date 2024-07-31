function [Hijo]=Reproducir(Individuo,PMut)

% if(alimento>coste)
% POSIBLE FORMA DE PROGRAMARLO
Hijo = Individuo;

if (rand<PMut)
    locmutacion=randi([1,length(Individuo)]);
    %if(rand<0.9) % Número sujeto a cambios, depende de investigación
    Hijo(locmutacion)=1;
    %else
    %Hijo(locmutacion)=0;
%end
end
end