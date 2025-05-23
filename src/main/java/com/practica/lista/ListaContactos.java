package com.practica.lista;

import com.practica.genericas.FechaHora;
import com.practica.genericas.PosicionPersona;

public class ListaContactos {
	private NodoTemporal lista;
	private int size;

	/**
	 * Insertamos en la lista de nodos temporales, y a la vez inserto en la lista de nodos de coordenadas.
	 * En la lista de coordenadas metemos el documento de la persona que está en esa coordenada
	 * en un instante
	 */
	public void insertarNodoTemporal (PosicionPersona p) {

		NodoTemporal aux = lista;
		boolean salir=false;
		boolean encontrado = false;
		NodoTemporal ant = null;


		while (condicionBuscarPosicion(aux, salir)) {
			if(aux.getFecha().compareTo(p.getFechaPosicion())==0)
			{
				salir=true;
				encontrado=true;
				comprobarCoordenadas(aux, p);

				/**
				 * Insertamos en la lista de coordenadas
				 */
			}

			else if(aux.getFecha().compareTo(p.getFechaPosicion())<0)
			{
				ant = aux;
				aux=aux.getSiguiente();

			}

			else if(aux.getFecha().compareTo(p.getFechaPosicion())>0)
			{
				salir=true;
			}

		}

		if(!encontrado)
		{
			NodoTemporal nuevo  = InsertarNuevoNodo(aux, ant, p);
			comprobarCoordenadas(nuevo, p);
		}

	}


	private NodoTemporal InsertarNuevoNodo(NodoTemporal aux, NodoTemporal ant, PosicionPersona p)
	{

		NodoTemporal nuevo = new NodoTemporal();
		nuevo.setFecha(p.getFechaPosicion());

		if(ant!=null)
		{
			nuevo.setSiguiente(aux);
			ant.setSiguiente(nuevo);
		}

		else
		{
			nuevo.setSiguiente(lista);
			lista = nuevo;
		}
		this.size++;
		return nuevo;
		}

	private boolean condicionBuscarPosicion(NodoTemporal aux, boolean salir)
	{
		return aux!=null && !salir;
	}

	private boolean condicionBuscarPersona(NodoPosicion npActual, boolean npEncontrado)
	{
		return npActual!=null && !npEncontrado;
	}

	private void comprobarCoordenadas(NodoTemporal aux, PosicionPersona p)
	{
		NodoPosicion npActual = aux.getListaCoordenadas();
		NodoPosicion npAnt= null;
		boolean npEncontrado = false;

		while (condicionBuscarPersona(npActual, npEncontrado))
		{
			if(npActual.getCoordenada().equals(p.getCoordenada()))
			{
				npEncontrado=true;
				npActual.setNumPersonas(npActual.getNumPersonas()+1);
			}
			else
			{
				npAnt = npActual;
				npActual = npActual.getSiguiente();
			}
		}

		if(!npEncontrado) crearNodoPosicion(aux, npAnt, p);
	}

	private void crearNodoPosicion(NodoTemporal aux, NodoPosicion npAnt, PosicionPersona p)
	{
		NodoPosicion npNuevo = new NodoPosicion(p.getCoordenada(),  1, null);
		if(aux.getListaCoordenadas()==null)
			aux.setListaCoordenadas(npNuevo);
		else
			npAnt.setSiguiente(npNuevo);
	}
	
	public int personasEnCoordenadas () {
		NodoPosicion aux = this.lista.getListaCoordenadas();
		if(aux==null)
			return 0;
		else {
			int cont;
			for(cont=0;aux!=null;) {
				cont += aux.getNumPersonas();
				aux=aux.getSiguiente();
			}
			return cont;
		}
	}
	
	public int tamanioLista () {
		return this.size;
	}

	public String getPrimerNodo() {
		NodoTemporal aux = lista;
		String cadena = aux.getFecha().getFecha().toString();
		cadena+= ";" +  aux.getFecha().getHora().toString();
		return cadena;
	}

	/**
	 * Métodos para comprobar que insertamos de manera correcta en las listas de 
	 * coordenadas, no tienen una utilidad en sí misma, más allá de comprobar que
	 * nuestra lista funciona de manera correcta.
	 */
	public int numPersonasEntreDosInstantes(FechaHora inicio, FechaHora fin) {
		if(this.size==0)
			return 0;
		NodoTemporal aux = lista;
		int cont = 0;
		cont = 0;
		while(aux!=null) {
			if(aux.getFecha().compareTo(inicio)>=0 && aux.getFecha().compareTo(fin)<=0) {
				NodoPosicion nodo = aux.getListaCoordenadas();
				while(nodo!=null) {
					cont = cont + nodo.getNumPersonas();
					nodo = nodo.getSiguiente();
				}				
				aux = aux.getSiguiente();
			}else {
				aux=aux.getSiguiente();
			}
		}
		return cont;
	}
	
	
	
	public int numNodosCoordenadaEntreDosInstantes(FechaHora inicio, FechaHora fin) {
		if(this.size==0)
			return 0;
		NodoTemporal aux = lista;
		int cont = 0;
		cont = 0;
		while(aux!=null) {
			if(aux.getFecha().compareTo(inicio)>=0 && aux.getFecha().compareTo(fin)<=0) {
				NodoPosicion nodo = aux.getListaCoordenadas();
				while(nodo!=null) {
					cont = cont + 1;
					nodo = nodo.getSiguiente();
				}				
				aux = aux.getSiguiente();
			}else {
				aux=aux.getSiguiente();
			}
		}
		return cont;
	}
	
	
	
	@Override
	public String toString() {
		String cadena="";
		int cont;
		cont=0;
		NodoTemporal aux = lista;
		for(cont=1; cont<size; cont++) {
			cadena += aux.getFecha().getFecha().toString();
			cadena += ";" +  aux.getFecha().getHora().toString() + " ";
			aux=aux.getSiguiente();
		}
		cadena += aux.getFecha().getFecha().toString();
		cadena += ";" +  aux.getFecha().getHora().toString();
		return cadena;
	}
	
	
	
}
