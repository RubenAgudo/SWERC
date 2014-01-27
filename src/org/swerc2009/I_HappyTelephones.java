package org.swerc2009;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class I_HappyTelephones {

	public static void main(String[] args) {
		
		try {
			Scanner sc = new Scanner(new File(args[0]));
			String[] linea;
			
			//primera prueba
			linea = sc.nextLine().split(" ");
			int N = Integer.parseInt(linea[0]);
			int M = Integer.parseInt(linea[1]);
			
			while(N!=0 && M!=0)
			{
				//estructura necesaria
				ArrayList<LinkedList<Llamada>> listaLamadas = new ArrayList<LinkedList<Llamada>>(1000);
				
				//inicializaar lists ligadas
				int k = 0;
				while(k < 1000)
				{
					listaLamadas.add(k, new LinkedList<Llamada>());
					k++;
				}
				int maxDuracion = 0;
				
				int i = 0;
				//Cargar los datos en la estructura
				while(i<N)
				{
					linea = sc.nextLine().split(" ");
					//crear la llamada
					Llamada l = new Llamada(Integer.parseInt(linea[0]),Integer.parseInt(linea[1]),Integer.parseInt(linea[3]));
					//insertar la llamada
					listaLamadas.get(Integer.parseInt(linea[2])).add(l);
					
					//llevar la duracion maxima
					if(l.duracion>maxDuracion)
					{
						maxDuracion = l.duracion;
					}
					
					i++;
				}
				
				//Atender las peticiones de los intervalos
				i=0;
				while(i<M)
				{
					linea = sc.nextLine().split(" ");
					//cogemos el intervalo
					int inicio = Integer.parseInt(linea[0]);
					int fin = Integer.parseInt(linea[1]);
					fin = fin + inicio;
					
					
					
					int num = 0; //num de llamadas en el intervalo
					//contamos las llamadas que pudieran haber empezado antes del intervalo
					int j = inicio-1;//recorremos desde antes del inicio del intervalo hasta maxDuracion
					int distAInicio = 2;
					Iterator<Llamada> it = null;
					while(j>(inicio-maxDuracion) && j>=0)
					{
						//recorrer la lista ligada del momento j
						it = listaLamadas.get(j).iterator();
						Llamada act;
						
						while(it.hasNext())
						{
							act = it.next();
							
							//si la llamada esta activa cuando empiece el intervalo
							if(act.duracion >= distAInicio)
							{
								num++;
							}
						}
						
						j--;
						distAInicio++;
					}
					//contamos las llamadas que empiezan en el intervalo
					j=inicio;
					while(j <= fin)
					{
						num = num + listaLamadas.get(j).size();
						j++;
					}
					//resultado
					System.out.println(num);
					i++;
				}
				
				
				//siguiente caso
				linea = sc.nextLine().split(" ");
				N = Integer.parseInt(linea[0]);
				M = Integer.parseInt(linea[1]);
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
