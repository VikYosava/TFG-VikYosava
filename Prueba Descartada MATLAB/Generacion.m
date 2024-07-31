% Cálculo de la probabilidad de que el hijo de un individuo mute

function [Generados,PoblacionInd]=Generacion(PoblacionEsp,PoblacionInd,ProbIndividuo,NGeneraciones,Individuo)

Generados=zeros(NGeneraciones,length(PoblacionEsp));
%Generados=ones(NGeneraciones,1)*PoblacionEsp;
IndBase=Individuo;
PoblacionEsp2=PoblacionEsp;
Generados(1,:)=PoblacionEsp2;

for i=2:NGeneraciones
    
    PoblacionEsp=PoblacionEsp2;

    for k=1:length(PoblacionEsp)
        for j=1:PoblacionEsp(k) %Multiplicar por probabilidad de rep
            PMut=ProbIndividuo(1,:)*Individuo+0.5;
            Hijo=Reproducir(PoblacionInd(:,k),PMut);
            
            Encontrado=false;

            if isequal(Hijo,PoblacionInd(:,k))

                PoblacionEsp2(k)=PoblacionEsp2(k)+1;
                Encontrado=true;

            end
            
            cont=2;
            % POSIBLEMENTE ELIMINAR ESTE WHILE
            while (~Encontrado)&&(~isequal(PoblacionInd(:,cont),IndBase))
                
                if isequal(Hijo,PoblacionInd(:,cont))
                    Encontrado=true;
                    PoblacionEsp2(cont)=PoblacionEsp2(cont)+1;
                end
                cont=cont+1;
                %Hijo';

            end
            if(~Encontrado)
                if isequal(PoblacionInd(:,cont),IndBase)
                    PoblacionInd(:,cont)=Hijo;
                    PoblacionEsp2(cont)=PoblacionEsp2(cont)+1;
                else
                    Error=404
                end
            end

            Generados(i,:)=PoblacionEsp2;

        end
    end
    
    

%{
for i=1:NGeneraciones
    
    % Cálculo de cada iteración
    
    for l=1:length(PoblacionEsp) % Convertir en un While que compruebe que las generaciones no estén vacías (optimizar)

        % Individuo.vida
        % Aquí se calculará cómo las mutaciones afectan a la vida de cada
        % individuo

        Individuo=PoblacionInd(:,l);
        PR=ProbIndividuo(3,:)*Individuo+0.5;
        %Generados(i,l)

        for k=1:Generados(i,l)*PR
            
            PMut=ProbIndividuo(1,:)*Individuo+0.5;
            %PMut
            Hijo=Reproducir(Individuo,PMut);
            % Genera un hijo nuevo que puede mutar o no
            
            Bool=false;
            %Hijo
            %PoblacionInd(:,l)
            if isequal(Hijo,PoblacionInd(:,l))

                % Comprobamos si el hijo no ha mutado, reduciendo el coste
                % de computación ya que un gran porcentaje no mutará
                %Hijo
                %Prev=Generados(i-1,l)
                %New=Generados(i,l)
                Generados(i,l)=Generados(i,l)+1;
                %Generados(i,l)

                Bool=true;
            end

            cont=2;
            % Bool = false significaría que el hijo nuevo es distinto
            % cont debe ser menor que el tamaño posible de la población
            % Generados no debe estar vacío, es decir, debe haber algun ser
            % vivo con el que comparar

            %Generados(i,cont)
            %PreBusc=PoblacionInd(:,l)'
            
            while(~Bool)&&(~isequal(PoblacionInd(:,l),IndBase))&&(cont<length(PoblacionInd(1,:)))
                % Aquí buscamos a qué especie nueva pertenece el individuo
                % que ha mutado, suponemos que no se puede retroceder a la
                % especie base. Como el array de PoblacionInd que guarda
                % las distintas especies en estado vacío está lleno de
                % especies base, si el while encuentra una especie base en
                % este array, supondrá que el hijo es de una especie no
                % registrada
                
                %Hijo
                %PoblacionInd(:,cont)
                if(Hijo==PoblacionInd(:,cont))
                    Generados(i,cont)=Generados(i,cont)+1;
                    Bool=true;
                    %Generados(i,cont)
                end
                cont=cont+1;
                %k
            end
            %Generados(i,cont) NUNCA DEBE SER =~0 AQUÍ
            if(~Bool)
                Generados(i,cont)=1; % Comprobar tamaños
                %PostBusc=PoblacionInd(:,l)'
                
                PoblacionInd(:,l)=Hijo;
                % Bool=true;
                % En caso de que el while no encuentre la especie a la que
                % pertenece el hijo, significará que es una especie nueva
                % (nueva combinación de mutaciones), por lo que se
                % almacenará su cantidad (1) en PoblacionEsp, y su código
                % de mutaciones (Hijo) en PoblacionInd
            end
            %Generados(i,:)=PoblacionEsp2;
        end
    end
end 
%}

end