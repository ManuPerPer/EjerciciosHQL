package ejerciciosHQL;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.build.AllowSysOut;
import org.hibernate.query.Query;
import Tablas.*;
import utiles.SessionFactoryUtil;

public class Principal {

	public static void main(String[] args) {

		SessionFactory s = new SessionFactoryUtil().getSessionFactory();
		Session sesion = s.openSession();
		
		
		Ministerio min = new Ministerio();
		min = sesion.load(Ministerio.class, 1);

		// ejercicio1(sesion);
		// ejercicio2(sesion, 2);
		// ejercicio3A(sesion);
		// ejercicio3B(sesion, 10000.0,300000.0);
		// ejercicio4(sesion,"J", min);
		// ejercicio5(sesion);
		// ejercicio6(sesion);
		// ejercicio7(sesion);
		// ejercicio8(sesion);
		 ejercicio9(sesion);
	}
	
	/*
	 * 9. Crea un método que pida por consola los alias de miembros y los guarde en un ArrayList
	 * hasta que se introduzca FIN. Luego debe mostrar el nombre, apellido y nombre del
	 * ministerio a que pertenecen los miembros cuyo alias está en la lista que se ha guardado
	 */

	public static void ejercicio9(Session sesion) {
		Scanner scLine = new Scanner(System.in);
		String alias = "";
		ArrayList<String> aliasMiembros = new ArrayList<>();

		try {
			System.out.println();
			System.out.println("Introduce el alias:");

			do {
				alias = scLine.nextLine();
				if(!alias.equalsIgnoreCase("fin")) {
					aliasMiembros.add(alias);
				}

			} while (!alias.equalsIgnoreCase("fin"));
			
			
			System.out.println("Miembros cuyo alias estan en el ArrayList:\n");

			Query q = sesion.createQuery("from Miembro m where m.alias in :listaAlias");
			q.setParameterList("listaAlias", aliasMiembros);
			List<Miembro> lista = q.getResultList();

			System.out.println();

			for (Miembro m : lista) {

				System.out.println(m.getNombre() + " " + m.getApellido1() + " (" + m.getAlias() + ") es del "
						+ m.getMinisterio().getNombre());

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Algo me falla");
		}
	}
	/*
	 * 8. Crea un método que liste los ministerios y la cantidad de miembros que tiene cada uno
	 */

	public static void ejercicio8(Session sesion) {
		try {
			
			String cantidad;
			String nombreMin;
			
			Query q = sesion.createQuery("select count(m.codMiembro), m.ministerio.nombre from Miembro m group by m.ministerio");
			q.setReadOnly(true);
			List<Object[]> datos = q.getResultList();

			for (Object[] d : datos) {

				cantidad = d[0].toString();
				nombreMin = d[1].toString();
				
				if(Integer.valueOf(cantidad) < 2) {
					System.out.println("El Ministerio " + nombreMin + " tiene " + cantidad+ " miembro");
				} else {
					System.out.println("El Ministerio " + nombreMin + " tiene " + cantidad + " miembros");	
				}

			}
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("algo me falla");
		}
	}
	
	/*
	 * 7. Crea un método que muestre el nombre de los miembros del gobierno y el nombre del
	 * ministerio al que pertenecen.
	 */

	public static void ejercicio7(Session sesion) {
		try {

			Query q = sesion.createQuery("select m.nombre, m.ministerio.nombre from Miembro m ");
			q.setReadOnly(true);
			List<Object[]> datos = q.getResultList();

			for (Object[] d : datos) {

				String nombreMiembro = d[0].toString();
				String nombreMinisterio = d[1].toString();
				System.out.println(nombreMiembro + " pertenece al " + nombreMinisterio);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/*
	 * 6. Crea un método que muestre por pantalla el nombre de los ministerios cuyo gasto sea
	 * superior a la media.
	 */
	public static void ejercicio6(Session sesion) {
		try {

			Query q = sesion.createQuery("select avg(gastos) from Ministerio");
			q.setReadOnly(true);
			double media = (double) q.getSingleResult();
			media = Math.round(media);
			System.out.println("La media de los ministerios es: " + media + "\n");

			Query qu = sesion.createQuery("from Ministerio where gastos > :media");
			qu.setReadOnly(true);
			qu.setParameter("media", media);

			System.out.println("Ministerios que tienen gastos superiores a la media:\n");
			List<Ministerio> lista = qu.getResultList();

			for (Ministerio m : lista) {

				System.out.println(m.getNombre() + " tiene un gasto de: " + m.getGastos());
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error en el metodo");
		}
	}
	
	/*
	 * 5. Crea un método que muestre por pantalla el gasto medio de los ministerios
	 */

	public static void ejercicio5(Session sesion) {
		try {
			Query q = sesion.createQuery("select avg(gastos) from Ministerio");
			double media = (double) q.getSingleResult();

			media = Math.round(media);
			System.out.println("La media del gasto de los ministerios es: " + media);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error en la media");
		}
	}
	
	/*
	 * 4. Crea un método que muestre por pantalla el nombre y apellido de los miembros del
	 * gobierno cuyo nombre empieza por una cadena pasada como parámetro y pertenecen al
	 * ministerio pasado como parámetro, sabiendo que los datos no se van a manipular.
	 */

	public static void ejercicio4(Session sesion, String nombreD, Ministerio min) {
		try {

			

			Query q = sesion.createQuery("from Miembro where nombre Like :nom and ministerio = :minis");
			q.setReadOnly(true);
			q.setParameter("nom", nombreD + "%");
			q.setParameter("minis", min);
			List<Miembro> lista = q.getResultList();

			for (Miembro m : lista) {

				System.out.println(m.getNombre() + " " + m.getApellido1());

			}
		} catch (Exception e) {
			System.out.println("Error inesperado");
		}
	}
	/*
	 * 3b. Crea un método que muestre los datos de los ministerios que tienen un presupuesto
	 * entre dos cantidades que se pasarán como parámetro (Utiliza between)
	 */

	public static void ejercicio3B(Session sesion, double a, double b) {
		try {
			Query q = sesion.createQuery("from Ministerio where presupuesto between :p1 and :p2");
			q.setReadOnly(true);
			q.setParameter("p1", a);
			q.setParameter("p2", b);
			List<Ministerio> lista = q.getResultList();

			for (Ministerio m : lista) {

				System.out.println(m.getNombre() + " tiene un presupuesto de " + m.getPresupuesto()
						+ "€, y tiene unos gastos de " + m.getGastos() + "€");

			}
		} catch (Exception e) {
			System.out.println("error en la consulta");
		}
	}

	/*
	 * 3a. Crea un método que muestre por pantalla los datos de los dos ministerios que menos
	 * presupuesto tienen de los que tienen un presupuesto de más de 300000 €.
	 * 
	 */
	public static void ejercicio3A(Session sesion) {
		try {

			Query q = sesion.createQuery("from Ministerio where presupuesto >300000 order by presupuesto asc");
			q.setReadOnly(true);
			q.setMaxResults(2);
			List<Ministerio> lista = q.getResultList();

			for (Ministerio m : lista) {

				System.out.println(m.getNombre() + " tiene un presupuesto de " + m.getPresupuesto()
						+ "€, y tiene unos gastos de " + m.getGastos() + "€");

			}
		} catch (Exception e) {
			System.out.println("error en la consulta");
		}
	}
	
	/*
	 * 2. Crea un método que muestre por pantalla el ministerio que ocupa la posición x pasada
	 * como parámetro, sabiendo los datos no se van a manipular.
	 */

	public static void ejercicio2(Session sesion, int ministerio) {
		try {
			Query q = sesion.createQuery("from Ministerio");
			q.setFirstResult(ministerio).setMaxResults(1);
			Ministerio m = (Ministerio) q.getSingleResult();
			System.out.println(m.getNombre() + " tiene un presupuesto de " + m.getPresupuesto()
					+ "€, y tiene unos gastos de " + m.getGastos() + "€");

		} catch (Exception e) {
			System.out.println("ese ministerio no existe");
		}
	}

	
	
	/*
	 * 1. Crea un método que muestre por pantalla todos los ministerios, sabiendo que los datos
	 */
	public static void ejercicio1(Session sesion) {
		Query q = sesion.createQuery("from Ministerio");

		q.setReadOnly(true);
		List<Ministerio> lista = q.getResultList();

		for (Ministerio m : lista) {

			System.out.println(m.getNombre() + " tiene un presupuesto de " + m.getPresupuesto()
					+ "€, y tiene unos gastos de " + m.getGastos() + "€");

		}
	}

}
