package ejercicios;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import Tablas.Miembro;
import Tablas.Ministerio;
import utiles.SessionFactoryUtil;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		SessionFactory s= new SessionFactoryUtil().getSessionFactory();
		Session sesion= s.openSession();
		
		
		
		
	//	ejercicio1(sesion);
	//	ejercicio2(sesion);
	//	ejercicio3(sesion);
	//	ejercicio4(sesion);	
	//	ejercicio5(sesion);
	//	ejercicio6(sesion,4);
	//	ejercicio7(sesion,3);		
	//	ejercicio8(sesion,3);
		
		ejercicio9(sesion, 1);
		
		
	}



	public static void ejercicio9(Session sesion, int codMinisterio) {
		
		Transaction t = (Transaction) sesion.beginTransaction();
		
		try {
			
			Ministerio m= new Ministerio();
			m= sesion.load(Ministerio.class, codMinisterio);
			double presupuesto= m.getPresupuesto();
			double nuevoPres=presupuesto*1.1;
			m.setPresupuesto(presupuesto);
			sesion.update(m);
			
			t.commit();
			System.out.println("El presupuesto del "+m.getNombre()+ "pasa de "+presupuesto+ " ha "+ Math.round(nuevoPres));
			
		} catch (Exception e) {
			
			if(t!=null) {
				t.rollback();
			}
			// TODO: handle exception
		}
		finally {
			sesion.close();
		}
	}



	public static void ejercicio8(Session sesion, int id) {
		int idMiembro=id;
		Transaction t = (Transaction) sesion.beginTransaction();
		
		try {
		Miembro m= new Miembro();
		m= sesion.load(Miembro.class, idMiembro);
		sesion.delete(m);
		t.commit();
		System.out.println("Se ha borrado el miembro "+idMiembro+" correctamente");
		} catch (Exception e) {
			if(t!=null) {
				t.rollback();
			}
			System.out.println("No se ha podido borrar el miembro "+idMiembro);
		}
		finally {
			sesion.close();
		}
	}



	public static void ejercicio7(Session sesion, int idMinisterio) {
		
		Ministerio d= new Ministerio();
		
		
		
		try {
			d=sesion.load(Ministerio.class, idMinisterio);
			
			System.out.println("El ministerio "+ d.getCodMinisterio()+" es el "+ d.getNombre()+" tiene un presupuesto de "+ d.getPresupuesto()+" y unos gastos de "+d.getPresupuesto());
			
		} catch (Exception e) {
			
			System.out.println("Ese ministerio no existe");
		}
		finally {
			sesion.close();
		}
	}



	public static void ejercicio6(Session sesion, int idMinisterio) {
		
		
		Ministerio d= new Ministerio();
		
		
		
		try {
			d=sesion.get(Ministerio.class, idMinisterio);
			
			System.out.println("El ministerio "+ d.getCodMinisterio()+" es el "+ d.getNombre()+" tiene un presupuesto de "+ d.getPresupuesto()+" y unos gastos de "+d.getPresupuesto());
			
		} catch (Exception e) {
			if(d!=null) {
			System.out.println("Ese ministerio no existe");
			}
		}
		finally {
			sesion.close();
		}
	}



	public static void ejercicio5(Session sesion) {
		Transaction t = (Transaction) sesion.beginTransaction();
		try {
			
		Ministerio ministerioNuevo= new Ministerio("Ministerio Secretarias", 180000, 100000);
		sesion.save(ministerioNuevo);
		
		Ministerio min=sesion.load(Ministerio.class, ministerioNuevo.getCodMinisterio());
		
		Miembro nuevo= new Miembro(min,"123324", "Diego"	,"Gonzalez", "El gallu");
		sesion.save(nuevo);
		System.out.println("Se ha insertado el miembro con ID="+nuevo.getCodMiembro()+" en el ministerio: "+min.getCodMinisterio());
		
		
		t.commit();
		} catch (Exception e) {
			if(t!=null) {
				t.rollback();
			}
		}
		finally {
			sesion.close();
		}
	}



	public static void ejercicio4(Session sesion) {
		Transaction t = (Transaction) sesion.beginTransaction();
		try {
			
			
			Ministerio d= new Ministerio();
			d= sesion.load(Ministerio.class, 4);
			Miembro nuevo= new Miembro(d, "44887", "Yisus", "Lobeiras", "La marida");
			
			sesion.save(nuevo);
			System.out.println("Miembro del Ministerio 4 dado de alta correctamente");
			t.commit();
			
			
		} catch (Exception e) {
			
			if(t!=null) {
				t.rollback();
			}
			
		}
		finally {
			sesion.close();
		}
	}



	public static void ejercicio3(Session sesion) {
		try {
			
			Ministerio min3= (Ministerio) sesion.load(Ministerio.class, 3);
			
			System.out.println("Nombre: "+min3.getNombre()+ ", CodMinisterio: "+min3.getCodMinisterio()+", presupuesto: "+min3.getPresupuesto()+", gastos: "+min3.getGastos());
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	


	public static void ejercicio2(Session sesion) {
		
		/*
		 * al no meterle apellido no crea al miembro correctamente ni el ministerio aunque la transaccion de el ok.
		 * indroduciendo el apellido, como se necesita en el constructor lo da por correcto
		 */
		Transaction t = (Transaction) sesion.beginTransaction();
		
		try {
			
		Ministerio min= new Ministerio();
		min.setNombre("Ministerio de los Palomares");
		min.setPresupuesto(200000);
		min.setGastos(100000);
		sesion.saveOrUpdate(min);
		
		Miembro miem= new Miembro();
		miem.setNif("554477");
		miem.setNombre("JoseMa");
		miem.setApellido1("Palomares");
		miem.setMinisterio(min);
		
		
		sesion.saveOrUpdate(miem);
		System.out.println("ministerio y miembro dados de alta correctamente");
		t.commit();
		
		} catch (Exception e) {
			// TODO: handle exception
			if(t!=null) {
				t.rollback();
				
			}
		}
		finally {
			sesion.close();
		}
	}

	public static void ejercicio1(Session sesion) {
		Transaction t = (Transaction) sesion.beginTransaction();
		
		try {
			Ministerio ministerioNuevo= new Ministerio("Ministerio de los Ministerios", 180000, 100000);
			sesion.save(ministerioNuevo);
			
			Ministerio min=sesion.load(Ministerio.class, ministerioNuevo.getCodMinisterio());
			
			Miembro nuevo= new Miembro(min,"123444", "Manolito"	,"PruebasNuevas", "Palormaitor");
			sesion.save(nuevo);
			System.out.println("ministerio y miembro dados de alta correctamente");
			
			
			t.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			
			if(t!=null) {
				t.rollback();
				
			}
		}
		finally {
			sesion.close();
		}
	}

}
